package com.zyuan.boot.Java封装类.集合;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TestSet {

    public static void main(String[] args) {
        Set set = new HashSet();
        set.add(1);
        set.remove(1);
        Set<String> treeSet = new TreeSet();
        treeSet.add("123");
    }

}
