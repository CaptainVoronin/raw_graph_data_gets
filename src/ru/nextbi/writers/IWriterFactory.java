package ru.nextbi.writers;

import ru.nextbi.model.GraphElementDescription;

import java.io.IOException;

public interface IWriterFactory{
    IVertexWriter createVertexWriter(GraphElementDescription vd ) throws IOException;
    IEdgeWriter createEdgeWriter(GraphElementDescription vd ) throws IOException;
    ILinkWriter createLinkWriter( String parentClassName, String className );
}
