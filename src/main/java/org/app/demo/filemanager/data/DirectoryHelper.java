package org.app.demo.filemanager.data;

import java.io.File;
import org.app.demo.filemanager.calculator.CustomFileThread;

/**
 *  @author Kartik
 *  This class helps create directories and files by using recursion
 *  It also loads the count and total number of files present in the directory.
 */
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
						
						// if subdirectory is relevant (not null) add it to the subdirectory list
						if(subDirectoryHelper.getDirectory() != null) {
						directory.getSubDirectoryList().add(subDirectoryHelper.getDirectory());
						if(subDirectoryHelper.getDirectory().getDepth()>0)
						subDirectoryHelper.getDirectory().setParentDirectory(directory);
						}
					}
					else if(childFile.isFile() && child.endsWith(fileExtention) &&(checkHidden || !childFile.isHidden())) {
						CustomFile customFile = new CustomFile(childFile,child,directory);
						CustomFileThread customFileThread = new CustomFileThread(customFile,
																directory.getLongFilesList(),directory.getShortFilesList());
						customFileThread.processFile();
						// if the directory has files, set it as relevant (to be sent)
						directory.setRelevant(true);
						//increase the file count
						directory.setFileCount((directory.getFileCount()+1));
						// increase the word count
						directory.setTotalWords(directory.getTotalWords() + customFile.getTotalWords());
					}
				}
				catch(Exception e) {
					directory.setErrorString("there was an error accessing this directory. the user running this"
					+ " process may not have the permissions to access all the files and/or folders of this directory");
				}
			}
		/** 
		 * check of the subdirectories have a file, if yes, set it as relevant(so that it is sent) also count
		 * the number of files and the total number of words inside it.
		 */
		for(Directory subDirectory : directory.getSubDirectoryList()) {
			if(subDirectory == null) continue;
			directory.setFileCount(directory.getFileCount() + subDirectory.getFileCount());
			directory.setTotalWords(directory.getTotalWords()+subDirectory.getTotalWords());
			if(subDirectory.isRelevant()) {
				directory.setRelevant(true);
			}
		}
		
		//if the directory is not relevant, set it to null, so that it is not sent.
		if(!directory.isRelevant()) {
			directory = null;
			}	
		return directory;
		
	}

}
