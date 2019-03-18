package ru.nextbi.writers;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

public class MongoConnection {
    String host;
    String dbName;
    int port;
    MongoClient mongoClient;
    MongoDatabase db;

    public MongoConnection(  String host, String dbName, int port )
    {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
    }

    public void connect()
    {
        mongoClient = new MongoClient(  );
        db = mongoClient.getDatabase( "db1" );
    }

    public MongoCollection getCollection( String collectionName )
    {
        return  db.getCollection( collectionName );
    }

    public MongoCollection<Document> createCollectionIfNotExists( String name )
    {
        if( !existsCollection( name ) )
            db.createCollection( name );
        return db.getCollection( name );
    }

    public void insertObject( String collectionName, Document obj )
    {
        db.getCollection( collectionName ).insertOne( obj );
    }

    public void clearCollection( String collectionName )
    {
        db.getCollection( collectionName ).deleteMany( new BasicDBObject() );
    }

    public boolean existsCollection( String name )
    {
        MongoIterable<String> collections = db.listCollectionNames();

        for( String collection :  collections )
        {
            if( collection.equalsIgnoreCase( name ))
                return true;
        }

        return false;
    }
}
