package ru.nextbi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class GraphElement
{
    public final static String KEY_ID = "key_id";

    public final static String KEY_LABEL = "key_label";

    public enum ELEMENT_TYPE{ VERTEX, EDGE };

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

    public GraphElement ( )
    {
        properties = new HashMap<>();
        properties.put( KEY_ID, UUID.randomUUID().toString() );

    }

    public String getId()
    {
        return getProperty( KEY_ID );
    }



    public String getProperty( String key )
    {
        return properties.get( key );
    }

    public String setProperty( String key, String val )
    {
        if( key.equalsIgnoreCase( KEY_ID ) )
            throw new IllegalArgumentException( key );
        return properties.put( key, val );
    }

}
