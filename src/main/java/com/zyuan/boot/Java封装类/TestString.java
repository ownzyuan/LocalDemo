package com.zyuan.boot.Java封装类;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class TestString {
    public static void main(String[] args) {
        String str = new String();
        str = "1234";
        int hashCode = str.hashCode();
        System.out.println(hashCode);
        System.out.println("1234".hashCode());
        boolean b = str.equals("1236");
        System.out.println(b);
        String str2 = new String("sad");
        System.out.println(str2);
        Object o = new Object();
        HashSet s = new HashSet();

    }
}
