package se.jhasselgren.noteapp.resources;

import io.dropwizard.hibernate.UnitOfWork;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.jhasselgren.noteapp.core.CommentThing;
import se.jhasselgren.noteapp.core.FileThing;
import se.jhasselgren.noteapp.core.LinkThing;
import se.jhasselgren.noteapp.core.TextThing;
import se.jhasselgren.noteapp.core.Thing;
import se.jhasselgren.noteapp.core.ToDoThing;
import se.jhasselgren.noteapp.db.ThingDAO;

/**
 * Created by jhas on 2014-09-13.
 */
@Path("thing")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ThingResource {

    private final ThingDAO thingDAO;

    public ThingResource(ThingDAO thingDAO) {
        this.thingDAO = thingDAO;
    }

    @Path("create")
    @POST
    @UnitOfWork
    public Response addParent(String name, String description){
    	ToDoThing parentThing = new ToDoThing();
    	parentThing.setName(name);
    	parentThing.setDescription(description);
    	
    	thingDAO.create(parentThing);
    	
    	return Response.ok(parentThing).build();
    }
    
    @Path("save")
    @PUT
    @UnitOfWork
    public Response save(Thing thing){
    	
    	Thing currentThing = thingDAO.findById(thing.getId()).orNull();
    	
    	if(currentThing == null){
    		return Response.status(Response.Status.BAD_REQUEST).build();
    	}
    	
    	currentThing.update(thing);
    	
    	thingDAO.create(currentThing);

        if(currentThing.getParent() != null){
            return Response.ok(currentThing.getParent()).build();
        }
        else{
            return Response.ok(currentThing).build();
        }
    }
	    

    @Path("{parentId}/add")
    @PUT
    @UnitOfWork
    public Response addChild(@PathParam(value = "parentId") Long id, Thing thing){
    	
      	Thing nonValidatedParent =  thingDAO.findById(id).orNull();
    	
    	if(nonValidatedParent == null){
    		return Response.status(Response.Status.BAD_REQUEST).build();
    	}
    	
    	if(!(nonValidatedParent instanceof ToDoThing)){
    		return Response.status(Response.Status.BAD_REQUEST).build();
    	}
    	
	    ToDoThing parent = (ToDoThing) nonValidatedParent;
	    	
    	parent.addChild(thing);
    	
        Thing createdThing = thingDAO.create(parent);
        return Response.ok(parent).build();
    }

    @Path("all")
    @GET
    @UnitOfWork
    public Response getAll(){
        List<Thing> things = thingDAO.findAll();
        return Response.ok(things).build();
    }

    @Path("{id}")
    @GET
    @UnitOfWork
    public Response get(@PathParam("id") long id){
        Thing thing = thingDAO.findById(id).orNull();

        if(thing == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(thing).build();
    }
    
    @Path("delete/{id}")
    @DELETE
    @UnitOfWork
    public Response deleteThing(@PathParam("id") long thingId){
    	Thing thing = thingDAO.findById(thingId).orNull();
    	 if(thing == null){
    		 return Response.status(Status.BAD_REQUEST).build();
    	 }
    	 
    	 ToDoThing parent = (ToDoThing) thing.getParent();
    	 
    	 parent.removeChild(thing);
    	 
    	 thingDAO.delete(thing);
    	 
    	 return Response.ok(parent).build();
    }
}
