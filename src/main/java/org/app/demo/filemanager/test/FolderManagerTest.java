package org.app.demo.filemanager.test;

import static org.junit.Assert.*;

import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
import org.app.demo.filemanager.exception.InvalidParameterException;
import org.app.demo.filemanager.utility.FolderManager;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kartik
 * Test behavior of FolderManager Class
 */

public class FolderManagerTest {
	
	FolderManager folderManager ;
	FolderManager folderManager2 ;
	String path;
	String invalidPath;
	
	@Before
	public void setUp() throws Exception {
		folderManager =  FolderManager.getFolderManager();
		path = "C:\\Users\\Kartik\\Documents\\java\\testfolder\\standard test folder";
		invalidPath = "aaaa";
	}

	 /**
	  * Test if the class is behaving like a singleton
	  */
	@Test
	public void testSingleton() {
		folderManager = FolderManager.getFolderManager();
		folderManager2 = FolderManager.getFolderManager();
		assertEquals(true, folderManager.equals(folderManager2));
		assertEquals(folderManager.toString(),folderManager2.toString());
	}
	/**
	 * Test if the properties are set if the file extension is null
	 * @throws InvalidOrEmptyPathException 
	 * @throws InvalidParameterException 
	 */
	@Test
	public void testProcessAllFilesWithPropertiesNotSet() throws InvalidOrEmptyPathException, InvalidParameterException {
		folderManager.setFileExtension(null);
		folderManager.processFilesForPath(path,null,null,null,null,null);
		assertEquals(".txt",folderManager.getFileExtension());
		assertEquals(1000,folderManager.getThresholdForLongFile());
		assertEquals(50,folderManager.getThresholdForWordRepetition());
		assertEquals(true,folderManager.isCheckHidden());
	}
	/**
	 * Test if the method throws appropriate exception
	 * @throws InvalidOrEmptyPathException
	 * @throws InvalidParameterException 
	 */
	@Test(expected=InvalidOrEmptyPathException.class)
	public void testProcessAllFilesWithInvalidPath() throws InvalidOrEmptyPathException, InvalidParameterException {
		folderManager.processFilesForPath(invalidPath,null,null,null,null,null);
		fail("Did not throw the necessary exception");
	}
	/**
	 * Test if all the properties are set from the file
	 */
	@Test
	public void testIfAllPropertiesAreSet() {
		assertEquals(1000,folderManager.getThresholdForLongFile());
		assertEquals(50,folderManager.getThresholdForWordRepetition());
		assertEquals(true,folderManager.isCheckHidden());
		assertEquals(".txt",folderManager.getFileExtension());
	}
	
	@Test
	public void testIfEmptyDirectoryIsCorrectlyReturned() throws InvalidOrEmptyPathException, InvalidParameterException {
		path = "C:/Users/Kartik/Documents/java/testfolder/with no file";
		Directory directory = folderManager.processFilesForPath(path,null,null,null,null,null);
		assertEquals("with no file",directory.getName() );
		assertEquals(0,directory.getDepth());
		assertEquals(0,directory.getFileCount());
		assertEquals(0,directory.getLongFilesList().size() );
		assertEquals(0,directory.getShortFilesList().size());
		assertEquals(null,directory.getParentDirectory());
		assertEquals(0,directory.getSubDirectoryList().size());
		assertEquals(false,directory.isRelevant());
	}
	@Test(expected=InvalidParameterException.class)
	public void testProcessAllFilesWithInvalidNumber() throws InvalidOrEmptyPathException, InvalidParameterException {
		folderManager.processFilesForPath(invalidPath,null,null,"323x",null,null);
		fail("Did not throw the necessary exception");
	}
	@Test(expected=InvalidParameterException.class)
	public void testProcessAllFilesWithInvalidNumberPath() throws InvalidOrEmptyPathException, InvalidParameterException {
		folderManager.processFilesForPath(invalidPath,null,null,null,"323x",null);
		fail("Did not throw the necessary exception");
	}
	
}
