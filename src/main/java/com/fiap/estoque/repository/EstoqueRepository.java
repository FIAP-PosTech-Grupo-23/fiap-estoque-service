package com.fiap.estoque.repository;

import com.fiap.estoque.repository.entity.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {
    
    Optional<EstoqueEntity> findByIdProduto(UUID idProduto);
    
    boolean existsByIdProduto(UUID idProduto);
    
    void deleteByIdProduto(UUID idProduto);
}
