package com.jewelry.jewelryshopbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/user/profile")
    public ResponseEntity<String> userProfile() {
        return ResponseEntity.ok("USER or ADMIN can access this endpoint");
    }

    @GetMapping("/api/admin/dashboard")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Only ADMIN can access this endpoint");
    }
}