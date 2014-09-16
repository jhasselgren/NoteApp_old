package se.jhasselgren.noteapp.core;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("FILE")
public class FileThing extends Thing {
	
	private String fileType; 
	
	public FileThing() {
		super(ThingType.FILE);
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
