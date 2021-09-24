package com.example.studytest.hotclassloader1;

import java.io.File;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
//https://juejin.cn/post/6844904138006855687#heading-8
// 热加载器
public class HotClassLoader {
    private static final long LOADER_INTERVAL = 3;
    // 指向动态加载module的jar文件
    //private static final String HOT_UPDATE_JAR_PATH = "F:\\study\\study-test\\target\\study-test-0.0.1-SNAPSHOT.jar";
    private static final String HOT_UPDATE_JAR_PATH = "F:\\study\\study-test\\target\\classes\\com\\example\\studytest\\hotclassloader";

    static ClassLoader classLoader;         //类加载器
    private static long lastModifiedTime = 0;  // jar文件最后更新时间

    // 开始监听jar文件是否有更新
    public void startListening() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            if (isHotUpdate()) {
                reload();
            }
        }, 0, LOADER_INTERVAL, TimeUnit.SECONDS);
    }

    // 动态获取新实例，注意返回值可能为null，调用者要加判断
    public static Object newInstance(String className) {
        if (classLoader != null) {
            try {
                synchronized (HotClassLoader.class) {
                    Class<?> aClass = classLoader.loadClass(className);
                    return aClass.newInstance();
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 判断是否有更新
    private boolean isHotUpdate() {
        File hotLoaderFile = new File(HOT_UPDATE_JAR_PATH);
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
    private void reload() {
        File jarPath = new File(HOT_UPDATE_JAR_PATH);
        System.out.println("jar lastModified xxxxxxxxxxxxxxxxxx: " + jarPath.lastModified());

        if (jarPath.exists()) {
            try {
                synchronized (HotClassLoader.class) {
                    classLoader = new MyClassLoader(new URL[]{jarPath.toURI().toURL()});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Hot update jar is not found.");
        }
    }
}
