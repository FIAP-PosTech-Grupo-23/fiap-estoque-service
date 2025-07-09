package com.fiap.estoque.exception;

public class EstoqueJaExisteException extends RuntimeException {
    
    public EstoqueJaExisteException(String message) {
        super(message);
    }
    
    public EstoqueJaExisteException(String message, Throwable cause) {
        super(message, cause);
    }
}
