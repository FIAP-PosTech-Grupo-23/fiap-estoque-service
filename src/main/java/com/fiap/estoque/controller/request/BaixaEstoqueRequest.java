package com.fiap.estoque.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "Requisição para baixa de estoque")
public record BaixaEstoqueRequest(
    @Schema(description = "ID do produto", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull(message = "ID do produto é obrigatório")
    UUID idProduto,
    
    @Schema(description = "Quantidade a ser baixada (positiva) ou estornada (negativa)", example = "5")
    @NotNull(message = "Quantidade é obrigatória")
    Integer quantidade
) {}
