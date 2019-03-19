package ru.nextbi.writers;

import java.io.IOException;

public abstract class ALinkWriter implements ILinkWriter {

    String parentClassName;
    String childClassName;
    String linkName;

    public ALinkWriter( String parentClassName, String childClassName )
    {
        this.parentClassName = parentClassName;
        this.childClassName = childClassName;
        linkName = "link_" + parentClassName + "_" + childClassName;
    }

    public String getParentClassName(){
        return parentClassName;
    }

    public String getChildClassName(){
        return childClassName;
    }

    protected String getLinkName()
    {
        return linkName;
    }

}
