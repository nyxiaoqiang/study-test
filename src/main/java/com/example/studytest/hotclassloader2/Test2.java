package com.example.studytest.hotclassloader2;

public class Test2 {
    public static void main(String[] args) throws Exception{
        MyService myService = (MyService) MyService.class.getClassLoader().loadClass("com.example.studytest.hotclassloader2.MyService").newInstance();
        myService.printVersion();
        Class<?> myServiceClass = MyService.class;
        MyService o = (MyService) myServiceClass.newInstance();
        o.printVersion();
        System.out.println(MyClassLoader.class.getClassLoader());
    }
}
