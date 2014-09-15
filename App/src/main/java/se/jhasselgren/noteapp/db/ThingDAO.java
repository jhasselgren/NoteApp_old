package se.jhasselgren.noteapp.db;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import se.jhasselgren.noteapp.core.Thing;

import java.util.List;

/**
 * Created by jhas on 2014-09-13.
 */
public class ThingDAO extends AbstractDAO<Thing> {
    public ThingDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Thing> findById(Long id){
        return Optional.fromNullable(get(id));
    }

    public Thing create(Thing thing){
        return persist(thing);
    }

    public List<Thing> findAll(){
        return list(currentSession().createQuery("Select t FROM Thing t"));
    }
}
