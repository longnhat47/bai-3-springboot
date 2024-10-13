package com.devnguyen.bai_3.repository.impl;

import com.devnguyen.bai_3.model.Employee;
import org.springframework.stereotype.Repository;
import com.devnguyen.bai_3.repository.impl.IEmployeeRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
    private List<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new Employee(1, "Thắng", LocalDate.of(2001, 1, 1), true, 1200000, "0999999999", 1),
                    new Employee(2, "Hai", LocalDate.of(2002, 2, 2), true, 8000000,"0993332199", 2),
                    new Employee(3, "Tứ", LocalDate.of(2003, 3, 3), true, 1500000,"0991124299", 3)
            )
    ); // Model nhỏ

    public List<Employee> findAll(query) {
        return employees.stream()
                .filter(emp -> (query.name == null || emp.getName().toLowerCase().contains(query.name.toLowerCase())))
                .filter(emp -> (query.gender == null || emp.getGender() == query.gender))
                .filter(emp -> (query.dobF == null || !emp.getBirthday().isBefore(query.dobF)))
                .filter(emp -> (query.dobT == null || !emp.getBirthday().isAfter(query.dobT)))
//                .filter(emp -> (query.salary == null || emp.getName().toLowerCase().contains(query.name.toLowerCase())))
                .filter(emp -> (query.phone == null || emp.getPhone().contains(query.phone)))
                .filter(emp -> (query.department == null || emp.getDepartmentId().equals(query.department)))
                .collect(Collectors.toList());;
    }

    public Optional<Employee> findById(Integer id) {
        return employees.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .map(JsonResponse::ok)
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    // CRUD
    public Employee save(Employee employee) {
        employee.setId((int) (Math.random() * (100 - 3)) + 3);
        employees.add(employee);

        return employee;
    }
}