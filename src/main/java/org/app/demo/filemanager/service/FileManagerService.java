package org.app.demo.filemanager.service;

import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
import org.app.demo.filemanager.exception.InvalidParameterException;
import org.app.demo.filemanager.utility.FolderManager;


/**
 *  @author Kartik
 *  This class acts as the service layer between the Web Service and data/utility layer
 */

public class FileManagerService {
	
	FolderManager folderManager = FolderManager.getFolderManager();
	
	/**
	 * Service method to get the details of the folder at a certain path
	 * @param path of the folder that needs to be searched
	 * @param countNumbers 
	 * @param thresholdForWordRepetition 
	 * @param thresholdForLongFile 
	 * @param checkHidden 
	 * @param fileExtension 
	 * @return the directory object, that needs to be converted and sent over to the client
	 * @throws InvalidOrEmptyPathException in case the input path is not a valid directory
	 * @throws InvalidParameterException in case the input parameter for threshold is not a number
	 */
	public Directory getFilesAtPath(String path, String fileExtension, String checkHidden, String thresholdForLongFile, 
										String thresholdForWordRepetition, String countNumbers ) throws InvalidOrEmptyPathException, InvalidParameterException {
		return folderManager.processFilesForPath(path,fileExtension,checkHidden,thresholdForLongFile,thresholdForWordRepetition,countNumbers);
	}

}
