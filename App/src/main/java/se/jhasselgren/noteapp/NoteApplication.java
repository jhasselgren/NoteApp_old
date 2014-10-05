package se.jhasselgren.noteapp;



import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.context.internal.ManagedSessionContext;
import se.jhasselgren.noteapp.core.*;
import se.jhasselgren.noteapp.db.FileInformationDAO;
import se.jhasselgren.noteapp.db.ThingDAO;
import se.jhasselgren.noteapp.resources.HelloResource;
import se.jhasselgren.noteapp.resources.ThingResource;

import java.util.ArrayList;

/**
 * Created by jhas on 2014-09-12.
 */
public class NoteApplication extends Application<NoteAppConfig> {
    public static void main(String[] args) throws Exception{
        new NoteApplication().run(args);
    }

    private final HibernateBundle<NoteAppConfig> hibernateBundle =
            new HibernateBundle<NoteAppConfig>(Thing.class, ToDoThing.class, TextThing.class, FileThing.class, CommentThing.class, LinkThing.class, FileInformation.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(NoteAppConfig noteAppConfig) {
                    return noteAppConfig.getDatabase();
                }
            };

    @Override
    public void initialize(Bootstrap<NoteAppConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/app", "/", "index.html", "front-end"));
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public String getName() {
        return "NoteApp";
    }

    @Override
    public void run(NoteAppConfig noteAppConfig, Environment environment) throws Exception {
        environment.jersey().setUrlPattern("/api/*");

        final ThingDAO thingDAO = new ThingDAO(hibernateBundle.getSessionFactory());
        final FileInformationDAO fileDAO = new FileInformationDAO(hibernateBundle.getSessionFactory());

        insertTestData(thingDAO);

        environment.jersey().register(new HelloResource());
        environment.jersey().register(new ThingResource(thingDAO, fileDAO));
    }


    public void insertTestData(ThingDAO dao){

        org.hibernate.Session session = hibernateBundle.getSessionFactory().openSession();
        ManagedSessionContext.bind(session);

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

        dao.create(todoThing);
        session.close();
    }

}
