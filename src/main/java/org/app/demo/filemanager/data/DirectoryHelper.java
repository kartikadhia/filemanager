package org.app.demo.filemanager.data;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class DirectoryHelper {
	private Directory directory = new Directory();
	private static String fileExtention;
	private static boolean checkHidden;

	
	public DirectoryHelper(String name, int depth,String fileExtention,boolean checkHidden) {
		directory.setName(name);
		directory.setDepth(depth);
		DirectoryHelper.fileExtention = fileExtention;
		DirectoryHelper.checkHidden = checkHidden;
	}
	public DirectoryHelper(String name, int depth) {
		directory.setName(name);
		directory.setDepth(depth);
	}
	
	public Directory getDirectory() {
		return directory;
	}
	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	
	public Directory processAllChildren(String path) {
		File thisFile = new File(path);
		String [] children = thisFile.list();
		for(String child : children) {
				try{
					String subPath = path + "/" + child;
					File childFile = new File(subPath);
					if(childFile.isDirectory() && (checkHidden || !childFile.isHidden())) {
						DirectoryHelper subDirectoryHelper = new DirectoryHelper(child,directory.getDepth()+1);
						subDirectoryHelper.processAllChildren(subPath);
						
						directory.getSubDirectoryList().add(subDirectoryHelper.getDirectory());
						
					}
					else if(childFile.isFile() && child.endsWith(fileExtention) &&(checkHidden || !childFile.isHidden())) {
						CustomFile customFile = new CustomFile(childFile,child,directory);
						CustomFileThread customFileThread = new CustomFileThread(customFile,
																directory.getLongFilesList(),directory.getShortFilesList());
						customFileThread.processFile();
						directory.setRelevant(true);
						directory.setFileCount((directory.getFileCount()+1));
					}
				}
				catch(Exception e) {
					directory.setErrorString("there was an error accessing this directory. the user running this"
					+ " process may not have the permissions to access this directory");
				}
			}
		for(Directory subDirectory : directory.getSubDirectoryList()) {
			directory.setFileCount(directory.getFileCount() + subDirectory.getFileCount());
			if(subDirectory.isRelevant()) {
				directory.setRelevant(true);
			}
		}
		if(!directory.isRelevant()) {
			directory = null;
			}	
		return directory;
		
	}
	
	
	

}
