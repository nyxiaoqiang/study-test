package com.example.studytest.thread;

public class TestRunnable implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("Runnable 接口，子线程："+i);
        }
    }

    public static void main(String[] args) {
        TestRunnable testRunnable = new TestRunnable();
        //创建线程对象，通过线程对象来开启我们的线程(代理模式)
        new Thread(testRunnable).start();
        for (int i = 0; i < 200; i++) {
            System.out.println("测试runnable，父线程："+i);
        }
    }
}
