package com.devnguyen.bai_3.utils;

import com.devnguyen.bai_2.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class JsonResponse {
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(ApiResponse.<T>builder().data(data).build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<T>builder().data(data).build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> noContent() {
        return ResponseEntity.notFound().build();
    }
}