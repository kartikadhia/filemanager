package org.app.demo.filemanager.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.app.demo.filemanager.calculator.FileProcessor;



/**
 * 
 * @author Kartik
 * This class gets the properties mentioned in the properties file and sets it for the object of 
 * Folder Manager Class
 */
public class PropertiesReader {
	
	final static Logger logger = Logger.getLogger(FileProcessor.class);
	
	private Properties properties = new Properties();
	public PropertiesReader () {}
	
	public PropertiesReader(FolderManager folderManager) {
		try(InputStream input = this.getClass().getClassLoader().getResourceAsStream("config.properties");)	{
			properties.load(input);
			folderManager.setFileExtention(properties.getProperty("file_extention",".txt"));
			folderManager.setThresholdForLongFile(
					Integer.valueOf(properties.getProperty("threshold_for_long_file","1000")));
			folderManager.setThresholdForWordRepetition(
					Integer.valueOf(properties.getProperty("threshold_for_word_repetition","50")));
			folderManager.setCheckHidden(Boolean.valueOf(properties.getProperty("check_hidden","true")));
			folderManager.setCountNumbers(Boolean.valueOf(properties.getProperty("count_numbers","true")));
			
			logger.debug(("read properties file,  file_extention = " +
										properties.getProperty("file_extention")));
			
		} catch ( IOException e) {
			logger.equals(e.getStackTrace());
			e.printStackTrace();
		}
	}
	
}
