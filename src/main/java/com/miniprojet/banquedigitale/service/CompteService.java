package com.miniprojet.banquedigitale.service;

import com.miniprojet.banquedigitale.dto.CompteDTO;
import com.miniprojet.banquedigitale.dto.CreateCompteRequest;
import com.miniprojet.banquedigitale.entities.*;

import java.util.List;

public interface CompteService {
    CompteBancaire creerCompte(CreateCompteRequest request);
    CompteCourant creerCompteCourant(Client client, double solde, double decouvert);
    CompteEpargne creerCompteEpargne(Client client, double solde, double taux);
    CompteBancaire get(Long id);
    List<CompteBancaire> list();
    List<CompteBancaire> getByClient(Long clientId);
    List<Operation> getOperations(Long compteId);
    CompteDTO getDTO(Long id);
    List<CompteDTO> getAllDTOs();
    void changeStatut(Long id, String statut);
    void delete(Long id);
}
