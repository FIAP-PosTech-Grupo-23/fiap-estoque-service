package com.fiap.estoque.repository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estoque")
public class EstoqueEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_produto", nullable = false, unique = true)
    private Long idProduto;
    
    @Column(nullable = false)
    private Integer quantidade;
    
    public EstoqueEntity() {
    }
    
    public EstoqueEntity(Long idProduto, Integer quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdProduto() {
        return idProduto;
    }
    
    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }
    
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
