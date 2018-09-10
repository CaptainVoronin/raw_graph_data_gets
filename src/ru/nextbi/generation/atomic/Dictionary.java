package ru.nextbi.generation.atomic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary
{
    boolean noCash;
    String filename;
    List<String> items;
    int size;
    boolean initalized;
    BufferedReader file;

    public Dictionary( String filename, boolean noCash)
    {
        this.filename = filename;
        this.noCash = noCash;
        file = null;
        initalized = false;
    }

    public Dictionary( List<String> items )
    {
        this.filename = null;
        this.noCash = false;
        file = null;
        this.items = new ArrayList<>( items );
        initalized = true;
    }

    public int getSize() throws DictionaryNotInitiaqlizedException
    {
        if( initalized )
            throw new DictionaryNotInitiaqlizedException();
        return size;
    }

    public String getValue( int index ) throws DictionaryNotInitiaqlizedException, IOException{
        if( !initalized && items == null )
            throw new DictionaryNotInitiaqlizedException();

        return innerGetValue( index );
    }

    public String getRndValue() throws DictionaryNotInitiaqlizedException, IOException{

        return getValue( IntGenerator.getInt( 0, size - 1 ) );
    }

    /**
     * Сделан для того, чтобы
     * @param index
     * @return
     */
    private String innerGetValue(int index) throws IOException{
        if( noCash )
            return getFromFile( index );
        else
            return items.get( index );
    }

    /**
     * Возвращает строку из файла с указанным индексом
     * @param index - номер строки в файле
     * @return - нейденную строку или null, если индекс косой
     * @throws IOException
     */
    private String getFromFile(int index) throws IOException{
        if( file == null )
            file = new BufferedReader(new FileReader( filename ));

        // Открутить на начало
        file.reset();

        int i = 0;
        String line = null;

        while( ( line = file.readLine() ) != null )
        {
            if( i == index )
                break;
            else
                line = null;
        }

        if( line == null )
            throw new IndexOutOfBoundsException();

        return line;
    }

    public void initialize() throws IOException{
        if( noCash )
            researchFile();
        else
            readCash();

        initalized = true;
    }

    /**
     * Просто считает строки в файле
     * @throws IOException
     */
    private void researchFile() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader( filename ));
        while (reader.readLine() != null) size++;
        reader.close();
    }

    /**
     * Читает содержимое файла в кэш
     * @throws IOException
     */
    private void readCash() throws IOException{
        items = GeneratorUtils.readDictionary( filename );
        size = items.size();
    }

    public void close() throws IOException{
        if( file != null )
            file.close();
    }

}
