package ru.nextbi.writers.mongo;

import ru.nextbi.model.GraphElementDescription;
import ru.nextbi.writers.IEdgeWriter;
import ru.nextbi.writers.ILinkWriter;
import ru.nextbi.writers.IVertexWriter;
import ru.nextbi.writers.IWriterFactory;

import java.io.IOException;
import java.util.Map;

public class MongoWritersFactory implements IWriterFactory{

    public MongoWritersFactory( Map<String, String> params )
    {

    }

    @Override
    public IVertexWriter createVertexWriter(GraphElementDescription vd) throws IOException{
        return null;
    }

    @Override
    public IEdgeWriter createEdgeWriter(GraphElementDescription vd){
        return null;
    }

    @Override
    public ILinkWriter createLinkWriter(String parentClassName, String className){
        return null;
    }
}
