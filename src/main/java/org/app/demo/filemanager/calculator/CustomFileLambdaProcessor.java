package org.app.demo.filemanager.calculator;



import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.log4j.Logger;
import org.app.demo.filemanager.data.CustomFile;


public class CustomFileLambdaProcessor {




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
		final static Logger logger = Logger.getLogger(CustomFileLambdaProcessor.class);
		
		public static int thresholdForLongFile;
		public static int thresholdForWordRepetition;
		public static boolean countNumbers;
		private CustomFile customFile;
		
		private volatile long totalWords = 0;
		private volatile ConcurrentHashMap <String, Long> wordCount = new  ConcurrentHashMap <String, Long> ();
	
		public  CustomFileLambdaProcessor() {
			
		}
		public CustomFileLambdaProcessor(CustomFile customFile) {
			this.customFile = customFile;
		}
		public CustomFile getCustomFile() {
			return customFile;
		}
		public void setCustomFile(CustomFile customFile) {
			this.customFile = customFile;
		}
		/**
		 * Processes the file by using lambdas. It generates streams of lines and then sub stream
		 * these substreams are then refined and the words in the files are counted.
		 * This map is later used to calculate the total number of words in the file
		 * The calculated details are then populated on the custom file object, 
		 *  This can then be sent along with the response to the webservice request.
		 *  
		 */
		
		
		public void processFile() {
			long startTime = System.currentTimeMillis();
			try {
				
				 Files.lines(
						Paths.get(customFile.getFile().getAbsolutePath()),StandardCharsets.ISO_8859_1)
				.map(thisLine -> thisLine.replaceAll("\\s"," ").split(" "))
				.flatMap(Arrays::stream)
				.filter(s -> !s.isEmpty()&& s.trim() != "")
				.map(String::toLowerCase)
				.map(s -> removeSpecialCharacters(s))
				.forEachOrdered(s -> {
					String word = s + "";
					if(wordCount.containsKey(word)) {
						wordCount.put(word, (wordCount.get(word)+1));
					}
					else {
						wordCount.put(word, (long) 1);
					}
				});
				
			} catch (IOException e) {
				customFile.setErrorString("There was an error accessing this file, the word count may not be accurate");
				e.printStackTrace();
			}
			 
			
			Map <String, Long> temp = 
			wordCount.entrySet().parallelStream()
			.filter(s -> {
				synchronized (this) {
					totalWords = totalWords + s.getValue();
				}
				return s.getValue()>thresholdForWordRepetition;})
			.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
			 
			 customFile.setTotalWords(totalWords);
			 if(totalWords>thresholdForLongFile) {
				 customFile.getParent().getLongFilesList().add(customFile);
				 customFile.setWordCount(temp);
			 }
			 else 
				 customFile.getParent().getShortFilesList().add(customFile);	 
			 
			 long stopTime = System.currentTimeMillis();
			 long elapsedTime = stopTime - startTime;
			 logger.debug("processed file "+ customFile.getName() + " in " + elapsedTime + "ms");
			
		}
		private Object removeSpecialCharacters(String word) {
			char [] wordArray = word.toCharArray();
			int i;
			char c;
			for(i=0;i<wordArray.length;i++) {
				c = wordArray[i];
				if(countNumbers ? (!((c>64&&c<91)||(c>96 &&c<123)||(c>47&&c<58))) : (!((c>64&&c<91)||(c>96 &&c<123))) ) {
					word = word.replace(c+"","" );
				}
			}
			return word;
		}

}


