package org.example.producer_consumer;

public class Producer implements Runnable {
    private final CustomBlockingQueue blockingQueue;

    public Producer(CustomBlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true){
            long timeMillis = System.currentTimeMillis();
            try {
             blockingQueue.put(String.valueOf(timeMillis));
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            sleep(1000);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
