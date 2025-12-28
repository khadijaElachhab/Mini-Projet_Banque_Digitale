package com.miniprojet.banquedigitale.dto;

import lombok.Data;

@Data
public class VirementRequest {
    private Long compteSource;
    private Long compteDestination;
    private double montant;
    private String description;
}