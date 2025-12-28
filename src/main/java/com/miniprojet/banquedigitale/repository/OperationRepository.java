package com.miniprojet.banquedigitale.repository;

import com.miniprojet.banquedigitale.entities.Operation;
import com.miniprojet.banquedigitale.enums.TypeOp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByCompteIdOrderByDateOpDesc(Long compteId);

    List<Operation> findByType(TypeOp type);

    long countByType(TypeOp type);

    @Query("SELECT o FROM Operation o WHERE o.compte.id = :compteId " +
            "AND o.dateOp BETWEEN :startDate AND :endDate ORDER BY o.dateOp DESC")
    List<Operation> findByCompteAndDateRange(
            @Param("compteId") Long compteId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("SELECT o FROM Operation o ORDER BY o.dateOp DESC")
    List<Operation> findAllOrderByDateDesc();
}
