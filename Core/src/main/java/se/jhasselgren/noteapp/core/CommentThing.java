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
