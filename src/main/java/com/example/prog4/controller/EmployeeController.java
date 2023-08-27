package com.example.prog4.controller;

import com.example.prog4.controller.mapper.EmployeeMapper;
import com.example.prog4.controller.validator.EmployeeValidator;
import com.example.prog4.model.Employee;
import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.service.CSVUtils;
import com.example.prog4.service.EmployeeService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/server/employee")
public class EmployeeController {
    private EmployeeMapper employeeMapper;
    private EmployeeValidator employeeValidator;
    private EmployeeService employeeService;
    private SpringTemplateEngine templateEngine;

    @GetMapping("/list/csv")
    public ResponseEntity<byte[]> getCsv(HttpSession session) {
        EmployeeFilter filters = (EmployeeFilter) session.getAttribute("employeeFiltersSession");
        List<Employee> data = employeeService.getAll(filters);

        String csv = CSVUtils.convertToCSV(data);
        byte[] bytes = csv.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "employees.csv");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }


    @GetMapping("/list/filters/clear")
    public String clearFilters(HttpSession session) {
        session.removeAttribute("employeeFilters");
        return "redirect:/employee/list";
    }

    @PostMapping("/createOrUpdate")
    public String saveOne(@ModelAttribute Employee employee) {
        employeeValidator.validate(employee);
        employeeService.saveOne(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/list/pdf")
    public void exportPdfForEmployeeList(HttpServletResponse response, HttpSession session) throws IOException, DocumentException {
        EmployeeFilter filters = (EmployeeFilter) session.getAttribute("employeeFiltersSession");
        List<Employee> data = employeeService.getAll(filters);

        String html = generateHtmlFromEmployees(data); // Generate HTML for the employee list
        generatePdfFromHtml(html, response); // Generate and send the PDF
    }

    // ... other methods ...

    private String generateHtmlFromEmployees(List<Employee> employees) throws IOException {
        StringBuilder htmlBuilder = new StringBuilder();

        // Begin the HTML structure
        htmlBuilder.append("<!DOCTYPE html>\n<html><head><style>")
                .append("body { font-family: Arial, sans-serif; }")
                .append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }")
                .append("th, td { border: 1px solid #000; padding: 8px; text-align: left; }")
                .append("</style></head><body>");
        htmlBuilder.append("<table>");
        htmlBuilder.append("<tr><th>Image</th><th>Last name</th><th>First name</th><th>CNAPS</th><th>Address</th><th>Phone</th><th>CIN</th><th>Departure date</th><th>Entrance date</th><th>Personal Email</th><th>Professional Email</th></tr>");
        // Generate employee list HTML
        for (Employee employee : employees) {
            htmlBuilder.append("<tr>");
            if (employee.getImage() != null && !employee.getImage().isEmpty()) {
                // Convert image data to base64 and include it in the HTML
                String imageData = Base64.getEncoder().encodeToString(employee.getImage().getBytes());
                htmlBuilder.append("<img src='data:image/jpeg;base64,").append(imageData).append("' width='100' height='100'/>");
            } else {
                htmlBuilder.append("No Image");
            }
            htmlBuilder
                    .append("<td>").append(employee.getLastName()).append("</td>")
                    .append("<td>").append(employee.getFirstName()).append("</td>")
                    .append("<td>").append(employee.getCnaps()).append("</td>")
                    .append("<td>").append(employee.getAddress()).append("</td>")
                    .append("<td>").append(employee.getPhones()).append("</td>")
                    .append("<td>").append(employee.getCin()).append("</td>")
                    .append("<td>").append(employee.getDepartureDate()).append("</td>")
                    .append("<td>").append(employee.getEntranceDate()).append("</td>")
                    .append("<td>").append(employee.getPersonalEmail()).append("</td>")
                    .append("<td>").append(employee.getProfessionalEmail()).append("</td>")
                    .append("</tr>");
        }

        htmlBuilder.append("</table>");
        // Close the HTML structure
        htmlBuilder.append("</body></html>");

        return htmlBuilder.toString();
    }

    private void generatePdfFromHtml(String html, HttpServletResponse response) throws IOException, DocumentException {
        String outputFileName = "employees.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + outputFileName);

        try (OutputStream outputStream = response.getOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);

            String baseUrl = "http://localhost:8080";
            renderer.getSharedContext().setBaseURL(baseUrl);

            renderer.layout();
            renderer.createPDF(outputStream);
        }
    }
}

