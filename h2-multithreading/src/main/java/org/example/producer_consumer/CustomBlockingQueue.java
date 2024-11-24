package org.example.producer_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue {
    final Lock lock = new ReentrantLock();

    final Condition produceCond = lock.newCondition();
    final Condition consumeCond = lock.newCondition();

    final Object[] array;
    int putIndex, takeIndex;
    int count;

    public CustomBlockingQueue(int size) {
        this.array = new Object[size];
    }

    public void put(Object x) throws InterruptedException {

        lock.lock();
        try {
            while (count == array.length) {
                produceCond.await();
            }

            array[putIndex] = x;
            putIndex++;
            if (putIndex == array.length) {
                putIndex = 0;
            }
            ++count;
            consumeCond.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                consumeCond.await();
            }
            Object x = array[takeIndex];
            takeIndex++;
            if (takeIndex == array.length) {
                takeIndex = 0;
            }
            --count;
            produceCond.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

}
