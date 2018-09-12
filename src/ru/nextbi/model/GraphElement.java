package ru.nextbi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class GraphElement
{
    public final static String KEY_ID = "id";

    public enum ELEMENT_TYPE{ VERTEX, EDGE };

    ELEMENT_TYPE type;
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> props)
    {
        Set<String> keys = props.keySet();

        for( String key : keys )
        {
            if( !properties.containsKey( key ) )
                properties.put( key, props.get( key ));
        }
    }

    Map<String, String> properties;

    public GraphElement ( ELEMENT_TYPE type )
    {
        properties = new HashMap<>();
        this.type = type;
    }

    public String getId()
    {
        return getProperty( KEY_ID );
    }

    public void setId( String id )
    {
        properties.put( KEY_ID, id );
    }

    public String getProperty( String key )
    {
        return properties.get( key );
    }

    public String setProperty( String key, String val )
    {
        return properties.put( key, val );
    }

    public ELEMENT_TYPE getType(){
        return type;
    };
}
