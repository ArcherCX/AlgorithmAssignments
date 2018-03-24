package com.archer.assignment.week2;

import java.util.Iterator;

public interface IDeque<Item> extends Iterable<Item> {
    /**
     * is the deque empty?
     */
    public boolean isEmpty();

    /**
     * return the number of items on the deque
     */
    public int size();

    /**
     * add the item to the front
     */
    public void addFirst(Item item);

    /**
     * add the item to the end
     */
    public void addLast(Item item);

    /**
     * remove and return the item from the front
     */
    public Item removeFirst();

    /**
     * remove and return the item from the end
     */
    public Item removeLast();

    /**
     * return an iterator over items in order from front to end
     */
//    public Iterator<Item> iterator();
}
