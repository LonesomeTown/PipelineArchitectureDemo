package com.smu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * ReadFilePipe
 *
 * @author T.W 2/12/23
 */
public class ReadFileFilter implements Runnable{
    private final BlockingQueue<List<String>> outputQueue;
    private final String fileName;

    ReadFileFilter(String fileName, BlockingQueue<List<String>> outputQueue) {
        this.fileName = fileName;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Read words from the input file and put them into the array list.
                String[] lineWords = line.split(", ");
                words.addAll(Arrays.asList(lineWords));
            }
            //Put the words list into the output queue.
            outputQueue.put(words);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
