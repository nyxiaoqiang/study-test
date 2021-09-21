package com.example.studytest.classload.parent;

public class TestMyClassLoader {
    public static void main(String[] args) throws Exception{
        MyClassLoader classLoader = new MyClassLoader();
        Class<?> aClass = classLoader.findClass("com.example.studytest.classload.parent.User");
        //com.example.studytest.classload.parent.MyClassLoader@31befd9f
        System.out.println(aClass.getClassLoader());
        aClass.newInstance();

        //sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(User.class.getClassLoader());
        Class<User> userClass = User.class;
        User user = userClass.newInstance();
    }
}
