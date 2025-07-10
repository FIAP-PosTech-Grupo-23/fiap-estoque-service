package com.fiap.estoque.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Request para baixa de estoque de múltiplos produtos")
public record BaixaEstoqueListRequest(
    @Schema(description = "Lista de produtos para baixa de estoque")
    @NotEmpty(message = "A lista de produtos não pode estar vazia")
    @Valid
    List<BaixaEstoqueRequest> produtos
) {
}
