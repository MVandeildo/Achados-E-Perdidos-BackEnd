package com.example.AchadosPerdidos.controller;

import com.example.AchadosPerdidos.model.Item;
import com.example.AchadosPerdidos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/itens")
public class ItemController {

    @Autowired
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // POST: /api/itens
    @PostMapping
    public ResponseEntity<Item> criarItem(@RequestBody Item item) {
        Item novoItem = itemService.salvar(item);
        return new ResponseEntity<>(novoItem, HttpStatus.CREATED);
    }

    // GET: /api/itens
    @GetMapping
    public List<Item> listarTodos() {
        return itemService.buscarTodos();
    }

    // GET: /api/itens/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarPorId(@PathVariable Long id) {
        return itemService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: /api/itens/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Item> atualizarItem(@PathVariable Long id, @RequestBody Item itemDetalhes) {
        try {
            Item itemAtualizado = itemService.atualizar(id, itemDetalhes);
            return ResponseEntity.ok(itemAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: /api/itens/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        try {
            itemService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET: /api/itens/status?status=Perdido
    @GetMapping("/status")
    public List<Item> buscarPorStatus(@RequestParam String status) {
        return itemService.buscarPorStatus(status);
    }
}