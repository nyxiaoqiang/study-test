package com.example.studytest.thread;

/**
 * 继承Thread类，重新run方法，调用start()方法开启线程
 */
public class TestThread extends Thread{

    @Override
    public void run() {
        //run方法线程体
        for (int i = 0; i < 20; i++) {
            System.out.println("子线程:"+i);
        }
    }

    //mian线程，主线程
    public static void main(String[] args) {
        TestThread thread = new TestThread();
        thread.start();
        for (int i = 0; i < 200; i++) {
            System.out.println("主线程:"+i);
        }
    }
}
