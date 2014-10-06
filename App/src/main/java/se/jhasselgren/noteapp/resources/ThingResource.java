package se.jhasselgren.noteapp.resources;

import com.codahale.metrics.annotation.Timed;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.dropwizard.hibernate.UnitOfWork;
import se.jhasselgren.noteapp.core.FileInformation;
import se.jhasselgren.noteapp.core.FileThing;
import se.jhasselgren.noteapp.core.Thing;
import se.jhasselgren.noteapp.core.ToDoThing;
import se.jhasselgren.noteapp.db.FileInformationDAO;
import se.jhasselgren.noteapp.db.ThingDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by jhas on 2014-09-13.
 */
@Path("thing")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ThingResource {

    private final ThingDAO thingDAO;
    private final FileInformationDAO fileInformationDAO;

    public ThingResource(ThingDAO thingDAO, FileInformationDAO fileInformationDAO) {
        this.thingDAO = thingDAO;
        this.fileInformationDAO = fileInformationDAO;
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

    @POST
    @Path("{id}/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @UnitOfWork
    @Timed
    public Response uploadFile(@PathParam("id") long id,
                               @FormDataParam("file") final InputStream inputStream,
                               @FormDataParam("file") final FormDataContentDisposition fileDetails,
                               @FormDataParam("fileType") String fileType){

        Thing fetchedFile = thingDAO.findById(id).orNull();

        if(fetchedFile == null){
            return Response.status(Status.BAD_REQUEST).entity("No thing with id: " + id + " could be found").build();
        }

        FileThing fileThing = null;

        if(fetchedFile instanceof FileThing){
            fileThing = (FileThing) fetchedFile;
        }
        else{
            return Response.status(Status.BAD_REQUEST).entity("Thing with id: " + id + " is not a file thing").build();
        }

       // boolean hasParent = fileThing.getParent() != null;

        String url = fileThing.getId() + " " + fileThing.getName();

        Thing current = fileThing;

        while (current.getParent() != null){
            url = current.getParent().getId() + " " + current.getName() + "/" + url;
            current = current.getParent();
        }

        java.nio.file.Path pathToFolder = Paths.get(System.getProperty("user.home"), "NoteApp", "Files", url);


        String fileName = com.google.common.io.Files.getNameWithoutExtension(fileDetails.getFileName());
        String fileExtension = com.google.common.io.Files.getFileExtension(fileDetails.getFileName());

        String prefix = "";

        int i = 1;

        while (Files.exists(pathToFolder.resolve(fileName+prefix+"."+fileExtension))){
            prefix = "_"+i;
            i++;
        }

        java.nio.file.Path pathToFile = pathToFolder.resolve(fileName+prefix+"."+fileExtension);
        FileInformation fileInformation = new FileInformation();

        try {
            if(Files.notExists(pathToFile.getParent())) {
                com.google.common.io.Files.createParentDirs(pathToFile.toFile());
            }
            java.nio.file.Files.copy(inputStream, pathToFile);
            fileInformation.setPath(pathToFile.toString());
            fileInformation.setFileType(fileType);

        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to save file").build();
        }

        fileThing.addFile(fileInformation);

        thingDAO.create(fileThing);

        fileThing = (FileThing)thingDAO.findById(fileThing.getId()).orNull();

        return Response.ok(fileThing).build();
    }

    @GET
    @Path("download/{id}")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("id") long fileId){
        FileInformation fileInformation = fileInformationDAO.findById(fileId).orNull();

        if(fileInformation == null){
            return Response.status(Status.BAD_REQUEST).entity("Could not find any file with ID:" + fileId).build();
        }

        java.nio.file.Path pathToFile = Paths.get(fileInformation.getPath());

        if(Files.exists(pathToFile)){

            File file = pathToFile.toFile();

            String fileExtension = com.google.common.io.Files.getFileExtension(fileInformation.getPath());

            String fileName = fileInformation.getFileThing().getName() + "_v" + fileInformation.getVersion() + "." +fileExtension;

            MediaType mediaType = null;

            if(fileInformation.getFileType() != null) {
                try {
                    mediaType = MediaType.valueOf(fileInformation.getFileType());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if(mediaType == null){
                mediaType = MediaType.APPLICATION_OCTET_STREAM_TYPE;
            }

            return Response.ok(file, mediaType).
                    header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").
                    build();
        }

        return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Unable to locate file on server").build();
    }
}
