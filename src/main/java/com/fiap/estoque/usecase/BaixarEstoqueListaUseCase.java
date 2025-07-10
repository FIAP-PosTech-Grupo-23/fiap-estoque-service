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
        // Primeira fase: verificar se todos os produtos têm estoque suficiente
        List<EstoqueResponse> resultados = produtosBaixa.stream()
            .map(this::verificarDisponibilidade)
            .toList();

        // Se algum produto estiver indisponível, retorna a lista com os status sem fazer baixa
        boolean algumIndisponivel = resultados.stream()
            .anyMatch(response -> StatusEstoque.INDISPONIVEL.getDescricao().equals(response.status()));

        if (algumIndisponivel) {
            return resultados;
        }

        // Segunda fase: todos estão disponíveis, fazer a baixa efetiva
        for (Estoque produto : produtosBaixa) {
            Optional<Estoque> estoqueOpt = estoqueGateway.buscarPorIdProduto(produto.getIdProduto());
            if (estoqueOpt.isPresent()) {
                Estoque estoqueAtual = estoqueOpt.get();
                estoqueAtual.removerQuantidade(produto.getQuantidade());
                estoqueGateway.salvar(estoqueAtual);
            }
        }

        // Retorna a lista com status de sucesso
        return produtosBaixa.stream()
            .map(produto -> new EstoqueResponse(produto.getIdProduto(), produto.getQuantidade(), StatusEstoque.DISPONIVEL.getDescricao()))
            .toList();
    }

    private EstoqueResponse verificarDisponibilidade(Estoque produtoBaixa) {
        try {
            Optional<Estoque> estoqueOpt = estoqueGateway.buscarPorIdProduto(produtoBaixa.getIdProduto());
            
            if (estoqueOpt.isEmpty()) {
                return new EstoqueResponse(produtoBaixa.getIdProduto(), produtoBaixa.getQuantidade(), StatusEstoque.INDISPONIVEL.getDescricao());
            }
            
            Estoque estoqueAtual = estoqueOpt.get();
            boolean disponivel = estoqueAtual.getQuantidade() >= produtoBaixa.getQuantidade();
            String status = disponivel ? StatusEstoque.DISPONIVEL.getDescricao() : StatusEstoque.INDISPONIVEL.getDescricao();
            
            return new EstoqueResponse(produtoBaixa.getIdProduto(), produtoBaixa.getQuantidade(), status);
        } catch (Exception e) {
            return new EstoqueResponse(produtoBaixa.getIdProduto(), produtoBaixa.getQuantidade(), StatusEstoque.INDISPONIVEL.getDescricao());
        }
    }
}
