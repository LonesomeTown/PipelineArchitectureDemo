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
        //First filter: read the words from the input file
        //Put the words into the output queue.(Pipe)
        ReadFileFilter readFilePipe = new ReadFileFilter(inputFile, readWordsQueue);
        //Second filter: remove the duplicates in the words
        //Get the words in the queue (last pipe) and remove, the put back to the queue. (Pipe)
        RemoveDuplicatesFilter removeDuplicatesPipe = new RemoveDuplicatesFilter(readWordsQueue, uniqueWordsQueue);
        //Third filter: sort the words
        //Get the words in the queue (last pipe) and sort, the put back to the queue. (Pipe)
        SortWordsFilter sortWordsPipe = new SortWordsFilter(uniqueWordsQueue, sortedWordsQueue);
        //Forth filter: print the words
        //Get the words in the queue (last pipe) and print.
        PrintWordsFilter printWordsPipe = new PrintWordsFilter(sortedWordsQueue);

        new Thread(readFilePipe).start();
        new Thread(removeDuplicatesPipe).start();
        new Thread(sortWordsPipe).start();
        new Thread(printWordsPipe).start();
    }
}