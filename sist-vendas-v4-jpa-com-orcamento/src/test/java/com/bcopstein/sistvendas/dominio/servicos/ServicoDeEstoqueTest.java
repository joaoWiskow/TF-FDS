package com.bcopstein.sistvendas.dominio.servicos;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.bcopstein.sistvendas.dominio.modelos.ProdutoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IEstoqueRepositorio;
import com.bcopstein.sistvendas.dominio.persistencia.IProdutoRepositorio;





public class ServicoDeEstoqueTest {
    @Mock
    private IEstoqueRepositorio estoqueRepositorio;

    @Mock
    private IProdutoRepositorio produtoRepositorio;

    @InjectMocks
    private ServicoDeEstoque servicoDeEstoque;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProdutosDisponiveis() {
        ProdutoModel produto1 = new ProdutoModel(1L, "Produto 1", 10.0);
        ProdutoModel produto2 = new ProdutoModel(2L, "Produto 2", 20.0);
        List<ProdutoModel> produtos = Arrays.asList(produto1, produto2);

        when(estoqueRepositorio.todosComEstoque()).thenReturn(produtos);

        List<ProdutoModel> result = servicoDeEstoque.produtosDisponiveis();

        assertEquals(2, result.size());
        assertEquals(produto1, result.get(0));
        assertEquals(produto2, result.get(1));
    }

    @Test
    public void testProdutoPorCodigo() {
        ProdutoModel produto = new ProdutoModel(1L, "Produto 1", 10.0);

        when(produtoRepositorio.consultaPorId(1L)).thenReturn(produto);

        ProdutoModel result = servicoDeEstoque.produtoPorCodigo(1L);

        assertEquals(produto, result);
    }

    @Test
    public void testQtdadeEmEstoque() {
        when(estoqueRepositorio.quantidadeEmEstoque(1L)).thenReturn(100);

        int result = servicoDeEstoque.qtdadeEmEstoque(1L);

        assertEquals(100, result);
    }

    @Test
    public void testBaixaEstoque() {
        servicoDeEstoque.baixaEstoque(1L, 10);

        verify(estoqueRepositorio).baixaEstoque(1L, 10);
    }
}