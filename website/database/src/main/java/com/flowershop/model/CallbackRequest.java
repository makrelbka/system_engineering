package com.flowershop.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "callback_requests")
public class CallbackRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private Boolean completed = false;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (completed == null) {
            completed = false;
        }
    }
    
    public CallbackRequest() {}
    
    public CallbackRequest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
