package com.miniprojet.banquedigitale.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.miniprojet.banquedigitale.enums.TypeOp;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateOp;
    private double montant;

    @Enumerated(EnumType.STRING)
    private TypeOp type;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CompteBancaire compte;

    @Transient
    private Long compteId;

    @PostLoad
    private void postLoad() {
        if (compte != null) {
            this.compteId = compte.getId();
        }
    }
}