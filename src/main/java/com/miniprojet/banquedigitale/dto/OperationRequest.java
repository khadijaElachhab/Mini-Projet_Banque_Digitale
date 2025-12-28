package com.miniprojet.banquedigitale.dto;

import lombok.Data;

@Data
public class OperationRequest {
    private Long compteId;
    private double montant;
    private String description;
}