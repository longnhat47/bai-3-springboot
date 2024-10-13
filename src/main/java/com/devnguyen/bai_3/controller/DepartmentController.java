package com.devnguyen.bai_3.controller;

import com.devnguyen.bai_2.dto.ApiResponse;
import com.devnguyen.bai_2.exception.ApiException;
import com.devnguyen.bai_2.exception.ErrorCode;
import com.devnguyen.bai_2.utils.JsonResponse;
import com.devnguyen.bai_3.model.Department;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private List<Department> departments = new ArrayList<>(
            Arrays.asList(
                    new Department(1, "ABC"),
                    new Department(2, "ACS"),
                    new Department(3, "AST")
            )
    );

    @GetMapping
    public ResponseEntity<ApiResponse<List<Department>>> getAll() {
        return JsonResponse.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> getById(@PathVariable("id") UUID id) {
        return departments.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .map(JsonResponse::ok)
                .orElseThrow(() -> new ApiException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Department>> create(@RequestBody Department DEPARTMENT) {
        DEPARTMENT.setId((int) (Math.random() * (100 - 3)) + 3);
        departments.add(DEPARTMENT);
        return JsonResponse.created(DEPARTMENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> putById(@PathVariable("id") UUID id, @RequestBody Department updatedDepartment) {
        Optional<Department> existingDepartment = departments.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();

        if (existingDepartment.isPresent()) {
            Department emp = existingDepartment.get();
            emp.setName(updatedDepartment.getName());

            return JsonResponse.ok(emp);
        } else {
            throw new ApiException(ErrorCode.DEPARTMENT_NOT_EXIST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> deleteDepartment(@PathVariable UUID id) {
        Optional<Department> existingDepartment = departments.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst();

        if (existingDepartment.isPresent()) {
            departments.remove(existingDepartment.get());
            return JsonResponse.noContent();
        } else {
            throw new ApiException(ErrorCode.DEPARTMENT_NOT_EXIST);
        }
    }
}
