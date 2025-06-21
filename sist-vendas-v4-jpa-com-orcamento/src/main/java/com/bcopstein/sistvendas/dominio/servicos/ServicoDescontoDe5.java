public class ServicoDescontoDe5 implements IServicoDesconto{
    double valor;
    Orcamento orcamento;
    List<Itens> orcDesc5;

    public desconto(double valor, Orcamento orcamento)
    {
        this.valor = valor;
        this.orcamento = orcamento;
        this.orcDesc5.getItens();
    }

    public double calculaDesconto()
    {
        
        for(Item i : orcDesc5)
        {
            if( i.getQuantidade > 3 )
            {
                this.valor += i.getProduto().getPrecoUnitario() * i.getQuantidade() * 0.05;
            }
        }
    }
    
    public double getDescontoDe5()
    {
        return valor;
    }

}