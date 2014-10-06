package se.jhasselgren.noteapp.core;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.*;

@Entity
@JsonTypeName("FILE")
public class FileThing extends Thing {

	public FileThing() {
		super(ThingType.FILE);
	}

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public FileInformation file;

    @OneToMany(mappedBy = "fileThing", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("version desc")
    public Set<FileInformation> fileArchive = new HashSet<FileInformation>();

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

    @JsonProperty("hasFile")
    public boolean hasFile(){
        return file != null;
    }

    public void addFile(FileInformation newFile){
        int version = 1;

        if(this.file != null){
            version = this.file.getVersion() + 1;
        }

        newFile.setVersion(version);
        newFile.setFileThing(this);
        this.setFile(newFile);

        this.fileArchive.add(newFile);

    }

    public void addToArchive(FileInformation oldFile){
        this.fileArchive.add(oldFile);
    }

	public String getFileType() {
        if(file == null){
            return "";
        }

		return file.getFileType();
	}

    public FileInformation getFile() {
        return file;
    }

    public void setFile(FileInformation file) {
        this.file = file;
    }

    public Set<FileInformation> getFileArchive() {
        return fileArchive;
    }

    public void setFileArchive(Set<FileInformation> fileArchive) {
        this.fileArchive = fileArchive;
    }

}
