package com.example.prog4.controller.validator;

import com.example.prog4.model.Employee;
import com.example.prog4.model.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.example.prog4.controller.validator.utils.StringValidator.isNotNullAndNotBlank;


@Component
@AllArgsConstructor
public class EmployeeValidator {
    private PhoneValidator phoneValidator;
    public void validate(Employee employee) {
        StringBuilder error = new StringBuilder();

        if (employee.getBirthDate() == null) {
            error.append("Birthdate is mandatory. ");
        } else if (employee.getBirthDate().isAfter(LocalDate.now())) {
            error.append("Birthdate could not be after today. ");
        }
        if (!isNotNullAndNotBlank(employee.getLastName())) {
            error.append("Last name is mandatory. ");
        }
        if (!isNotNullAndNotBlank(employee.getFirstName())) {
            error.append("First name is mandatory. ");
        }
        if (employee.getPhones() == null || employee.getPhones().isEmpty()) {
            error.append("At least one phone number is expected. ");
        } else {
            try {
                employee.getPhones().forEach(phoneValidator::validate);
            } catch (BadRequestException e){
                error.append(e.getMessage());
            }
        }
        if (!isNotNullAndNotBlank(employee.getAddress())) {
            error.append("Address is mandatory. ");
        }
        if (employee.getSex() == null) {
            error.append("Sex is mandatory. ");
        }
        if (!isNotNullAndNotBlank(employee.getCin())) {
            error.append("CIN is mandatory. ");
        }
        if (employee.getCsp() == null) {
            error.append("Socio-professional category is mandatory. ");
        }
        if (employee.getPositions() == null || employee.getPositions().size() == 0) {
            error.append("At least one position is expected. ");
        }
        if (employee.getEntranceDate() == null) {
            error.append("Entrance date is mandatory. ");
        } else if (employee.getEntranceDate().isBefore(employee.getBirthDate())) {
            error.append("Entrance date can't be before birthDate. ");
        } else if (employee.getEntranceDate().isAfter(LocalDate.now())) {
            error.append("Entrance date can't be after today. ");
        }
        if (employee.getEntranceDate() != null && employee.getDepartureDate() != null && employee.getDepartureDate().isBefore(employee.getEntranceDate())) {
            error.append("Departure date can't be before entrance date. ");
        }
        if (!isNotNullAndNotBlank(employee.getProfessionalEmail())) {
            error.append("Professional email is mandatory. ");
        }
        if (!isNotNullAndNotBlank(employee.getPersonalEmail())) {
            error.append("Personal email is mandatory. ");
        }
        if (!isNotNullAndNotBlank(employee.getCnaps())) {
            error.append("Cnaps is mandatory. ");
        }
        if (!error.isEmpty()) {
            throw new BadRequestException(error.toString());
        }
    }
}
