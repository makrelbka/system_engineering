package com.flowershop.service;

import com.flowershop.model.Order;
import com.flowershop.model.OrderItem;
import com.flowershop.model.Product;
import com.flowershop.repository.OrderRepository;
import com.flowershop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                       ProductRepository productRepository,
                       ProductService productService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Transactional
    public Order createOrder(String customerName, String customerEmail, String customerPhone,
                            String deliveryAddress, List<Map<String, Object>> items) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);
        order.setDeliveryAddress(deliveryAddress);
        order.setStatus(Order.OrderStatus.NEW);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (Map<String, Object> itemData : items) {
            Long productId = Long.valueOf(itemData.get("productId").toString());
            Integer quantity = Integer.valueOf(itemData.get("quantity").toString());

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Товар не найден: " + productId));

            if (product.getStock() < quantity) {
                throw new RuntimeException("Недостаточно товара '" + product.getName() + "'. Доступно: " + product.getStock());
            }

            productService.decreaseStock(productId, quantity, customerName, customerEmail);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);
            totalPrice += product.getPrice() * quantity;
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
