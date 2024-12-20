package com.example.employeetaxapp.controller;

import com.example.employeetaxapp.dto.TaxDetailsResponse;
import com.example.employeetaxapp.model.Employee;
import com.example.employeetaxapp.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @GetMapping("/{id}/tax-details")
    public ResponseEntity<TaxDetailsResponse> getTaxDetails(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeService.calculateTax(employee));
    }
}
