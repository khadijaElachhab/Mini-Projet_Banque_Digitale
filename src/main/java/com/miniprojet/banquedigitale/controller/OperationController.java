package com.miniprojet.banquedigitale.controller;

import com.miniprojet.banquedigitale.dto.*;
import com.miniprojet.banquedigitale.entities.Operation;
import com.miniprojet.banquedigitale.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OperationController {

    private final OperationService ops;

    @PostMapping("/versement")
    public ResponseEntity<Void> versement(@RequestBody OperationRequest request) {
        ops.versement(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/retrait")
    public ResponseEntity<Void> retrait(@RequestBody OperationRequest request) {
        ops.retrait(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/virement")
    public ResponseEntity<Void> virement(@RequestBody VirementRequest request) {
        ops.virement(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Operation>> list() {
        return ResponseEntity.ok(ops.list());
    }

    @GetMapping("/dto")
    public ResponseEntity<List<OperationDTO>> listDTOs() {
        return ResponseEntity.ok(ops.getAllDTOs());
    }

    @GetMapping("/compte/{compteId}")
    public ResponseEntity<List<Operation>> getByCompte(@PathVariable Long compteId) {
        return ResponseEntity.ok(ops.getByCompte(compteId));
    }

    @GetMapping("/compte/{compteId}/range")
    public ResponseEntity<List<Operation>> getByDateRange(
            @PathVariable Long compteId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        return ResponseEntity.ok(ops.getByDateRange(compteId, start, end));
    }
}