package org.app.demo.filemanager.test;

import static org.junit.Assert.*;

import org.app.demo.filemanager.calculator.FileProcessor;
import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileProcessorTest {
	Directory directory;
	FileProcessor fileProcessor;
	String path;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		fileProcessor = new FileProcessor();
		path = "C:\\Users\\Kartik\\Documents\\java\\testfolder\\standard test folder";

		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAll() throws InvalidOrEmptyPathException {
		directory =  fileProcessor.processFilesForPath(path,".txt"
				,1000,50,true);
		assertEquals(5, directory.getFileCount());
		assertEquals(0, directory.getDepth());
		System.out.println(directory.getName());
		assertEquals(true, directory.isRelevant());
		
		assertEquals("folder level 1",directory.getSubDirectoryList().get(0).getName());
		assertEquals(true,directory.getSubDirectoryList().get(0).isRelevant());
		//check if the directory of the child is the directory itself
		assertEquals(directory, directory.getSubDirectoryList().get(0).getParentDirectory());
		assertEquals(1,directory.getSubDirectoryList().size());
		assertEquals(2965,directory.getTotalWords());
	}

}