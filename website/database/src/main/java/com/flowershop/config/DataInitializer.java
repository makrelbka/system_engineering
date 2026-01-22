package com.flowershop.config;

import com.flowershop.model.Category;
import com.flowershop.model.Product;
import com.flowershop.model.User;
import com.flowershop.repository.CategoryRepository;
import com.flowershop.repository.ProductRepository;
import com.flowershop.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public DataInitializer(CategoryRepository categoryRepository,
                          ProductRepository productRepository,
                          UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {

        if (userRepository.count() == 0) {
            User admin = new User("admin@flowershop.ru", "admin", "Администратор", "+7 (495) 123-45-67", User.UserRole.ADMIN);
            userRepository.save(admin);
        }

        if (productRepository.count() > 0) {
            return;
        }

        Category category = new Category("Букеты", "Свежие букеты цветов");
        category = categoryRepository.save(category);

        Product product1 = new Product("Розовые розы", 45.99,
            "Элегантный букет свежих розовых роз",
            "https://images.unsplash.com/photo-1716982360804-b0bfdb28103e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwaW5rJTIwcm9zZXMlMjBib3VxdWV0fGVufDF8fHx8MTc2ODM3MTA3MHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            10);
        product1.setCategory(category);
        productRepository.save(product1);

        Product product2 = new Product("Подсолнухи", 39.99,
            "Яркая и жизнерадостная композиция",
            "https://images.unsplash.com/photo-1629386255808-c3ceb405173c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzdW5mbG93ZXIlMjBib3VxdWV0fGVufDF8fHx8MTc2ODMwNjMwMHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            8);
        product2.setCategory(category);
        productRepository.save(product2);

        Product product3 = new Product("Тюльпаны микс", 42.99,
            "Разноцветные свежие тюльпаны",
            "https://images.unsplash.com/photo-1556291474-7ded8a3b2d38?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0dWxpcHMlMjBjb2xvcmZ1bHxlbnwxfHx8fDE3Njg0MDM0NDV8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            15);
        product3.setCategory(category);
        productRepository.save(product3);

        Product product4 = new Product("Лаванда", 34.99,
            "Ароматная лаванда для релаксации",
            "https://images.unsplash.com/photo-1541927634837-a7d5c4892527?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsYXZlbmRlciUyMGZsb3dlcnN8ZW58MXx8fHwxNzY4MzcyOTI4fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            12);
        product4.setCategory(category);
        productRepository.save(product4);

        Product product5 = new Product("Белые орхидеи", 59.99,
            "Экзотические орхидеи в горшке",
            "https://images.unsplash.com/photo-1710524784485-5c77ae822ecc?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3aGl0ZSUyMG9yY2hpZHxlbnwxfHx8fDE3Njg0MDM0NDZ8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            5);
        product5.setCategory(category);
        productRepository.save(product5);

        Product product6 = new Product("Букет из лилий", 48.99,
            "Роскошная композиция из свежих лилий",
            "https://images.unsplash.com/photo-1712258090342-f31b387221a3?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsaWx5JTIwZmxvd2VycyUyMGJvdXF1ZXR8ZW58MXx8fHwxNzY4NDAzNDQ2fDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            7);
        product6.setCategory(category);
        productRepository.save(product6);

        Product product7 = new Product("Розовые пионы", 54.99,
            "Роскошный букет розовых пионов",
            "https://images.unsplash.com/photo-1588457776180-4206b4909301?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwZW9ueSUyMGZsb3dlcnMlMjBwaW5rfGVufDF8fHx8MTc2ODMzNzgzN3ww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            9);
        product7.setCategory(category);
        productRepository.save(product7);

        Product product8 = new Product("Белые ромашки", 32.99,
            "Простые и элегантные белые ромашки",
            "https://images.unsplash.com/photo-1618929990290-e32fa75925be?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxkYWlzeSUyMGZsb3dlcnMlMjB3aGl0ZXxlbnwxfHx8fDE3Njg0MDM0NDd8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            20);
        product8.setCategory(category);
        productRepository.save(product8);
    }
}
