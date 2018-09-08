package ru.nextbi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseVertex extends GraphElement
{

    BaseVertex parent;
    Map<String, String> posessors;

    public BaseVertex( BaseVertex parent )
    {
        super( );
        this.parent = parent;
        posessors = new HashMap<>();
    }

    public ELEMENT_TYPE getElementType()
    {
        return ELEMENT_TYPE.VERTEX;
    }

    public BaseVertex getParent()
    {
        return parent;
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
