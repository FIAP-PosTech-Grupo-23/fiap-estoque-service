package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.exception.ProdutoNaoEncontradoException;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ConsultarEstoqueUseCase {
    
    private final EstoqueGateway gateway;
    
    public ConsultarEstoqueUseCase(EstoqueGateway gateway) {
        this.gateway = gateway;
    }
    
    public Estoque executar(UUID idProduto) {
        return gateway.buscarPorIdProduto(idProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado: " + idProduto));
    }
}
