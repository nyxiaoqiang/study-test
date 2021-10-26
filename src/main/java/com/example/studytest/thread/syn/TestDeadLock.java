package com.example.studytest.thread.syn;

/**
 * 死锁：多个线程互相 持有 对方需要的资源，形成僵持
 */
public class TestDeadLock {
    public static  String obj1 = "obj1";
    public static  String obj2 = "obj2";

    public static void main(String[] args) {
        new Thread(new LockA()).start();
        new Thread(new LockB()).start();
    }

}

class LockA implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("LockA开始执行");
            while (true){
                synchronized (TestDeadLock.obj1){
                    System.out.println("LockA锁住了obj1");
                    Thread.sleep(3000);//此处等待是给LockB能锁住obj2的机会
                    synchronized (TestDeadLock.obj2){
                        System.out.println("LockA锁住了obj2");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class LockB implements Runnable{
    @Override
    public void run() {
        try {
            System.out.println("LockB开始执行");
            while (true){
                synchronized (TestDeadLock.obj2){
                    System.out.println("LockB锁住了obj2");
                    Thread.sleep(3000);//此处等待是给LockB能锁住obj2的机会
                    synchronized (TestDeadLock.obj1){
                        System.out.println("LockB锁住了obj1");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}