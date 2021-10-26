package com.example.studytest.thread.syn;

/**
 * 两个人分别去 银行、账户 取钱，导致不安全的取钱
 */
public class UnSafeBank {
    public static void main(String[] args) {
        Account account = new Account(100,"结婚基金");
        Drawing you = new Drawing(account,50,"你");
        Drawing girlFirend = new Drawing(account,100,"女朋友");
        you.start();
        girlFirend.start();
    }
}

//账户
class Account{
    int money;
    String name;

    public Account(int money, String name) {
        this.money = money;
        this.name = name;
    }
}

//银行：模拟取钱
class Drawing extends Thread{

    //账户
    Account account ;
    //准备取多少钱
    int drawingMoney;
    //现在手里有多少钱
    int nowMiney;

    public Drawing(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    @Override
    public void run() {
        //首先判断有没有钱
        if(account.money-drawingMoney<0){
            System.out.println(Thread.currentThread().getName()+"余额不足");
            return;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        account.money = account.money-drawingMoney;
        nowMiney = nowMiney + drawingMoney;
        System.out.println(account.name+"余额："+account.money);
        System.out.println(this.getName()+"手里的钱"+nowMiney);
    }
}