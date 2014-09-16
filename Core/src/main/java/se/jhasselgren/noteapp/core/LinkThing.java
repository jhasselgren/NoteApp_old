package se.jhasselgren.noteapp.core;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("LINK")
public class LinkThing extends Thing {

	private String link;
	
	public LinkThing() {
		super(ThingType.LINK);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	
}
