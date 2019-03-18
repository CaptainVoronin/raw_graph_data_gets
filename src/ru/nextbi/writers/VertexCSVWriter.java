package ru.nextbi.writers;

import ru.nextbi.GTDGenerator;
import ru.nextbi.generation.GraphObjectProperty;
import ru.nextbi.model.BaseVertex;
import ru.nextbi.model.VertexDescription;

import java.io.*;
import java.util.Map;

public class VertexCSVWriter implements IVertextWriter
{
    File dir;
    String filename;
    char delimiter;
    VertexSerializer vertexSerializer;
    VertexDescription vertexDescription;
    FileOutputStream fous;


    public VertexCSVWriter( File targetDir, VertexDescription vertexDescription, Map<String,String> config ) throws IOException{
        dir = targetDir;
        String fileExt = config.getOrDefault( "ext", "csv" );
        this.vertexDescription = vertexDescription;
        this.filename = targetDir.getPath() + File.separator + vertexDescription.getClassName() + "." + fileExt;
        this.delimiter = ( char ) config.getOrDefault( "delimiter", "," ).getBytes()[0];

        vertexSerializer = new VertexSerializer();
        vertexSerializer.setDelimiter( delimiter );
        vertexSerializer.setNullValueSequence( config.getOrDefault(GTDGenerator.NULL_VALUE_SEQUENCE, "" ) );

        fous = new FileOutputStream ( this.filename );
        //fous.write( '\ufeff' );
    }

    public void writeElement( BaseVertex v) throws Exception {
        fous.write( vertexSerializer.vertexToString(vertexDescription, v).getBytes( ));
    }

    public void writeHeader() throws IOException {

        StringBuilder st = new StringBuilder();

        Map<String, GraphObjectProperty > props = vertexDescription.getProperties();

        for( String key : props.keySet() )
            st.append( props.get( key ).name ).append( delimiter );

        st.deleteCharAt( st.length() - 1 );
        st.append( '\n' );

        fous.write( st.toString().getBytes( ) );

     }

    public void close()
    {
        if( fous != null )
            try {
                fous.close();
            } catch( IOException e ) {
                e.printStackTrace();
            }
    }
}
