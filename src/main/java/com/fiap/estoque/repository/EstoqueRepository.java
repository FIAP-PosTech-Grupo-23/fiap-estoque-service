package com.fiap.estoque.repository;

import com.fiap.estoque.repository.entity.EstoqueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long> {
    
    Optional<EstoqueEntity> findByIdProduto(Long idProduto);
    
    boolean existsByIdProduto(Long idProduto);
    
    void deleteByIdProduto(Long idProduto);
}
