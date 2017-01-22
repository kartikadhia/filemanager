package org.app.demo.filemanager.webservice;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
import org.app.demo.filemanager.exception.InvalidParameterException;
import org.app.demo.filemanager.service.FileManagerService;
import org.apache.log4j.Logger;

/**
 * @author Kartik
 * This is the main web service that is exposed to the client
 * It has method(s) that will help service the requests to our URL
 */

@Path("/files")
public class FileManagerWebService {
	
	final static Logger logger = Logger.getLogger(FileManagerWebService.class);
	private FileManagerService fileManagerService = new FileManagerService();
	
	/**
	 * Service all requests for the mapping /files/{path}
	 * Get and return the details of the path
	 * @param path to the folder
	 * @param fileExtension : the file extension that needs to be checked
	 * @param thresholdForLongFile : count of words, when the file should be classified as long
	 * @param checkHidden : flag which specifies if hidden files need to be checked
	 * @param thresholdForWordRepetition specify when a word should be categorized as repeated
	 * @param countNumbers count numbers as words or not
	 * @return Folder structure and file details for the given path
	 */
	
	@GET
	@Path("/{path:.*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFilesAtPath(@PathParam("path") String path,
										@QueryParam("fileExtension") String fileExtension,
										@QueryParam("checkHidden") String checkHidden,
										@QueryParam("thresholdForLongFile") String thresholdForLongFile,
										@QueryParam("thresholdForWordRepetition") String thresholdForWordRepetition,
										@QueryParam("countNumbers") String countNumbers) {
		try{
			long startTime = System.currentTimeMillis();
			
			
			logger.info("Received request for path " + path);
			//FIXME
			System.out.println("fileExtension = " + fileExtension);
			System.out.println("checkHidden = " + checkHidden);
			System.out.println("thresholdForLongFile = " + thresholdForLongFile);
			System.out.println("thresholdForWordRepetition = " + thresholdForWordRepetition);
			System.out.println("countNumbers = " + countNumbers);
			Directory directory =   fileManagerService.getFilesAtPath(path,fileExtension,checkHidden,
															thresholdForLongFile,thresholdForWordRepetition,countNumbers);
			long stopTime = System.currentTimeMillis();
		    long elapsedTime = stopTime - startTime;
		    logger.info("Request served in "+ elapsedTime + "ms");
		    return Response.status(Status.OK).entity(directory).build();
			}
		catch(InvalidOrEmptyPathException e) {
			logger.info("Invalid path!");
			return Response.status(Status.NOT_FOUND).build();
		}
		catch(InvalidParameterException e) {
			logger.info("Invalid path!");
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	
}
