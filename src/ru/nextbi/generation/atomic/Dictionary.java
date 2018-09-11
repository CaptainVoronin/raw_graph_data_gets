package ru.nextbi.generation.atomic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dictionary
{
    boolean noCash;
    boolean initalized;
    IStringProvider provider;
    /**
     * Конструктор для файлового источника словаря
     * @param filename имя файла словаря
     * @param noCash если true, то не кэшировать словарь в памяти
     */
    public Dictionary( String filename, boolean noCash ) throws IOException{

        if( noCash ) {
            provider = new FileStringProvider(filename);
            initalized = false;
        }
        else {
            provider = new InMemoryStringProvider( GeneratorUtils.readDictionary( filename ) );
            initalized = true;
        }
    }

    /**
     * Конструктор для словаря в памяти
     * @param items элементы словаря
     */
    public Dictionary( List<String> items )
    {
        provider = new InMemoryStringProvider( items );
        initalized = true;
    }

    public int getSize() throws DictionaryNotInitiaqlizedException
    {
        if( !initalized )
            throw new DictionaryNotInitiaqlizedException();
        return provider.getSize();
    }

    public String getValue( int index ) throws DictionaryNotInitiaqlizedException, IOException{
        if( !initalized && provider == null )
            throw new DictionaryNotInitiaqlizedException();

        return provider.getString( index );
    }

    public String getRndValue() throws DictionaryNotInitiaqlizedException, IOException{

        return getValue( IntGenerator.getInt( 0, getSize() - 1 ) );
    }

    public void initialize() throws IOException{

        // Словарь может быть инициализирован еще в конструкторе
        if( initalized )
            return;

        provider.init();

        initalized = true;
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
