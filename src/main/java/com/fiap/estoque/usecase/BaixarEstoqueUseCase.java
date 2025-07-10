package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.exception.EstoqueInsuficienteException;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.springframework.stereotype.Service;

@Service
public class BaixarEstoqueUseCase {
    
    private final EstoqueGateway gateway;
    
    public BaixarEstoqueUseCase(EstoqueGateway gateway) {
        this.gateway = gateway;
    }
    
    public Estoque executar(Long idProduto, Integer quantidadeParaBaixa) {
        Estoque estoque = gateway.buscarPorIdProduto(idProduto)
                .orElseThrow(() -> new com.fiap.estoque.exception.ProdutoNaoEncontradoException(idProduto));

        if (!estoque.isDisponivel(quantidadeParaBaixa)) {
            throw new EstoqueInsuficienteException(idProduto, estoque.getQuantidade(), quantidadeParaBaixa);
        }
        
        estoque.removerQuantidade(quantidadeParaBaixa);
        return gateway.salvar(estoque);
    }
}
