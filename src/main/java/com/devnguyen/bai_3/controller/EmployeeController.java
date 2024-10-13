package com.devnguyen.bai_3.controller;

import com.devnguyen.bai_2.dto.ApiResponse;
import com.devnguyen.bai_2.exception.ApiException;
import com.devnguyen.bai_2.exception.ErrorCode;
import com.devnguyen.bai_2.utils.JsonResponse;
import com.devnguyen.bai_3.model.Employee;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new Employee(1, "Thắng", LocalDate.of(2001, 1, 1), true, 1200000, "0999999999", 1),
                    new Employee(2, "Hai", LocalDate.of(2002, 2, 2), true, 8000000,"0993332199", 2),
                    new Employee(3, "Tứ", LocalDate.of(2003, 3, 3), true, 1500000,"0991124299", 3)
            )
    );

    @GetMapping
    public ResponseEntity<ApiResponse<List<Employee>>> getAll(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "dobF", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE ) LocalDate dobF,
            @RequestParam(value = "dobT", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE ) LocalDate dobT,
            @RequestParam(value = "gender", required = false) Boolean gender,
            @RequestParam(value = "salary", required = false) Integer salary,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "departmentId", required = false) Integer department
    ) {
        List<Employee> list = employees.stream()
                .filter(emp -> (name == null || emp.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(emp -> (gender == null || emp.getGender() == gender))
                .filter(emp -> (dobF == null || !emp.getBirthday().isBefore(dobF)))
                .filter(emp -> (dobT == null || !emp.getBirthday().isAfter(dobT)))
//                .filter(emp -> (salary == null || emp.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(emp -> (phone == null || emp.getPhone().contains(phone)))
                .filter(emp -> (department == null || emp.getDepartmentId().equals(department)))
                .collect(Collectors.toList());
        return JsonResponse.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getById(@PathVariable("id") Integer id) {
        return employees.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .map(JsonResponse::ok)
                .orElseThrow(() -> new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> create(@RequestBody Employee employee) {
        employee.setId((int) (Math.random() * (100 - 3)) + 3);
        employees.add(employee);
        return JsonResponse.created(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> putById(@PathVariable("id") Integer id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> existingEmployee = employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();

        if (existingEmployee.isPresent()) {
            Employee emp = existingEmployee.get();
            emp.setName(updatedEmployee.getName());
            emp.setBirthday(updatedEmployee.getBirthday());
            emp.setGender(updatedEmployee.getGender());
            emp.setSalary(updatedEmployee.getSalary());
            emp.setPhone(updatedEmployee.getPhone());
            emp.setDepartmentId(updatedEmployee.getDepartmentId());

            return JsonResponse.ok(emp);
        } else {
            throw new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> deleteEmployee(@PathVariable Integer id) {
        Optional<Employee> existingEmployee = employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();

        if (existingEmployee.isPresent()) {
            employees.remove(existingEmployee.get());
            return JsonResponse.noContent();
        } else {
            throw new ApiException(ErrorCode.EMPLOYEE_NOT_EXIST);
        }
    }
}
