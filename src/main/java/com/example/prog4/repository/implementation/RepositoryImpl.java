package com.example.prog4.repository.implementation;

import com.example.prog4.controller.mapper.EmployeeMapper;
import com.example.prog4.model.Employee;
import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.Repository;
import com.example.prog4.repository.cnaps.EmployeeCnapsRepository;
import com.example.prog4.repository.cnaps.entity.EmployeeCnaps;
import com.example.prog4.repository.employee.EmployeeRepository;
import com.example.prog4.repository.employee.dao.EmployeeManagerDao;
import com.example.prog4.repository.mapper.EmployeeEntityMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RepositoryImpl implements Repository {
  private final EmployeeRepository repository;
  private final EmployeeCnapsRepository cnapsRepository;
  private final EmployeeEntityMapper entityMapper;
  private final EmployeeMapper mapper;
  private EmployeeManagerDao employeeManagerDao;
  private final EntityManager entityManager;
  @Override
  public Employee getById(String id) {
    com.example.prog4.repository.employee.entity.Employee employee = repository.findById(id).orElseThrow(() -> new NotFoundException("Not found id=" + id));
    EmployeeCnaps employeeCnaps = cnapsRepository.findByEndToEndId(id);
    if(employeeCnaps == null) {
      throw new NotFoundException("Not found id=" + id);
    }
    return mapper.toView(entityMapper.toDomain(employeeCnaps, employee));
  }

  @Override
  public Employee save(Employee employee) {
    EmployeeCnaps actual = cnapsRepository.findByEndToEndId(employee.getId());
    com.example.prog4.repository.employee.entity.Employee savedEmployee = repository.save(mapper.toDomain(employee, actual));
    cnapsRepository.save(entityMapper.toDomain(savedEmployee));
    return mapper.toView(savedEmployee);
  }

  @Override
  public List<Employee> getAll(EmployeeFilter filter) {
    Sort sort = Sort.by(filter.getOrderDirection(), filter.getOrderBy().toString());
    Pageable pageable = PageRequest.of(filter.getIntPage() - 1, filter.getIntPerPage(), sort);
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();

    CriteriaQuery<com.example.prog4.repository.employee.entity.Employee>
            employeeQuery =
            builder.createQuery(com.example.prog4.repository.employee.entity.Employee.class);
    Root<com.example.prog4.repository.employee.entity.Employee> employeeRoot = employeeQuery.from(
            com.example.prog4.repository.employee.entity.Employee.class);

    List<com.example.prog4.repository.employee.entity.Employee> employees =
            employeeManagerDao.findByCriteria(
                    entityManager, builder, employeeQuery, employeeRoot, filter.getLastName(), filter.getFirstName(),
                    filter.getCountryCode(),
                    filter.getSex(), filter.getPosition(), filter.getEntrance(), filter.getDeparture(),
                    pageable
            );

    List<EmployeeCnaps> employeeCnaps = cnapsRepository.findAll();
    Map<String, String> map = employeeCnaps.stream()
            .collect(Collectors.toMap(EmployeeCnaps::getEndToEndId, EmployeeCnaps::getCnaps));
    employees.forEach(employee -> {
      String cnaps = map.get(employee.getId());
      if(cnaps != null){
        employee.setCnaps(cnaps);
      }
    });
    return employees.stream()
            .map(mapper::toView)
            .toList();
  }

}
