package com.miniprojet.banquedigitale.service;

import com.miniprojet.banquedigitale.dto.CompteDTO;
import com.miniprojet.banquedigitale.dto.CreateCompteRequest;
import com.miniprojet.banquedigitale.entities.*;
import com.miniprojet.banquedigitale.enums.StatCompte;
import com.miniprojet.banquedigitale.repository.ClientRepository;
import com.miniprojet.banquedigitale.repository.CompteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository repo;
    private final ClientRepository clientRepo;

    @Override
    public CompteBancaire creerCompte(CreateCompteRequest request) {
        Client client = clientRepo.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));

        if ("COURANT".equalsIgnoreCase(request.getType())) {
            return creerCompteCourant(client, request.getSolde(),
                    request.getDecouvert() != null ? request.getDecouvert() : 0);
        } else if ("EPARGNE".equalsIgnoreCase(request.getType())) {
            return creerCompteEpargne(client, request.getSolde(),
                    request.getTauxInteret() != null ? request.getTauxInteret() : 0);
        }
        throw new IllegalArgumentException("Type de compte invalide");
    }

    @Override
    public CompteCourant creerCompteCourant(Client c, double solde, double decouvert) {
        CompteCourant cc = new CompteCourant();
        cc.setClient(c);
        cc.setSolde(solde);
        cc.setDecouvert(decouvert);
        cc.setDateCreation(new Date());
        cc.setStatut(StatCompte.CREATED);
        cc.setDevise("MAD");
        return repo.save(cc);
    }

    @Override
    public CompteEpargne creerCompteEpargne(Client c, double solde, double taux) {
        CompteEpargne ce = new CompteEpargne();
        ce.setClient(c);
        ce.setSolde(solde);
        ce.setTauxInteret(taux);
        ce.setDateCreation(new Date());
        ce.setStatut(StatCompte.CREATED);
        ce.setDevise("MAD");
        return repo.save(ce);
    }

    @Override
    public CompteBancaire get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));
    }

    @Override
    public List<CompteBancaire> list() {
        return repo.findAll();
    }

    @Override
    public List<CompteBancaire> getByClient(Long clientId) {
        return repo.findByClientId(clientId);
    }

    @Override
    public List<Operation> getOperations(Long compteId) {
        CompteBancaire compte = get(compteId);
        return compte.getOperations();
    }

    @Override
    public CompteDTO getDTO(Long id) {
        CompteBancaire c = get(id);
        return mapToDTO(c);
    }

    @Override
    public List<CompteDTO> getAllDTOs() {
        return repo.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void changeStatut(Long id, String statut) {
        CompteBancaire compte = get(id);
        compte.setStatut(StatCompte.valueOf(statut.toUpperCase()));
        repo.save(compte);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Compte non trouvé");
        }
        repo.deleteById(id);
    }

    private CompteDTO mapToDTO(CompteBancaire c) {
        CompteDTO dto = new CompteDTO();
        dto.setId(c.getId());
        dto.setDateCreation(c.getDateCreation());
        dto.setSolde(c.getSolde());
        dto.setStatut(c.getStatut().name());
        dto.setDevise(c.getDevise());
        dto.setClientId(c.getClient().getId());
        dto.setClientNom(c.getClient().getNom());
        dto.setClientEmail(c.getClient().getEmail());

        if (c instanceof CompteCourant) {
            dto.setType("COURANT");
            dto.setDecouvert(((CompteCourant) c).getDecouvert());
        } else if (c instanceof CompteEpargne) {
            dto.setType("EPARGNE");
            dto.setTauxInteret(((CompteEpargne) c).getTauxInteret());
        }

        return dto;
    }
}
