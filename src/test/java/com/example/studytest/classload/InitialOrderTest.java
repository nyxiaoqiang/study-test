package com.example.studytest.classload;



public class InitialOrderTest {

    //常量在准备阶段就赋值
    public static final String staticConstantFiled = "常量";

    //准备阶段分配内存并赋默认值，初始化阶段赋值为静态变量
    public static String staticFiled = "静态变量";

    //变量  创建对象的时候执行
    public String filed = "变量";

    //静态代码块  初始化阶段执行
    static {
        System.out.println(staticConstantFiled);
        System.out.println(staticFiled);
        System.out.println("静态代码块");
    }

    //非静态代码块  创建对象的时候执行
    {
        System.out.println(filed);
        System.out.println("非静态代码块");
    }

    //构造器   创建对象的时候执行
    public InitialOrderTest(){
        System.out.println("构造器");
    }


    public static void main(String[] args) {
        //new InitialOrderTest();
        /**
         * 常量
         * 静态变量
         * 静态代码块
         */

        new InitialOrderTest();
        /**
         * 常量
         * 静态变量
         * 静态代码块
         * 变量
         * 非静态代码块
         * 构造器
         */
    }
}
