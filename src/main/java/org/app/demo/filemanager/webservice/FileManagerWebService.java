package org.app.demo.filemanager.webservice;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import org.app.demo.filemanager.data.Directory;
import org.app.demo.filemanager.exception.InvalidOrEmptyPathException;
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
	
	@GET
	@Path("/{path:.*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFilesAtPath(@PathParam("path") String path) {
		try{
			long startTime = System.currentTimeMillis();
			logger.info("Received request for path " + path);
			//Get the information of the folder and the files
			Directory directory =   fileManagerService.getFilesAtPath(path);
			long stopTime = System.currentTimeMillis();
		    long elapsedTime = stopTime - startTime;
		    logger.info("Request served in "+ elapsedTime + "ms");
		    return Response.status(Status.OK).entity(directory).build();
			}
		catch(InvalidOrEmptyPathException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
