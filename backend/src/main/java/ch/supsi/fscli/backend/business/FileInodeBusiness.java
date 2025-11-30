package ch.supsi.fscli.backend.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileInodeBusiness extends Inode {

    private boolean isSoftLink;
    private String linkPath;

    @JsonCreator
    public FileInodeBusiness(@JsonProperty("id") long id) {
        super(id, InodeType.FILE);
    }


    public boolean isSoftLink() {
        return isSoftLink;
    }

    public void setSoftLink(boolean softLink) {
        isSoftLink = softLink;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath;
    }



}