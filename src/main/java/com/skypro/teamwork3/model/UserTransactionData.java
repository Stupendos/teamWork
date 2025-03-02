package com.skypro.teamwork3.model;

import java.math.BigDecimal;

public class UserTransactionData {
    private final String id;
    private final String userId;
    private final String productId;
    private final String type;
    private final BigDecimal amount;

    public UserTransactionData(String id, String userId, String productId, String type, BigDecimal amount) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.type = type;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
