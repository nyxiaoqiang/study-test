package com.example.studytest.memory;


import java.util.ArrayList;
import java.util.List;

public class TestMemoryOverFlow {
    public static void main(String[] args) throws Exception{
        List<Order> orders = new ArrayList<>();

        int i = 0 ;
        while (true){
            Order order = new Order();
            order.setId(1);
            orders.add(order);
            System.out.println(i++);
            Thread.sleep(30);
        }
    }
}
