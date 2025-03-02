package com.skypro.teamwork3.model;

public class Product {
    private final String id;
    private final String type;
    private final String name;

    public Product(String id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
