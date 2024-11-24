package org.example.optional_task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConsumerClassicThread extends Thread {

    private final LinkedList<Integer> list;
    private final List<Integer> readOpsPerSec;

    public ConsumerClassicThread(LinkedList<Integer> list, String name) {
        super(name);
        this.list = list;
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
        synchronized (list) {
            if (list.isEmpty()) {
                list.wait();
            }
            Integer number = list.pop();
            list.notify();
            return number;
        }
    }

    public List<Integer> getReadOpsPerSec() {
        return readOpsPerSec;
    }
}
