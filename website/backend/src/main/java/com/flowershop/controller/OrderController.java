package com.flowershop.controller;

import com.flowershop.model.Order;
import com.flowershop.service.OrderService;
import com.flowershop.service.WebSocketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    private final OrderService orderService;
    private final WebSocketService webSocketService;
    
    public OrderController(OrderService orderService, WebSocketService webSocketService) {
        this.orderService = orderService;
        this.webSocketService = webSocketService;
    }
    
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request) {
        try {
            String customerName = (String) request.get("customerName");
            String customerEmail = (String) request.get("customerEmail");
            String customerPhone = (String) request.get("customerPhone");
            String deliveryAddress = (String) request.get("deliveryAddress");
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
            
            if (items == null || items.isEmpty()) {
                return ResponseEntity.badRequest().body("Список товаров пуст");
            }
            
            // Создаем заказ (внутри списываются товары)
            Order order = orderService.createOrder(customerName, customerEmail, customerPhone, 
                                                  deliveryAddress, items);
            
            // Отправляем обновление через WebSocket
            webSocketService.notifyProductUpdate();
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("success", true, "message", "Заказ создан", "orderId", order.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
