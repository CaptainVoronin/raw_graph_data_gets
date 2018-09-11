package ru.nextbi.generation.atomic;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStringProvider{
    int getSize() throws ProviderNotInitiaqlizedException;
    String getString( int index ) throws IOException, ProviderNotInitiaqlizedException;
    void  init() throws IOException;
    void close();
}
