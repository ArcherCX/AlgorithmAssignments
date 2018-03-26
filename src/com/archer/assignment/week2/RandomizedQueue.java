package com.archer.assignment.week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements IRandomizedQueue<Item> {
    private static final int DEFAULT_SIZE = 1;
    private Item[] data;
    private int index = -1;

    public RandomizedQueue() {
        this.data = (Item[]) new Object[DEFAULT_SIZE];
    }


    public boolean isEmpty() {
        return index < 0;
    }


    public int size() {
        return index + 1;
    }


    public void enqueue(Item item) {
        checkNull(item);
        if (index + 1 == data.length) increaseSize();
        data[++index] = item;
    }

    /**
     * 数组大小增倍，时机为当数组填满时
     */
    private void increaseSize() {
        Item[] old = data;
        data = (Item[]) new Object[old.length << 1];
        System.arraycopy(old, 0, data, 0, old.length);
    }


    public Item dequeue() {
        checkEmpty();
        final int de = StdRandom.uniform(index + 1);
        swap(de, index);//通过交换，保证有效item都在index范围内
        Item item = data[index];
        data[index--] = null;
        int edge = data.length >> 2;
        if (edge > 0 && index < edge) decreaseSize();
        return item;
    }

    /**
     * 数组大小减小一半，时机为当数组内只有四分之一内容时
     */
    private void decreaseSize() {
        Item[] old = data;
        data = (Item[]) new Object[old.length >> 1];
        System.arraycopy(old, 0, data, 0, index + 1);
    }

    /**
     * 交换数据数组中的两个元素
     */
    private void swap(int src, int des) {
        if (src == des) return;
        Item source = data[src];
        data[src] = data[des];
        data[des] = source;
    }


    public Item sample() {
        checkEmpty();
        return data[StdRandom.uniform(index + 1)];
    }


    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    /**
     * 参数空检查
     *
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

    private class RQIterator implements Iterator<Item> {
        private int[] idxs;
        private int curret;
        private final int size;

        private RQIterator() {
            size = size();
            idxs = new int[size];
            for (int i = 0; i < size; i++) {
                idxs[i] = i;
            }
            randomIdxs();
        }

        /**
         * 打乱输出顺序
         */
        private void randomIdxs() {
            int swapTimes = (size >> 1);//交换次数，因为进行交换的对象在前面，所以只要交换后一半即可,但并不均匀
            for (int i = size - 1; i > swapTimes; i--) {
                int o = idxs[i];
                int replaced = StdRandom.uniform(i);
                idxs[i] = idxs[replaced];
                idxs[replaced] = o;
            }
        }


        public boolean hasNext() {
            return curret != size;
        }


        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return data[idxs[curret++]];
        }


        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
