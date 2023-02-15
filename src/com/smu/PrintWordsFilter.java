package com.smu;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

class PrintWordsFilter implements Runnable {
    private final BlockingQueue<Set<String>> inputQueue;

    PrintWordsFilter(BlockingQueue<Set<String>> inputQueue) {
        this.inputQueue = inputQueue;
    }

    @Override
    public void run() {
        try {
            Set<String> sortedWords = inputQueue.take();
            sortedWords.forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}