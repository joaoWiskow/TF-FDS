import com.bcopstein.sistvendas.persistencia.Orcamento;

public class ServicoImpostoPE implements IServicoImposto{
    double valor;
    Orcamento orcamento;
    List<Itens> orcPE;

    public ServicoImpostoPE(double valor, Orcamento orcamento)
    {
        this.valor = valor;
        this.orcamento = orcamento;
        orcPE = orcamento.getItens();
    }

    public calculaImpostoPE()
    {
        for(Item i : orcPE)
        {
            if( i.getProduto().getDescricao().contains("*") )
            {
                this.valor += i.getProduto().getPrecoUnitario() * i.getQuantidade() * 0.05;
            }
            else{
                this.valor += i.getProduto().getPrecoUnitario() * i.getQuantidade() * 0.15;
            }
        }
    }

    public double getImpostoPE()
    {
        return valor;
    };

}