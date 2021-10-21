package com.example.studytest.thread;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * 练习Thread,实现多线程同步下载图片
 */
public class TestThread2 extends Thread{

    private String url;
    private String name;

    public TestThread2(String url,String name){
        this.url = url;
        this.name = name;
    }

    @Override
    public void run() {
        WebDownLoader webDownLoader = new WebDownLoader();
        webDownLoader.downloader(url,name);
        System.out.println("下载成功："+name);
    }

    public static void main(String[] args) {
        String url = "https://imglf3.lf127.net/img/dm1IZFQ2ejJ3b1VGS2U4cEh2Ui9Fakx4MUtXVHJPaEtFVVU2bHZLTnVGVGtMQUlFMlh2SEhBPT0.jpg?imageView&thumbnail=302y540&enlarge=1";
        TestThread2 thread1 = new TestThread2(url,"狂神的图片1.jpg");
        TestThread2 thread2 = new TestThread2(url,"狂神的图片2.jpg");
        TestThread2 thread3 = new TestThread2(url,"狂神的图片3.jpg");
        TestThread2 thread4 = new TestThread2(url,"狂神的图片4.jpg");
        TestThread2 thread5 = new TestThread2(url,"狂神的图片5.jpg");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}

class WebDownLoader{
    public void downloader(String url,String name){
        try {
            //org.apache.commons.io.FileUtils
            FileUtils.copyURLToFile(new URL(url),new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常");
        }
    }
}