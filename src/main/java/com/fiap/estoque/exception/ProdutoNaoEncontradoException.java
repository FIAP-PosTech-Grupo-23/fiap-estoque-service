package com.fiap.estoque.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(Long idProduto) {
        super("Produto com ID " + idProduto + " não encontrado no estoque.");
    }
}
