package ru.nextbi.writers.mongo;

import com.mongodb.client.MongoCollection;
import ru.nextbi.writers.ALinkWriter;
import ru.nextbi.writers.DBObjectHelper;
import ru.nextbi.writers.ILinkWriter;

import java.io.IOException;
import java.lang.annotation.Documented;

public class MongoLinkWriter extends ALinkWriter{

    MongoConnection connection;
    String parentClassName;
    String childClassName;
    MongoCollection collection;

    public MongoLinkWriter( MongoConnection connection, String parentClassName, String childClassName )
    {
        super( parentClassName, childClassName );
        this.connection = connection;
        this.parentClassName = parentClassName;
        this.childClassName = childClassName;
    }

    @Override
    public void init() throws IOException{
        collection = connection.createCollectionIfNotExists( getLinkName() );
    }

    @Override
    public void write(String parent, String child) throws IOException
    {
        collection.insertOne(DBObjectHelper.linkToObject( getParentClassName(), getChildClassName(), parent, child ));
    }

    @Override
    public void close(){

    }
}
