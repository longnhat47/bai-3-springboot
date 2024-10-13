package com.devnguyen.bai_3.repository.impl;

import com.devnguyen.bai_3.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeRepository {
    List<Employee> findAll();
    Optional<Employee> findById(UUID id);
    Employee save(Employee employee);
}