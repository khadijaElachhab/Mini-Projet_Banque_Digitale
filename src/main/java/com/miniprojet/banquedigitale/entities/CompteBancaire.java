package com.miniprojet.banquedigitale.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.miniprojet.banquedigitale.enums.StatCompte;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_CPTE")
@Data @NoArgsConstructor @AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompteCourant.class, name = "COURANT"),
        @JsonSubTypes.Type(value = CompteEpargne.class, name = "EPARGNE")
})
public abstract class CompteBancaire {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreation;
    private double solde;

    @Enumerated(EnumType.STRING)
    private StatCompte statut;

    private String devise;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @JsonIgnore
    @OneToMany(mappedBy = "compte", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operation> operations = new ArrayList<>();
}