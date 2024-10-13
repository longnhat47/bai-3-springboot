package com.devnguyen.bai_3.service.impl;

import com.devnguyen.bai_3.model.Employee;
import com.devnguyen.bai_3.repository.impl.EmployeeRepository;
import com.devnguyen.bai_3.service.IEmployeeService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService implements IEmployeeService {
    EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    // CRUD
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}