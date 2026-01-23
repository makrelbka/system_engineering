package com.flowershop.service;

import com.flowershop.model.CallbackRequest;
import com.flowershop.model.Order;
import com.flowershop.model.User;
import com.flowershop.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final OrderRepository orderRepository;
    private final List<User> adminUsers = new ArrayList<>();
    private final List<CallbackRequest> callbackRequests = new ArrayList<>();
    private final AtomicLong adminIdSequence = new AtomicLong(1);
    private final AtomicLong callbackIdSequence = new AtomicLong(1);

    public AdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void initAdmins() {
        if (adminUsers.isEmpty()) {
            adminUsers.add(new User(
                adminIdSequence.getAndIncrement(),
                "admin@flowershop.ru",
                "admin",
                "Администратор",
                "+7 (495) 123-45-67",
                User.UserRole.ADMIN
            ));
        }
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> result = adminUsers.stream()
                .filter(user -> user.getEmail().equals(email)
                        && user.getPassword().equals(password)
                        && user.getRole() == User.UserRole.ADMIN)
                .findFirst();
        if (result.isPresent()) {
            logger.info("Вход в админку: {}", email);
        } else {
            logger.warn("Неудачная попытка входа в админку: {}", email);
        }
        return result;
    }

    public List<User> getAllAdmins() {
        return adminUsers.stream()
                .filter(user -> user.getRole() == User.UserRole.ADMIN)
                .toList();
    }

    @Transactional
    public User createAdmin(String email, String password, String name, String phone) {
        boolean exists = adminUsers.stream().anyMatch(user -> user.getEmail().equals(email));
        if (exists) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        User admin = new User(adminIdSequence.getAndIncrement(), email, password, name, phone, User.UserRole.ADMIN);
        adminUsers.add(admin);
        logger.info("Создан администратор: {}", email);
        return admin;
    }

    @Transactional
    public void deleteAdmin(Long id) {
        User admin = adminUsers.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Администратор не найден"));
        if (admin.getRole() != User.UserRole.ADMIN) {
            throw new RuntimeException("Пользователь не является администратором");
        }
        adminUsers.remove(admin);
        logger.info("Удален администратор: {}", admin.getEmail());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getPendingOrders() {
        return orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.NEW ||
                               order.getStatus() == Order.OrderStatus.IN_PROCESS)
                .toList();
    }

    public List<Order> getCompletedOrders() {
        return orderRepository.findAll().stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.COMPLETED)
                .toList();
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));
        order.setStatus(status);
        Order savedOrder = orderRepository.save(order);
        logger.info("Обновлен статус заказа #{}: {}", orderId, status);
        return savedOrder;
    }

    public List<CallbackRequest> getPendingCallbacks() {
        return callbackRequests.stream()
                .filter(request -> !request.isCompleted())
                .toList();
    }

    public List<CallbackRequest> getCompletedCallbacks() {
        return callbackRequests.stream()
                .filter(CallbackRequest::isCompleted)
                .toList();
    }

    @Transactional
    public CallbackRequest markCallbackCompleted(Long id) {
        CallbackRequest request = callbackRequests.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));
        request.setCompleted(true);
        logger.info("Заявка на звонок отмечена как выполненная: #{}", id);
        return request;
    }

    @Transactional
    public CallbackRequest createCallback(String name, String phone) {
        CallbackRequest request = new CallbackRequest(
                callbackIdSequence.getAndIncrement(),
                name,
                phone,
                LocalDateTime.now(),
                false
        );
        callbackRequests.add(request);
        logger.info("Создана заявка на звонок: имя={}, телефон={}, id={}", name, phone, request.getId());
        return request;
    }
}
