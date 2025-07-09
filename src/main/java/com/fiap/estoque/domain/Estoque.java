package com.fiap.estoque.domain;

import java.util.Objects;
import java.util.UUID;

public class Estoque {

    private UUID idProduto;
    private Integer quantidade;

    public Estoque(UUID idProduto, Integer quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public UUID getIdProduto() {
        return idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void removerQuantidade(int valor) {
        if (quantidade < valor) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        this.quantidade -= valor;
    }

    public void adicionarQuantidade(int valor) {
        this.quantidade += valor;
    }

    public boolean isDisponivel(int valor) {
        return this.quantidade >= valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estoque)) return false;
        Estoque estoque = (Estoque) o;
        return Objects.equals(idProduto, estoque.idProduto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto);
    }
}
