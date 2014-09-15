package se.jhasselgren.noteapp.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by jhas on 2014-09-12.
 */
@Path("hello")
public class HelloResource {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(){
        return Response.ok("Hello World").build();
    }

}
