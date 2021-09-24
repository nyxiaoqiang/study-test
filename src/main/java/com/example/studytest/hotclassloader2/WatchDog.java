package com.example.studytest.hotclassloader2;


import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WatchDog {

    private static long lastModifiedTime = 0;


    // 开始监听jar文件是否有更新
    public void startListening(String name) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            if (isHotUpdate(name)) {
                reload(name);
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    // 判断是否有更新
    private boolean isHotUpdate(String name) {
        String path = com.example.studytest.hotclassloader2.MyClassLoader.class.getResource("").getPath();
        File hotLoaderFile = new File(path+"/"+name+".class");
        boolean isHotUpdate = false;
        if (hotLoaderFile.exists()) {
            long newModifiedTime = hotLoaderFile.lastModified();
            isHotUpdate = lastModifiedTime != newModifiedTime;
            lastModifiedTime = newModifiedTime;
        } else {
            System.out.println(hotLoaderFile.getAbsolutePath() + " is not found.");
        }

        System.out.println("isHotUpdate：" + isHotUpdate);
        return isHotUpdate;
    }

    // 重新加载jar文件
    private void reload(String name) {
        try {
            MyClassLoader myClassLoader = new MyClassLoader();
            myClassLoader.findClass(name,true);
            System.out.println("加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
