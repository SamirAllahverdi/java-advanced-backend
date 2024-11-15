package org.example.producer_consumer;

public class Main {

    public static void main(String[] args) {
        CustomBlockingQueue blockingQueue = new CustomBlockingQueue(3);

        Producer producer = new  Producer(blockingQueue);
        Consumer consumer1 = new  Consumer(blockingQueue);
        Consumer consumer2 = new  Consumer(blockingQueue);


        Thread producerThread  = new Thread(producer);
        Thread consumer1Thread  = new Thread(consumer1);
        Thread consumer2Thread  = new Thread(consumer2);

        producerThread.start();
        consumer2Thread.start();

    }
}
