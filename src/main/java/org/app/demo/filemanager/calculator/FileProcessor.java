package org.app.demo.filemanager.calculator;

import java.io.File;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.data.DirectoryHelper;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
import org.app.demo.filemanager.webservice.FileManagerWebService;

/**
 * @author Kartik
 * This class  validates if the path is proper and then calls approriate method to generate the 
 * file structure and then process the file.
 * It also throws the InvalidOrEmptyPathException, in case the path is not a valid directory in the file system.
 * 
 *
 */

public class FileProcessor {
	final static Logger logger = Logger.getLogger(FileProcessor.class);

	public Directory processFilesForPath(String path, String fileExtention, int thresholdForLongFile,
			int thresholdForWordRepetition, boolean checkHidden) throws InvalidOrEmptyPathException {
		// check if the original path is a valid directory, if not throw an exception
		
		if(path == "") {
			logger.info("Input path is not a valid directory");
			throw new InvalidOrEmptyPathException("Input path is an empty String");
		}
		
		if((path.charAt(path.length()-1) == '/' || path.charAt(path.length()-1) == '\\')) {
			path = path.substring(0, path.length()-1);
		}
		
		if(!((new File(path)).isDirectory())) {
			logger.info("Input path is not a valid directory");
			throw new InvalidOrEmptyPathException("Input path is not a valid directory");
		}
		
		//get the name of root directory
		String name = path.substring(path.lastIndexOf('/')+1, path.length());
		CustomFileThread customFileThread = new CustomFileThread(thresholdForLongFile,thresholdForWordRepetition);
		
		DirectoryHelper parentDirectoryHelper = new DirectoryHelper(name,0,fileExtention,checkHidden);
		Directory parent = parentDirectoryHelper.processAllChildren(path);

		return parent;
	}
	/**
	 * returns an empty directory, in case the path is a valid root folder but it does not contain any files
	 * or relevant sub folders.
	 * @param path
	 * @return
	 */
	public Directory getEmptyDirectory(String path) {
		Directory emptyDirectory = new Directory();
		
		if((path.charAt(path.length()-1) == '/' || path.charAt(path.length()-1) == '\\')) {
			path = path.substring(0, path.length()-1);
		}
		
		String name = path.substring(path.lastIndexOf('/')+1, path.length());
		
		emptyDirectory.setName(name);
		return emptyDirectory;
	}
}
