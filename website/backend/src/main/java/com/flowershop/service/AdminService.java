package com.flowershop.service;

import com.flowershop.model.CallbackRequest;
import com.flowershop.model.Order;
import com.flowershop.model.User;
import com.flowershop.repository.CallbackRequestRepository;
import com.flowershop.repository.OrderRepository;
import com.flowershop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CallbackRequestRepository callbackRequestRepository;

    public AdminService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        CallbackRequestRepository callbackRequestRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.callbackRequestRepository = callbackRequestRepository;
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> result = userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password)
                        && user.getRole() == User.UserRole.ADMIN);
        if (result.isPresent()) {
            logger.info("Вход в админку: {}", email);
        } else {
            logger.warn("Неудачная попытка входа в админку: {}", email);
        }
        return result;
    }

    public List<User> getAllAdmins() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.UserRole.ADMIN)
                .toList();
    }

    @Transactional
    public User createAdmin(String email, String password, String name, String phone) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }
        User admin = new User(email, password, name, phone, User.UserRole.ADMIN);
        User savedAdmin = userRepository.save(admin);
        logger.info("Создан администратор: {}", email);
        return savedAdmin;
    }

    @Transactional
    public void deleteAdmin(Long id) {
        User admin = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Администратор не найден"));
        if (admin.getRole() != User.UserRole.ADMIN) {
            throw new RuntimeException("Пользователь не является администратором");
        }
        userRepository.delete(admin);
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
        return callbackRequestRepository.findByCompletedOrderByCreatedAtDesc(false);
    }

    public List<CallbackRequest> getCompletedCallbacks() {
        return callbackRequestRepository.findByCompletedOrderByCreatedAtDesc(true);
    }

    @Transactional
    public CallbackRequest markCallbackCompleted(Long id) {
        CallbackRequest request = callbackRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));
        request.setCompleted(true);
        CallbackRequest savedRequest = callbackRequestRepository.save(request);
        logger.info("Заявка на звонок отмечена как выполненная: #{}", id);
        return savedRequest;
    }

    @Transactional
    public CallbackRequest createCallback(String name, String phone) {
        CallbackRequest request = new CallbackRequest(name, phone);
        CallbackRequest savedRequest = callbackRequestRepository.save(request);
        logger.info("Создана заявка на звонок: имя={}, телефон={}, id={}", name, phone, savedRequest.getId());
        return savedRequest;
    }
}
