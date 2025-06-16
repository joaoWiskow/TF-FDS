package com.bcopstein.sistvendas.persistencia;

import java.util.ArrayList;
import java.util.List;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
@Entity
public class Orcamento {
    @Id 
    @GeneratedValue
    private long id;
    
    @OneToMany(cascade=CascadeType.PERSIST)
    private List<ItemPedido> itens = new ArrayList<>();
    private double custoItens;
    private double imposto;
    private double desconto;
    private double custoConsumidor;
    private boolean efetivado;

    public Orcamento(){
    }

    public void addItensPedido(ItemPedido item){
        itens.add(item);
    }

    public List<ItemPedido> getItens(){
        return itens;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public double getCustoItens() {
        return custoItens;
    }

    public void setCustoItens(double custoItens){
        this.custoItens = custoItens;
    }

    public double getImposto() {
        return imposto;
    }

    public void setImposto(double imposto){
        this.imposto = imposto;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto){
        this.desconto = desconto;
    }

    public double getCustoConsumidor() {
        return custoConsumidor;
    }

    public void setCustoConsumidor(double custoConsumidor){
        this.custoConsumidor = custoConsumidor;
    }

    public boolean isEfetivado() {
        return efetivado;
    }

    public void efetiva(){
        efetivado = true;
    }

    public static Orcamento fromOrcamentoModel(OrcamentoModel oModel){
        Orcamento orc = new Orcamento();
        orc.setId(oModel.getId());
        orc.setCustoConsumidor(oModel.getCustoConsumidor());
        orc.setCustoItens(oModel.getCustoItens());
        orc.setImposto(oModel.getImposto());
        orc.setDesconto(oModel.getDesconto());
        if (oModel.isEfetivado()) orc.efetiva();
        for(ItemPedidoModel itm : oModel.getItens()){
            orc.addItensPedido(ItemPedido.fromItemPedidoModel(itm));        
        }
        return orc;
    }

    public static OrcamentoModel toOrcamentoModel(Orcamento orc){
        OrcamentoModel oModel = new OrcamentoModel();
        oModel.setId(orc.getId());
        oModel.setCustoConsumidor(orc.getCustoConsumidor());
        oModel.setCustoItens(orc.getCustoItens());
        oModel.setImposto(orc.getImposto());
        oModel.setDesconto(orc.getDesconto());
        if (orc.isEfetivado()) oModel.efetiva();
        for(ItemPedido it : orc.getItens())
        oModel.addItensPedido(ItemPedido.toItemPedidoModel(it));  
        return oModel;
    }
}
