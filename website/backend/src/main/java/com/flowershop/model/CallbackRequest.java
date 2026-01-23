package com.flowershop.model;

import java.time.LocalDateTime;

public class CallbackRequest {
    private Long id;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private boolean completed;

    public CallbackRequest() {}

    public CallbackRequest(Long id, String name, String phone, LocalDateTime createdAt, boolean completed) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.createdAt = createdAt;
        this.completed = completed;
    }

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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
