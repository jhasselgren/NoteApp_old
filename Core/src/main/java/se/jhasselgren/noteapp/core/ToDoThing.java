package se.jhasselgren.noteapp.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("TODO")
public class ToDoThing extends Thing {
	
	private int status = 0;
	
	@OneToOne()
	private Thing result;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<Thing> things = new HashSet<Thing>();
	
	public ToDoThing() {
		super(ThingType.TODO);	
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
}
