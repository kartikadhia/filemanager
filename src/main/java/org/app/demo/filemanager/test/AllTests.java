package org.app.demo.filemanager.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AllTests.class, CustomFileThreadTest.class, FileProcessorTest.class, PropertiesReaderTest.class })

public class AllTests {

}
