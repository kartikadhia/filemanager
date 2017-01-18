package org.app.demo.filemanager.data;

import java.io.BufferedReader;

/**
 * This class is the bare bones structure for the file information that is sent on the web service.
 * 
 */
import java.io.File;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name","size","totalWords","wordCount","errorString"})
public class CustomFile implements Serializable{
	@JsonIgnore
	private static final long serialVersionUID = 4625897134647623553L;
	@JsonIgnore
	private File file;
	@JsonIgnore
	private Directory parent;
	
	private long size ;
	private ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<String, Integer>();
	private long totalWords;
	private String errorString= "";
	private String name;
	
	public CustomFile () {}
	
	public CustomFile(File file,String name,Directory parent) {
		this.setSize(file.length());
		this.file = file;
		this.setName(name);
		this.setParent(parent);
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public ConcurrentHashMap<String, Integer> getWordCount() {
		return wordCount;
	}

	public void setWordCount(ConcurrentHashMap<String, Integer> wordCount) {
		this.wordCount = wordCount;
	}
	
	public String getErrorString() {
		return errorString;
	}
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}
	
	public long getTotalWords() {
		return totalWords;
	}
	
	public void setTotalWords(long totalWords) {
		this.totalWords = totalWords;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Directory getParent() {
		return parent;
	}
	
	public void setParent(Directory parent) {
		this.parent = parent;
	}


}
