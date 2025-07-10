package com.fiap.estoque.exception;

public class ProdutoNaoLocalizadoException extends RuntimeException {
    public ProdutoNaoLocalizadoException(Long idProduto) {
        super("Produto com ID " + idProduto + " não encontrado no estoque.");
    }
}
