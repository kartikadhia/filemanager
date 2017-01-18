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
	
	public Directory getFilesAtPath(String path ) throws InvalidOrEmptyPathException {
		return folderManager.processFilesForPath(path);
	}

}
