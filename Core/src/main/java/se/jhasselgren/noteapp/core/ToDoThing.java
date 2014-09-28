package se.jhasselgren.noteapp.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("TODO")
public class ToDoThing extends Thing {
	
	private int status = 0;
	
	@OneToOne()
	private Thing result;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @OrderBy("id ASC")
    private Set<Thing> things = new HashSet<Thing>();
	
	public ToDoThing() {
		super(ThingType.TODO);	
	}
	
	@Override
	public void update(Thing thing) {
		
		if(thing instanceof ToDoThing){
			ToDoThing inputThing = (ToDoThing) thing;
			this.setName(inputThing.getName());
			this.setDescription(inputThing.getDescription());
            this.setStatus((inputThing.getStatus()));
		}
		else{
			throw new IllegalArgumentException("Input is not a instance of ToDoThing");
		}
	}
	
	public boolean isCompleted() {
		return result != null;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Thing getResult() {
		return result;
	}

	public void setResult(Thing result) {
		if(result != null){
			this.status = 100;
		}
		else{
			if(this.status == 100){
				this.status = 90;
			}
		}
		this.result = result;
	}

	public Set<Thing> getThings() {
        return things;
    }

    public void setThings(Set<Thing> things) {
        this.things = things;
    }
    
    public void addChild(Thing thing){
    	this.things.add(thing);
    	thing.setParent(this);
    }
    
    public void removeChild(Thing thing){
    	this.things.remove(thing);
    }
}
