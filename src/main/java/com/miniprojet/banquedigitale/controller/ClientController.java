package com.miniprojet.banquedigitale.controller;

import com.miniprojet.banquedigitale.dto.ClientDTO;
import com.miniprojet.banquedigitale.entities.Client;
import com.miniprojet.banquedigitale.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<Client> save(@RequestBody Client c) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client c) {
        return ResponseEntity.ok(service.update(id, c));
    }

    @GetMapping
    public ResponseEntity<List<Client>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/{id}/dto")
    public ResponseEntity<ClientDTO> getDTO(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDTO(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Client>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(service.search(keyword));
    }
}