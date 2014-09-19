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
	
	@Override
	public void update(Thing thing) {
		
		if(thing instanceof FileThing){
			FileThing inputThing = (FileThing) thing;
			this.setName(inputThing.getName());
			this.setDescription(inputThing.getDescription());
		}
		else{
			throw new IllegalArgumentException("Input is not a instance of FileThing");
		}
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
