package com.example.employeetaxapp.repository;

import com.example.employeetaxapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
