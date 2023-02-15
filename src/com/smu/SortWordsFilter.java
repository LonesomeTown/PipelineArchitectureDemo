package com.smu;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

public class SortWordsFilter implements Runnable {
    private final BlockingQueue<Set<String>> inputQueue;
    private final BlockingQueue<Set<String>> outputQueue;

    SortWordsFilter(BlockingQueue<Set<String>> inputQueue, BlockingQueue<Set<String>> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        try {
            //Use the TreeSet to sort the characters.
            Set<String> sortedWords = new TreeSet<>(inputQueue.take());
            outputQueue.put(sortedWords);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}