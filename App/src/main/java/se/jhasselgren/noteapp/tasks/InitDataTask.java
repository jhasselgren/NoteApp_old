package se.jhasselgren.noteapp.tasks;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import se.jhasselgren.noteapp.core.ToDoThing;
import se.jhasselgren.noteapp.db.ThingDAO;

import com.google.common.collect.ImmutableMultimap;

public class InitDataTask extends Task{
	
	ThingDAO thingDAO;
	
	public InitDataTask(ThingDAO thingDAO) {
		super("InitDataTask");

		this.thingDAO = thingDAO;
	}

	@Override
	public void execute(ImmutableMultimap<String, String> parameters,
			PrintWriter output) throws Exception {
		
		
		ToDoThing todoThing = new ToDoThing();
		
		todoThing.setName("ToDo Test");
		todoThing.setDescription("Vi testar hur en ToDo thing ser ut och hanteras");
		todoThing.setStatus(70);
		
		{
			
		}
		
		thingDAO.create(todoThing);
		
	}

}
