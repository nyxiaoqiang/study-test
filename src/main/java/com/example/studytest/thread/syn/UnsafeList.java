package com.example.studytest.thread.syn;

import java.util.ArrayList;
import java.util.List;

public class UnsafeList {
    public static void main(String[] args) throws Exception{
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            new Thread(() ->{
                list.add(Thread.currentThread().getName());
            }).start();
        }
        Thread.sleep(3000);
        System.out.println(list.size());

        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            new Thread(() ->{
                synchronized (list2){
                    list2.add(Thread.currentThread().getName());
                }
            }).start();
        }
        Thread.sleep(3000);
        System.out.println(list2.size());
    }
}
