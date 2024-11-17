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
    }

    private int readNumber() {
        synchronized (list) {
            if (list.isEmpty()) {
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(getName() + " was interrupted");
                }
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
