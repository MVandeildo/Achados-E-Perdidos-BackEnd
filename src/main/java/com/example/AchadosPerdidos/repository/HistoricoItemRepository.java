package com.example.AchadosPerdidos.repository;

import com.example.AchadosPerdidos.model.HistoricoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoItemRepository extends JpaRepository<HistoricoItem, Long> {
    List<HistoricoItem> findByItemIdOrderByDataResolucaoAsc(Long id);
}
