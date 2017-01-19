package org.app.demo.filemanager.test;

import static org.junit.Assert.*;


import org.app.demo.filemanager.utility.FolderManager;
import org.app.demo.filemanager.utility.PropertiesReader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class PropertiesReaderTest {
	PropertiesReader propertiesReader;
	FolderManager folderManager = FolderManager.getFolderManager();
	@Before
	public void setUp() throws Exception {
		propertiesReader = new PropertiesReader(folderManager);
	}

	/**
	 * test if properties are correct
	 */
	@Test
	@Ignore
	public void test() {
		assertEquals("",folderManager.getFileExtention());
		assertEquals(1000, folderManager.getThresholdForLongFile());
		assertEquals(50, folderManager.getThresholdForWordRepetition());
		
	}

}
