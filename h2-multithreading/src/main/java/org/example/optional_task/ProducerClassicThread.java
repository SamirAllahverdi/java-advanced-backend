package org.example.optional_task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class ProducerClassicThread extends Thread {

    private final LinkedList<Integer> list;
    private final RandomGenerator randomNumberGenerator;
    private final List<Integer> writeOpsPerSec;

    public ProducerClassicThread(LinkedList<Integer> list, String name) {
        super(name);
        this.randomNumberGenerator = RandomGeneratorFactory.getDefault().create();
        this.list = list;
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
        synchronized (list) {
            if (!list.isEmpty()) {
                list.wait();
            }
            int randomNumber = randomNumberGenerator.nextInt(0, 20);
            list.add(randomNumber);
            list.notify();
        }
    }

    public List<Integer> getWriteOpsPerSec() {
        return writeOpsPerSec;
    }
}
