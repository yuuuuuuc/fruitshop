package com.fruitshop.model;

import java.sql.Timestamp;

public class User {
    private int id;
    private String email;
    private String phone;
    private String passwordHash;
    private String uname;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 构造函数
    public User() {}

    public User(int id, String email, String phone, String passwordHash, String uname,
                Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;
        this.uname = uname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter和Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
