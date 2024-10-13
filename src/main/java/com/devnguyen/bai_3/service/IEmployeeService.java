package com.devnguyen.bai_3.service;
import com.devnguyen.bai_3.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface IEmployeeService {
    List<Employee> findAll();
    Optional<Employee> findById(Integer id);
    Employee save(Employee employee);
}