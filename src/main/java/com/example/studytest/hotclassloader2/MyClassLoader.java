package com.example.studytest.hotclassloader2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyClassLoader extends ClassLoader{

    File classPathFile = null;

    Map<String,Class> clazzCache = new ConcurrentHashMap<>();

    public MyClassLoader() {
        String path = MyClassLoader.class.getResource("").getPath();
        System.out.println(path);
        this.classPathFile = new File(path);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return this.findClass(name,false);
    }

    protected Class<?> findClass(String name,boolean force) throws ClassNotFoundException{
        String className = MyClassLoader.class.getPackage().getName() + "." + name;
        Class cls = clazzCache.get(className);
        if(force){
            cls = null;
        }

        if(cls == null && classPathFile != null){
            File classFile = new File(classPathFile + "\\" + name.replaceAll("\\.","/")+".class");
            if(classFile.exists()){
                FileInputStream fis = null;
                ByteArrayOutputStream bas = null;
                try {
                    fis = new FileInputStream(classFile);
                    bas = new ByteArrayOutputStream();
                    byte [] bytes = new byte[1024];
                    int len ;
                    while ((len = fis.read(bytes)) != -1){
                        bas.write(bytes,0,len);
                    }
                    cls = defineClass(className,bas.toByteArray(),0,bas.size());
                    clazzCache.put(className,cls);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        if(fis!=null){
                            fis.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(bas!= null){
                            bas.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return clazzCache.get(className);
    }
}
