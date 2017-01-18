package org.app.demo.filemanager.data;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Kartik
 * This is our data structure that represents a folder/directory in the file system.
 * it may have other folder/directory, and other files. We will only load the files that are
 * relevant to our case.
 * Each folder will have a parent folder (except for the root folder)
 */
@JsonPropertyOrder({"name", "depth","longFilesList","shortFilesList","subDirectoryList","errorString"})
public class Directory implements Serializable {
	@JsonIgnore
	private static final long serialVersionUID = -1569540135638641823L;
	//FIXME implement HATEOAS for parentDirectory
	@JsonIgnore
	private Directory parentDirectory;
	@JsonIgnore
	private boolean isRelevant = false;
	private List<Directory> subDirectoryList = new ArrayList<Directory>();
	private List<CustomFile> longFilesList = new ArrayList<CustomFile>();
	private List<CustomFile> shortFilesList = new ArrayList<CustomFile>();
	private int depth;
	private int fileCount = 0;
	private String errorString = "";
	private String name;
	private long totalWords;

	public Directory () {
	}
	
	public List<Directory> getSubDirectoryList() {
		return subDirectoryList;
	}
	public void setSubDirectoryList(List<Directory> subDirectoryList) {
		this.subDirectoryList = subDirectoryList;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public String getErrorString() {
		return errorString;
	}
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}
	@JsonIgnore
	public Directory getParentDirectory() {
		return parentDirectory;
	}
	@JsonIgnore
	public void setParentDirectory(Directory parentDirectory) {
		this.parentDirectory = parentDirectory;
	}
	
	public List<CustomFile> getLongFilesList() {
		return longFilesList;
	}

	public void setLongFilesList(List<CustomFile> longFilesList) {
		this.longFilesList = longFilesList;
	}

	public List<CustomFile> getShortFilesList() {
		return shortFilesList;
	}

	public void setShortFilesList(List<CustomFile> shortFilesList) {
		this.shortFilesList = shortFilesList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore()
	public boolean isRelevant() {
		return isRelevant;
	}
	@JsonIgnore
	public void setRelevant(boolean isRelevant) {
		this.isRelevant = isRelevant;
	}

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	public long getTotalWords() {
		return totalWords;
	}

	public void setTotalWords(long totalWords) {
		this.totalWords = totalWords;
	}
	

}
