package com.flowershop.config;

import com.flowershop.model.Product;
import com.flowershop.repository.ProductRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductDataInitializer {
    private final ProductRepository productRepository;

    public ProductDataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initProducts() {
        if (productRepository.count() > 0) {
            return;
        }

        productRepository.save(new Product(
            "Розовые розы",
            45.99,
            "Элегантный букет свежих розовых роз",
            "https://images.unsplash.com/photo-1716982360804-b0bfdb28103e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxwaW5rJTIwcm9zZXMlMjBib3VxdWV0fGVufDF8fHx8MTc2ODM3MTA3MHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
        ));
        productRepository.save(new Product(
            "Подсолнухи",
            39.99,
            "Яркая и жизнерадостная композиция",
            "https://images.unsplash.com/photo-1629386255808-c3ceb405173c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzdW5mbG93ZXIlMjBib3VxdWV0fGVufDF8fHx8MTc2ODMwNjMwMHww&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
        ));
        productRepository.save(new Product(
            "Тюльпаны микс",
            42.99,
            "Разноцветные свежие тюльпаны",
            "https://images.unsplash.com/photo-1556291474-7ded8a3b2d38?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx0dWxpcHMlMjBjb2xvcmZ1bHxlbnwxfHx8fDE3Njg0MDM0NDV8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
        ));
        productRepository.save(new Product(
            "Белые орхидеи",
            59.99,
            "Экзотические орхидеи в горшке",
            "https://images.unsplash.com/photo-1710524784485-5c77ae822ecc?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHx3aGl0ZSUyMG9yY2hpZHxlbnwxfHx8fDE3Njg0MDM0NDZ8MA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral"
        ));
    }
}
