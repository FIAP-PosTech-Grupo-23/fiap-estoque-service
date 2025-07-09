package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.exception.ProdutoNaoEncontradoException;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AtualizarEstoqueUseCase {
    
    private final EstoqueGateway gateway;
    
    public AtualizarEstoqueUseCase(EstoqueGateway gateway) {
        this.gateway = gateway;
    }
    
    public Estoque executar(UUID idProduto, Integer novaQuantidade) {
        if (!gateway.existePorIdProduto(idProduto)) {
            throw new ProdutoNaoEncontradoException(idProduto);
        }
        
        Estoque estoqueAtualizado = new Estoque(idProduto, novaQuantidade);
        return gateway.salvar(estoqueAtualizado);
    }
}
