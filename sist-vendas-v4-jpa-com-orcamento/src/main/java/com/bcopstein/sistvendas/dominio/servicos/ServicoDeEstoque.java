package com.bcopstein.sistvendas.dominio.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;

@Service
public class ServicoDeEstoque{
    private IEstoqueRepositorio estoque;
    private IProdutoRepositorio produtos;
    
    @Autowired
    public ServicoDeEstoque(IProdutoRepositorio produtos,IEstoqueRepositorio estoque){
        this.produtos = produtos;
        this.estoque = estoque;
    }
 
    public List<ProdutoModel> produtosDisponiveis(){
        return estoque.todosComEstoque();
    }

    public ProdutoModel produtoPorCodigo(long id){
        return this.produtos.consultaPorId(id);
    }

    public int qtdadeEmEstoque(long id){
        System.out.println("--qtEstoque: "+id);
        int qtde = estoque.quantidadeEmEstoque(id);
        System.out.println("--qtEstoque: "+id+" qtde: "+qtde);
        return qtde;
    }

    public void baixaEstoque(long id,int qtdade){       
         System.out.println("--qtEstoque: "+id);
        estoque.baixaEstoque(id,qtdade);
    }  
}
