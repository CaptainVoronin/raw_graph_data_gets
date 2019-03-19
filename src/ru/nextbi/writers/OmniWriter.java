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
    Map<String, IVertexWriter> writers;
    private Map<String, ILinkWriter> ownershipWiters;
    IWriterFactory factory;

    public OmniWriter( Map<String, String> config, IWriterFactory factory )
    {
        this.config = config;
        writers = new HashMap<>();
        ownershipWiters = new HashMap<>();
        this.factory = factory;
    }

/*
    public OmniWriter( Map<String, String> config, File targerDir )
    {
        this.targetDir = targerDir;
        this.config = config;
        writers = new HashMap<>();
        ownershipWiters = new HashMap<>();
    }
*/

    public void write( VertexDescription vd, BaseVertex v ) throws Exception{
        IVertexWriter writer = writers.get( vd.getClassName() );
        if( writer == null )
        {
            writer = createWriter( vd );
            writers.put( vd.getClassName(), writer );
        }
        writer.writeElement( v );
    }

    private IVertexWriter createWriter(VertexDescription vd) throws IOException
    {
        // TODO: Привести в порядок CSVWrite'ы
        //VertexCSVWriter writer = new VertexCSVWriter( targetDir, vd, config );
        IVertexWriter writer = factory.createVertexWriter( vd );
        writer.writeHeader();
        return writer;
    }

    public void closeAll()
    {
        for( IVertexWriter writer : writers.values() )
            writer.close();

        for( ILinkWriter lw : ownershipWiters.values() )
            lw.close();
    }

    public void closeLinkWriters()
    {
        for( ILinkWriter lw : ownershipWiters.values() )
            lw.close();
        ownershipWiters.clear();
    }

    public void forgetVertex(String key)
    {
        IVertexWriter writer = writers.get( key );
        if( writer != null ) {
            writer.close();
            writers.remove( key );
        }
    }

    public void forgetLinkWriter(String key)
    {
        ILinkWriter writer = ownershipWiters.get( key );
        if( writer != null ) {
            writer.close();
            writers.remove( key );
        }
    }

    public void writeOwnership(String parentClassName, String className, String parentId, String id) throws IOException {
        String key = "link_" + parentClassName + "_" + className;
        ILinkWriter writer = ownershipWiters.get ( key );
        if( writer == null )
        {
            writer = factory.createLinkWriter( parentClassName, className );
            writer.init();
            ownershipWiters.put( key, writer );
        }

        writer.write( parentId, id );
    }
}
