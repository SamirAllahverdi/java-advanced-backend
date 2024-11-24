package org.example.object_pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingObjectPool<T> {

    private final BlockingQueue<T> pool;
    private final Lock lock = new ReentrantLock();
    private final Condition takeCond = lock.newCondition();
    private final Condition getCond = lock.newCondition();
    private final int size;

    public BlockingObjectPool(int size) {
        pool = new LinkedBlockingQueue<>(size);
        this.size = size;

    }

    public Object get() {
        try {
            lock.lock();
            while (pool.isEmpty()) {
                getCond.await();
            }

            takeCond.signal();
            return pool.take();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void take(T object) {
        try {
            lock.lock();
            while (size == pool.size()) {
                takeCond.await();
            }
            pool.put(object);
            getCond.signal();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
