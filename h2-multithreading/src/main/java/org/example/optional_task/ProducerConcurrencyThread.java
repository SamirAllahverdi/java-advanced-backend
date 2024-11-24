package org.example.optional_task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class ProducerConcurrencyThread extends Thread {

    private final BlockingQueue<Integer> blockingQueue;
    private final RandomGenerator randomNumberGenerator;
    private final Lock lock;
    private final Condition readCond;
    private final Condition writeCon;
    private final List<Integer> writeOpsPerSec;

    public ProducerConcurrencyThread(BlockingQueue<Integer> blockingQueue, Lock lock, Condition readCond, Condition writeCon, String name) {
        super(name);
        this.randomNumberGenerator = RandomGeneratorFactory.getDefault().create();
        this.lock = lock;
        this.blockingQueue = blockingQueue;
        this.readCond = readCond;
        this.writeCon = writeCon;
        this.writeOpsPerSec = new ArrayList<>();
        System.out.println(name + " created");
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                long start = System.currentTimeMillis();
                long end = start;
                int currWriteOpsPerSec = 0;
                while (end - start < 1_000) {
                    addNumber();
                    currWriteOpsPerSec++;
                    end = System.currentTimeMillis();
                }
                writeOpsPerSec.add(currWriteOpsPerSec);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted");
            Thread.currentThread().interrupt();
        }
    }

    private void addNumber() throws InterruptedException {
        try {
            lock.lock();
            if (!blockingQueue.isEmpty()) {
                readCond.await();
            }
            blockingQueue.put(randomNumberGenerator.nextInt(0, 20));
            writeCon.signal();
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> getWriteOpsPerSec() {
        return writeOpsPerSec;
    }
}
