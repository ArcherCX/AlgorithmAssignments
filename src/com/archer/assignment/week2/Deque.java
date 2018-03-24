package com.archer.assignment.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements IDeque<Item> {
//    private static final int DEFAULT_SIZE = 1;
//    private Item[] data;
    private Node<Item> head, tail;
    private int count;

    private static class Node<Item> {
        private Node(Item item) {
            this.item = item;
        }

        private final Item item;
        private Node<Item> prev, next;
    }

    public Deque() {
//        data = (Item[]) new Object[DEFAULT_SIZE];
    }

    public boolean isEmpty() {
        return head == null;
    }


    public int size() {
        return count;
    }


    public void addFirst(Item item) {
        checkNull(item);
        Node<Item> oldHead = head;
        head = new Node<>(item);
        head.next = oldHead;
        if (tail == null) {
            tail = head;
        }
        if (oldHead != null) {
            oldHead.prev = head;
        }
        count++;
    }


    public void addLast(Item item) {
        checkNull(item);
        Node<Item> oldTail = tail;
        tail = new Node<>(item);
        tail.prev = oldTail;
        if (head == null) {
            head = tail;
        }
        if (oldTail != null) {
            oldTail.next = tail;
        }
        count++;
    }


    public Item removeFirst() {
        checkEmpty();
        Item remove = head.item;
        head = head.next;
        head.prev = null;
        count--;
        return remove;
    }


    public Item removeLast() {
        checkEmpty();
        Item remove = tail.item;
        tail = tail.prev;
        tail.next = null;
        count--;
        return remove;
    }


    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * 参数空检查
     * @param param 需要检查的参数
     */
    private void checkNull(Item param) {
        if (param == null) throw new IllegalArgumentException("param can not be null");
    }

    /**
     * 移除item时进行空检查
     */
    private void checkEmpty() {
        if (isEmpty()) throw new NoSuchElementException();
    }


    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current = head;

        @Override
        public boolean hasNext() {
            return current == null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item next = current.item;
            current = current.next;
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
