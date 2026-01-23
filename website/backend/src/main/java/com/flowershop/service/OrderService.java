package com.flowershop.service;

import com.flowershop.model.Order;
import com.flowershop.model.OrderItem;
import com.flowershop.model.Product;
import com.flowershop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                       ProductService productService) {
        this.orderRepository = orderRepository;
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

            Product product = productService.getProductById(productId)
                    .orElseThrow(() -> new RuntimeException("Товар не найден: " + productId));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getImage());
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());

            orderItems.add(orderItem);
            totalPrice += product.getPrice() * quantity;
        }

        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        String itemsSummary = orderItems.stream()
                .map(item -> item.getProductName() + " x" + item.getQuantity())
                .reduce((left, right) -> left + ", " + right)
                .orElse("-");
        logger.info("Создан заказ #{}: клиент={}, email={}, телефон={}, адрес={}, сумма=${}, товары=[{}]",
                savedOrder.getId(),
                savedOrder.getCustomerName(),
                savedOrder.getCustomerEmail(),
                savedOrder.getCustomerPhone(),
                savedOrder.getDeliveryAddress(),
                String.format("%.2f", savedOrder.getTotalPrice()),
                itemsSummary);

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
