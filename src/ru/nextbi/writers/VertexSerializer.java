package ru.nextbi.writers;

import ru.nextbi.generation.GraphObjectProperty;
import ru.nextbi.model.BaseVertex;
import ru.nextbi.model.GraphElement;
import ru.nextbi.model.VertexDescription;

import java.util.Map;

public class VertexSerializer
{
    char delimiter;

    public void setDelimiter(char delimiter)
    {
        this.delimiter = delimiter;
    }

    public String vertexToString(VertexDescription vd, BaseVertex v ) throws Exception {
        StringBuilder st = new StringBuilder( );

        // Если указан родитель
        if( v.getParent() != null )
        {
            String parentID = v.getParent();
            if( parentID == null || parentID.trim().length() == 0 )
                throw new Exception( "Incorrect parent id" );
            st.append( v.getParent() ).append( delimiter );
        }
        else if( vd.getParentClassName() != null )
            throw new Exception( "Link violation!");

        // Свойства
        Map<String, GraphObjectProperty> props = vd.getProperties();
        Map<String, String>  values = v.getProperties();

        // ID придется записать силовым методом
        //st.append( values.get( GraphElement.KEY_ID ) ).append( delimiter );

        for( String name : props.keySet() ) {
            st.append(values.get(name)).append(delimiter);
        }

        Map<String, String> links = v.getPosessors();
        for( String className : vd.getLinks() )
        {
            st.append( links.get( className ) ).append( delimiter );
        }

        st.deleteCharAt( st.length() - 1 );
        st.append( '\n' );

        return st.toString();
    }
}
