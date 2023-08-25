package com.example.prog4.repository.cnaps;

import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.repository.cnaps.entity.EmployeeCnaps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional("cnapsTransactionManager")
public interface EmployeeCnapsRepository extends JpaRepository<EmployeeCnaps, String> {
  EmployeeCnaps findByEndToEndId(String id);

}
