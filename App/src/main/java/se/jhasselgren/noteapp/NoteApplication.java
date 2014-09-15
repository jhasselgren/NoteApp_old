package se.jhasselgren.noteapp;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import se.jhasselgren.noteapp.core.Thing;
import se.jhasselgren.noteapp.db.ThingDAO;
import se.jhasselgren.noteapp.resources.HelloResource;
import se.jhasselgren.noteapp.resources.ThingResource;

/**
 * Created by jhas on 2014-09-12.
 */
public class NoteApplication extends Application<NoteAppConfig> {
    public static void main(String[] args) throws Exception{
        new NoteApplication().run(args);
    }

    private final HibernateBundle<NoteAppConfig> hibernateBundle =
            new HibernateBundle<NoteAppConfig>(Thing.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(NoteAppConfig noteAppConfig) {
                    return noteAppConfig.getDatabase();
                }
            };

    @Override
    public void initialize(Bootstrap<NoteAppConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/app", "/", "index.html", "front-end"));
        bootstrap.addBundle(new AssetsBundle("/bower_components","/bower_components", null, "bower"));

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

        environment.jersey().register(new HelloResource());
        environment.jersey().register(new ThingResource(thingDAO));

    }
}
