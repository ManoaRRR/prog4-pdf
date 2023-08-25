package com.example.prog4.repository.employee;

import com.example.prog4.repository.employee.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("employeeTransactionManager")
public interface PhoneRepository extends JpaRepository<Phone, String> {
    @Query(value = "select * from \"phone\" p where p.value = :value", nativeQuery = true)
    Optional<Phone> findOneByValue(@Param("value") String value);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM \"phone\" p WHERE p.value = :value)", nativeQuery = true)
    boolean isPhoneAlreadyExist(@Param("value") String value);

    @Query(value = "SELECT EXISTS (SELECT 1 FROM \"phone\" p WHERE p.id = :id AND p.employee_id IS NOT NULL AND p.employee_id != :employee_id)", nativeQuery = true)
    boolean isPhoneAlreadyUsedByOthers(@Param("id") String id, @Param("employee_id") String employeeId);
}
