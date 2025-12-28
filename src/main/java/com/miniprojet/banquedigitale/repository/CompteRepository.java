package com.miniprojet.banquedigitale.repository;

import com.miniprojet.banquedigitale.entities.CompteBancaire;
import com.miniprojet.banquedigitale.enums.StatCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompteRepository extends JpaRepository<CompteBancaire, Long> {

    List<CompteBancaire> findByClientId(Long clientId);

    List<CompteBancaire> findByStatut(StatCompte statut);

    long countByStatut(StatCompte statut);

    @Query("SELECT SUM(c.solde) FROM CompteBancaire c")
    Double getTotalSolde();

    @Query("SELECT COUNT(c) FROM CompteBancaire c WHERE TYPE(c) = :type")
    long countByType(@Param("type") String type);
}