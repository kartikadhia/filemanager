package org.app.demo.filemanager.test;

import static org.junit.Assert.*;

import org.app.demo.filemanager.utility.FolderManager;
import org.app.demo.filemanager.utility.PropertiesReader;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

public class PropertiesReaderTest {
	PropertiesReader propertiesReader;
	FolderManager folderManager = FolderManager.getFolderManager();
	@Before
	public void setUp() throws Exception {
		// propertiesReader = new PropertiesReader(folderManager);
	}

	@After
	public void tearDown() throws Exception {
	}
	/**
	 * test if properties are correct
	 */
	@Test
	@Ignore
	public void test() {
		/* need to test on server, cant test using plain jvm, 
		assertEquals("",folderManager.getFileExtention());
		assertEquals(1000, folderManager.getThresholdForLongFile());
		assertEquals(50, folderManager.getThresholdForWordRepetition());
		*/
	}

}
