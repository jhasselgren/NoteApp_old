package se.jhasselgren.noteapp.core;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by jhas on 2014-09-13.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type",
		visible = true
		
		)
@JsonSubTypes(
		{
			@Type(value = ToDoThing.class),
			@Type(value = TextThing.class),
			@Type(value = FileThing.class),
			@Type(value = LinkThing.class),
			@Type(value = CommentThing.class)
		}
		)
public abstract class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Lob
    private  String description;

    final private ThingType type; 
    
    private final long created = DateTime.now().getMillis();
    
    protected Thing(ThingType type){
    	this.type = type;	
    }
    
    @ManyToOne
    @JoinColumn(name="parentId", referencedColumnName="id", nullable = true)
    private Thing parent;

    public abstract void update(Thing thing) throws IllegalArgumentException;
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ThingType getType() {
		return type;
	}
    
    @JsonIgnore
	public Thing getParent() {
		return parent;
	}

    @JsonIgnore
	public void setParent(Thing parent) {
		this.parent = parent;
	}

	public long getCreated() {
		return created;
	}
	
    
}
