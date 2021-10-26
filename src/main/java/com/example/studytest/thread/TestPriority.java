package com.example.studytest.thread;

/**
 * 测试线程的优先级
 */
public class TestPriority {
    public static void main(String[] args) {
        Runnable myPriority = new MyPriority();
        Thread thread1 = new Thread(myPriority);
        Thread thread2 = new Thread(myPriority);
        Thread thread3 = new Thread(myPriority);
        Thread thread4 = new Thread(myPriority);
        System.out.println(Thread.currentThread().getName()+"--->"+Thread.currentThread().getPriority());

        thread1.start();
        thread2.setPriority(1);
        thread2.start();
        thread3.setPriority(4);
        thread3.start();
        thread4.setPriority(Thread.MAX_PRIORITY);
        thread4.start();
    }

}

class MyPriority implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"--->"+Thread.currentThread().getPriority());
    }
}