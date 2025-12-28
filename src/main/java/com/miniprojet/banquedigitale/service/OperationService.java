package com.miniprojet.banquedigitale.service;

import com.miniprojet.banquedigitale.dto.OperationDTO;
import com.miniprojet.banquedigitale.dto.OperationRequest;
import com.miniprojet.banquedigitale.dto.VirementRequest;
import com.miniprojet.banquedigitale.entities.Operation;

import java.util.Date;
import java.util.List;

public interface OperationService {
    void versement(OperationRequest request);
    void retrait(OperationRequest request);
    void virement(VirementRequest request);
    List<Operation> list();
    List<Operation> getByCompte(Long compteId);
    List<Operation> getByDateRange(Long compteId, Date start, Date end);
    List<OperationDTO> getAllDTOs();
}
