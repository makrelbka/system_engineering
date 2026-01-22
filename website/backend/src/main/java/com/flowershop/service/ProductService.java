package com.flowershop.service;

import com.flowershop.model.Product;
import com.flowershop.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private static final String LOG_FILE = "/app/logs/flowershop.log";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void logInitialStock() {
        ensureLogFile();
        List<Product> products = productRepository.findAll();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        StringBuilder logLine = new StringBuilder();
        logLine.append(String.format("%s - НАЧАЛЬНОЕ КОЛИЧЕСТВО ТОВАРОВ:\n", time));

        for (Product product : products) {
            logLine.append(String.format("  - %s: %d шт.\n", product.getName(), product.getStock()));
        }

        writeLogLine(logLine.toString());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findByStockGreaterThan(0);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void decreaseStock(Long productId, Integer quantity, String customerName, String customerEmail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден: " + productId));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Недостаточно товара. Доступно: " + product.getStock());
        }

        int oldStock = product.getStock();
        product.setStock(oldStock - quantity);
        productRepository.save(product);
        int newStock = product.getStock();

        writeLog(customerName, customerEmail, quantity, product.getName(), product.getPrice(), oldStock, newStock);
    }

    private void writeLog(String customerName, String customerEmail, Integer quantity,
                         String productName, Double price, int oldStock, int newStock) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logLine = String.format("%s - ПОКУПКА: Клиент %s (%s) купил %d шт. товара '%s' по цене $%.2f. Было: %d шт., осталось: %d шт.",
                time, customerName, customerEmail, quantity, productName, price, oldStock, newStock);
        writeLogLine(logLine);
    }

    private void writeLogLine(String logLine) {
        try {
            ensureLogFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
                writer.print(logLine);
                if (!logLine.endsWith("\n")) {
                    writer.println();
                }
            }
        } catch (IOException e) {

        }
    }

    private void ensureLogFile() {
        try {
            Path logPath = Path.of(LOG_FILE);
            Files.createDirectories(logPath.getParent());
            if (!Files.exists(logPath)) {
                Files.createFile(logPath);
            }
        } catch (IOException ignored) {
        }
    }
}
