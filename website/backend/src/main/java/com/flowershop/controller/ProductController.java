package com.flowershop.controller;

import com.flowershop.model.Product;
import com.flowershop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/decrease")
    public ResponseEntity<?> decreaseStock(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        try {
            Integer quantity = (Integer) request.get("quantity");
            String customerName = (String) request.get("customerName");
            String customerEmail = (String) request.get("customerEmail");

            if (quantity == null || customerName == null || customerEmail == null) {
                return ResponseEntity.badRequest().body("Необходимы: quantity, customerName, customerEmail");
            }

            productService.decreaseStock(id, quantity, customerName, customerEmail);
            return ResponseEntity.ok(Map.of("success", true, "message", "Товар списан"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
