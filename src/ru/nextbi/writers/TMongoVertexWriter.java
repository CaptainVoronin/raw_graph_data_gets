package ru.nextbi.writers;

import ru.nextbi.model.BaseVertex;

import java.io.IOException;

public class TMongoVertexWriter implements IVertextWriter
{
    MongoConnection connection;

    public TMongoVertexWriter(MongoConnection connection )
    {
        this.connection = connection;
    }

    @Override
    public void writeElement(BaseVertex v) throws Exception{

    }

    @Override
    public void writeHeader() throws IOException{

    }

    @Override
    public void close(){

    }
}
