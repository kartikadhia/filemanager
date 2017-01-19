package org.app.demo.filemanager.test;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.webservice.FileManagerWebService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileManagerWebServiceTest {

	FileManagerWebService fileManagerWebService;
	String path;
	String invalidPath;	
	String emptyPath;
	Response response;
	@Before
	public void setUp() throws Exception {
		path = "C:\\Users\\Kartik\\Documents\\java\\testfolder\\standard test folder";
		invalidPath = "aaaa";
		fileManagerWebService = new FileManagerWebService();
	}

	@After
	public void tearDown() throws Exception {
	}
	/**
	 * test for an OK path
	 */
	@Test
	public void testWebServiceCall() {
		response = fileManagerWebService.getAllFilesAtPath(path);
		//check if response is of the appropriate class
		assertEquals(Directory.class, response.getEntity().getClass());
		assertEquals(200,response.getStatus());
	}
	/**
	 * test for an invalid path
	 */
	@Test
	public void testWebServiceCallForInvalidPath() {
		response = fileManagerWebService.getAllFilesAtPath(invalidPath);
		//check if response is of the appropriate class
		assertEquals(null, response.getEntity());
		assertEquals(404,response.getStatus());
	}
	/**
	 * test for an Empty path
	 */
	@Test
	public void testWebServiceCallForEmptyPath() {
		response = fileManagerWebService.getAllFilesAtPath("");
		//check if response is of the appropriate class
		assertEquals(null, response.getEntity());
		assertEquals(404,response.getStatus());
	}

}
