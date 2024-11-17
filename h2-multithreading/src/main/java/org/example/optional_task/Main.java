package org.example.optional_task;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> nonBlockingList = new LinkedList<>();
        ProducerClassicThread producer = new ProducerClassicThread(nonBlockingList, "ProducerClassic");
        ConsumerClassicThread consumer = new ConsumerClassicThread(nonBlockingList, "ConsumerClassic");

        BlockingQueue<Integer> blockingDeque = new LinkedBlockingDeque<>();
        final Lock lock = new ReentrantLock();
        final Condition readCond = lock.newCondition();
        final Condition writeCon = lock.newCondition();
        ProducerConcurrencyThread producerConcurrencyThread = new ProducerConcurrencyThread(blockingDeque, lock, readCond, writeCon, "ProducerConcurrency");
        ConsumerConcurrencyThread consumerConcurrency = new ConsumerConcurrencyThread(blockingDeque, lock, readCond, writeCon, "ConsumerConcurrency");

        producer.start();
        consumer.start();
        producerConcurrencyThread.start();
        consumerConcurrency.start();

        Thread hook = new Thread(() -> {
            producer.interrupt();
            consumer.interrupt();
            producerConcurrencyThread.interrupt();
            consumerConcurrency.interrupt();
            try {
                producer.join();
                consumer.join();
                producerConcurrencyThread.join();
                consumerConcurrency.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("ProducerClassic writes Ops/Sec: " + producer.getWriteOpsPerSec());
            System.out.println("ConsumerClassic reads Ops/Sec: " + consumer.getReadOpsPerSec());
            System.out.println("ProducerConcurrency writes Ops/Sec: " + producerConcurrencyThread.getWriteOpsPerSec());
            System.out.println("ConsumerConcurrency reads Ops/Sec: " + consumerConcurrency.getReadOpsPerSec());
            System.out.println("Shutdown hook executed");
        });
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
