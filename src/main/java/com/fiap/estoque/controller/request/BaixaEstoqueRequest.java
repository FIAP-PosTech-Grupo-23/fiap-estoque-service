package com.fiap.estoque.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Requisição para baixa de estoque")
public record BaixaEstoqueRequest(
    @Schema(description = "ID do produto", example = "1")
    @NotNull(message = "ID do produto é obrigatório")
    Long idProduto,
    
    @Schema(description = "Quantidade a ser baixada (positiva) ou estornada (negativa)", example = "5")
    @NotNull(message = "Quantidade é obrigatória")
    Integer quantidade
) {}
