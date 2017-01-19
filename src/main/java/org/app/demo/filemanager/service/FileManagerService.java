package org.app.demo.filemanager.service;

import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
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
	 * @return the directory object, that needs to be converted and sent over to the client
	 * @throws InvalidOrEmptyPathException in case the 
	 */
	public Directory getFilesAtPath(String path ) throws InvalidOrEmptyPathException {
		return folderManager.processFilesForPath(path);
	}

}
