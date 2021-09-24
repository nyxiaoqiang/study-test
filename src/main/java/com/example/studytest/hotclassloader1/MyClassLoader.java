package com.example.studytest.hotclassloader1;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends URLClassLoader {


    public MyClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name,false);
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
