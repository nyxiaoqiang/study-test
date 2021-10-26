package com.example.studytest.thread.syn;

import java.util.concurrent.CopyOnWriteArrayList;

public class TestJuc {
    public static void main(String[] args) throws Exception{
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                list.add(Thread.currentThread().getName());
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(list.size());
    }
}
