package com.zyuan.boot.Java封装类.Number;

public class TestLong {

    public static void main(String[] args) {
        Long long01 = 130L;
        Long long02 = 130L;
        Long long03 = 120L;
        Long long04 = 120L;
        System.out.println(long01 == long02); // false
        System.out.println(long03 == long04); // true
    }

}
