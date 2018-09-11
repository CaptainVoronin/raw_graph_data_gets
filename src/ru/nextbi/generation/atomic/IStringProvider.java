package ru.nextbi.generation.atomic;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStringProvider{
    int getSize();
    String getString( int index ) throws IOException;
    void  init() throws IOException;
    void close();
}
