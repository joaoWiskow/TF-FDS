package com.bcopstein.sistvendas.persistencia;

import com.bcopstein.sistvendas.dominio.modelos.ItemPedidoModel;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido {
    @Id
    @GeneratedValue
    public long id;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Produto produto;
    private int quantidade;
    
    public ItemPedido(long id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }
    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    protected ItemPedido() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id;}
    
    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItemPedido [produto=" + produto + ", quantidade=" + quantidade + "]";
    }


    public static ItemPedido fromItemPedidoModel(ItemPedidoModel ipModel){
        Produto prod = Produto.fromProdutoModel(ipModel.getProduto());
        return new ItemPedido(ipModel.getId(),prod,ipModel.getQuantidade());
    }

    public static ItemPedidoModel toItemPedidoModel(ItemPedido ip){
        ProdutoModel prod = Produto.toProdutoModel(ip.getProduto());
        return new ItemPedidoModel(ip.getId(), prod, ip.getQuantidade());
    }
}