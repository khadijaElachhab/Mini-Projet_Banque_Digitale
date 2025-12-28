package com.miniprojet.banquedigitale.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CompteDTO {
    private Long id;
    private Date dateCreation;
    private double solde;
    private String statut;
    private String devise;
    private String type;
    private Double decouvert;
    private Double tauxInteret;
    private Long clientId;
    private String clientNom;
    private String clientEmail;
}
