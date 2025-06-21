public class ServicoImpostoRS implements IServicoImposto{
    double valor;
    Orcamento orcamento;
    public ServicoImpostoFederal(double valor, Orcamento orcamento)
    {
        this.valor = valor;
        this.orcamento = orcamento;
    }

    public double calculaImpostoFederal(double valor)
    {
        this.valor = valor * 0.15;
    }

    public double getImpostoFederal()
    {
        return valor;
    };

}