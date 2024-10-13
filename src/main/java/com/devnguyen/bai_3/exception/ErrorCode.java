package com.devnguyen.bai_3.exception;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    EMPLOYEE_NOT_EXIST(40402, "Employee is not exist.", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_EXIST(40401, "Department is not exist.", HttpStatus.NOT_FOUND);

    Integer code;
    String message;
    HttpStatus status;
}
