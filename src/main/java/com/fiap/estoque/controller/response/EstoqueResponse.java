package com.fiap.estoque.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da operação de estoque")
public record EstoqueResponse(
    @Schema(description = "ID do produto", example = "1")
    Long idProduto,

    @Schema(description = "Quantidade solicitada", example = "15")
    Integer quantidade,

    @Schema(description = "Status da operação", example = "DISPONIVEL")
    StatusEstoque status
) {
}
