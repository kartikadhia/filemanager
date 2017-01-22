package org.app.demo.filemanager.calculator;

import java.io.File;
import org.apache.log4j.Logger;
import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.data.DirectoryHelper;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;

/**
 * 
 * @author Kartik
 * This class  validates if the path is proper and then calls appropriate method to generate the 
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
			int thresholdForWordRepetition, boolean checkHidden,boolean countNumbers) throws InvalidOrEmptyPathException {
		// check if the original path is a valid directory, if not throw an exception
		File file;
		String name;
		if(path == "") {
			logger.info("Input path is not a valid directory");
			throw new InvalidOrEmptyPathException("Input path is an empty String");
		}
		//remove /  or \\ from the end of the path
		if((path.charAt(path.length()-1) == '/' || path.charAt(path.length()-1) == '\\')) {
			path = path.substring(0, path.length()-1);
		}
		path = path.replace('\\', '/');
		// check if input path is a valid directory, if not throw an exception
		file = new File(path);
		if(!((file).isDirectory())) {
			// if the path is a valid file, remove filename from the path details about this file and send the root folder:
			if(file.isFile())  {
				System.out.println("file detected");
				 String fileName = path.substring(path.lastIndexOf('/')+1, path.length());
				 System.out.println("file name = " + fileName);
				 if(fileName.endsWith(fileExtention)) {
					 System.out.println("truncating path " + path);
					 path = path.substring(0,path.lastIndexOf('/'));
					 System.out.println("truncated path " + path);
					 return processFilesForPath(path,fileExtention,thresholdForLongFile
							 					,thresholdForWordRepetition,checkHidden,countNumbers);
				 }
				 else
				 throw new InvalidOrEmptyPathException("Input path is not file that matches the extension to be checked");	 
			}
			logger.info("Input path is not a valid directory");
			throw new InvalidOrEmptyPathException("Input path is not a valid directory");
		}
		
		
		//get the name of root directory
		name = path.substring(path.lastIndexOf('/')+1, path.length());
		//call to the constructor, to set the static values of the class
		@SuppressWarnings("unused")
		CustomFileThread customFileThread = new CustomFileThread(thresholdForLongFile,thresholdForWordRepetition,countNumbers);
		
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
