package com.fiap.estoque.usecase;

import com.fiap.estoque.controller.response.EstoqueResponse;
import com.fiap.estoque.controller.response.StatusEstoque;
import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaixarEstoqueListaUseCaseTest {

    @Mock
    private EstoqueGateway estoqueGateway;

    @InjectMocks
    private BaixarEstoqueListaUseCase useCase;

    @Test
    void deveProcessarBaixaQuandoTodosProdutosDisponiveis() {
        Estoque produto1 = new Estoque(1L, 5);
        Estoque produto2 = new Estoque(2L, 3);
        List<Estoque> produtosBaixa = List.of(produto1, produto2);
        
        Estoque estoque1 = new Estoque(1L, 10);
        Estoque estoque2 = new Estoque(2L, 8);
        
        when(estoqueGateway.buscarPorIdProduto(1L)).thenReturn(Optional.of(estoque1));
        when(estoqueGateway.buscarPorIdProduto(2L)).thenReturn(Optional.of(estoque2));
        
        List<EstoqueResponse> resultado = useCase.executar(produtosBaixa);
        
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(r -> r.status() == StatusEstoque.DISPONIVEL));
        verify(estoqueGateway).atualizarQuantidade(1L, 5);
        verify(estoqueGateway).atualizarQuantidade(2L, 5);
    }

    @Test
    void deveRetornarIndisponivelQuandoAlgumProdutoInsuficiente() {
        Estoque produto1 = new Estoque(1L, 15);
        List<Estoque> produtosBaixa = List.of(produto1);
        
        Estoque estoque1 = new Estoque(1L, 10);
        
        when(estoqueGateway.buscarPorIdProduto(1L)).thenReturn(Optional.of(estoque1));
        
        List<EstoqueResponse> resultado = useCase.executar(produtosBaixa);
        
        assertEquals(1, resultado.size());
        assertEquals(StatusEstoque.INDISPONIVEL, resultado.get(0).status());
        verify(estoqueGateway, never()).atualizarQuantidade(anyLong(), anyInt());
    }

    @Test
    void deveRetornarIndisponivelQuandoProdutoNaoExiste() {
        Estoque produto1 = new Estoque(1L, 5);
        List<Estoque> produtosBaixa = List.of(produto1);
        
        when(estoqueGateway.buscarPorIdProduto(1L)).thenReturn(Optional.empty());
        
        List<EstoqueResponse> resultado = useCase.executar(produtosBaixa);
        
        assertEquals(1, resultado.size());
        assertEquals(StatusEstoque.INDISPONIVEL, resultado.get(0).status());
    }
}
