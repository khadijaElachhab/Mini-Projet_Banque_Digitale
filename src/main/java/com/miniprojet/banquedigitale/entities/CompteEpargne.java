package com.miniprojet.banquedigitale.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("EPARGNE")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompteEpargne extends CompteBancaire {
    private double tauxInteret;
}