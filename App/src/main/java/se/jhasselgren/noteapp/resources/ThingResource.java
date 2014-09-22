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
    
    @Path("create-data")
    @GET
    @UnitOfWork
    public Response createData(){
    	ToDoThing todoThing = new ToDoThing();
		
		todoThing.setName("ToDo Test");
		todoThing.setDescription("Vi testar hur en ToDo thing ser ut och hanteras");
		todoThing.setStatus(70);
		
		{
			TextThing childThing = new TextThing();
			childThing.setName("Text Thing Test");
			
			childThing.setDescription("<p>Try-hard readymade 3 wolf moon DIY food truck gentrify scenester. Bushwick sriracha biodiesel, semiotics Schlitz fixie kale chips squid stumptown umami Marfa Williamsburg flexitarian lomo. Polaroid McSweeney's crucifix, skateboard wolf actually Helvetica synth you probably haven't heard of them street art. Leggings aesthetic Marfa irony, Kickstarter narwhal twee blog. Yr before they sold out Helvetica jean shorts authentic tofu Blue Bottle lomo. Tote bag Blue Bottle Brooklyn fanny pack Tonx twee. Umami wayfarers crucifix artisan, normcore VHS street art bespoke tofu flexitarian Tumblr art party asymmetrical.</p>"
					+ "<p>Yr sustainable 8-bit viral Banksy. Echo Park you probably haven't heard of them forage craft beer selvage, Etsy kogi lo-fi. Pop-up 90's mixtape PBR&B, salvia ethical American Apparel shabby chic blog narwhal raw denim fanny pack typewriter locavore Pinterest. Fingerstache Godard skateboard 3 wolf moon next level. Typewriter umami cray disrupt. Plaid aesthetic semiotics Tumblr raw denim sustainable Thundercats cardigan actually quinoa wolf yr. Pickled lomo iPhone tattooed small batch, Carles Austin kogi Shoreditch brunch cred freegan synth before they sold out typewriter.</p>");
			
			todoThing.addChild(childThing);
		}
		{
			CommentThing childThing = new CommentThing();
			childThing.setComment("Vi testar en kommentar");
			childThing.setSentBy("Joakim Hasselgren");
			todoThing.addChild(childThing);
		}
		{
			FileThing childThing = new FileThing();
			childThing.setDescription("En test fil som inte finns");
			childThing.setName("Test fil");
			todoThing.addChild(childThing);
		}
		{
			LinkThing childThing = new LinkThing();
			childThing.setName("Google");
			childThing.setLink("http://google.se");
			childThing.setDescription("En länk till google");
			todoThing.addChild(childThing);
		}
		
		return Response.ok(thingDAO.create(todoThing)).build();
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
    	
        Thing createdThing = thingDAO.create(thing);
        return Response.ok(createdThing).build();
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
