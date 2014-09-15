package se.jhasselgren.noteapp.resources;

import se.jhasselgren.noteapp.core.Thing;
import se.jhasselgren.noteapp.db.ThingDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @Path("add")
    @POST
    public Response add(Thing thing){
        Thing createdThing = thingDAO.create(thing);
        return Response.ok(createdThing).build();
    }

    @Path("all")
    @GET
    public Response getAll(){
        List<Thing> things = thingDAO.findAll();
        return Response.ok(things).build();
    }
}
