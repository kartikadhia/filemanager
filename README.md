# filemanager
## project for getting details about files on a server
### Requirement:
An application that helps manage .txt files on machine, by sending the details about files (size, word count, number)
as a web service response.
It also sends the words that occur more than certain number of times and also their counts.
<<<<<<< 5fcfa7711abd59bdee4d1b20d2cd23c0cdb2564e
The files need to be categorized into long and short, depending on the number of words. Also words that are
repeated certain number of times need to be sent by the web service
=======
The files need to be categorized into long and short.
>>>>>>> a1b066471b3eab5f278c766b6da11f4f404919bc
The information should be accessible over the web, over a web service, and should be presented in the form of JSON, 
so that applications written on any of the technologies, are be able to access this information.
The application should be able to handle multiple request at the same time.

### Design:
1. Since the output is supposed to be JSON, the obvious choice of the WebService is RESTful.
	I've used Jersey Web Service API, to design the WebService, and Jersey Jackson support to help conversion of
	the data objects into Json.
2. The application can be fundamentally divided into three parts,
..1. The WebService : That caters to requests from clients
..2. The Service layer : That connects the WebService with the back-end.
..3. The Back-end : Which does the processing of folders and files
3. The Back-End can be further divided into sub groups:
.. 1. The Data Classes, which represent the data that will be sent over the web service.
.. 2. The calculators that do the job of processing files/folders in the file system
.. 3. Utility classes that support the calculators and connect with the services
.. 4. Exceptions which represent the exceptions that could be thrown while processing
4. In order to provide flexibility of configuration, the information about the file extension that needs to be 	checked, the threshold which decides if the file is long, the threshold which decides if the number is counted,
	the flag to check hidden files and the flag to consider numbers as words are set in a properties file, which are
	read with the help of a special class in the application
5. The entry point from the service to the Back-End happens through the class FolderManager. This class 	instantiates the PropertiesReader file. In order to speed up response times and to read the properties file only
	once, this class has been made as singleton.
6. The FolderManager class then calls the FileProcessor class that creates the folder structure and also calls 	FileProcessor, when it finds a file. It also does basic checks on the path variable and throws appropriate 	exception in case it is not correct
7. The FileProcessor class is responsible for processing  single file in order to find its word count and word 	repetition frequency. It employs multithreading in order to do it's job faster. The main thread extracts lines
	from the file and adds it to a BlockingArrayQueue. The threads generated, pick up these lines and process them.
	(Producer-Consumer design pattern).
8. Apache Logging is used to log the incoming request, service times and errors occurring in the application.	
	
### Features:
1. The application is flexible in terms of configuration, and the thresholds can be changed in the configuration file.
2. Also, Instead, if the user/developer wishes to send these attributes, he can do so by adding them to the query 	parameter instead.
	If the user/developer decides to do so, the parameters that are sent are overridden by the default ones.
	In case no parameters are sent, the default ones are used.
3. If the process running the server is not able to access a particular path, the error message is sent via an 	additional parameter in the Web service output. This has been used in the client program to 
	display appropriate messages. Also the search does not stop even if one unreachable folder is encountered!
4. Quick response times, with better utilization of the CPU processing power in case of big files.
5. If, instead of a folder path, the path of a file is sent to the Web Service, it will return a folder object
	consisting the details of this file.
	
### Sample Output:
The Web Service is invoked for the test folder(it is kept at the path \test folder). in the .output file, you can find the response.
The whole folder is processed in 1.2 seconds, (on a quad core i7 processor, with an SSD drive)
