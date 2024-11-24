package org.example.das;

import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    //TODO: how to do below?
    //Run your samples with different versions of Java (6, 8, and 10, 11) and measure the performance.
    //Provide a simple report to your mentor

    public static void main(String[] args) {
        adjustLogging();

        test(new ThreadSafeMap<>());
        //  test(new ThreadUnsafeMap<>());
        //  test(new HashMap<>());
        //  test(new ConcurrentHashMap<>());
        //  test(Collections.synchronizedMap(new HashMap<>()));
    }

    private static void adjustLogging() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new Formatter() {
            @Override
            public String format(java.util.logging.LogRecord record) {
                return String.format("[%s] %s%n",
                        Thread.currentThread().getName(),
                        record.getMessage());
            }
        });
        LOG.addHandler(handler);
        LOG.setUseParentHandlers(false);
    }

    private static void test(Map<Integer, Integer> map) {
        ThreadFactory customThreadFactory = new ThreadFactory() {
            private int counter = 1;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Thread-" + (counter++));
            }
        };
        ExecutorService service = Executors.newFixedThreadPool(2, customThreadFactory);
        try {
            service.execute(() -> sumValues(map));
            service.execute(() -> addValues(map));
        } finally {
            service.shutdown();
        }
    }

    private static void sumValues(Map<Integer, Integer> map) {
        int sum = 0;
        sleep(900);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            sleep(100);
            sum += entry.getValue();
        }
        LOG.info("sum = " + sum);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    private static void addValues(Map<Integer, Integer> map) {
        for (int i = 0; i < 100; i++) {
            sleep(100);
            LOG.info("Adding " + i);
            map.put(i, i);
        }
    }
}