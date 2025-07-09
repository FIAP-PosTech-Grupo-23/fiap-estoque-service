package com.fiap.estoque.exception;

import java.util.UUID;

public class ProdutoNaoEncontradoException extends RuntimeException {
    
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
    
    public ProdutoNaoEncontradoException(UUID idProduto) {
        super("Produto com ID " + idProduto + " não encontrado no estoque");
    }
}
