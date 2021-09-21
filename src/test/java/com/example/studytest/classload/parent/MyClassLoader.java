package com.example.studytest.classload.parent;

import java.io.InputStream;

public class MyClassLoader extends ClassLoader{

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            System.out.println(name);
            String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
            InputStream is = getClass().getResourceAsStream(filename);
            if(is == null){
                throw new ClassNotFoundException(name);
            }
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            //底层是C++写的
            return defineClass(name,bytes,0,bytes.length);
        }catch (Exception e){
            throw new ClassNotFoundException(name);
        }
    }
}
