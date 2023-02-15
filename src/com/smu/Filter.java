package com.smu;

import java.util.concurrent.BlockingQueue;

public abstract class Filter<T> implements Runnable {
    protected final BlockingQueue<T> input;
    protected final BlockingQueue<T> output;
    private final T poisonPill;

    protected Filter(BlockingQueue<T> input, BlockingQueue<T> output, T poisonPill) {
        this.input = input;
        this.output = output;
        this.poisonPill = poisonPill;
    }

    public abstract void process(T item) throws InterruptedException;

    public void run() {
        try {
            while (true) {
                T item = input.take();
                process(item);
                if (item == poisonPill) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}