package org.example.deadlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Deadlock {
    static final List<Integer> collection = new ArrayList<>();

    static final Object lock = new Object();


    public static void main(String[] args) throws Exception {

        ExecutorService es = Executors.newFixedThreadPool(3);
        Future write = write(es);
        Future future = printSum(es);
        Future future1 = printSquareRootOfSumOfSquares(es);

        write.get();
        future.get();
        future1.get();

        es.shutdown();
    }

    private static Future printSquareRootOfSumOfSquares(ExecutorService es) throws ExecutionException, InterruptedException {
        Runnable writer = () -> {
            System.out.println(Thread.currentThread().getName() + " starting ");

            while(true) {
                int sum = 0;
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " locked for collection " + collection);
                    for (int a = 0; a < collection.size(); a++) {
                        sum += Math.pow(collection.get(a), 2);
                    }
                }

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ":Sum Of Squares: " + sum);
                System.out.println(Thread.currentThread().getName() + ":Square Root: " + Math.sqrt(sum));
            }
        };

        return es.submit(writer);

    }

    private static Future printSum(ExecutorService es) throws ExecutionException, InterruptedException {

        Runnable writer = () -> {
            System.out.println(Thread.currentThread().getName() + " starting ");
            while (true) {
                int sum = 0;
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " locked for collection " + collection);
                    for (int a = 0; a < collection.size(); a++) {
                        sum += collection.get(a);
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(Thread.currentThread().getName() + " :Sum: " + sum);
            }
        };

   return es.submit(writer);
    }

    private static Future write(ExecutorService es) throws Exception {

        Runnable writer = () -> {
            System.out.println(Thread.currentThread().getName() + " starting ");
            for (int a = 0; a < 100; a++) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + " locked for add " + a);
                    collection.add(a);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        return es.submit(writer);
    }


}
