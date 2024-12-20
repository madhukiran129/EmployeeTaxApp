package com.example.employeetaxapp.service;

import com.example.employeetaxapp.dto.TaxDetailsResponse;
import com.example.employeetaxapp.model.Employee;
import com.example.employeetaxapp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public TaxDetailsResponse calculateTax(Employee employee) {
        double yearlySalary = calculateYearlySalary(employee);
        double tax = calculateTaxAmount(yearlySalary);
        double cess = calculateCessAmount(yearlySalary);

        return new TaxDetailsResponse(
            employee.getId().toString(),
            employee.getFirstName(),
            employee.getLastName(),
            yearlySalary,
            tax,
            cess
        );
    }

    private double calculateYearlySalary(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        int monthsWorked = Period.between(employee.getDateOfJoining(), currentDate).getMonths();
        return employee.getSalary() * monthsWorked;
    }

    private double calculateTaxAmount(double yearlySalary) {
        double tax = 0;
        if (yearlySalary > 250000) {
            tax += Math.min(yearlySalary - 250000, 250000) * 0.05;
        }
        if (yearlySalary > 500000) {
            tax += Math.min(yearlySalary - 500000, 500000) * 0.10;
        }
        if (yearlySalary > 1000000) {
            tax += (yearlySalary - 1000000) * 0.20;
        }
        return tax;
    }

    private double calculateCessAmount(double yearlySalary) {
        return yearlySalary > 2500000 ? (yearlySalary - 2500000) * 0.02 : 0;
    }
}