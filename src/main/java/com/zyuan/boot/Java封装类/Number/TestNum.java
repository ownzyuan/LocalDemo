package com.zyuan.boot.Java封装类.Number;

public class TestNum {
    public static void main(String[] args) {
        Long l = new Long(123L);
        l.equals(145L);
        int hashCode = l.hashCode();
        Integer i = new Integer(300);
        boolean a = 300 == i;
        int iii = 300;
        boolean b = i.equals(300);
        boolean c = 300 == iii;
        boolean d = i == iii;
    }
}
