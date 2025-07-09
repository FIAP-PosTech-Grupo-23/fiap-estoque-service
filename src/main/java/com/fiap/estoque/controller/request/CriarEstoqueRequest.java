package com.fiap.estoque.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "Requisição para registrar movimentação de estoque (baixa ou reposição)")
public record CriarEstoqueRequest(
    @Schema(description = "ID do produto", example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull(message = "ID do produto é obrigatório")
    UUID idProduto,
    
    @Schema(description = "Quantidade movimentada (positiva para baixa, negativa para devolução)", example = "15")
    @NotNull(message = "Quantidade é obrigatória")
    Integer quantidade
) {}
