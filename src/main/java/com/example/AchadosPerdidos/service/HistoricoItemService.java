package com.example.AchadosPerdidos.service;

import com.example.AchadosPerdidos.model.HistoricoItem;
import com.example.AchadosPerdidos.repository.HistoricoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoItemService {

    @Autowired
    private final HistoricoItemRepository historicoItemRepository;

    public HistoricoItemService(HistoricoItemRepository historicoItemRepository) {
        this.historicoItemRepository = historicoItemRepository;
    }

    public HistoricoItem salvar(HistoricoItem historicoItem) {
        return historicoItemRepository.save(historicoItem);
    }

    public Optional<HistoricoItem> buscarPorId(Long id) {
        return historicoItemRepository.findById(id);
    }

    public List<HistoricoItem> buscarHistoricoPorItem(Long itemId) {
        return historicoItemRepository.findByItemIdItemOrderByDataResolucaoAsc(itemId);
    }
}
