package com.bcopstein.sistvendas.persistencia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;

@Repository
@Primary
public class EstoqueRepJPA implements IEstoqueRepositorio{
    @Autowired
    private EstoqueJPA_ItfRep estoque;

    @Override
    public List<ProdutoModel> todos() {
        List<ItemDeEstoque> itens = estoque.findAll();
        return itens.stream()
                .map(it->Produto.toProdutoModel(it.getProduto()))
                .toList();
    }

    @Override
    public List<ProdutoModel> todosComEstoque() {
        List<ItemDeEstoque> itens = estoque.findAll();
        return itens.stream()
                .filter(it->it.getQuantidade()>0)
                .map(it->Produto.toProdutoModel(it.getProduto()))
                .toList();
    }

    @Override
    public int quantidadeEmEstoque(long codigo) {
        ItemDeEstoque item = this.findByProdId(codigo);
        System.out.println("item: "+item);
        if (item == null){
            return -1;
        }else{
            return item.getQuantidade();
        }
            
    }

    @Override
    public int baixaEstoque(long codProd, int qtdade) {
        ItemDeEstoque item = this.findByProdId(codProd);
        if (item == null){
            throw new IllegalArgumentException("Produto inexistente");
        }
        if (item.getQuantidade() < qtdade){
            throw new IllegalArgumentException("Quantidade em estoque insuficiente");
        }
        int novaQuantidade = item.getQuantidade() - qtdade;
        item.setQuantidade(novaQuantidade);
        estoque.save(item);
        return novaQuantidade;
    }

    private ItemDeEstoque findByProdId(long cod){
        return estoque.findAll().stream()
                    .filter(it->it.getProduto().getId()==cod)
                    .findFirst()
                    .orElse(null);
    }
}
