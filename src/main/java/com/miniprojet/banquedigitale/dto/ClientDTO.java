package com.miniprojet.banquedigitale.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String email;
    private int nombreComptes;
}
