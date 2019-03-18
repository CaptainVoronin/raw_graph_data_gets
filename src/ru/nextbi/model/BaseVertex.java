package ru.nextbi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseVertex extends GraphElement
{

    // ID'ы
    Map<String, String> links;

    public BaseVertex(  )
    {
        super( ELEMENT_TYPE.VERTEX );
        links = new HashMap<>();
    }

    @Override
    public ELEMENT_TYPE getType()
    {
        return ELEMENT_TYPE.VERTEX;
    }

    public void addLink(String className, String link)
    {
        links.put( className, link );
    }

    public Map<String, String> getLinks()
    {
        return links;
    }
}
