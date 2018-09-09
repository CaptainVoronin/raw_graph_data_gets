package ru.nextbi.writers;

import ru.nextbi.generation.GraphObjectProperty;
import ru.nextbi.model.BaseVertex;
import ru.nextbi.model.VertexDescription;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class VertexCSVWriter
{
    File dir;
    String filename;
    char delimiter;

    public VertexCSVWriter( File targetDir, String filename, char delimiter )
    {
        dir = targetDir;
        this.filename = filename;
        this.delimiter = delimiter;
    }

    public void write(VertexDescription vd, List<BaseVertex> vertices ) throws Exception
    {
        System.out.println( "Start writing " + vd.getClassName() );
        File f = new File( dir.getPath() + "/" + filename );
        FileWriter fw = new FileWriter( f );
        writeHeader(  fw, vd );
        writeElements( fw, vd, vertices );
        fw.close();
        System.out.println( "Done" );
    }

    private void writeElements(FileWriter fw, VertexDescription vd, List<BaseVertex> vertices) throws Exception {
        VertexSerializer vs = new VertexSerializer();
        vs.setDelimiter( delimiter );
        for( BaseVertex v : vertices )
            fw.write(vs.vertexToString(vd, v));
    }

    private void writeHeader(FileWriter fw, VertexDescription vd) throws IOException {
        StringBuilder st = new StringBuilder();

        if( vd.getParentClassName() != null )
            st.append( vd.getParentClassName() ).append( "_id" ).append( delimiter );

        Map<String, GraphObjectProperty > props = vd.getProperties();

        for( String key : props.keySet() )
            st.append( props.get( key ).name ).append( delimiter );

        for( String className : vd.getLinks() )
            st.append(  className + "_id" ).append( delimiter );

        st.deleteCharAt( st.length() - 1 );
        st.append( '\n' );

        fw.write( st.toString() );
    }
}
