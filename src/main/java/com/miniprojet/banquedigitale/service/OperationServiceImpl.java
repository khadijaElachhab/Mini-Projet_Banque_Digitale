package com.miniprojet.banquedigitale.service;

import com.miniprojet.banquedigitale.dto.OperationDTO;
import com.miniprojet.banquedigitale.dto.OperationRequest;
import com.miniprojet.banquedigitale.dto.VirementRequest;
import com.miniprojet.banquedigitale.entities.*;
import com.miniprojet.banquedigitale.enums.TypeOp;
import com.miniprojet.banquedigitale.repository.CompteRepository;
import com.miniprojet.banquedigitale.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OperationServiceImpl implements OperationService {

    private final CompteRepository compteRepo;
    private final OperationRepository opRepo;

    @Override
    public void versement(OperationRequest request) {
        if (request.getMontant() <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }

        CompteBancaire cp = compteRepo.findById(request.getCompteId())
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));

        Operation op = new Operation();
        op.setCompte(cp);
        op.setDateOp(new Date());
        op.setMontant(request.getMontant());
        op.setType(TypeOp.CREDIT);
        op.setDescription(request.getDescription() != null ?
                request.getDescription() : "Versement");

        cp.setSolde(cp.getSolde() + request.getMontant());

        opRepo.save(op);
        compteRepo.save(cp);
    }

    @Override
    public void retrait(OperationRequest request) {
        if (request.getMontant() <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }

        CompteBancaire cp = compteRepo.findById(request.getCompteId())
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));

        if (cp instanceof CompteCourant) {
            CompteCourant cc = (CompteCourant) cp;
            if (cp.getSolde() - request.getMontant() < -cc.getDecouvert()) {
                throw new IllegalArgumentException("Découvert dépassé");
            }
        } else if (cp.getSolde() < request.getMontant()) {
            throw new IllegalArgumentException("Solde insuffisant");
        }

        Operation op = new Operation();
        op.setCompte(cp);
        op.setDateOp(new Date());
        op.setMontant(request.getMontant());
        op.setType(TypeOp.DEBIT);
        op.setDescription(request.getDescription() != null ?
                request.getDescription() : "Retrait");

        cp.setSolde(cp.getSolde() - request.getMontant());

        opRepo.save(op);
        compteRepo.save(cp);
    }

    @Override
    public void virement(VirementRequest request) {
        if (request.getMontant() <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }

        OperationRequest retrait = new OperationRequest();
        retrait.setCompteId(request.getCompteSource());
        retrait.setMontant(request.getMontant());
        retrait.setDescription("Virement vers compte " + request.getCompteDestination());

        OperationRequest versement = new OperationRequest();
        versement.setCompteId(request.getCompteDestination());
        versement.setMontant(request.getMontant());
        versement.setDescription("Virement depuis compte " + request.getCompteSource());

        retrait(retrait);
        versement(versement);
    }

    @Override
    public List<Operation> list() {
        return opRepo.findAllOrderByDateDesc();
    }

    @Override
    public List<Operation> getByCompte(Long compteId) {
        return opRepo.findByCompteIdOrderByDateOpDesc(compteId);
    }

    @Override
    public List<Operation> getByDateRange(Long compteId, Date start, Date end) {
        return opRepo.findByCompteAndDateRange(compteId, start, end);
    }

    @Override
    public List<OperationDTO> getAllDTOs() {
        return opRepo.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OperationDTO mapToDTO(Operation o) {
        OperationDTO dto = new OperationDTO();
        dto.setId(o.getId());
        dto.setDateOp(o.getDateOp());
        dto.setMontant(o.getMontant());
        dto.setType(o.getType().name());
        dto.setDescription(o.getDescription());
        dto.setCompteId(o.getCompte().getId());
        return dto;
    }
}