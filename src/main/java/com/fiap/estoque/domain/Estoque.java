package com.fiap.estoque.domain;

public class Estoque {

    private Long idProduto;
    private Integer quantidade;

    public Estoque(Long idProduto, Integer quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void removerQuantidade(int valor) {
        this.quantidade -= valor;
    }

    public boolean isDisponivel(int valor) {
        return this.quantidade >= valor;
    }
}
