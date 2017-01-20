package org.app.demo.filemanager.utility;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.app.demo.filemanager.calculator.FileProcessor;
import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
import org.app.demo.filemanager.webservice.FileManagerWebService;

/**
 *  @author Kartik
 *  This Class manages the calls to other classes with the correct params.
 *  It obtains the properties from the properties file class, that allows it to tune the
 *  params with which the program is running. finally it sends the required data to the service 
 *  The class has been designed to be singleton. this keeps the object of this class in memory, thus helping
 *  to reduce turn around times.
 *  
 */

public class FolderManager {
	
	final static Logger logger = Logger.getLogger(FolderManager.class);
	
	private static String fileExtention;
	private static int thresholdForLongFile;
	private static int thresholdForWordRepetition;
	private static boolean checkHidden;
	
	private  PropertiesReader propertiesReader;
	private FileProcessor fileProcessor;
	private static volatile FolderManager folderManager;
	
	private FolderManager () {
		propertiesReader = new PropertiesReader(this);
		fileProcessor = new FileProcessor();
	}
	
	public static FolderManager getFolderManager() {
		if(folderManager == null)
			synchronized (FolderManager.class) {
				if(folderManager == null) {
					folderManager= new FolderManager();
				}				
			}
		return folderManager; 
	}
	public String getFileExtention() {
		return fileExtention;
	}

	public void setFileExtention(String fileExtention) {
		this.fileExtention = fileExtention;
	}

	public int getThresholdForLongFile() {
		return thresholdForLongFile;
	}

	public void setThresholdForLongFile(int thresholdForLongFile) {
		this.thresholdForLongFile = thresholdForLongFile;
	}

	public int getThresholdForWordRepetition() {
		return thresholdForWordRepetition;
	}

	public void setThresholdForWordRepetition(int thresholdForWordRepetition) {
		this.thresholdForWordRepetition = thresholdForWordRepetition;
	}
	
	public boolean isCheckHidden() {
		return checkHidden;
	}

	public void setCheckHidden(boolean checkHidden) {
		this.checkHidden = checkHidden;
	}
	
	/**
	 * sets values of the properties in case there is failure to read the properties file
	 * Then creates a directory and returns it 
	 * In case there is no file in the whole folder and it's subfolder, it sends 
	 * an empty directory with it's name
	 * @param path
	 * @return
	 * @throws InvalidOrEmptyPathException
	 */

	public Directory processFilesForPath(String path) throws InvalidOrEmptyPathException {
		
		if(fileExtention == null) {
			logger.info("Could not read the properties file, using default values");
			fileExtention = ".txt";
			thresholdForLongFile = 1000;
			thresholdForWordRepetition = 50;
			checkHidden = true;
		}
		Directory directory =  fileProcessor.processFilesForPath(path,fileExtention
					,thresholdForLongFile,thresholdForWordRepetition,checkHidden);
		// if directory does not contain any files or relevant folders, return empty directory
		if(directory == null) {
			directory = fileProcessor.getEmptyDirectory(path);
		}
			
		return directory;
	}




}
