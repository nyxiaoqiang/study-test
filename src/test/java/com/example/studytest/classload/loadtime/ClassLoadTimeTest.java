package com.example.studytest.classload.loadtime;


public class ClassLoadTimeTest {

    //使用-XX:+TraceClassLoading 监控类的加载
    public static void main(String[] args) {
        User user = new User();
        user.working();
    }
}
