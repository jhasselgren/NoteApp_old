package se.jhasselgren.noteapp.core;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("TEXT")
public class TextThing extends Thing{
	
	public TextThing() {
		super(ThingType.TEXT);
	}

	@Override
	public void update(Thing thing) {
		
		if(thing instanceof TextThing){
			TextThing inputThing = (TextThing) thing;
			this.setName(inputThing.getName());
			this.setDescription(inputThing.getDescription());
		}
		else{
			throw new IllegalArgumentException("Input is not a instance of TextThing");
		}
	}
}
