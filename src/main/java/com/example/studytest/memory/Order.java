package com.example.studytest.memory;

import java.math.BigDecimal;

public class Order {
    private int id;
    private String name;
    private BigDecimal money;

    private byte [] my = new byte[1024 * 1024];

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public byte[] getMy() {
        return my;
    }

    public void setMy(byte[] my) {
        this.my = my;
    }
}
