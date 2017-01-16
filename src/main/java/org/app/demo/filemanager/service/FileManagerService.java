package org.app.demo.filemanager.service;

import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.utility.FolderManager;


/**
 *  @author Kartik
 *  This class acts as the service layer between the Web Service and data/utility layer
 */

public class FileManagerService {
	
	FolderManager folderManager = new FolderManager();
	
	public Directory getFilesAtPath(String path ) {
		
		return folderManager.processFilesForPath(path);
	}
	

}
