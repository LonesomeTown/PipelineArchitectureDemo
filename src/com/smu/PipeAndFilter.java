package com.smu;

import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PipeAndFilter {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: PipeAndFilter <input_file>");
            System.exit(1);
        }

        String inputFile = args[0];

        BlockingQueue<List<String>> readWordsQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Set<String>> uniqueWordsQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Set<String>> sortedWordsQueue = new LinkedBlockingQueue<>();

        ReadFileFilter readFilePipe = new ReadFileFilter(inputFile, readWordsQueue);
        RemoveDuplicatesFilter removeDuplicatesPipe = new RemoveDuplicatesFilter(readWordsQueue, uniqueWordsQueue);
        SortWordsFilter sortWordsPipe = new SortWordsFilter(uniqueWordsQueue, sortedWordsQueue);
        PrintWordsFilter printWordsPipe = new PrintWordsFilter(sortedWordsQueue);

        new Thread(readFilePipe).start();
        new Thread(removeDuplicatesPipe).start();
        new Thread(sortWordsPipe).start();
        new Thread(printWordsPipe).start();
    }
}