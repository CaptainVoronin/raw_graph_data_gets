package ru.nextbi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseVertex extends GraphElement
{

    String parentID;
    Map<String, String> posessors;

    public BaseVertex( String parentID )
    {
        super( ELEMENT_TYPE.VERTEX );
        this.parentID = parentID;
        posessors = new HashMap<>();
    }

    @Override
    public ELEMENT_TYPE getType()
    {
        return ELEMENT_TYPE.VERTEX;
    }

    public String getParent()
    {
        return parentID;
    }

    public void addPosessor( String className, String id )
    {
        posessors.put(  className, id );
    }

    public Map<String,String> getPosessors()
    {
        return posessors;
    }
}
