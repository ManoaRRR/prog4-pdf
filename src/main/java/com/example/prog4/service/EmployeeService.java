package com.example.prog4.service;

import com.example.prog4.model.Employee;
import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.repository.Repository;
import com.example.prog4.repository.employee.EmployeeRepository;
import com.example.prog4.repository.employee.dao.EmployeeManagerDao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private EmployeeRepository entityRepository;
    private EmployeeManagerDao employeeManagerDao;
    private final Repository repository;


    public com.example.prog4.model.Employee getOne(String id) {
        return repository.getById(id);
    }

  public List<Employee> getAll(EmployeeFilter filter) {
    return repository.getAll(filter);
  }


  public void saveOne(com.example.prog4.model.Employee employee) {
        repository.save(employee);
    }
}
