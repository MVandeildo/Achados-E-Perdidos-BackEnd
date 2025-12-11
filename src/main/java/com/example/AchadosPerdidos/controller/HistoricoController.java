package com.example.AchadosPerdidos.controller;

import com.example.AchadosPerdidos.model.HistoricoItem;
import com.example.AchadosPerdidos.service.HistoricoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
public class HistoricoController {

    @Autowired
    private final HistoricoItemService historicoItemService;

    public HistoricoController(HistoricoItemService historicoItemService) {
        this.historicoItemService = historicoItemService;
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<HistoricoItem>> buscarHistoricoPorItem(@PathVariable Long itemId) {

        List<HistoricoItem> historico = historicoItemService.buscarHistoricoPorItem(itemId);

        if (historico.isEmpty()) {
            // Retorna 404 Not Found se não houver histórico para o item
            return ResponseEntity.notFound().build();
        }

        // Retorna 200 OK com a lista do histórico
        return ResponseEntity.ok(historico);
    }
}
