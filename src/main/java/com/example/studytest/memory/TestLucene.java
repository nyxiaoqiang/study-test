package com.example.studytest.memory;

import org.apache.lucene.util.RamUsageEstimator;

//使用lucene计算对象大小
public class TestLucene {
    public static void main(String[] args) {
        Order order = new Order();
        String size = RamUsageEstimator.humanSizeOf(order);
        System.out.println(size);
    }
}
