package com.example.studytest.classload;


public class TestFanshe {
    public static void main(String[] args) throws Exception{
        //验证反射的情况下，类会对类的成员进行初始化
        //Class.forName("com.example.studytest.classload.SubClass");
        /**
         * 父类--静态变量
         * 父类--静态初始化块
         * 子类--静态变量
         * 子类--静态初始化块
         */

        Class aClass = SubClass.class.getClassLoader().loadClass("com.example.studytest.classload.SubClass");
        /**
         * 什么信息都没有打印，说明类加载只是做了，类的生命周期中的加载的部分。并没有进行类的初始化
         * 除非使用才会初始化
         *
         * 父类--静态变量
         * 父类--静态初始化块
         * 子类--静态变量
         * 子类--静态初始化块
         * 父类--变量
         * 父类--初始化块
         * 父类--构造器
         * i=1, j=8
         * 子类--变量
         * 子类--初始化块
         * 子类--构造器
         * i=1,j=9
         */
        System.out.println("----------");
        aClass.newInstance();
    }
}
