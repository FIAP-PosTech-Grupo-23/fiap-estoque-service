package com.fiap.estoque.usecase;

import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.exception.EstoqueInsuficienteException;
import com.fiap.estoque.exception.ProdutoNaoEncontradoException;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.springframework.stereotype.Service;

@Service
public class BaixarEstoqueUseCase {
    
    private final EstoqueGateway gateway;
    
    public BaixarEstoqueUseCase(EstoqueGateway gateway) {
        this.gateway = gateway;
    }
    
    public boolean executar(Estoque requisicao) {
        Estoque estoque = gateway.buscarPorIdProduto(requisicao.getIdProduto())
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado: " + requisicao.getIdProduto()));
        
        // Verifica se há estoque suficiente
        if (!estoque.isDisponivel(requisicao.getQuantidade())) {
            throw new EstoqueInsuficienteException("Estoque insuficiente. Disponível: " + estoque.getQuantidade() + ", Solicitado: " + requisicao.getQuantidade());
        }
        
        // Realiza a baixa
        estoque.removerQuantidade(requisicao.getQuantidade());
        gateway.salvar(estoque);
        
        return true;
    }
}
