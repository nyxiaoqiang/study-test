package com.example.studytest.thread.syn;

public class UnSafeBuyTicket {

    public static void main(String[] args) {
        BuyTicket buyTicket = new BuyTicket();
        new Thread(buyTicket,"小明").start();
        new Thread(buyTicket,"小李").start();
        new Thread(buyTicket,"小刘").start();
    }
}

class BuyTicket implements Runnable{
    private int ticketNums = 10;
    private boolean flag = true;

    @Override
    public void run() {
        while (flag){
            buy();
        }
    }

    private void buy(){
        if(ticketNums <= 0){
            flag = false;
            return;
        }

        //模拟延迟
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"买到第"+ticketNums--+"张票");
    }
}