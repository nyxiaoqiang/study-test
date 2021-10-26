package com.example.studytest.thread;

public class TestDaemon {
    public static void main(String[] args) {
        Thread god = new Thread(new God());
        //默认是false表示默认是用户线程
        god.setDaemon(true);
        god.start();

        new Thread(new You()).start();
    }
}

class God implements Runnable{
    @Override
    public void run() {
        while(true){
            System.out.println("上帝保佑着你");
        }
    }
}

class You implements  Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("你一生都开心的活着");
        }
        System.out.println("=====goodbye! word=====");
    }
}