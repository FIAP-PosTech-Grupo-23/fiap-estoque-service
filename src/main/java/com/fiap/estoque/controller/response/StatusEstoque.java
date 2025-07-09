package com.fiap.estoque.controller.response;

public enum StatusEstoque {
    DISPONIVEL("Disponível"),
    INDISPONIVEL("Indisponível");

    private final String descricao;

    StatusEstoque(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
