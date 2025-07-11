package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.exception.EstoqueJaExisteException;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarEstoqueUseCaseTest {

    @Mock
    private EstoqueGateway gateway;

    @InjectMocks
    private CriarEstoqueUseCase useCase;

    @Test
    void deveCriarEstoqueQuandoNaoExiste() {
        Estoque estoque = new Estoque(1L, 10);
        
        when(gateway.existePorIdProduto(1L)).thenReturn(false);
        when(gateway.salvar(estoque)).thenReturn(estoque);
        
        Estoque resultado = useCase.executar(estoque);
        
        assertEquals(estoque, resultado);
        verify(gateway).existePorIdProduto(1L);
        verify(gateway).salvar(estoque);
    }

    @Test
    void deveLancarExcecaoQuandoEstoqueJaExiste() {
        Estoque estoque = new Estoque(1L, 10);
        
        when(gateway.existePorIdProduto(1L)).thenReturn(true);
        
        assertThrows(EstoqueJaExisteException.class, () -> useCase.executar(estoque));
        verify(gateway).existePorIdProduto(1L);
        verify(gateway, never()).salvar(any());
    }
}
