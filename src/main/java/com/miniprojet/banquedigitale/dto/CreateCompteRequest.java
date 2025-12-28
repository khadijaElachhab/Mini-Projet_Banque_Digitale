package com.miniprojet.banquedigitale.dto;

import lombok.Data;

@Data
public class CreateCompteRequest {
    private Long clientId;
    private double solde;
    private String type; // "COURANT" ou "EPARGNE"
    private Double decouvert;
    private Double tauxInteret;
}