package ru.nextbi.writers;

import ru.nextbi.model.BaseVertex;
import ru.nextbi.model.VertexDescription;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OmniWriter{
    Map<String, String> config;
    File targetDir;
    Map<String, VertexCSVWriter> writers;

    public OmniWriter( Map<String, String> config, File targerDir )
    {
        this.targetDir = targerDir;
        this.config = config;
        writers = new HashMap<>();
    }

    public void write(VertexDescription vd, BaseVertex v ) throws Exception{
        VertexCSVWriter writer = writers.get( vd.getClassName() );
        if( writer == null )
        {
            writer = createWriter( vd );
            writers.put( vd.getClassName(), writer );
        }
        writer.writeElement( v );
    }

    private VertexCSVWriter createWriter(VertexDescription vd) throws IOException
    {
        VertexCSVWriter writer = new VertexCSVWriter( targetDir, vd, config );
        writer.writeHeader();
        return writer;
    }

    public void closeAll()
    {
        for( String className : writers.keySet() )
            writers.get( className ).close();
    }

}
