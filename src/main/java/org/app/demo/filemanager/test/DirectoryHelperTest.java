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
		
		//check all attributes of the directory
		assertEquals(0,directory.getDepth());
		assertEquals(5,directory.getFileCount());
		assertEquals(1,directory.getLongFilesList().size());
		assertEquals(2,directory.getShortFilesList().size());
		assertEquals("",directory.getErrorString());
		assertEquals("standard test folder",directory.getName());
		assertEquals(2965,directory.getTotalWords());
		assertEquals(null,directory.getParentDirectory());
		
		//check all attributes of the file
		assertEquals("standard test big file.txt",directory.getLongFilesList().get(0).getName());
		assertEquals(4830,directory.getLongFilesList().get(0).getSize());
		assertEquals(1186,directory.getLongFilesList().get(0).getTotalWords());
		assertEquals(Integer.valueOf(1154),directory.getLongFilesList().get(0).getWordCount().get("abc"));
		
		//check all attributes of the sub folder
		assertEquals(1,directory.getSubDirectoryList().get(0).getDepth());
		assertEquals("folder level 1",directory.getSubDirectoryList().get(0).getName());
		assertEquals(2,directory.getSubDirectoryList().get(0).getFileCount());
		assertEquals(1,directory.getSubDirectoryList().get(0).getLongFilesList().size());
		assertEquals(1,directory.getSubDirectoryList().get(0).getShortFilesList().size());
		assertEquals("folder level 2",directory.getSubDirectoryList().get(0).getSubDirectoryList().get(0).getName());
		assertEquals(1186,directory.getSubDirectoryList().get(0).getTotalWords());
		assertEquals(directory,directory.getSubDirectoryList().get(0).getParentDirectory());
		assertEquals(null,directory.getSubDirectoryList().get(0).getErrorString());
	
	}

}
