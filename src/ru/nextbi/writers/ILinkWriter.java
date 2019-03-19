package ru.nextbi.writers;

import java.io.IOException;

public interface ILinkWriter{
    void init( ) throws IOException;
    void write( String parent, String child ) throws IOException;
    void close();
}
