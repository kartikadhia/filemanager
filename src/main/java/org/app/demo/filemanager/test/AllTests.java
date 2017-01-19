package org.app.demo.filemanager.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({  CustomFileThreadTest.class, FileProcessorTest.class, PropertiesReaderTest.class,
	FolderManagerTest.class })

public class AllTests {

}
