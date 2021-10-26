package com.example.studytest.thread;

public class TestJoin {
    public static void main(String[] args) throws Exception{
        //启动我们的线程
        MyThread myThread = new MyThread();
        Thread thread = new Thread(myThread);
        thread.start();

        //主线程代码
        for (int i = 1; i <=100; i++) {
            System.out.println("主线程代码"+i);
            thread.join(); //插队
        }
    }
    
}

class MyThread implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(10);
            }catch (Exception e){

            }
            System.out.println("vip 执行"+i);
        }
    }
}