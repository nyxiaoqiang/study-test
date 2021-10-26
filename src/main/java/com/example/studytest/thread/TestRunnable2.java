package com.example.studytest.thread;

/**
 * 多个线程同时操作同一个对象
 * 以买火车票的例子进行举例
 */
public class TestRunnable2 implements Runnable{

    private int ticketNums = 10;

    @Override
    public void run() {
        while(true){
            if(ticketNums <= 0){
                break;
            }
            System.out.println(Thread.currentThread().getName()+"拿到了第:"+ticketNums+"张票");
            ticketNums--;
        }
    }

    public static void main(String[] args) {
        TestRunnable2 testRunnable2 = new TestRunnable2();
        new Thread(testRunnable2,"河南省服务器").start();
        new Thread(testRunnable2,"上海市服务器").start();
        new Thread(testRunnable2,"北京市服务器").start();
    }

    /**
     * 线程不安全问题
     *
     * 河南省服务器拿到了第:10张票
     * 河南省服务器拿到了第:9张票
     * 河南省服务器拿到了第:8张票
     * 河南省服务器拿到了第:7张票
     * 河南省服务器拿到了第:6张票
     * 河南省服务器拿到了第:5张票
     * 河南省服务器拿到了第:4张票
     * 上海市服务器拿到了第:10张票
     * 上海市服务器拿到了第:2张票
     * 北京市服务器拿到了第:10张票
     * 上海市服务器拿到了第:1张票
     * 河南省服务器拿到了第:3张票
     */
}
