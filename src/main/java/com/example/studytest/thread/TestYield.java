package com.example.studytest.thread;

/*
 *
测试礼让线程  ，礼让不一定成功
 */
public class TestYield {
    public static void main(String[] args) {
        new Thread(new MyYield(),"A线程").start();
        new Thread(new MyYield(),"B线程").start();
    }
}

class MyYield implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"线程开始执行");
        Thread.yield();
        System.out.println(Thread.currentThread().getName()+"线程停止执行");
    }
}
