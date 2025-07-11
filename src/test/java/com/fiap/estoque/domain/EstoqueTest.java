package com.fiap.estoque.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueTest {

    @Test
    void deveRemoverQuantidadeCorretamente() {
        Estoque estoque = new Estoque(1L, 10);
        
        estoque.removerQuantidade(3);
        
        assertEquals(7, estoque.getQuantidade());
    }

    @Test
    void deveRetornarTrueQuandoQuantidadeDisponivelSuficiente() {
        Estoque estoque = new Estoque(1L, 10);
        
        assertTrue(estoque.isDisponivel(5));
        assertTrue(estoque.isDisponivel(10));
    }

    @Test
    void deveRetornarFalseQuandoQuantidadeDisponivelInsuficiente() {
        Estoque estoque = new Estoque(1L, 5);
        
        assertFalse(estoque.isDisponivel(10));
    }
}
