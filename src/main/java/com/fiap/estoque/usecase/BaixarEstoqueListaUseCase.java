package com.fiap.estoque.usecase;

import com.fiap.estoque.controller.response.EstoqueResponse;
import com.fiap.estoque.controller.response.StatusEstoque;
import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.gateway.EstoqueGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaixarEstoqueListaUseCase {

    private final EstoqueGateway estoqueGateway;

    public BaixarEstoqueListaUseCase(EstoqueGateway estoqueGateway) {
        this.estoqueGateway = estoqueGateway;
    }

    public List<EstoqueResponse> executar(List<Estoque> produtosBaixa) {
        List<EstoqueResponse> resultados = produtosBaixa.stream()
            .map(this::verificarDisponibilidade)
            .toList();

        boolean algumIndisponivel = resultados.stream()
            .anyMatch(response -> StatusEstoque.INDISPONIVEL.equals(response.status()));

        if (algumIndisponivel) {
            return resultados;
        }

        for (Estoque produto : produtosBaixa) {
            Optional<Estoque> estoqueOpt = estoqueGateway.buscarPorIdProduto(produto.getIdProduto());
            if (estoqueOpt.isPresent()) {
                Estoque estoqueAtual = estoqueOpt.get();
                Integer novaQuantidade = estoqueAtual.getQuantidade() - produto.getQuantidade();
                estoqueGateway.atualizarQuantidade(produto.getIdProduto(), novaQuantidade);
            }
        }

        return produtosBaixa.stream()
            .map(produto -> new EstoqueResponse(produto.getIdProduto(), produto.getQuantidade(), StatusEstoque.DISPONIVEL))
            .toList();
    }

    private EstoqueResponse verificarDisponibilidade(Estoque produtoBaixa) {
        Optional<Estoque> estoqueOpt = estoqueGateway.buscarPorIdProduto(produtoBaixa.getIdProduto());
        
        if (estoqueOpt.isEmpty()) {
            return new EstoqueResponse(produtoBaixa.getIdProduto(), produtoBaixa.getQuantidade(), StatusEstoque.INDISPONIVEL);
        }
        
        Estoque estoqueAtual = estoqueOpt.get();
        boolean disponivel = estoqueAtual.getQuantidade() >= Math.abs(produtoBaixa.getQuantidade());
        StatusEstoque status = disponivel ? StatusEstoque.DISPONIVEL : StatusEstoque.INDISPONIVEL;
        
        return new EstoqueResponse(produtoBaixa.getIdProduto(), produtoBaixa.getQuantidade(), status);
    }
}
