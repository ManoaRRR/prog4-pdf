package com.example.prog4.repository.mapper;

import com.example.prog4.repository.cnaps.entity.EmployeeCnaps;
import com.example.prog4.repository.employee.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class EmployeeEntityMapper {
  public Employee toDomain(EmployeeCnaps employeeCnaps, Employee employee) {
    employee.setCnaps(employeeCnaps.getCnaps());
    return employee;
  }
  public Employee toDomainList(List<EmployeeCnaps> employeesCnapsList, List<Employee> employeesList) {
    if (employeesCnapsList.size() != employeesList.size()) {
      throw new IllegalArgumentException("Input lists must have the same size");
    }

    List<Employee> result = IntStream.range(0, employeesCnapsList.size())
            .mapToObj(i -> {
              EmployeeCnaps employeeCnaps = employeesCnapsList.get(i);
              Employee employee = employeesList.get(i);
              employee.setCnaps(employeeCnaps.getCnaps());
              return employee;
            })
            .collect(Collectors.toList());

    return (Employee) result;
  }

  public EmployeeCnaps toDomain(Employee employee) {
    return EmployeeCnaps.builder()
        .cin(employee.getCin())
        .cnaps(employee.getCnaps())
        .image(employee.getImage())
        .address(employee.getAddress())
        .lastName(employee.getLastName())
        .firstName(employee.getFirstName())
        .personalEmail(employee.getPersonalEmail())
        .professionalEmail(employee.getProfessionalEmail())
        .registrationNumber(employee.getRegistrationNumber())
        .birthDate(employee.getBirthDate())
        .entranceDate(employee.getEntranceDate())
        .departureDate(employee.getDepartureDate())
        .childrenNumber(employee.getChildrenNumber())
        .sex(employee.getSex())
        .csp(employee.getCsp())
        .endToEndId(employee.getId())
        .build();
  }

}
