package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.exception.ProdutoNaoEncontradoException;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BaixaEstoqueUseCase {
    
    private final EstoqueGateway gateway;
    
    public BaixaEstoqueUseCase(EstoqueGateway gateway) {
        this.gateway = gateway;
    }
    
    public Estoque executar(UUID idProduto, Integer quantidade) {
        Estoque estoque = gateway.buscarPorIdProduto(idProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(idProduto));
        
        if (estoque.getQuantidade() < quantidade) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque. Disponível: " 
                + estoque.getQuantidade() + ", Solicitado: " + quantidade);
        }
        
        Estoque estoqueAtualizado = new Estoque(idProduto, estoque.getQuantidade() - quantidade);
        return gateway.salvar(estoqueAtualizado);
    }
}
