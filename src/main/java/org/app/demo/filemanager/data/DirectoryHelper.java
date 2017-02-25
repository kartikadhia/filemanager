package org.app.demo.filemanager.data;

import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;
import org.app.demo.filemanager.calculator.CustomFileLambdaProcessor;
import org.app.demo.filemanager.calculator.FileProcessor;



/**
 *  @author Kartik
 *  This class helps create directories and files by using recursion
 *  It also loads the count and total number of files present in the directory.
 *  
 */
public class DirectoryHelper {
	final static Logger logger = Logger.getLogger(FileProcessor.class);
	
	private Directory directory = new Directory();
	public String fileExtention;
	public boolean checkHidden;

	
	public DirectoryHelper() {
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

	/**
	 * creates objects of type directory that can be converted to JSON and sent over the web.
	 * checks the folder for sub folders and file recursively, to create a structured directory, that
	 * represents the folder structure.
	 * It excludes folders that do not have files having the correct extension
	 * 
	 * @param path to the folder
	 * @return Directory containing details of the folder
	 */
	public Directory processAllChildren(String path,List<CustomFileLambdaProcessor> masterFileList) {
		File thisFile = new File(path);
		String [] children = thisFile.list();
		for(String child : children) {
				try{
					String subPath = path + "/" + child;
					File childFile = new File(subPath);
					if(childFile.isDirectory() && (checkHidden || !childFile.isHidden())) {
						DirectoryHelper subDirectoryHelper = new DirectoryHelper(child,directory.getDepth()+1);
						subDirectoryHelper.processAllChildren(subPath,masterFileList);
						
						// if subdirectory is relevant (not null) add it to the subdirectory list
						if(subDirectoryHelper.getDirectory() != null) {
						directory.getSubDirectoryList().add(subDirectoryHelper.getDirectory());
						//set the parent directory of the current directory, only if it is not the root parent
						if(subDirectoryHelper.getDirectory().getDepth()>0)
						subDirectoryHelper.getDirectory().setParentDirectory(directory);
						}
					}
					else if(childFile.isFile() && child.endsWith(fileExtention) &&(checkHidden || !childFile.isHidden())) {
						CustomFile customFile = new CustomFile(childFile,child,directory);
						CustomFileLambdaProcessor customFileLambdaProcessor = 
								new CustomFileLambdaProcessor(customFile);
						
						// Instead of processing the file in the normal way, we will process it using Streams, for now, just add the file to a list. 
						//customFileThread.processFile();
						masterFileList.add(customFileLambdaProcessor);
						customFile.setParent(directory);
						// if the directory has files, set it as relevant (to be sent)
						directory.setRelevant(true);
						//increase the file count
						directory.setFileCount((directory.getFileCount()+1));
					}
				}
				catch(Exception e) {
					directory.setErrorString("there was an error accessing this directory. the user running this"
					+ " process may not have the permissions to access all the files and/or folders of this directory");
				logger.error(e);
				}
			}
		/* 
		 * check of the subdirectories have a file, if yes, set it as relevant(so that it is sent) also count
		 * the number of files and the total number of words inside it.
		 */
		for(Directory subDirectory : directory.getSubDirectoryList()) {
			if(subDirectory == null) continue;
			directory.setFileCount(directory.getFileCount() + subDirectory.getFileCount());
			
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
