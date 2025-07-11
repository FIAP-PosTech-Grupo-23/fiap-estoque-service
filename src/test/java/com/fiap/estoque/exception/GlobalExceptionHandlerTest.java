package com.fiap.estoque.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @Test
    void deveRetornarNotFoundParaProdutoNaoEncontrado() {
        Long idProduto = 1L;
        ProdutoNaoEncontradoException ex = new ProdutoNaoEncontradoException(idProduto);

        ResponseEntity<ErrorResponse> response = handler.handleProdutoNaoEncontrado(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().status());
        assertEquals("Produto não encontrado", response.getBody().error());
        assertTrue(response.getBody().message().contains("1"));
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void deveRetornarConflictParaEstoqueJaExiste() {
        String mensagem = "Estoque já existe para o produto";
        EstoqueJaExisteException ex = new EstoqueJaExisteException(mensagem);

        ResponseEntity<ErrorResponse> response = handler.handleEstoqueJaExiste(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(409, response.getBody().status());
        assertEquals("Estoque já existe", response.getBody().error());
        assertEquals(mensagem, response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void deveRetornarBadRequestParaArgumentoInvalido() {
        String mensagem = "Quantidade deve ser positiva";
        IllegalArgumentException ex = new IllegalArgumentException(mensagem);

        ResponseEntity<ErrorResponse> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().status());
        assertEquals("Argumento inválido", response.getBody().error());
        assertEquals(mensagem, response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void deveRetornarBadRequestParaEstoqueInsuficiente() {
        Long idProduto = 1L;
        EstoqueInsuficienteException ex = new EstoqueInsuficienteException(idProduto, 5, 10);

        ResponseEntity<ErrorResponse> response = handler.handleEstoqueInsuficiente(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().status());
        assertEquals("Estoque insuficiente", response.getBody().error());
        assertTrue(response.getBody().message().contains("1"));
        assertTrue(response.getBody().message().contains("5"));
        assertTrue(response.getBody().message().contains("10"));
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void deveRetornarBadRequestParaErroValidacao() {
        FieldError fieldError = new FieldError("objeto", "campo", "Campo obrigatório");
        
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<ValidationErrorResponse> response = handler.handleValidationErrors(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().status());
        assertEquals("Erro de validação", response.getBody().error());
        assertEquals("Campo obrigatório", response.getBody().errors().get("campo"));
        assertNotNull(response.getBody().timestamp());
    }

    @Test
    void deveRetornarInternalServerErrorParaExcecaoGenerica() {
        String mensagem = "Erro inesperado";
        Exception ex = new Exception(mensagem);

        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().status());
        assertEquals("Erro interno do servidor", response.getBody().error());
        assertEquals(mensagem, response.getBody().message());
        assertNotNull(response.getBody().timestamp());
    }
}
