package org.app.demo.filemanager.test;

import static org.junit.Assert.*;

import org.app.demo.filemanager.utility.FolderManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FolderManagerTest {
	
	FolderManager folderManager ;
	FolderManager folderManager2 ;

	@After
	public void tearDown() throws Exception {
	}
	/* doesnt work after the properties were read by server from class path
	 * */
	 
	@Test
	public void testSingleton() {
		folderManager = FolderManager.getFolderManager();
		folderManager = null;
		folderManager2 = FolderManager.getFolderManager();
		assertEquals(true, folderManager.equals(folderManager2));
		assertEquals(folderManager.toString(),folderManager2.toString());
	}

}
