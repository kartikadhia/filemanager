package org.app.demo.filemanager.calculator;

import java.io.BufferedReader;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.app.demo.filemanager.data.CustomFile;


/**
 *  @author Kartik
 *  This class processes the relevant files by using multiple threads to process the file parallely
 *  the buffered reader reads the file and inserts the lines into a ArrayBlockingQueue
 *  (producer consumer pattern)
 *  The threads then pick up these lines from the queue and process them to check for their words.
 *  finally, the final word count is calculated and also the file is classified as long or short, depending on the
 *  count of its words.
 *  
 */

public class CustomFileThread implements Callable <Long> {
	
	final static Logger logger = Logger.getLogger(CustomFileThread.class);
	
	private static int thresholdForLongFile;
	private static int thresholdForWordRepetition;
	private CustomFile customFile;
	private static boolean countNumbers;
	private List<CustomFile> parentLongFilesList;
	private List<CustomFile> parentShortFilesList;
	private int totalWords = 0;
	private ArrayBlockingQueue  <String> setOfLines = new ArrayBlockingQueue  <String> (10);
	private ConcurrentHashMap <String, Integer> wordCount ;
	public  CustomFileThread() {
		
	}
	
	public CustomFileThread(int thresholdForLongFile, int thresholdForWordRepetition,boolean countNumbers) {
		CustomFileThread.thresholdForLongFile =thresholdForLongFile;
		CustomFileThread.thresholdForWordRepetition = thresholdForWordRepetition;
		CustomFileThread.countNumbers = countNumbers;
	}
	public CustomFileThread(CustomFile customFile,List<CustomFile> parentLongFilesList,
									List<CustomFile> parentShortFilesList) {
		this.customFile = customFile;
		this.parentLongFilesList= parentLongFilesList;
		this.parentShortFilesList = parentShortFilesList;
	}
	/**
	 * Processes the file by dividing it into lines and then adding them to a ArrayBlockingQueue
	 * generates threads using the executor framework that then pick up these lines and process them further
	 * Adds the information to the file class, so that it can be sent in the response
	 */
	public void processFile() {
		//process word count here
		long startTime = System.currentTimeMillis();
		String line;
		wordCount = customFile.getWordCount();
		List<Future<Long>> listOfFutures = new ArrayList<Future<Long>>();
		// Choose the executor and the number of threads that best suits the processor and purpose
		// work stealing pool took about 875 ms, cached threadpool too about 1100 ms
		// fixed thread pool took about 1000 ms with 10 threads and 840ms with 4 threads
		
		// ExecutorService executor =  Executors.newCachedThreadPool();
		// ExecutorService executor =  Executors.newWorkStealingPool();
		   ExecutorService executor =  Executors.newFixedThreadPool(4); 
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(customFile.getFile()))) {
			while((line= bufferedReader.readLine()) != null) {
				line = line.trim();
				line = line.toLowerCase();
				if(line == "") continue;
				setOfLines.put(line);
				Future<Long> future = executor.submit(this);
				listOfFutures.add(future);
				}
			// wait until all the futures return.
			for (Future <Long> future : listOfFutures) {
				future.get();
			}
			// stop accepting new tasks, close the executor
			executor.shutdown();
		}
		catch (IOException e1) {
			customFile.setErrorString("There was an error reading this file, the file could be open or"
					+ " blocked by other resources");
			logger.error(e1);
		}
		catch (ExecutionException |InterruptedException e1) { 
			customFile.setErrorString("Unknown error occured while reading this file");
			logger.error(e1);
		}

		for(String word : wordCount.keySet() ) {
			totalWords = totalWords + wordCount.get(word);
			if(wordCount.get(word)<thresholdForWordRepetition) {
					wordCount.remove(word);
				}
			}
		logger.debug("total words = " + totalWords) ;
		customFile.setTotalWords(totalWords);
		if(totalWords>thresholdForLongFile) {
			customFile.setWordCount(wordCount);
			parentLongFilesList.add(customFile);
		}
		else {
			customFile.setWordCount(null);
			parentShortFilesList.add(customFile);
		}
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    logger.debug("processed file "+ customFile.getName() + " in " + elapsedTime + "ms");
	}
	/**
	 * overridden method that is called when a new task is submitted for this class.
	 * The generated thread picks up lines from the ArrayBlockingQueue, then
	 * splits it into words and then populates an array, that is later used to count the number of words and
	 * occurrence of each word.
	 */

	@Override
	public Long call() throws Exception {
		String line;
		char [] wordArray;
		int i;
		char c;
		try {
			line = setOfLines.take();
			String[] words = line.replaceAll("\\s+", " ").split(" ");
			
			for(String word : words) {
				wordArray = word.toCharArray();
				for(i=0;i<wordArray.length;i++) {
					c = wordArray[i];
					if(countNumbers ? (!((c>64&&c<91)||(c>96 &&c<123)||(c>47&&c<58))) : (!((c>64&&c<91)||(c>96 &&c<123))) ) {
						word = word.replace(c+"","" );
					}
				}
				if(word.isEmpty()) continue;
				synchronized (this) {
					if(wordCount.containsKey(word)) {
						wordCount.put(word, (wordCount.get(word)+1));
					}
					else {
						wordCount.put(word, 1);
					}
			}
			}	
			
		} 
		catch (InterruptedException e) {
			customFile.setErrorString("An Error occurred while processing this file, word count may not"
					+"be accurate");
			
			logger.error(e);
		}
		return new Long(0);
	}

}
