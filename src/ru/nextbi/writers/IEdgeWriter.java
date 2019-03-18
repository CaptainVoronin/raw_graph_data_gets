package ru.nextbi.writers;

import ru.nextbi.model.BaseEdge;

import java.io.IOException;

public interface IEdgeWriter{
    void writeElement( BaseEdge edge ) throws Exception;
    void writeHeader( ) throws IOException;
    void close();
}
