package ru.nextbi.generation.atomic;

import java.io.*;
import java.util.List;

public class Dictionary
{
    boolean noCash;

    IStringProvider provider;
    /**
     * Конструктор для файлового источника словаря
     * @param filename имя файла словаря
     * @param noCash если true, то не кэшировать словарь в памяти
     */
    public Dictionary( String filename, boolean noCash ) throws IOException{

        if( noCash ) {
            provider = new FileStringProvider(filename);
        }
        else {
            provider = new InMemoryStringProvider( GeneratorUtils.readDictionary( filename ) );
        }
    }

    /**
     * Конструктор для словаря в памяти
     * @param items элементы словаря
     */
    public Dictionary( List<String> items )
    {
        provider = new InMemoryStringProvider( items );
    }

    public int getSize() throws ProviderNotInitiaqlizedException
    {
        return provider.getSize();
    }

    public String getValue( int index ) throws ProviderNotInitiaqlizedException, IOException{
        return provider.getString( index );
    }

    public String getRndValue() throws ProviderNotInitiaqlizedException, IOException{

        return getValue( IntGenerator.getInt( 0, getSize() - 1 ) );
    }

    public void initialize() throws IOException{
        provider.init();
    }


    public void close() throws IOException{
        if( provider != null ) {
            provider.close();
            provider = null;
        }
    }

    @Override
    protected void finalize() throws Throwable{
        if( provider != null )
            provider.close();
    }
}
