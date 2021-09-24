package com.example.studytest.hotclassloader2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        WatchDog hotClassLoader = new WatchDog();
        hotClassLoader.startListening("MyService");

        // 休眠一会，等加载完
        sleep(3000);
        mockCaller();
    }

    // 模拟调用者
    private static void mockCaller() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            try {
                MyClassLoader classLoader = new MyClassLoader();
                /*
                会报错  自己转自己异常 很奇怪为什么，百度也没找到答案
                Class<?> myServiceClass = classLoader.findClass("MyService");
                MyService myService = (MyService)myServiceClass.newInstance();
                */
                Service myService = (Service)(classLoader.findClass("MyService").newInstance());
                myService.printVersion();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    private static void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
