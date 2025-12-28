package com.miniprojet.banquedigitale.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("COURANT")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompteCourant extends CompteBancaire {
    private double decouvert;
}