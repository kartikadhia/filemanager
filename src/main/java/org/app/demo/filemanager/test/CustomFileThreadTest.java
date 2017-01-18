package org.app.demo.filemanager.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import org.app.demo.filemanager.calculator.CustomFileThread;
import org.app.demo.filemanager.data.CustomFile;
import org.app.demo.filemanager.data.Directory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

public class CustomFileThreadTest {
	CustomFileThread customFileThread1 = new CustomFileThread(1000,50);
	
	CustomFileThread customFileThread;
	CustomFile customFile;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CustomFileThread customFileThread1 = new CustomFileThread(1000,50);
	}

	@Before
	public void setUp() throws Exception {
		
		
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

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
	}
	public void testSmallFile() {
		File file = new File("C:/Users/Kartik/Documents/java/testfolder/standard test folder/standard test small file.txt");
		customFile = new CustomFile(file,"test",new Directory());
		File originalFile = file;
		List<CustomFile> parentLongFilesList = new ArrayList<CustomFile> ();
		List<CustomFile> parentShortFilesList = new ArrayList<CustomFile> ();
		
		CustomFileThread customFileThread = new CustomFileThread(customFile,parentLongFilesList,parentShortFilesList);
		customFileThread.processFile();
		assertEquals(originalFile.length(), customFile.getFile().length());
		assertEquals(1, parentLongFilesList.size());
		assertEquals(0, parentShortFilesList.size());
		
	}

}
