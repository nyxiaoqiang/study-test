package com.example.studytest.hotclassloader1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HotLoadTest {
    public static void main(String[] args) {
        HotClassLoader hotClassLoader = new HotClassLoader();
        hotClassLoader.startListening();

        // 休眠一会，等加载完
        sleep(3000);
        mockCaller();
        sleep(50000000);
    }

    // 模拟调用者
    private static void mockCaller() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(()->{
            try {
                Service mockService = (Service) HotClassLoader.newInstance("com.example.studytest.hotclassloader.MyService");
                mockService.printVersion();
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
