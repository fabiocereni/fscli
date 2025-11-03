package ch.supsi.fscli.backend.business;

import java.util.*;

class FSElement  {
    private FSElement parent;
    private String name;
    private List<FSElement> content;


    public FSElement(FSElement parent, String name) {
        this.parent = parent;
        this.name = name;
        this.content = new ArrayList<>();
    }

    
    public FSElement getParent() {
        return parent;
    }

    
    public void setParent(FSElement parent) {
        this.parent = parent;
    }

    
    public String getName() {
        return this.parent == null ? "root" : this.name;
    }

    
    public void setName(String name) {
        if(this.parent != null) {
            this.name = name;
        } else {
            System.out.println("cannot rename the root folder");
        }
    }

    
    public List<FSElement> getContent() {
        return content;
    }

    
    public void setCont(List<FSElement> cont) {
        this.content = cont;
    }

    
    public String toString() {
        return toStringHelper(0);
    }

    private String toStringHelper(int level) {
        StringBuilder sb = new StringBuilder();

        sb.append("\t".repeat(Math.max(0, level)));

        sb.append(name);

        if (content != null && !content.isEmpty()) {
            sb.append(":\n");
            for (FSElement el : content) {
                if (el != null) {
                    sb.append(el.toStringHelper(level + 1)).append("\n");
                } else {
                    sb.append("\t".repeat(Math.max(0, level + 1)));
                    sb.append("null\n");
                }
            }
        }

        return sb.toString().trim();
    }
}