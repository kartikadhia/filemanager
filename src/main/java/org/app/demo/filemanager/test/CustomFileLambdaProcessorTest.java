package org.app.demo.filemanager.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.app.demo.filemanager.calculator.CustomFileLambdaProcessor;
import org.app.demo.filemanager.calculator.CustomFileThread;
import org.app.demo.filemanager.data.CustomFile;
import org.app.demo.filemanager.data.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomFileLambdaProcessorTest {

	static CustomFileLambdaProcessor customFileLambdaProcessor ;
	
	CustomFileLambdaProcessor customFileLambdaProcessor1;
	CustomFile customFile;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 
	}
	@Before
	public void setUp() throws Exception {
		File file = new File("C:/Users/Kartik/Documents/java/testfolder/standard test folder/standard test big file.txt");
		Directory directory = new Directory();
		CustomFile customFile = new CustomFile( file, "standard test big file.txt",directory);
		customFileLambdaProcessor = new CustomFileLambdaProcessor(customFile);
		customFileLambdaProcessor.thresholdForLongFile = 1000;
		customFileLambdaProcessor.countNumbers = true;
		customFileLambdaProcessor.thresholdForWordRepetition = 50;
	}
	
	

	@After
	public void tearDown() throws Exception {
	}
	/**
	 * tests if a big file is counted correctly
	 */
	@Test
	public void testBigFile() {
		File file = new File("C:/Users/Kartik/Documents/java/testfolder/standard test folder/standard test big file.txt");
		Directory directory = new Directory();
		CustomFile customFile = new CustomFile( file, "standard test big file.txt",directory);
		customFileLambdaProcessor = new CustomFileLambdaProcessor(customFile);
		customFileLambdaProcessor.thresholdForLongFile = 1000;
		customFileLambdaProcessor.countNumbers = true;
		customFileLambdaProcessor.thresholdForWordRepetition = 50;
		
		customFile = new CustomFile(file,"test",directory);

		customFileLambdaProcessor.processFile();
		assertEquals(file.length(), customFile.getFile().length());
		assertEquals(1, directory.getLongFilesList().size());
		assertEquals(0,directory.getShortFilesList().size());
		assertEquals(1190, customFile.getTotalWords());
		assertEquals(Integer.valueOf(1154),customFile.getWordCount().get("abc"));
	}
	/**
	 * Tests if the file is added to the correct list and if the words are counted correctly. (standard test small file
	 * is a file with 595  of words, including some with special characters, that don't need to be included)
	 * also checks if the length of the file is set correctly
	 */
	@Test
	public void testSmallFile() {
		File file = new File("C:/Users/Kartik/Documents/java/testfolder/standard test folder/standard test small file.txt");
		
		Directory directory = new Directory();
		CustomFile customFile = new CustomFile( file, "standard test big file.txt",directory);
		customFileLambdaProcessor = new CustomFileLambdaProcessor(customFile);
		customFileLambdaProcessor.thresholdForLongFile = 1000;
		customFileLambdaProcessor.countNumbers = true;
		customFileLambdaProcessor.thresholdForWordRepetition = 50;
		File originalFile = file;
		customFileLambdaProcessor.processFile();
		assertEquals(originalFile.length(), customFile.getFile().length());
		assertEquals(1, directory.getLongFilesList().size());
		assertEquals(0,directory.getShortFilesList().size());
		assertEquals(595, customFile.getTotalWords());
		assertEquals(Integer.valueOf(0),customFile.getWordCount().get("abc"));
		
	}
	/**
	 * tests if an empty file is counted correctly
	 */
	
	@Test
	public void testEmptyFile() {
		File file = new File("C:/Users/Kartik/Documents/java/testfolder/standard test folder/standard test empty file.txt");
		Directory directory = new Directory();
		CustomFile customFile = new CustomFile( file, "standard test big file.txt",directory);
		customFileLambdaProcessor = new CustomFileLambdaProcessor(customFile);
		customFileLambdaProcessor.thresholdForLongFile = 1000;
		customFileLambdaProcessor.countNumbers = true;
		customFileLambdaProcessor.thresholdForWordRepetition = 50;
		customFileLambdaProcessor.processFile();
		File originalFile = file;
		assertEquals(originalFile.length(), customFile.getFile().length());
		assertEquals(originalFile.length(), customFile.getFile().length());
		assertEquals(1, directory.getLongFilesList().size());
		assertEquals(0,directory.getShortFilesList().size());
		assertEquals(0, customFile.getTotalWords());
		
	}
}
