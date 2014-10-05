package se.jhasselgren.noteapp.db;

import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import se.jhasselgren.noteapp.core.FileInformation;

/**
 * Created by jhas on 2014-10-05.
 */
public class FileInformationDAO extends AbstractDAO<FileInformation>{

    public FileInformationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public Optional<FileInformation> findById(Long id){
        return Optional.fromNullable(get(id));
    }
}
