package com.example.AchadosPerdidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.AchadosPerdidos.model.Item;
import java.util.List;


public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findByStatus(String status);
    
    List<Item> findByLocalizacaoContainingIgnoreCase(String localizacao);

    List<Item> findByUsuarioId(Long id);

    List<Item> findByTituloContainingIgnoreCase(String titulo);
    
}
