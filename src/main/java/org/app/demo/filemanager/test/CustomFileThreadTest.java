package org.app.demo.filemanager.test;

import static org.junit.Assert.*;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.app.demo.filemanager.calculator.CustomFileThread;
import org.app.demo.filemanager.data.CustomFile;
import org.app.demo.filemanager.data.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class CustomFileThreadTest {
	CustomFileThread customFileThread1 = new CustomFileThread(1000,50);
	
	CustomFileThread customFileThread;
	CustomFile customFile;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		@SuppressWarnings("unused")
		CustomFileThread customFileThread1 = new CustomFileThread(1000,50);
	}

	@Before
	public void setUp() throws Exception {
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
		customFile = new CustomFile(file,"test",new Directory());
		File originalFile = file;
		List<CustomFile> parentLongFilesList = new ArrayList<CustomFile> ();
		List<CustomFile> parentShortFilesList = new ArrayList<CustomFile> ();
		CustomFileThread customFileThread = new CustomFileThread(customFile,parentLongFilesList,parentShortFilesList);
		customFileThread.processFile();
		assertEquals(originalFile.length(), customFile.getFile().length());
		assertEquals(1, parentLongFilesList.size());
		assertEquals(0, parentShortFilesList.size());
		assertEquals(1186, customFile.getTotalWords());
		assertEquals(Integer.valueOf(1154),customFile.getWordCount().get("abc"));
	}
	/**
	 * Tests if the file is added to the correct list and if the words are counted correctly. (standard test small file
	 * is a file with 593  of words, including some with special characters, that don't need to be included)
	 * also checks if the length of the file is set correctly
	 */
	@Test
	public void testSmallFile() {
		File file = new File("C:/Users/Kartik/Documents/java/testfolder/standard test folder/standard test small file.txt");
		customFile = new CustomFile(file,"test",new Directory());
		File originalFile = file;
		List<CustomFile> parentLongFilesList = new ArrayList<CustomFile> ();
		List<CustomFile> parentShortFilesList = new ArrayList<CustomFile> ();
		CustomFileThread customFileThread = new CustomFileThread(customFile,parentLongFilesList,parentShortFilesList);
		customFileThread.processFile();
		assertEquals(originalFile.length(), customFile.getFile().length());
		assertEquals(0, parentLongFilesList.size());
		assertEquals(1, parentShortFilesList.size());
		assertEquals(593, customFile.getTotalWords());
		
	}
	/**
	 * tests if an empty file is counted correctly
	 */
	
	@Test
	public void testEmptyFile() {
		File file = new File("C:/Users/Kartik/Documents/java/testfolder/standard test folder/standard test empty file.txt");
		customFile = new CustomFile(file,"test",new Directory());
		File originalFile = file;
		List<CustomFile> parentLongFilesList = new ArrayList<CustomFile> ();
		List<CustomFile> parentShortFilesList = new ArrayList<CustomFile> ();
		CustomFileThread customFileThread = new CustomFileThread(customFile,parentLongFilesList,parentShortFilesList);
		customFileThread.processFile();
		assertEquals(originalFile.length(), customFile.getFile().length());
		assertEquals(0, parentLongFilesList.size());
		assertEquals(1, parentShortFilesList.size());
		assertEquals(0, customFile.getTotalWords());
		
	}

}
