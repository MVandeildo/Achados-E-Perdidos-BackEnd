package com.example.AchadosPerdidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AchadosPerdidos.model.FotoItem;
import com.example.AchadosPerdidos.service.FotoItemService;

@RestController
@RequestMapping("/api/foto_item")

public class FotosItensController {
    
@Autowired
private final FotoItemService fotoItemService;


    public FotosItensController(FotoItemService fotoItemService) {
        this.fotoItemService = fotoItemService;
    }

    @PostMapping
    public ResponseEntity<FotoItem> criarFotoItem(@RequestBody FotoItem item) {
        FotoItem novaFotoItem = fotoItemService.salvar(item);
        return new ResponseEntity<>(novaFotoItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFotoItem(@PathVariable Long id) {
        try {
            fotoItemService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
