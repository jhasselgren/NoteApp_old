package se.jhasselgren.noteapp.core;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("TEXT")
public class TextThing extends Thing{
	
	public TextThing() {
		super(ThingType.TEXT);
	}
}
