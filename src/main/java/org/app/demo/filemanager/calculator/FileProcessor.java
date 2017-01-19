package org.app.demo.filemanager.calculator;

import java.io.File;
import org.apache.log4j.Logger;
import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.data.DirectoryHelper;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;

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
	/**
	 * 
	 * @param path to the folder
	 * @param fileExtention The file extension that needs to be checked
	 * @param thresholdForLongFile Decide when a file is classified as long
	 * @param thresholdForWordRepetition Decide the number of words that needs to be extracted
	 * @param checkHidden Activate/deactivate checking of hidden files
	 * @return The directory object containing details of it's sub directories and files
	 * @throws InvalidOrEmptyPathException when the path is empty or null
	 */
	
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
		//create a constructor, to set the static values of the class
		@SuppressWarnings("unused")
		CustomFileThread customFileThread = new CustomFileThread(thresholdForLongFile,thresholdForWordRepetition);
		
		DirectoryHelper parentDirectoryHelper = new DirectoryHelper(name,0,fileExtention,checkHidden);
		Directory parent = parentDirectoryHelper.processAllChildren(path);

		return parent;
	}
	/**
	 * returns an empty directory, in case the path is a valid root folder but it does not contain any files
	 * or relevant sub folders.
	 * @param path to the folder
	 * @return the empty directory containing its name
	 */
	public Directory getEmptyDirectory(String path) {
		Directory emptyDirectory = new Directory();
		
		if((path.charAt(path.length()-1) == '/' || path.charAt(path.length()-1) == '\\')) {
			path = path.substring(0, path.length()-1);
		}
		path = path.replace('\\', '/');
		String name = path.substring(path.lastIndexOf('/')+1, path.length());
		
		emptyDirectory.setName(name);
		return emptyDirectory;
	}
}
