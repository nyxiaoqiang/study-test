package com.example.studytest.thread;

/**
 * 用线程去实现龟兔赛跑
 */
public class Race implements Runnable{

    private static String winner;

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            if("兔子".equals(Thread.currentThread().getName()) && i%10==0){
                //模拟兔子睡觉
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+"跑了"+i+"步");
            if(gameOver(i)){
                break;
            }
        }
    }

    /**
     * 判断是否比赛结束
     * @return
     */
    private boolean gameOver(int steps){
        if(winner!=null){
            return true;
        }else if(steps == 100){
            winner = Thread.currentThread().getName();
            System.out.println("winner is:"+winner);
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        Race race = new Race();
        new Thread(race,"兔子").start();
        new Thread(race,"乌龟").start();
    }
}
