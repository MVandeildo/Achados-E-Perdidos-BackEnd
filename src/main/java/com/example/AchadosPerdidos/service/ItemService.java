package com.example.AchadosPerdidos.service;

import com.example.AchadosPerdidos.model.Item;
import com.example.AchadosPerdidos.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item salvar(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> buscarTodos() {
        return itemRepository.findAll();
    }

    public Optional<Item> buscarPorId(Long id) {
        return itemRepository.findById(id);
    }

    public Item atualizar(Long id, Item itemDetalhes) {
        return itemRepository.findById(id).map(itemExistente -> {
            itemExistente.setTitulo(itemDetalhes.getTitulo());
            itemExistente.setDescricao(itemDetalhes.getDescricao());
            itemExistente.setStatus(itemDetalhes.getStatus());
            itemExistente.setLocalizacao(itemDetalhes.getLocalizacao());
            itemExistente.setLatitude(itemDetalhes.getLatitude());
            itemExistente.setLongitude(itemDetalhes.getLongitude());
            itemExistente.setUsuario(itemDetalhes.getUsuario());

            return itemRepository.save(itemExistente);
        }).orElseThrow(() -> new RuntimeException("Item não encontrado com ID: " + id));
    }

    public void deletar(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item não encontrado com ID: " + id);
        }
        itemRepository.deleteById(id);
    }

    public List<Item> buscarPorStatus(String status) {
        return itemRepository.findByStatus(status);
    }

    public List<Item> buscarPorLocalizacao(String localizacao) {
        return itemRepository.findByLocalizacaoContainingIgnoreCase(localizacao);
    }

    public List<Item> buscarPorUsuario(Long usuarioId) {
        return itemRepository.findByUsuarioId(usuarioId);
    }
}