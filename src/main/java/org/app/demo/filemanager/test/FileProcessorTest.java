package org.app.demo.filemanager.test;

import static org.junit.Assert.*;
import org.app.demo.filemanager.calculator.FileProcessor;
import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;

public class FileProcessorTest {
	Directory directory;
	FileProcessor fileProcessor;
	String path;
	String invalidPath;


	@Before
	public void setUp() throws Exception {
		fileProcessor = new FileProcessor();
		path = "C:\\Users\\Kartik\\Documents\\java\\testfolder\\standard test folder";
		invalidPath = "aaaa";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testForNormalPath() throws InvalidOrEmptyPathException {
		directory =  fileProcessor.processFilesForPath(path,".txt",1000,50,true);
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
		assertEquals(1,directory.getSubDirectoryList().get(0).getDepth());
		assertEquals("",directory.getErrorString());
	}
	
	@Test(expected=InvalidOrEmptyPathException.class)
	public void testWrongPath() throws InvalidOrEmptyPathException  {
	
			directory =  fileProcessor.processFilesForPath(invalidPath,".txt"
					,1000,50,true);
		    fail( "did not throw expected exception" );

	}
	@Test(expected=InvalidOrEmptyPathException.class)
	public void testEmptyPath() throws InvalidOrEmptyPathException  {
			directory =  fileProcessor.processFilesForPath("",".txt"
					,1000,50,true);
		    fail( "did not throw expected exception" );

	}
	@Test
	public void testForEmptyDirectory() {
		directory = fileProcessor.getEmptyDirectory(path);
		assertEquals("standard test folder",directory.getName() );
		assertEquals(0,directory.getDepth());
		assertEquals(0,directory.getFileCount());
		assertEquals(0,directory.getLongFilesList().size() );
		assertEquals(0,directory.getShortFilesList().size());
		assertEquals(null,directory.getParentDirectory());
		assertEquals(0,directory.getSubDirectoryList().size());
		assertEquals(false,directory.isRelevant());
	}
	
	

}
