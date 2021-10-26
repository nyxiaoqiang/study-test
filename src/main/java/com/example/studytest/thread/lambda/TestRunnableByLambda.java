package com.example.studytest.thread.lambda;


public class TestRunnableByLambda {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("子线程数字:"+i);
            }
        });
        thread.start();

        for (int i = 0; i < 100; i++) {
            System.out.println("父线程数字："+i);
        }
    }
}
