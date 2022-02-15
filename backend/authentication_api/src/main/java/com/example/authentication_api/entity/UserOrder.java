package com.example.authentication_api.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// todo : remove this entity, not serving any purpose for efficient code
@Entity
@Table
public class UserOrder {

    @Id
    private String orderId;
    private String email;

    // todo : why are these constructors? should not typically create constructors .. but use builder pattern
    public UserOrder() {
    }

    public UserOrder(String orderId, String email) {
        this.orderId = orderId;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
