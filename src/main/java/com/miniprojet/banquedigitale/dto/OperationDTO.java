package com.miniprojet.banquedigitale.dto;

import lombok.Data;
import java.util.Date;

@Data
public class OperationDTO {
    private Long id;
    private Date dateOp;
    private double montant;
    private String type;
    private String description;
    private Long compteId;
}