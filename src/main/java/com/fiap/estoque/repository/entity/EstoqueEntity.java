package com.fiap.estoque.repository.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "estoque")
public class EstoqueEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_produto", nullable = false, unique = true)
    private UUID idProduto;
    
    @Column(nullable = false)
    private Integer quantidade;
    
    public EstoqueEntity() {
    }
    
    public EstoqueEntity(UUID idProduto, Integer quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public UUID getIdProduto() {
        return idProduto;
    }
    
    public void setIdProduto(UUID idProduto) {
        this.idProduto = idProduto;
    }
    
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
