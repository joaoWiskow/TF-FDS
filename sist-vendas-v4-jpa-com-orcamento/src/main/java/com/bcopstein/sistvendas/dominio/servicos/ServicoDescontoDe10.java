import com.bcopstein.sistvendas.persistencia.Orcamento;

public class ServicoDescontoDe10 implements IServicoDesconto{
    double valor;
    Orcamento orcamento;
    List<Itens> orcDesc10;

    public desconto(Orcamento orcamento)
    {
        valor = 0;
        this.orcamento = orcamento;
        this.orcDesc10.getItens();
    }

    public double calculaDescontoDe10()
    {
        if( orcDesc10.length() > 10 )
        {
            this.valor = orcamento.getCustoItens() * 0.1;
        }
    }
    public double getDescontoDe10()
    {
        return valor;
    }

}