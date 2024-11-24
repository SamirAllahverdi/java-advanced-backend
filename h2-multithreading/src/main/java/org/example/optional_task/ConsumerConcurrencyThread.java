package org.example.optional_task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConsumerConcurrencyThread extends Thread {

    private final BlockingQueue<Integer> blockingQueue;
    private final Lock lock;
    private final Condition readCond;
    private final Condition writeCon;
    private final List<Integer> readOpsPerSec;

    public ConsumerConcurrencyThread(BlockingQueue<Integer> blockingQueue, Lock lock, Condition readCond, Condition writeCon, String name) {
        super(name);
        this.blockingQueue = blockingQueue;
        this.lock = lock;
        this.readCond = readCond;
        this.writeCon = writeCon;
        this.readOpsPerSec = new ArrayList<>();
        System.out.println(name + " created");
    }

    @Override
    public void run() {
        try {
            int sum = 0;
            while (!isInterrupted()) {
                long start = System.currentTimeMillis();
                long end = start;
                int currReadOpsPerSec = 0;
                while (end - start < 1_000) {
                    int number = readNumber();
                    sum += number;
                    currReadOpsPerSec++;
                    end = System.currentTimeMillis();
                }
                readOpsPerSec.add(currReadOpsPerSec);
            }

        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted");
            Thread.currentThread().interrupt();
        }
    }

    private int readNumber() throws InterruptedException {
        try {
            lock.lock();
            if (blockingQueue.isEmpty()) {
                writeCon.await();
            }
            Integer number = blockingQueue.take();
            readCond.signal();
            return number;
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> getReadOpsPerSec() {
        return readOpsPerSec;
    }
}
