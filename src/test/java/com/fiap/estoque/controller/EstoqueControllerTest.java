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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstoqueControllerTest {

    @Mock
    private BaixarEstoqueListaUseCase baixarEstoqueListaUseCase;

    @Mock
    private CriarEstoqueUseCase criarEstoqueUseCase;

    @Mock
    private ConsultarEstoqueUseCase consultarEstoqueUseCase;

    @Mock
    private EstoqueMapper estoqueMapper;

    @InjectMocks
    private EstoqueController controller;

    @Test
    void deveCriarEstoqueComSucesso() {
        CriarEstoqueRequest request = new CriarEstoqueRequest(1L, 10);
        Estoque domain = new Estoque(1L, 10);
        Estoque estoqueCriado = new Estoque(1L, 10);
        EstoqueResponse response = new EstoqueResponse(1L, 10, StatusEstoque.DISPONIVEL);

        when(estoqueMapper.toDomain(request)).thenReturn(domain);
        when(criarEstoqueUseCase.executar(domain)).thenReturn(estoqueCriado);
        when(estoqueMapper.toResponse(estoqueCriado, StatusEstoque.DISPONIVEL)).thenReturn(response);

        ResponseEntity<EstoqueResponse> resultado = controller.criar(request);

        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(response, resultado.getBody());
        verify(estoqueMapper).toDomain(request);
        verify(criarEstoqueUseCase).executar(domain);
        verify(estoqueMapper).toResponse(estoqueCriado, StatusEstoque.DISPONIVEL);
    }

    @Test
    void deveConsultarEstoqueDisponivelComSucesso() {
        Long idProduto = 1L;
        Estoque estoque = new Estoque(idProduto, 10);
        EstoqueResponse response = new EstoqueResponse(idProduto, 10, StatusEstoque.DISPONIVEL);

        when(consultarEstoqueUseCase.executar(idProduto)).thenReturn(estoque);
        when(estoqueMapper.toResponse(estoque, StatusEstoque.DISPONIVEL)).thenReturn(response);

        ResponseEntity<EstoqueResponse> resultado = controller.consultar(idProduto);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(response, resultado.getBody());
        verify(consultarEstoqueUseCase).executar(idProduto);
        verify(estoqueMapper).toResponse(estoque, StatusEstoque.DISPONIVEL);
    }

    @Test
    void deveConsultarEstoqueIndisponivelQuandoQuantidadeZero() {
        Long idProduto = 1L;
        Estoque estoque = new Estoque(idProduto, 0);
        EstoqueResponse response = new EstoqueResponse(idProduto, 0, StatusEstoque.INDISPONIVEL);

        when(consultarEstoqueUseCase.executar(idProduto)).thenReturn(estoque);
        when(estoqueMapper.toResponse(estoque, StatusEstoque.INDISPONIVEL)).thenReturn(response);

        ResponseEntity<EstoqueResponse> resultado = controller.consultar(idProduto);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(response, resultado.getBody());
        assertEquals(StatusEstoque.INDISPONIVEL, resultado.getBody().status());
        verify(consultarEstoqueUseCase).executar(idProduto);
        verify(estoqueMapper).toResponse(estoque, StatusEstoque.INDISPONIVEL);
    }

    @Test
    void deveBaixarEstoqueComSucesso() {
        BaixaEstoqueRequest request1 = new BaixaEstoqueRequest(1L, 5);
        BaixaEstoqueRequest request2 = new BaixaEstoqueRequest(2L, 3);
        List<BaixaEstoqueRequest> requests = List.of(request1, request2);

        Estoque domain1 = new Estoque(1L, 5);
        Estoque domain2 = new Estoque(2L, 3);
        List<Estoque> domains = List.of(domain1, domain2);

        EstoqueResponse response1 = new EstoqueResponse(1L, 5, StatusEstoque.DISPONIVEL);
        EstoqueResponse response2 = new EstoqueResponse(2L, 3, StatusEstoque.DISPONIVEL);
        List<EstoqueResponse> responses = List.of(response1, response2);

        when(estoqueMapper.toDomain(request1)).thenReturn(domain1);
        when(estoqueMapper.toDomain(request2)).thenReturn(domain2);
        when(baixarEstoqueListaUseCase.executar(domains)).thenReturn(responses);

        ResponseEntity<List<EstoqueResponse>> resultado = controller.baixar(requests);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(responses, resultado.getBody());
        assertEquals(2, resultado.getBody().size());
        verify(estoqueMapper).toDomain(request1);
        verify(estoqueMapper).toDomain(request2);
        verify(baixarEstoqueListaUseCase).executar(domains);
    }

    @Test
    void deveBaixarEstoqueComListaVazia() {
        List<BaixaEstoqueRequest> requests = List.of();
        List<Estoque> domains = List.of();
        List<EstoqueResponse> responses = List.of();

        when(baixarEstoqueListaUseCase.executar(domains)).thenReturn(responses);

        ResponseEntity<List<EstoqueResponse>> resultado = controller.baixar(requests);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertTrue(resultado.getBody().isEmpty());
        verify(baixarEstoqueListaUseCase).executar(domains);
    }
}
