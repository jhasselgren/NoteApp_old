package se.jhasselgren.noteapp.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by jhas on 2014-10-04.
 */
@Entity
public class FileInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String path;
    private String fileType;
    private int version;
    private final long created = DateTime.now().getMillis();

    @ManyToOne
    @JoinColumn(name = "fileThing_id")
    private FileThing fileThing;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonIgnore
    public String getFileType() {
        return fileType;
    }
    @JsonIgnore
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    @JsonIgnore
    public FileThing getFileThing() {
        return fileThing;
    }
    @JsonIgnore
    public void setFileThing(FileThing fileThing) {
        this.fileThing = fileThing;
    }

    public long getCreated() {
        return created;
    }
}
