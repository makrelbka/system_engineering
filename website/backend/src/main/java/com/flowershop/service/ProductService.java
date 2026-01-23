package com.flowershop.service;

import com.flowershop.model.Product;
import com.flowershop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product createProduct(String name, Double price, String description, String image) {
        if (name == null || name.isBlank()) {
            throw new RuntimeException("Название товара обязательно");
        }
        if (price == null || price < 0) {
            throw new RuntimeException("Цена должна быть не меньше 0");
        }
        Product product = new Product(name, price, description, image);
        Product savedProduct = productRepository.save(product);
        logger.info("Добавлен товар #{}: {} (${})", savedProduct.getId(), savedProduct.getName(),
                String.format("%.2f", savedProduct.getPrice()));
        return savedProduct;
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Товар не найден");
        }
        productRepository.deleteById(id);
        logger.info("Удален товар #{}", id);
    }
}
