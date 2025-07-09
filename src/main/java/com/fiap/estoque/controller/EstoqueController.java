package com.fiap.estoque.controller;

import com.fiap.estoque.controller.request.CriarEstoqueRequest;
import com.fiap.estoque.controller.response.EstoqueResponse;
import com.fiap.estoque.controller.response.StatusEstoque;
import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.mapper.EstoqueMapper;
import com.fiap.estoque.usecase.BaixarEstoqueUseCase;
import com.fiap.estoque.usecase.ConsultarEstoqueUseCase;
import com.fiap.estoque.usecase.CriarEstoqueUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/estoques")
@Tag(name = "Estoque", description = "API para gerenciamento de estoque")
public class EstoqueController {

    private final BaixarEstoqueUseCase baixarEstoqueUseCase;
    private final CriarEstoqueUseCase criarEstoqueUseCase;
    private final ConsultarEstoqueUseCase consultarEstoqueUseCase;
    private final EstoqueMapper estoqueMapper;

    public EstoqueController(BaixarEstoqueUseCase baixarEstoqueUseCase, 
                           CriarEstoqueUseCase criarEstoqueUseCase,
                           ConsultarEstoqueUseCase consultarEstoqueUseCase,
                           EstoqueMapper estoqueMapper) {
        this.baixarEstoqueUseCase = baixarEstoqueUseCase;
        this.criarEstoqueUseCase = criarEstoqueUseCase;
        this.consultarEstoqueUseCase = consultarEstoqueUseCase;
        this.estoqueMapper = estoqueMapper;
    }

    @PostMapping("/baixa")
    @Operation(summary = "Baixa de estoque", description = "Registra movimentação de baixa no estoque")
    public ResponseEntity<EstoqueResponse> baixar(@Valid @RequestBody CriarEstoqueRequest request) {
        try {
            Estoque domain = estoqueMapper.toDomain(request);
            boolean sucesso = baixarEstoqueUseCase.executar(domain);

            String status = sucesso ? StatusEstoque.DISPONIVEL.getDescricao() : StatusEstoque.INDISPONIVEL.getDescricao();
            EstoqueResponse response = new EstoqueResponse(request.idProduto(), request.quantidade(), status);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            EstoqueResponse response = new EstoqueResponse(request.idProduto(), request.quantidade(), StatusEstoque.INDISPONIVEL.getDescricao());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping
    @Operation(summary = "Criar estoque", description = "Cria um novo registro de estoque para um produto")
    public ResponseEntity<EstoqueResponse> criar(@Valid @RequestBody CriarEstoqueRequest request) {
        Estoque domain = estoqueMapper.toDomain(request);
        Estoque estoqueCriado = criarEstoqueUseCase.executar(domain);
        
        EstoqueResponse response = estoqueMapper.toResponse(estoqueCriado, StatusEstoque.DISPONIVEL.getDescricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{idProduto}")
    @Operation(summary = "Consultar estoque", description = "Consulta o estoque de um produto específico")
    public ResponseEntity<EstoqueResponse> consultar(
            @Parameter(description = "ID do produto") @PathVariable UUID idProduto) {
        Estoque estoque = consultarEstoqueUseCase.executar(idProduto);
        
        String status = estoque.getQuantidade() > 0 ? StatusEstoque.DISPONIVEL.getDescricao() : StatusEstoque.INDISPONIVEL.getDescricao();
        EstoqueResponse response = estoqueMapper.toResponse(estoque, status);
        
        return ResponseEntity.ok(response);
    }
}
