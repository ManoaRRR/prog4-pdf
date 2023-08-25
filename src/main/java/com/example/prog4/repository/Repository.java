package com.example.prog4.repository;

import com.example.prog4.model.Employee;
import com.example.prog4.model.EmployeeFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Repository {
  Employee getById(String id);
  Employee save(Employee employee);
  List<Employee> getAll(EmployeeFilter filter);
}
