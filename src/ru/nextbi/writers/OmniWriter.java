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
    private Map<String, LinkWriter> ownershipWiters;

    public OmniWriter( Map<String, String> config, File targerDir )
    {
        this.targetDir = targerDir;
        this.config = config;
        writers = new HashMap<>();
        ownershipWiters = new HashMap<>();
    }

    public void write( VertexDescription vd, BaseVertex v ) throws Exception{
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
        for( VertexCSVWriter writer : writers.values() )
            writer.close();

        for( LinkWriter lw : ownershipWiters.values() )
            lw.close();
    }

    public void closeLinkWriters()
    {
        for( LinkWriter lw : ownershipWiters.values() )
            lw.close();
        ownershipWiters.clear();
    }

    public void forgetVertex(String key)
    {
        VertexCSVWriter writer = writers.get( key );
        if( writer != null ) {
            writer.close();
            writers.remove( key );
        }
    }

    public void forgetLinkWriter(String key)
    {
        LinkWriter writer = ownershipWiters.get( key );
        if( writer != null ) {
            writer.close();
            writers.remove( key );
        }
    }

    public void writeOwnership(String parentClassName, String className, String parentId, String id) throws IOException {
        String key = parentClassName + "-" + className;
        LinkWriter writer = ownershipWiters.get ( key );
        if( writer == null )
        {
            writer = new LinkWriter( targetDir, parentClassName, className,
                                      config.getOrDefault( "delimiter", "," ).charAt(0) );
            writer.init();
            ownershipWiters.put( key, writer );
        }

        writer.write( parentId, id );
    }
}
