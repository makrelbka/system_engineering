package com.flowershop.controller;

import com.flowershop.model.CallbackRequest;
import com.flowershop.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/callback")
@CrossOrigin(origins = "http://localhost:5173")
public class CallbackController {
    private final AdminService adminService;
    
    public CallbackController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @PostMapping
    public ResponseEntity<?> createCallback(@RequestBody Map<String, String> data) {
        try {
            CallbackRequest request = adminService.createCallback(
                data.get("name"),
                data.get("phone")
            );
            return ResponseEntity.ok(Map.of("success", true, "message", "Заявка отправлена"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
