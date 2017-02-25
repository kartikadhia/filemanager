package org.app.demo.filemanager.calculator;

import java.io.File;


import java.util.ArrayList;
import java.util.List;
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
 */

public class FileProcessor {
	final static Logger logger = Logger.getLogger(FileProcessor.class);
	private List<CustomFileLambdaProcessor> masterFileList = new ArrayList <CustomFileLambdaProcessor> ();
	
	/**
	 * This method  validates if the path is proper and then calls appropriate method to generate the 
	 * file structure and then process the file.
	 * It uses 
	 * @param path to the folder
	 * @param fileExtention The file extension that needs to be checked
	 * @param thresholdForLongFile Decide when a file is classified as long
	 * @param thresholdForWordRepetition Decide the number of words that needs to be extracted
	 * @param checkHidden Activate/deactivate checking of hidden files
	 * @return The directory object containing details of it's sub directories and files
	 * @throws InvalidOrEmptyPathException when the path is empty or null
	 */
	
	public Directory processFilesForPath(String path, String fileExtention, boolean checkHidden,boolean countNumbers,
									int thresholdForLongFile,int thresholdForWordRepetition) throws InvalidOrEmptyPathException {
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

				 String fileName = path.substring(path.lastIndexOf('/')+1, path.length());

				 if(fileName.endsWith(fileExtention)) {
					 path = path.substring(0,path.lastIndexOf('/'));
		
					 return processFilesForPath(path,fileExtention,checkHidden, countNumbers,
								 thresholdForLongFile, thresholdForWordRepetition);
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
		
		DirectoryHelper parentDirectoryHelper = new DirectoryHelper();
		parentDirectoryHelper.getDirectory().setName(name);
		parentDirectoryHelper.getDirectory().setDepth(0);
		parentDirectoryHelper.fileExtention = fileExtention;
		parentDirectoryHelper.checkHidden = checkHidden;
		Directory parent = parentDirectoryHelper.processAllChildren(path,masterFileList);
		

		LambdaFileProcessor lambdaFileProcessor = new LambdaFileProcessor();
		CustomFileLambdaProcessor.thresholdForLongFile = thresholdForLongFile;
		CustomFileLambdaProcessor.thresholdForWordRepetition = thresholdForWordRepetition;
		CustomFileLambdaProcessor.countNumbers = countNumbers;
		masterFileList.parallelStream().forEach((customFileLambdaProcessor) -> {
				try	{
					 lambdaFileProcessor.processFile(customFileLambdaProcessor);
				}
				catch (Exception e) {
					customFileLambdaProcessor.getCustomFile().setErrorString("Exception occured while  parallely processing"
							+ " this file");
					logger.error("Exception while parallely processing files in class FileProcessor" + e.getStackTrace());
				}
		});
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
