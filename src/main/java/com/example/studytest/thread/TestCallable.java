package com.example.studytest.thread;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程创建方式3，实现Callable接口
 */
public class TestCallable implements Callable<Boolean> {

    private String url;
    private String name;

    public TestCallable(String url,String name){
        this.url = url;
        this.name = name;
    }

    @Override
    public Boolean call() throws Exception {
        WebDownLoader1 webDownLoader1 = new WebDownLoader1();
        webDownLoader1.downloader(url,name);
        System.out.println("下载成功："+name);
        return true;
    }

    public static void main(String[] args) throws Exception{
        String url = "https://imglf3.lf127.net/img/dm1IZFQ2ejJ3b1VGS2U4cEh2Ui9Fakx4MUtXVHJPaEtFVVU2bHZLTnVGVGtMQUlFMlh2SEhBPT0.jpg?imageView&thumbnail=302y540&enlarge=1";

        TestCallable callable1 = new TestCallable(url,"狂神的图片1.jpg");
        TestCallable callable2 = new TestCallable(url,"狂神的图片2.jpg");
        TestCallable callable3 = new TestCallable(url,"狂神的图片3.jpg");

        //创建执行服务
        ExecutorService service = Executors.newFixedThreadPool(3);
        Future<Boolean> submit1 = service.submit(callable1);
        Future<Boolean> submit2 = service.submit(callable2);
        Future<Boolean> submit3 = service.submit(callable3);

        //获取结果
        Boolean rs1 = submit1.get();
        Boolean rs2 = submit2.get();
        Boolean rs3 = submit3.get();

        //关闭服务
        service.shutdownNow();
    }

}


class WebDownLoader1{
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