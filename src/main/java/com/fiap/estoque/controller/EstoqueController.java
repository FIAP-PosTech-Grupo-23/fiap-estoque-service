package com.fiap.estoque.controller;

import com.fiap.estoque.controller.request.BaixaEstoqueRequest;
import com.fiap.estoque.controller.request.CriarEstoqueRequest;
import com.fiap.estoque.controller.response.EstoqueResponse;
import com.fiap.estoque.controller.response.StatusEstoque;
import com.fiap.estoque.domain.Estoque;
import com.fiap.estoque.mapper.EstoqueMapper;
import com.fiap.estoque.usecase.BaixarEstoqueListaUseCase;
import com.fiap.estoque.usecase.ConsultarEstoqueUseCase;
import com.fiap.estoque.usecase.CriarEstoqueUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/estoques")
@Tag(name = "Estoque", description = "API para gerenciamento de estoque")
public class EstoqueController {

    private final BaixarEstoqueListaUseCase baixarEstoqueListaUseCase;
    private final CriarEstoqueUseCase criarEstoqueUseCase;
    private final ConsultarEstoqueUseCase consultarEstoqueUseCase;
    private final EstoqueMapper estoqueMapper;

    public EstoqueController(BaixarEstoqueListaUseCase baixarEstoqueListaUseCase, 
                           CriarEstoqueUseCase criarEstoqueUseCase,
                           ConsultarEstoqueUseCase consultarEstoqueUseCase,
                           EstoqueMapper estoqueMapper) {
        this.baixarEstoqueListaUseCase = baixarEstoqueListaUseCase;
        this.criarEstoqueUseCase = criarEstoqueUseCase;
        this.consultarEstoqueUseCase = consultarEstoqueUseCase;
        this.estoqueMapper = estoqueMapper;
    }

    @PostMapping("/baixa")
    @Operation(summary = "Baixa de estoque", description = "Registra movimentação de baixa no estoque de múltiplos produtos")
    public ResponseEntity<List<EstoqueResponse>> baixar(@Valid @RequestBody List<BaixaEstoqueRequest> request) {
        List<Estoque> produtosBaixa = request.stream()
            .map(estoqueMapper::toDomain)
            .toList();
            
        List<EstoqueResponse> resultados = baixarEstoqueListaUseCase.executar(produtosBaixa);
        
        return ResponseEntity.ok(resultados);
    }

    @PostMapping
    @Operation(summary = "Criar estoque", description = "Cria um novo registro de estoque para um produto")
    public ResponseEntity<EstoqueResponse> criar(@Valid @RequestBody CriarEstoqueRequest request) {
        Estoque domain = estoqueMapper.toDomain(request);
        Estoque estoqueCriado = criarEstoqueUseCase.executar(domain);
        
        EstoqueResponse response = estoqueMapper.toResponse(estoqueCriado, StatusEstoque.DISPONIVEL);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{idProduto}")
    @Operation(summary = "Consultar estoque", description = "Consulta o estoque de um produto específico")
    public ResponseEntity<EstoqueResponse> consultar(
            @Parameter(description = "ID do produto") @PathVariable UUID idProduto) {
        Estoque estoque = consultarEstoqueUseCase.executar(idProduto);
        
        StatusEstoque status = estoque.getQuantidade() > 0 ? StatusEstoque.DISPONIVEL : StatusEstoque.INDISPONIVEL;
        EstoqueResponse response = estoqueMapper.toResponse(estoque, status);
        
        return ResponseEntity.ok(response);
    }
}
