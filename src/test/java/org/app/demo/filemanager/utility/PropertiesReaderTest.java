package org.app.demo.filemanager.utility;

import static org.junit.Assert.*;


import org.app.demo.filemanager.utility.FolderManager;
import org.app.demo.filemanager.utility.PropertiesReader;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
/**
 * TDD Tester class for class PropertiesReader
 * @author Kartik
 *
 */


public class PropertiesReaderTest {
	PropertiesReader propertiesReader;
	FolderManager folderManager = FolderManager.getFolderManager();
	@Before
	public void setUp() throws Exception {
		propertiesReader = new PropertiesReader();
	}

	/**
	 * test if properties are correct
	 */
	@Test
	@Ignore
	public void test() {
		assertEquals("",folderManager.getFileExtension());
		assertEquals(1000, folderManager.getThresholdForLongFile());
		assertEquals(50, folderManager.getThresholdForWordRepetition());
		assertEquals(true,folderManager.isCheckHidden());
	}

}
