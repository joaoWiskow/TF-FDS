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
    private ProdutosDisponiveisUC produtosDisponiveis;
    private CriaOrcamentoUC criaOrcamento;
    private EfetivaOrcamentoUC efetivaOrcamento;
    private BuscaOrcamentoUC buscaOrcamento;

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
}