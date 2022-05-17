package com.zyuan.boot.数据结构.栈;

import java.util.Arrays;
import java.util.EmptyStackException;

public class CustomStack<E> {

    // 物理存储结构，通过Object类型数组存放
    private Object[] elementData;
    // 栈里面的元素个数
    private int elementCount = 0;
    // 数组默认大小
    private int defaultLength = 4;
    // 数组的操作指针，从-1开始，使用前+1，也就是说从0开始进行变动
    private int index = -1;

    // 添加元素
    public E push(E item) {
        this.capacity();
        elementData[++this.index] = item;
        this.elementCount++;
        return item;
    }

    // 取出栈顶元素
    public E pop() {
        if (this.index == -1) {
            throw new EmptyStackException();
        }
        Object returnElement = this.elementData[this.index];
        this.elementData[this.index] = null;
        this.index--;
        this.elementCount--;
        return (E)returnElement;
    }

    // 判断是否为空
    public boolean isEmpty() {
        return this.elementCount == 0;
    }

    // 通过条件查询
    public boolean search(E item) {
        if (item == null) {
            for (int i = this.elementCount; i >= 0; i--) {
                if (this.elementData[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i = this.elementCount; i >= 0; i--) {
                if (item.equals(this.elementData[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    public int size() {
        return this.elementCount;
    }

    // 初始化数组
    private void capacity() {
        if (this.elementData == null) {
            this.elementData = new Object[this.defaultLength];
        }
        // 添加元素后的元素个数如果大于默认大小，则需要扩容(1.5倍扩容)
        if ((this.elementCount + 1) - this.defaultLength > 0) {
            this.defaultLength += this.defaultLength >> 1;
            this.elementData = Arrays.copyOf(this.elementData, this.defaultLength);
//            Object[] newElementData = new Object[this.defaultLength];
//            for (int i = 0; i < this.elementData.length; i++) {
//                newElementData[i] = this.elementData[i];
//            }
//            this.elementData = newElementData;
        }
    }

}
