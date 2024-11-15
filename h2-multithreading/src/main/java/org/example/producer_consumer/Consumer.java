package org.example.producer_consumer;

public class Consumer implements Runnable{
    private final CustomBlockingQueue blockingQueue;

    public Consumer(CustomBlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true){
            try {
                String element = String.valueOf(blockingQueue.take());
                System.out.println(Thread.currentThread().getName() + " consume " + element );
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
