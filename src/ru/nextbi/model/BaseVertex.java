package ru.nextbi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseVertex extends GraphElement
{

    //String parentID;

    // ID'ы
    Map<String, String> links;

    public BaseVertex( /*String parentID*/ )
    {
        super( ELEMENT_TYPE.VERTEX );
      //  this.parentID = parentID;
        links = new HashMap<>();
    }

    @Override
    public ELEMENT_TYPE getType()
    {
        return ELEMENT_TYPE.VERTEX;
    }

/*
    public String getParent()
    {
        return parentID;
    }
*/

    public void addLink(String className, String link)
    {
        links.put( className, link );
    }

    public Map<String, String> getLinks()
    {
        return links;
    }
}
