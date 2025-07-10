package com.fiap.estoque.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Resposta da operação de estoque")
public record EstoqueResponse(
    @Schema(description = "ID do produto", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID idProduto,

    @Schema(description = "Quantidade solicitada", example = "15")
    Integer quantidade,

    @Schema(description = "Status da operação", example = "DISPONIVEL")
    StatusEstoque status
) {
}
