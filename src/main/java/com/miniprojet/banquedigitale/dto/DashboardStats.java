package com.miniprojet.banquedigitale.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStats {
    private long totalClients;
    private long totalComptes;
    private long totalOperations;
    private double soldeTotal;
    private long comptesActifs;
    private long comptesSuspendus;

    private long comptesCourants;
    private long comptesEpargnes;
    private long operationsVersements;
    private long operationsRetraits;
    private long operationsVirements;
    private double montantTotalVersements;
    private double montantTotalRetraits;

    private List<String> moisLabels;
    private List<Long> operationsParMois;

    private Map<String, Long> comptesParType;
    private Map<String, Long> operationsParType;
}
