package org.app.demo.filemanager.data;

import static org.junit.Assert.*;

import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.data.DirectoryHelper;
import org.junit.Before;
import org.junit.Test;

/**
 * TDD testing class
 * @author Kartik
 * Tester class for  DirectoryHelper
 */
public class DirectoryHelperTest {
	
	String path;
	String invalidPath;
	DirectoryHelper directoryHelper;
	Directory directory;


	@Before
	public void setUp() throws Exception {
		path = "C:\\Users\\Kartik\\Documents\\java\\testfolder\\standard test folder";
		invalidPath = "aaaa";
		directoryHelper = new DirectoryHelper();

		directoryHelper.fileExtention = ".txt";
		directoryHelper.checkHidden = true;
	}

	@Test
	public void testProcessAllChildren() {

		directory = directoryHelper.processAllChildren(path, null);
		
		//check all attributes of the directory
		assertEquals(0,directory.getDepth());
		assertEquals(5,directory.getFileCount());
		assertEquals(1,directory.getLongFilesList().size());
		assertEquals(2,directory.getShortFilesList().size());
		assertEquals("",directory.getErrorString());
		assertEquals("standard test folder",directory.getName());

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
		assertEquals(0,directory.getSubDirectoryList().get(0).getLongFilesList().size());
		assertEquals(2,directory.getSubDirectoryList().get(0).getFileCount());
		assertEquals(0,directory.getSubDirectoryList().get(0).getShortFilesList().size());
		assertEquals("folder level 2",directory.getSubDirectoryList().get(0).getSubDirectoryList().get(0).getName());
		assertEquals(directory,directory.getSubDirectoryList().get(0).getParentDirectory());
		assertEquals("",directory.getSubDirectoryList().get(0).getErrorString());
	
	}

}
