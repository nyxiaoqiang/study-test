package com.example.studytest.memory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TestDirectBufferOOM {

    public static void main(String[] args) {
        final int _1M = 1024 * 1024;
        List<ByteBuffer> list = new ArrayList<>();
        int count = 1;
        while (true){
            ByteBuffer buffer = ByteBuffer.allocateDirect(_1M);
            list.add(buffer);
            System.out.println(count++);
        }
    }
}
