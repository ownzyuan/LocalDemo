package com.zyuan.boot.数据结构.栈;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomStackTest {

    @Test
    public void testStack() {
        CustomStack<String> customStack = new CustomStack<>();
        boolean isEmpty01 = customStack.isEmpty();
        System.out.println("添加元素前，是否为空：" + isEmpty01);
        customStack.push("first");
        customStack.push("second");
        customStack.push("third");
        customStack.push("fourth");
        customStack.push("fifth");
        int size = customStack.size();
        System.out.println("添加后，一共" + size + "个元素");
        boolean isEmpty02 = customStack.isEmpty();
        System.out.println("添加元素后，是否为空：" + isEmpty02);
        boolean isExist01 = customStack.search("fifth");
        System.out.println("移除元素前，该元素是否存在：" + isExist01);
        String popElement = customStack.pop();
        System.out.println("取出来的元素为：" + popElement);
        boolean isExist02 = customStack.search("fifth");
        System.out.println("移除元素后，该元素是否存在：" + isExist02);
    }
}