package ru.nextbi.writers;

import com.mongodb.client.MongoCollection;
import ru.nextbi.model.BaseVertex;
import ru.nextbi.model.VertexDescription;

import java.io.IOException;
import java.util.List;

public class TMongoVertexWriter implements IVertextWriter
{
    MongoConnection connection;
    VertexDescription vertexDescription;
    MongoCollection collection;

    public TMongoVertexWriter(MongoConnection connection, VertexDescription vertexDescription )
    {
        this.connection = connection;
        this.vertexDescription = vertexDescription;
        collection = connection.createCollectionIfNotExists( vertexDescription.getClassName() );
    }

    @Override
    public void writeElement( BaseVertex v ) throws Exception{
        collection.insertOne( DBObjectHelper.edgeToDBObject( vertexDescription, v.getProperties() ) );
    }

    @Override
    public void writeHeader() throws IOException{

    }

    @Override
    public void close(){

    }
}
