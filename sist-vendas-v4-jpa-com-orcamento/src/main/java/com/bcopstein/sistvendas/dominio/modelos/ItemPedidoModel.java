package com.bcopstein.sistvendas.dominio.modelos;

public class ItemPedidoModel {
    private long id;
    private ProdutoModel produto;
    private int quantidade;
    
    public ItemPedidoModel(long id, ProdutoModel produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ItemPedidoModel(ProdutoModel produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id;}

    public ProdutoModel getProduto() {
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
}
