package ru.nextbi.writers.mongo;

import ru.nextbi.GTDGenerator;
import ru.nextbi.model.GraphElementDescription;
import ru.nextbi.model.VertexDescription;
import ru.nextbi.writers.IEdgeWriter;
import ru.nextbi.writers.ILinkWriter;
import ru.nextbi.writers.IVertexWriter;
import ru.nextbi.writers.IWriterFactory;

import java.io.IOException;
import java.util.Map;

public class MongoWritersFactory implements IWriterFactory{

    MongoConnection connection;

    public MongoWritersFactory( Map<String, String> params )
    {
        connection = new MongoConnection( params.get(GTDGenerator.DBHOST),
                                          Integer.parseInt( params.get( GTDGenerator.DBPORT) ),
                                          params.get( GTDGenerator.DBNAME ) );
    }

    @Override
    public IVertexWriter createVertexWriter(GraphElementDescription vd) throws IOException{
        return new TMongoVertexWriter(  connection, (VertexDescription) vd);
    }

    @Override
    public IEdgeWriter createEdgeWriter(GraphElementDescription ed)
    {
        return new TMongoEdgeWriter( connection, (VertexDescription) ed);
    }

    @Override
    public ILinkWriter createLinkWriter(String parentClassName, String className)
    {
        return new MongoLinkWriter( connection, parentClassName, className );
    }
}
