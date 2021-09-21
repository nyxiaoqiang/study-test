package com.example.studytest.nio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

@SpringBootTest
public class NioBasicTest {

    /**
     * 容量(capacity) ： 表示缓冲区中最大存储数据的容量。一旦声明不能改变。
     * 限制(limit)：表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
     * 位置(position)：表示缓冲区中正在操作数据的位置。
     * 标记(mark)：表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置。
     * 标记、位置、限制、容量遵守以下不变式： 0 <= mark <= position <= limit <= capacity
     */
    @Test
    public void testBuffer(){
        String str = "study nio buffer";
        //分配一个指定大小的非直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //分配一个指定大小的直接缓冲区
        //ByteBuffer allocateDirect = ByteBuffer.allocateDirect(1024);
        System.out.println(">>>>>>>>allocate");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //使用put方法将数据写到buffer中
        buffer.put(str.getBytes());
        System.out.println(">>>>>>>>put");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //使用flip方法切换成读取模式(posi变为0，limit变成16)
        buffer.flip();
        System.out.println(">>>>>>>>flip");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //使用get读取数据,新建一个数组用于接收数据
        byte[] by = new byte[buffer.limit()];
        buffer.get(by);
        System.out.println(new String(by,0,by.length));
        System.out.println(">>>>>>>>get");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //使用rewind可以重复读
        buffer.rewind();
        System.out.println(">>>>>>>>rewind");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //clear清空缓冲区，但是缓冲区的数据还在，但是处于被遗忘状态。
        buffer.clear();
        System.out.println(">>>>>>>>clear");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //mark可以对当前位置进行标记
        buffer.put("abcdefg".getBytes());
        byte []  by2 = new byte[buffer.limit()];
        buffer.flip();
        buffer.get(by2,0,2);
        System.out.println(new String(by2,0,by2.length));
        buffer.mark();
        buffer.get(by2,2,2);
        System.out.println(new String(by2,0,by2.length));
        System.out.println(buffer.position());
        //reset可以使position恢复到mark的位置
        buffer.reset();
        System.out.println(buffer.position());

        //hasRemaining判断缓冲区是否还有数据
        if(buffer.hasRemaining()){
            //获取缓冲区中可以操作的数量 capacity - position
            System.out.println(buffer.remaining());
        }
    }

    /**
     * 获取Channel的三种方式
     * @throws Exception
     */
    @Test
    public void getChannel() throws Exception{
        //1、使用本地IO获取
            // FileInputStream
            // FileOutputStream
            // RandomAccessFile
        FileInputStream fileInputStream = new FileInputStream(new File("F:\\Hello.java"));
        fileInputStream.getChannel();
        //2、使用通道的open方法
        FileChannel open = FileChannel.open(Paths.get("F:\\Hello.java"), StandardOpenOption.READ);
        //3、Files
        Files.newByteChannel(Paths.get("F:\\Hello.java"), StandardOpenOption.READ);
    }

    //利用通道完成文件复制-非直接缓冲区
    @Test
    public void copyFileByChannel() throws Exception{
        FileInputStream fileInputStream = new FileInputStream(new File("F:\\demo.jar"));
        FileOutputStream fileOutputStream = new FileOutputStream(new File("F:\\demo-copy.java"));
        //创建通道
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel = fileOutputStream.getChannel();
        //创建指定大小的非直接缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //读取文件
        while (inChannel.read(byteBuffer)!=-1){
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        fileInputStream.close();
        fileOutputStream.close();
        inChannel.close();
        outChannel.close();
    }

    //利用通道完成文件复制 - 直接缓冲区(内存映射技术)
    @Test
    public void copyFileByChannelDirect() throws Exception{
        //新的创建通道方式
        FileChannel inChannel = FileChannel.open(Paths.get("F:\\demo.jar"),StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("F:\\demo-copy.jar"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer inMap = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMap = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        byte[] bytes = new byte[inMap.limit()];
        inMap.get(bytes);
        outMap.put(bytes);

        inChannel.close();
        outChannel.close();
    }

    //利用通道完成文件复制 - 直接缓冲区
    @Test
    public void copyFileByChannelDirect2() throws Exception{
        //新的创建通道方式
        FileChannel inChannel = FileChannel.open(Paths.get("F:\\demo.jar"),StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("F:\\demo-copy.jar"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

        //内存映射文件
        outChannel.transferFrom(inChannel,0,inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    /**
     * 使用阻塞式NIO实现聊天(单向)（Tcp版）--客户端
     */
    @Test
    public void BlockingNIOClientTest() throws Exception{
        //创建通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        FileChannel fileChannel = FileChannel.open(Paths.get("F:\\demo.jar"));

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //读取本地文件发给服务放
        while (fileChannel.read(buffer)!=-1){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
        socketChannel.close();
        fileChannel.close();
    }

    /**
     * 使用阻塞式NIO实现聊天（单向）（Tcp版）--客户端
     */
    @Test
    public void BlockingNIOServerTest() throws Exception{
        //获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        FileChannel fileChannel = FileChannel.open(Paths.get("F:\\demo-copy.jar"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        //绑定链接
        serverSocketChannel.bind(new InetSocketAddress(8888));
        //获取客户端连接的通道
        SocketChannel socketChannel = serverSocketChannel.accept();

        //获取数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.read(byteBuffer)!=-1){
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        socketChannel.close();
        fileChannel.close();
        serverSocketChannel.close();;
    }



    /**
     * 使用阻塞式NIO实现聊天(双向)（Tcp版）--客户端
     * sChannel.shutdownOutput();
     */
    @Test
    public void BlockingNIOClientTest2() throws Exception{
        //创建通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        FileChannel fileChannel = FileChannel.open(Paths.get("F:\\demo.jar"),StandardOpenOption.READ);

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //读取本地文件发给服务放
        while (fileChannel.read(buffer)!=-1){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        //告诉客户端发送已经完成，可以接收数据了
        socketChannel.shutdownOutput();
        int len = 0 ;
        while ((len=socketChannel.read(buffer))!=-1){
            buffer.flip();
            System.out.println(new String(buffer.array(),0,len));
            buffer.clear();
        }

        socketChannel.close();
        fileChannel.close();
    }

    /**
     * 使用阻塞式NIO实现聊天（双向）（Tcp版）--客户端
     */
    @Test
    public void BlockingNIOServerTest2() throws Exception{
        //获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        FileChannel fileChannel = FileChannel.open(Paths.get("F:\\demo-copy.jar"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        //绑定链接
        serverSocketChannel.bind(new InetSocketAddress(8888));
        //获取客户端连接的通道
        SocketChannel socketChannel = serverSocketChannel.accept();

        //获取数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.read(byteBuffer)!=-1){
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        byteBuffer.put("成功接收文件".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        socketChannel.close();
        fileChannel.close();
        serverSocketChannel.close();;
    }


    /**
     * 使用非阻塞模式实现聊天室  -- 客户端1
     */
    @Test
    public void NonBlockingNIOClient1Test() throws Exception{
        //获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        //切换成非阻塞模式
        socketChannel.configureBlocking(false);
        //分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //将输入的文字发给服务端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();
            System.out.println(next);
            buffer.put((new Date()+" "+next).getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        socketChannel.close();
    }

    /**
     * 使用非阻塞模式实现聊天室  -- 客户端2
     */
    public void NonBlockingNIOClient2Test() {

    }

    /**
     * 使用非阻塞模式实现聊天室 -- 服务端
     */
    @Test
    public void NonBlockingNIOServerTest() throws Exception{
        //获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        SocketChannel socketChannel = null;
        //切换到非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //绑定连接
        serverSocketChannel.bind(new InetSocketAddress(8888));
        //获取选择器
        Selector selector = Selector.open();
        //将通道注册到选择器上，并且需要指定“监听接收事件”
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮询式的获取选择器上已经“准备就绪”的事件
        while (selector.select()>0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){ //准备就绪处理方式
                    //如果客户端准备就绪，则获取客户端的连接
                    socketChannel = serverSocketChannel.accept();
                    //切换为非阻塞模式
                    socketChannel.configureBlocking(false);
                    //将该客户端通道注册到选择器上
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){//获取到当前选择器上 “读就绪” 状态的通道
                    socketChannel = (SocketChannel) selectionKey.channel();
                    //读取数据
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    int len =0 ;
                    //注意千万不要用！=-1
                    while ((len = socketChannel.read(allocate))>0){
                        allocate.flip();
                        System.out.println(new String(allocate.array(),0,len));
                        allocate.clear();
                    }
                }

                iterator.remove();
            }
        }
        serverSocketChannel.close();
        socketChannel.close();

    }

}
