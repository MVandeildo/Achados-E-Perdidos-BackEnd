package com.example.AchadosPerdidos.repository;

import com.example.AchadosPerdidos.model.FotoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoItemRepository extends JpaRepository<FotoItem, Long> {
    List<FotoItem> findByItemIdItem(Long idItem);
}