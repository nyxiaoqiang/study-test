package com.example.studytest.classload;

import sun.net.spi.nameservice.dns.DNSNameService;

import java.io.BufferedReader;
import java.langtest.String;

public class ClassLoadTest {
    public static void main(String[] args) {
        System.out.println(BufferedReader.class.getClassLoader());

        System.out.println(DNSNameService.class.getClassLoader());

        System.out.println(ClassLoadTest.class.getClassLoader());
    }
}
