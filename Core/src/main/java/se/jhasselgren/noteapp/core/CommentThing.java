package se.jhasselgren.noteapp.core;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("COMMENT")
public class CommentThing extends Thing {

	private String sentBy;
	private String comment;
	
	public CommentThing() {
		super(ThingType.COMMENT);
	}
	
	@Override
	public void update(Thing thing) {
		
		if(thing instanceof CommentThing){
			CommentThing inputThing = (CommentThing) thing;
			this.setName(inputThing.getName());
			this.setDescription(inputThing.getDescription());
		}
		else{
			throw new IllegalArgumentException("Input is not a instance of CommentThing");
		}
	}

	public String getSentBy() {
		return sentBy;
	}

	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
