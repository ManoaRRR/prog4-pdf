package com.example.prog4.service;

import com.example.prog4.model.Employee;
import com.example.prog4.model.Phone;
import com.example.prog4.model.exception.InternalServerErrorException;
import com.example.prog4.repository.employee.entity.Position;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class CSVUtils {
    public static String convertToCSV(List<Employee> data) {
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer);

            // Write the header row
            String[] header = {
                    "Id",
                    "RegistrationNumber",
                    "LastName",
                    "FirstName",
                    "PersonalEmail",
                    "ProfessionalEmail",
                    "CIN",
                    "CNAPS",
                    "ChildrenNumber",
                    "BirthDate",
                    "EntranceDate",
                    "DepartureDate",
                    "Sex",
                    "CSP",
                    "Address",
                    "Phones",
                    "Positions"
            };
            csvWriter.writeNext(header);

            // Write the data rows
            for (Employee employee : data) {
                String[] row = {
                        employee.getId(),
                        employee.getRegistrationNumber(),
                        employee.getLastName(),
                        employee.getFirstName(),
                        employee.getPersonalEmail(),
                        employee.getProfessionalEmail(),
                        employee.getCin(),
                        employee.getCnaps(),
                        employee.getChildrenNumber().toString(),
                        employee.getBirthDate().toString(),
                        employee.getEntranceDate().toString(),
                        employee.getDepartureDate().toString(),
                        employee.getSex().toString(),
                        employee.getCsp().toString(),
                        employee.getAddress(),
                        String.join(";", employee.getPhones().stream().map(Phone::getValue).toList()),
                        String.join(";", employee.getPositions().stream().map(Position::getName).toList()),
                };
                csvWriter.writeNext(row);
            }

            csvWriter.close();
            return writer.toString();
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}