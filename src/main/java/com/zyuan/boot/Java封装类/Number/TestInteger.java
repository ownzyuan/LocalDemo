package com.zyuan.boot.Java封装类.Number;

public class TestInteger {

    public static void main(String[] args) {
        Integer integer01 = 100;
        Integer integer02 = 100;
        Integer integer03 = 200;
        Integer integer04 = 200;
        System.out.println(integer01==integer02); // true
        System.out.println(integer03==integer04); // false
        System.out.println(integer01.intValue());
    }

}
