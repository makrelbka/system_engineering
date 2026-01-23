package com.flowershop.controller;

import com.flowershop.model.CallbackRequest;
import com.flowershop.model.Order;
import com.flowershop.model.Product;
import com.flowershop.model.User;
import com.flowershop.service.AdminService;
import com.flowershop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    private final AdminService adminService;
    private final ProductService productService;

    public AdminController(AdminService adminService, ProductService productService) {
        this.adminService = adminService;
        this.productService = productService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        return adminService.authenticate(email, password)
                .map(admin -> ResponseEntity.ok(Map.of(
                    "success", true,
                    "id", admin.getId(),
                    "name", admin.getName(),
                    "email", admin.getEmail()
                )))
                .orElse(ResponseEntity.status(401).body(Map.of("success", false, "message", "Неверный email или пароль")));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PostMapping("/users")
    public ResponseEntity<?> createAdmin(@RequestBody Map<String, String> data) {
        try {
            User admin = adminService.createAdmin(
                data.get("email"),
                data.get("password"),
                data.get("name"),
                data.get("phone")
            );
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(adminService.getAllOrders());
    }

    @GetMapping("/orders/pending")
    public ResponseEntity<List<Order>> getPendingOrders() {
        return ResponseEntity.ok(adminService.getPendingOrders());
    }

    @GetMapping("/orders/completed")
    public ResponseEntity<List<Order>> getCompletedOrders() {
        return ResponseEntity.ok(adminService.getCompletedOrders());
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> data) {
        try {
            Order.OrderStatus status = Order.OrderStatus.valueOf(data.get("status"));
            Order order = adminService.updateOrderStatus(id, status);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/callbacks/pending")
    public ResponseEntity<List<CallbackRequest>> getPendingCallbacks() {
        return ResponseEntity.ok(adminService.getPendingCallbacks());
    }

    @GetMapping("/callbacks/completed")
    public ResponseEntity<List<CallbackRequest>> getCompletedCallbacks() {
        return ResponseEntity.ok(adminService.getCompletedCallbacks());
    }

    @PutMapping("/callbacks/{id}/complete")
    public ResponseEntity<?> markCallbackCompleted(@PathVariable Long id) {
        try {
            CallbackRequest request = adminService.markCallbackCompleted(id);
            return ResponseEntity.ok(request);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody Map<String, Object> data) {
        try {
            String name = data.get("name") != null ? data.get("name").toString() : null;
            String description = data.get("description") != null ? data.get("description").toString() : null;
            String image = data.get("image") != null ? data.get("image").toString() : null;
            Double price = data.get("price") != null ? Double.valueOf(data.get("price").toString()) : null;

            Product product = productService.createProduct(name, price, description, image);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
