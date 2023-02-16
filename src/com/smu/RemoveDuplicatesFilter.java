package com.smu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class RemoveDuplicatesFilter implements Runnable{
    private final BlockingQueue<List<String>> inputQueue;
    private final BlockingQueue<Set<String>> outputQueue;

    RemoveDuplicatesFilter(BlockingQueue<List<String>> inputQueue, BlockingQueue<Set<String>> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        try {
            //Use the set to remove duplicates
            Set<String> uniqueWords = new HashSet<>(inputQueue.take());
            outputQueue.put(uniqueWords);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}