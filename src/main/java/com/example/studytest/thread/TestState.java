package com.example.studytest.thread;

/**
 * 观察测试线程的状态
 */
public class TestState {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("///////");
        });
        //new
        System.out.println(thread.getState());
        thread.start();
        //start
        System.out.println(thread.getState());

        Thread.State state = thread.getState();
        while (state != Thread.State.TERMINATED){
            try {
                Thread.sleep(100);
                state = thread.getState();
                System.out.println(state);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
