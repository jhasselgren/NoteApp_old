package se.jhasselgren.noteapp.core;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jhas on 2014-09-13.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private  String description;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="parent_id")
    private Thing parent;

    @OneToMany( mappedBy = "parent")
    private Set<Thing> things = new HashSet<Thing>();

    public Thing(String name, String description, Set<Thing> things) {
        this();
        this.name = name;
        this.description = description;
        this.things = things;
    }

    public Thing() {
    }

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

    public Set<Thing> getThings() {
        return things;
    }

    public void setThings(Set<Thing> things) {
        this.things = things;
    }
}
