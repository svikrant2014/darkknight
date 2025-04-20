package org.darkknight;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

//  Bounded Semaphore
public class BlockingQueueWithWaitAndNotify {

    // you can use conditions as well
    // private Condition notEmpty = lock.newCondition();
    // private Condition notFull  = lock.newCondition();

    private Queue<Integer> myqueue;
    private int size;

    public BlockingQueueWithWaitAndNotify(int size) {
        this.size = size;
        myqueue = new LinkedList<>();
    }

    public synchronized void addItem(int value) {
        while (myqueue.size() == size) {
            System.out.println("Queue is full Waiting for free space");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        myqueue.add(value);
        notifyAll();
        System.out.println("Item Added in the list " + new Date() + " and List Size is " + myqueue.size());
    }

    public synchronized void removeItem() {
        while (myqueue.size() == 0) {
            System.out.println("Queue is empty Waiting for to get fill");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        myqueue.poll();
        notifyAll();
        System.out.println("Item removed from the list " + new Date() + " and List Size is " + myqueue.size());
    }

    public static void main(String[] args) {

        BlockingQueueWithWaitAndNotify p = new BlockingQueueWithWaitAndNotify(5);

        //Producer
        Runnable producer = () -> {
            while (true) {
                p.addItem(new Random().nextInt(1000));
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(producer).start();
        new Thread(producer).start();

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Consumer
        Runnable consumer = () -> {
            while (true) {
                p.removeItem();
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(consumer).start();
        new Thread(consumer).start();
    }
}