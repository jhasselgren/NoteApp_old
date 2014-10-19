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

	@Override
	public void update(Thing thing) {
		
		if(thing instanceof LinkThing){
			LinkThing inputThing = (LinkThing) thing;
			this.setName(inputThing.getName());
			this.setDescription(inputThing.getDescription());
            this.setLink(inputThing.getLink());
		}
		else{
			throw new IllegalArgumentException("Input is not a instance of LinkThing");
		}
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}




}
