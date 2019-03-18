package ru.nextbi.writers;

import ru.nextbi.model.BaseVertex;

import java.io.IOException;

public interface IVertextWriter{
    void writeElement( BaseVertex v) throws Exception;
    void writeHeader() throws IOException;
    void close();
}
