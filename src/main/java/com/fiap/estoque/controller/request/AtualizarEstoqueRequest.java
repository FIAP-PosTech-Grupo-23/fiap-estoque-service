package com.fiap.estoque.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Requisição para atualizar estoque")
public record AtualizarEstoqueRequest(
    @Schema(description = "Nova quantidade do estoque", example = "50")
    @NotNull(message = "Quantidade é obrigatória")
    @PositiveOrZero(message = "Quantidade deve ser zero ou positiva")
    Integer quantidade
) {}
