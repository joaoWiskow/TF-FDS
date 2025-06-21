public class ServicoImpostoRS implements IServicoImposto{
    double valor;
    Orcamento orcamento;
    public ServicoImpostoRS(double valor, Orcamento orcamento)
    {
        this.valor = valor;
        this.orcamento = orcamento;
    }

    public double calculaImpostoRS()
    {
        double tmp = orcamento.getCustoItens();
        if(tmp > 100.0)
        {
            this.valor = (custoItens - 100) * 0.1 ;            
        }
    }

    public double getImpostoRS()
    {
        return valor;
    };

}