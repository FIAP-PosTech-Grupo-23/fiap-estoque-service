package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.springframework.stereotype.Service;

@Service
public class ConsultarEstoqueUseCase {

    private final EstoqueGateway gateway;

    public ConsultarEstoqueUseCase(EstoqueGateway gateway) {
        this.gateway = gateway;
    }

    public Estoque executar(Long idProduto) {
        return gateway.buscarPorIdProduto(idProduto)
                .orElseThrow(() -> new com.fiap.estoque.exception.ProdutoNaoEncontradoException(idProduto));
    }
}
