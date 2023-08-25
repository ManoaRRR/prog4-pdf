package com.example.prog4.repository.cnaps;

import com.example.prog4.repository.cnaps.entity.CnapsEmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("cnapsTransactionManager")
@Repository
public interface EmployeeCnapsPositionRepository
    extends JpaRepository<CnapsEmployeePosition, String> {
}
