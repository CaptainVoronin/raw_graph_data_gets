package ru.nextbi.writers;

import ru.nextbi.generation.GraphObjectProperty;
import ru.nextbi.model.*;

import java.util.Map;

public class TEdgeSerializer
{
    char delimiter;

    public void setDelimiter(char delimiter)
    {
        this.delimiter = delimiter;
    }

    public String edgeToString(TEdgeDescription ted, BaseEdge v ) throws Exception
    {
        StringBuilder st = new StringBuilder( );

        Map<String, GraphObjectProperty> props = ted.getProperties();
        Map<String, String>  values = v.getProperties();

        // ID придется записать силовым методом
        //st.append( values.get( GraphElement.KEY_ID ) ).append( delimiter );

        st.append( v.getFrom() ).append( delimiter );
        st.append( v.getTo() ).append( delimiter );

        // Свойства
        for( String name : props.keySet() )
            st.append( values.get( name ) ).append( delimiter );
        st.deleteCharAt( st.length() - 1 );
        st.append( '\n' );

        return st.toString();
    }
}
