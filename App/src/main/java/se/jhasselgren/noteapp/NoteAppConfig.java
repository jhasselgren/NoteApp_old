package se.jhasselgren.noteapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;

/**
 * Created by jhas on 2014-09-12.
 */
public class NoteAppConfig extends Configuration {

    @Valid
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")

    public DataSourceFactory getDatabase() {
        return database;
    }

    @JsonProperty("database")
    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }
}
