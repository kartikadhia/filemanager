package org.app.demo.filemanager.utility;


import org.apache.log4j.Logger;
import org.app.demo.filemanager.calculator.FileProcessor;
import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
import org.app.demo.filemanager.exception.InvalidParameterException;


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
	
	private static String fileExtension;
	private static int thresholdForLongFile;
	private static int thresholdForWordRepetition;
	private static boolean checkHidden;
	private static boolean countNumbers;
	
	@SuppressWarnings("unused")
	private static PropertiesReader propertiesReader = new PropertiesReader();
	private FileProcessor fileProcessor;
	private static volatile FolderManager folderManager;
	
	private FolderManager () {
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
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		FolderManager.fileExtension = fileExtension;
	}

	public int getThresholdForLongFile() {
		return thresholdForLongFile;
	}

	public void setThresholdForLongFile(int thresholdForLongFile) {
		FolderManager.thresholdForLongFile = thresholdForLongFile;
	}

	public int getThresholdForWordRepetition() {
		return thresholdForWordRepetition;
	}

	public void setThresholdForWordRepetition(int thresholdForWordRepetition) {
		FolderManager.thresholdForWordRepetition = thresholdForWordRepetition;
	}
	
	public boolean isCheckHidden() {
		return checkHidden;
	}

	public void setCheckHidden(boolean checkHidden) {
		FolderManager.checkHidden = checkHidden;
	}
	
	/**
	 * sets values of the properties in case there is failure to read the properties file
	 * Then creates a directory and returns it 
	 * In case there is no file in the whole folder and it's subfolder, it sends 
	 * an empty directory with it's name
	 * @param path
	 * @return
	 * @throws InvalidOrEmptyPathException
	 * @throws InvalidParameterException 
	 */

	public Directory processFilesForPath(String path,  String fileExtensionFromParam, String checkHiddenFromParam, String thresholdForLongFileFromParam, 
												String thresholdForWordRepetitionFromParam, String countNumbersFromParam) 
														throws InvalidOrEmptyPathException, InvalidParameterException {
		//If the params have not been loaded from the properties file,
		//set default values
		if(fileExtension == null) {
			logger.info("Could not read the properties file, using default values");
			fileExtension = ".txt";
			thresholdForLongFile = 1000;
			thresholdForWordRepetition = 50;
			checkHidden = true;
			countNumbers = true;
		}
		// if any of the param is passed to the webservice, set it as the passed value, else
		//use the default value
		if(fileExtensionFromParam != null) {
			fileExtension = fileExtensionFromParam;
		}
		if(checkHiddenFromParam != null) {
			checkHidden = Boolean.valueOf(checkHiddenFromParam);
		}
		if(countNumbersFromParam != null) {
			countNumbers = Boolean.valueOf(countNumbersFromParam);
		}
		// if the input value for the threshold is not a number, throw an exception
		try {
			if(thresholdForLongFileFromParam != null) {
				thresholdForLongFile = Integer.valueOf(thresholdForLongFileFromParam);
			}
			if(thresholdForWordRepetitionFromParam != null) {
				thresholdForWordRepetition = Integer.valueOf(thresholdForWordRepetitionFromParam);
			}
		}
		catch (NumberFormatException e) {
			throw new InvalidParameterException("The input parameter for threshold is not a number.");
		}
		
		Directory directory =  fileProcessor.processFilesForPath(path,fileExtension
					,thresholdForLongFile,thresholdForWordRepetition,checkHidden,countNumbers);
		
		// if directory does not contain any files or relevant folders, return empty directory
		if(directory == null) {
			directory = fileProcessor.getEmptyDirectory(path);
		}
			
		return directory;
	}

	public boolean isCountNumbers() {
		return countNumbers;
	}

	public void setCountNumbers(boolean countNumbers) {
		FolderManager.countNumbers = countNumbers;
	}




}
