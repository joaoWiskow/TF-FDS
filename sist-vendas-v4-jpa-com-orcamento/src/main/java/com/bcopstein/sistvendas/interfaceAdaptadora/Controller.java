package com.bcopstein.sistvendas.interfaceAdaptadora;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.sistvendas.aplicacao.casosDeUso.BuscaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.CriaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.EfetivaOrcamentoUC;
import com.bcopstein.sistvendas.aplicacao.casosDeUso.ProdutosDisponiveisUC;
import com.bcopstein.sistvendas.aplicacao.dtos.ItemPedidoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.aplicacao.dtos.ProdutoDTO;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDescontoDe10;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDescontoDe5;


@RestController
public class Controller {
    @Autowired private ProdutosDisponiveisUC produtosDisponiveis;
    @Autowired private CriaOrcamentoUC criaOrcamento;
    @Autowired private EfetivaOrcamentoUC efetivaOrcamento;
    @Autowired private BuscaOrcamentoUC buscaOrcamento;
    @Autowired private CatalogoProdutosUC catalogoProdutos;
    @Autowired private ServicoDeEstoque servicoDeEstoque;

    @Autowired
    public Controller(ProdutosDisponiveisUC produtosDisponiveis,
                      CriaOrcamentoUC criaOrcamento,
                      EfetivaOrcamentoUC efetivaOrcamento,
                      BuscaOrcamentoUC buscaOrcamento){
        this.produtosDisponiveis = produtosDisponiveis;
        this.criaOrcamento = criaOrcamento;
        this.efetivaOrcamento = efetivaOrcamento;
        this.buscaOrcamento = buscaOrcamento;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*")
    public String welcomeMessage(){
        return("Bem vindo as lojas ACME");
    }

    @GetMapping("produtosDisponiveis")
    @CrossOrigin(origins = "*")
    public List<ProdutoDTO> produtosDisponiveis(){
        return produtosDisponiveis.run();
    }    

    @PostMapping("novoOrcamento")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO novoOrcamento(@RequestBody List<ItemPedidoDTO> itens){
        return criaOrcamento.run(itens);
    }

    @GetMapping("efetivaOrcamento/{id}")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO efetivaOrcamento(@PathVariable(value="id") long idOrcamento){
        
        String estado = "";
        String pais = "";
        int erro = 0;  
        if( (dateAtual - dataOrcamento) > 21 )
        {
            switch ( estado )
            {
                case RS:
                ServicoImpostoRS impostoRS = new ServicoImpostoRS(orcamento.getCustoItens() , orcamento);
                impostoRS.calculaImpostoRS();
                orcamento.setImposto( impostoRS.getImpostoRS() );
                break;

                case SP:
                ServicoImpostoSP impostoSP = new ServicoImpostoSP(orcamento.getCustoItens() , orcamento);
                impostoSP.calculaImpostoSP();
                orcamento.setImposto( impostoSP.getImpostoSP() );
                break;

                case PE:
                ServicoImpostoPE impostoPE = new ServicoImpostoPE(orcamento.getCustoItens() , orcamento);
                impostoPE.calculaImpostoPE();
                orcamento.setImposto( impostoPE.getImpostoPE() );
                break;

                default:
                System.out.println("Erro");
                erro++;
                break;
            }
            switch ( pais )
            {
                case BR:
                ServicoImpostoFederal impostoBR = new ServicoImpostoFederal(orcamento.getCustoItens(), orcamento);
                impostoBR.calculaImpostoFederal();
                double impTemp = orcamento.getImposto();
                impTemp = impTemp + impostoBR.getImpostoFederal();
                orcamento.setImposto( impTemp );

                default:
                System.out.println("Erro");
                erro++;
                break;
            }
            if(erro = 0)
            {
                ServicoDescontoDe5 desc5 = new ServicoDescontoDe5( orcamento );
                desc5.calculaDescontoDe5();
                orcamento.setDesconto(desc5.getDescontoDe5());

                double tempDesc10 = 0;
                ServicoDescontoDe10 desc10 = new ServicoDescontoDe10( orcamento );
                tempDesc10 = desc10.calculaDescontoDe10() + orcamento.getDesconto();
                orcamento.setDesconto( tempDesc10 );

                double tempCustoFinal = orcamento.getCustoItens() + orcamento.getImposto() - orcamento.getDesconto();
                orcamento.setCustoConsumidor( tempCustoFinal );
            }
               
        }       

        return efetivaOrcamento.run(idOrcamento);
    }

    @GetMapping("buscaOrcamento/{id}")
    @CrossOrigin(origins = "*")
    public OrcamentoModel buscaOrcamento(@PathVariable(value="id") long idOrcamento){
        return buscaOrcamento.run(idOrcamento);
    }

    @PostMapping("entradaEstoque")
    public void entradaEstoque(@RequestBody ItemPedidoDTO entrada) {
    servicoDeEstoque.entradaEstoque(
        entrada.getProduto().getId(),
        entrada.getQuantidade()
    );
    }

    @PostMapping("consultaEstoque")
    public Map<Long, Integer> consultaEstoque(@RequestBody List<Long> codigos) {
        return servicoDeEstoque.quantidadeParaLista(codigos);
    }

    @GetMapping("orcamentosEfetivados")
    public List<OrcamentoDTO> orcamentosEfetivados(
    @RequestParam("inicio") String inicio,
    @RequestParam("fim") String fim
    ){
    LocalDate dataIni = LocalDate.parse(inicio);
    LocalDate dataFim = LocalDate.parse(fim);
    return buscaOrcamento.orcamentosNoPeriodo(dataIni, dataFim);
}

//Relatorio relacionando efetivados aos disponiveis ,custo cons e total vend
@GetMapping("relatorio")
@CrossOrigin(origins = "*")
public String relatorioGeral() {
    StringBuilder relatorio = new StringBuilder();
    List<ProdutoDTO> produtos = produtosDisponiveis.run();
    relatorio.append("Produtos disponíveis: ").append(produtos.size()).append("\n");

    LocalDate hoje = LocalDate.now();
    LocalDate inicio = hoje.minusDays(30);
    List<OrcamentoDTO> orcamentos = buscaOrcamento.orcamentosNoPeriodo(inicio, hoje);

    int efetivados = 0;
    int totalItensVendidos = 0;
    double valorTotalEfetivados = 0.0;

    for (OrcamentoDTO o : orcamentos) {
        if (o.isEfetivado()) {
            efetivados++;
            valorTotalEfetivados += o.getCustoConsumidor();
            for (ItemPedidoDTO item : o.getItens()) {
                totalItensVendidos += item.getQuantidade();
            }
        }
    }
    relatorio.append("Orçamentos efetivados nos últimos 30 dias: ").append(efetivados).append("\n");
    relatorio.append("Total de itens vendidos em 30 dias: ").append(totalItensVendidos).append("\n");
    relatorio.append("Valor total dos orçamentos efetivados em 30 dias: R$ ")
             .append(String.format("%.2f", valorTotalEfetivados)).append("\n");

    return relatorio.toString();
}
}