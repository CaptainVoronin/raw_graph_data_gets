package ru.nextbi.writers.mongo;

import com.mongodb.client.MongoCollection;
import ru.nextbi.model.BaseEdge;
import ru.nextbi.model.VertexDescription;
import ru.nextbi.writers.DBObjectHelper;
import ru.nextbi.writers.IEdgeWriter;
import ru.nextbi.writers.mongo.MongoConnection;

import java.io.IOException;

public class TMongoEdgeWriter implements IEdgeWriter{

    MongoConnection connection;
    VertexDescription vertexDescription;
    MongoCollection collection;

    public TMongoEdgeWriter (MongoConnection connection, VertexDescription vertexDescription )
    {
        this.connection = connection;
        this.vertexDescription = vertexDescription;
        collection = connection.createCollectionIfNotExists( vertexDescription.getClassName() );
    }

    @Override
    public void writeElement( BaseEdge v ) throws Exception{

        collection.insertOne( DBObjectHelper.edgeToDBObject( vertexDescription, v.getProperties() ) );
    }

    @Override
    public void writeHeader() throws IOException{

    }

    @Override
    public void close(){

    }
}
