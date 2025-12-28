package com.miniprojet.banquedigitale.controller;

import com.miniprojet.banquedigitale.dto.*;
import com.miniprojet.banquedigitale.entities.*;
import com.miniprojet.banquedigitale.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comptes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CompteController {

    private final CompteService compteService;

    @PostMapping
    public ResponseEntity<CompteBancaire> creer(@RequestBody CreateCompteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(compteService.creerCompte(request));
    }

    @GetMapping
    public ResponseEntity<List<CompteBancaire>> list() {
        return ResponseEntity.ok(compteService.list());
    }

    @GetMapping("/dto")
    public ResponseEntity<List<CompteDTO>> listDTOs() {
        return ResponseEntity.ok(compteService.getAllDTOs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteBancaire> get(@PathVariable Long id) {
        return ResponseEntity.ok(compteService.get(id));
    }

    @GetMapping("/{id}/dto")
    public ResponseEntity<CompteDTO> getDTO(@PathVariable Long id) {
        return ResponseEntity.ok(compteService.getDTO(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CompteBancaire>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(compteService.getByClient(clientId));
    }

    @GetMapping("/{id}/operations")
    public ResponseEntity<List<Operation>> getOperations(@PathVariable Long id) {
        return ResponseEntity.ok(compteService.getOperations(id));
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<Void> changeStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        compteService.changeStatut(id, statut);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
