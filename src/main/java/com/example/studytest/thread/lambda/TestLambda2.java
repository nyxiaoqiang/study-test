package com.example.studytest.thread.lambda;

public class TestLambda2 {
    public static void main(String[] args) {
        ILove love = new Love();
        love.love(1);

        love = (int a) -> {
            System.out.println("I love you:"+a);
        };
        love.love(2);

        love = (a) -> {
            System.out.println("I love you:"+a);
        };
        love.love(3);

        love = a ->  System.out.println("I love you:"+a);
        love.love(4);
    }
}

interface ILove{
    void love(int a);
}

class Love implements ILove{
    @Override
    public void love(int a) {
        System.out.println("I love you:"+a);
    }
}