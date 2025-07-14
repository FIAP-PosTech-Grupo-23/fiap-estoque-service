package com.fiap.estoque.controller.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatusEstoqueTest {

    @Test
    void deveRetornarDescricaoCorreta() {
        assertEquals("Disponível", StatusEstoque.DISPONIVEL.getDescricao());
        assertEquals("Indisponível", StatusEstoque.INDISPONIVEL.getDescricao());
    }

    @Test
    void deveRetornarToStringCorreto() {
        assertEquals("Disponível", StatusEstoque.DISPONIVEL.toString());
        assertEquals("Indisponível", StatusEstoque.INDISPONIVEL.toString());
    }
}
