package org.app.demo.filemanager.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({  CustomFileThreadTest.class, FileProcessorTest.class, PropertiesReaderTest.class,
	FolderManagerTest.class,DirectoryHelperTest.class,FileManagerWebServiceTest.class })

public class AllTests {

}
