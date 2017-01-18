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
 *  
 */

public class FolderManager {
	
	final static Logger logger = Logger.getLogger(FolderManager.class);
	
	private String fileExtention;
	private int thresholdForLongFile;
	private int thresholdForWordRepetition;
	private boolean checkHidden;
	
	private PropertiesReader propertiesReader;
	private FileProcessor fileProcessor;
	
	
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
	public FolderManager () {
		propertiesReader = new PropertiesReader(this);
		fileProcessor = new FileProcessor();
	}
	
	public Directory processFilesForPath(String path) throws InvalidOrEmptyPathException {
		// if the read of the config file fails, use default values (for resilience)
		if(fileExtention == null) {
			logger.info("Could not read the properties file, using default values");
			fileExtention = ".txt";
			thresholdForLongFile = 1000;
			thresholdForWordRepetition = 50;
			checkHidden = true;
		}
		return fileProcessor.processFilesForPath(path,fileExtention
					,thresholdForLongFile,thresholdForWordRepetition,checkHidden);
		
	}




}
