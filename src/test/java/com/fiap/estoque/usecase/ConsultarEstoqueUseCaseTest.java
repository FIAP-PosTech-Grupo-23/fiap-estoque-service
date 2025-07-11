package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarEstoqueUseCaseTest {

    @Mock
    private EstoqueGateway gateway;

    @InjectMocks
    private ConsultarEstoqueUseCase useCase;

    @Test
    void deveRetornarEstoqueQuandoEncontrado() {
        Long idProduto = 1L;
        Estoque estoque = new Estoque(idProduto, 10);
        
        when(gateway.buscarPorIdProduto(idProduto)).thenReturn(Optional.of(estoque));
        
        Estoque resultado = useCase.executar(idProduto);
        
        assertEquals(idProduto, resultado.getIdProduto());
        assertEquals(10, resultado.getQuantidade());
        verify(gateway).buscarPorIdProduto(idProduto);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        Long idProduto = 1L;
        
        when(gateway.buscarPorIdProduto(idProduto)).thenReturn(Optional.empty());
        
        assertThrows(com.fiap.estoque.exception.ProdutoNaoEncontradoException.class, 
                    () -> useCase.executar(idProduto));
        verify(gateway).buscarPorIdProduto(idProduto);
    }
}
