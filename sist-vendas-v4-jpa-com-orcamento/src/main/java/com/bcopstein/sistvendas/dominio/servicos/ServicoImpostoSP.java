public class ServicoImpostoRS implements IServicoImposto{
    double valor;
    Orcamento orcamento;
    public ServicoImpostoSP(double valor, Orcamento orcamento)
    {
        this.valor = valor;
        this.orcamento = orcamento;
    }

    public calculaImpostoSP()
    {
        
        this.valor = valor * 0.12;
        
    }
    
    public double getImpostoSP()
    {
        return valor;
    }

}