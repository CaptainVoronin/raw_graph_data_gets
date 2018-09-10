package ru.nextbi.generation.atomic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dictionary
{
    boolean noCash;
    String filename;
    List<String> items;
    int size;
    boolean initalized;
    RandomAccessFile file;

    /**
     * Конструктор для файлового источника словаря
     * @param filename имя файла словаря
     * @param noCash если true, то не кэшировать словарь в памяти
     */
    public Dictionary( String filename, boolean noCash )
    {
        this.filename = filename;
        this.noCash = noCash;
        file = null;
        initalized = false;
    }

    /**
     * Конструктор для словаря в памяти
     * @param items элементы словаря
     */
    public Dictionary( List<String> items )
    {
        this.filename = null;
        this.noCash = false;
        file = null;
        this.items = new ArrayList<>( items );
        size = this.items.size();
        initalized = true;
    }

    public int getSize() throws DictionaryNotInitiaqlizedException
    {
        if( !initalized )
            throw new DictionaryNotInitiaqlizedException();
        return size;
    }

    public String getValue( int index ) throws DictionaryNotInitiaqlizedException, IOException{
        if( !initalized && items == null )
            throw new DictionaryNotInitiaqlizedException();

        return innerGetValue( index );
    }

    public String getRndValue() throws DictionaryNotInitiaqlizedException, IOException{

        return getValue( IntGenerator.getInt( 0, getSize() - 1 ) );
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
            return items.get( index ).trim();
    }

    /**
     * Возвращает строку из файла с указанным индексом
     * @param index - номер строки в файле
     * @return - найденную строку или null, если индекс косой
     * @throws IOException
     */
    private String getFromFile(int index) throws IOException{
        if( file == null  )
            file = new RandomAccessFile( filename, "r" );


        int i = 0;
        String line = null;

        while( ( line = file.readLine() ) != null )
        {
            if( i == index )
                break;
            else
                line = null;
            i++;
        }

        file.seek( 0L );

        if( line == null )
            throw new IndexOutOfBoundsException();

        return line.trim();
    }

    public void initialize() throws IOException{

        // Словарь может быть инициализирован еще в конструкторе
        if( initalized )
            return;

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
        File f = new File( filename );
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
        if( file != null ) {
            try {
                file.close();
            }finally {
                file = null;
            }
        }
    }

    @Override
    protected void finalize() throws Throwable{
        if( file != null )
            file.close();
    }
}
