package com.zyuan.boot.数据结构.链表;

public interface CustomList<E> {

    boolean add(E e);

    E get(int index);

    boolean isEmpty();

    int size();

    boolean contains(E e);

    boolean remove(E e);
}
