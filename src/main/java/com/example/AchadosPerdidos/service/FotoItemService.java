package com.example.AchadosPerdidos.service;

import com.example.AchadosPerdidos.model.FotoItem;
import com.example.AchadosPerdidos.repository.FotoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotoItemService {

    @Autowired
    private final FotoItemRepository fotoItemRepository;

    
    public FotoItemService(FotoItemRepository fotoItemRepository) {
        this.fotoItemRepository = fotoItemRepository;
    }

    public FotoItem salvar(FotoItem fotoItem) {
        return fotoItemRepository.save(fotoItem);
    }

    public Optional<FotoItem> buscarPorId(Long id) {
        return fotoItemRepository.findById(id);
    }

    public List<FotoItem> buscarFotosPorItem(Long itemId) {
        return fotoItemRepository.findByItemIdItem(itemId);
    }

    public void deletar(Long id) {
        if (!fotoItemRepository.existsById(id)) {
            throw new RuntimeException("Foto não encontrada com ID: " + id);
        }
        fotoItemRepository.deleteById(id);
    }
}
