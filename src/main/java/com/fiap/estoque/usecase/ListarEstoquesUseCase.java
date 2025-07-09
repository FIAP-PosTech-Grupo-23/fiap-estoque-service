package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarEstoquesUseCase {
    
    private final EstoqueGateway gateway;
    
    public ListarEstoquesUseCase(EstoqueGateway gateway) {
        this.gateway = gateway;
    }
    
    public List<Estoque> executar() {
        return gateway.buscarTodos();
    }
}
