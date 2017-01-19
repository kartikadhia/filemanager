package org.app.demo.filemanager.test;

import static org.junit.Assert.*;

import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.data.DirectoryHelper;
import org.junit.Before;
import org.junit.Test;

public class DirectoryHelperTest {
	
	String path;
	String invalidPath;
	DirectoryHelper directoryHelper;
	Directory directory;


	@Before
	public void setUp() throws Exception {
		path = "C:\\Users\\Kartik\\Documents\\java\\testfolder\\standard test folder";
		invalidPath = "aaaa";
		directoryHelper = new DirectoryHelper("standard test folder", 0,".txt",true);
	}

	@Test
	public void testProcessAllChildren() {

		directory = directoryHelper.processAllChildren(path);
		assertEquals(0,directory.getDepth());
		assertEquals(5,directory.getFileCount());
		assertEquals(1,directory.getLongFilesList().size());
		assertEquals(2,directory.getShortFilesList().size());
		assertEquals("",directory.getErrorString());
		assertEquals("standard test folder",directory.getName());
		
	}

}
