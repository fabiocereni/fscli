package ch.supsi.fscli.backend.business.filesystem.structure;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/*
* Servono per chiarire a jackson il tipo preciso
* durante il salvataggio e permettere poi un caricamento
* corretto
*/
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "inodeType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DirectoryInodeBusiness.class, name = "directory"),
        @JsonSubTypes.Type(value = FileInodeBusiness.class, name = "file")
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@json_id")
public abstract class Inode {
    private long id;
    private InodeType type;
    private int linkCount;

    public Inode() {
    }

    public Inode(long id, InodeType type) {
        this.id = id;
        this.type = type;
        this.linkCount = 0;
    }

    public long getId() {
        return id;
    }

    public InodeType getType() {
        return type;
    }

    public int getLinkCount() {
        return linkCount;
    }

    public void incLinkCount() {
        linkCount++;
    }

    public void decLinkCount() {
        linkCount--;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(InodeType type) {
        this.type = type;
    }

    public void setLinkCount(int linkCount) {
        this.linkCount = linkCount;
    }
}



