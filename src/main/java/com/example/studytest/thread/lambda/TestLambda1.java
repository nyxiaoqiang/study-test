package com.example.studytest.thread.lambda;

/**
 * 推导Lambda表达式
 */
public class TestLambda1 {
    public static void main(String[] args) {
        //普通方式
        ILike like = new Like();
        like.lambda();

        //静态内部类
        like = new Like1();
        like.lambda();

        //局部内部类
        class Like2 implements ILike{
            @Override
            public void lambda() {
                System.out.println("test lambda2");
            }
        }
        like = new Like2();
        like.lambda();

        //匿名内部类
        like = new ILike() {
            @Override
            public void lambda() {
                System.out.println("test lambda3");
            }
        };
        like.lambda();

        //使用Lambda优化
        like = () -> {
            System.out.println("test lambda4");
        };
        like.lambda();

    }

    //2、使用静态内部类来优化
    //实现类
    static class Like1 implements ILike{

        @Override
        public void lambda() {
            System.out.println("test lambda1");
        }
    }

}

/**
 * 定义一个函数式接口
 */

interface ILike{
    void lambda();
}

//实现类
class Like implements ILike{

    @Override
    public void lambda() {
        System.out.println("test lambda");
    }
}