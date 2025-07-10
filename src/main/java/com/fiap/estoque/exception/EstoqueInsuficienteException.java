package com.fiap.estoque.exception;

public class EstoqueInsuficienteException extends RuntimeException {

    public EstoqueInsuficienteException(Long idProduto, int quantidadeDisponivel, int quantidadeSolicitada) {
        super("Estoque insuficiente para o produto " + idProduto + ". Disponível: " + quantidadeDisponivel + ", Solicitado: " + quantidadeSolicitada);
    }
}
