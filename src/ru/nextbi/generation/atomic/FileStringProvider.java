package ru.nextbi.generation.atomic;

import java.io.*;

public class FileStringProvider implements  IStringProvider{

    String filename;
    RandomAccessFile file;
    int size;
    boolean initalized;

    public FileStringProvider( String filename )
    {
        this.filename = filename;
        size = 0;
        initalized = false;
    }

    @Override
    public int getSize() throws ProviderNotInitiaqlizedException{
        if( !initalized )
            throw new ProviderNotInitiaqlizedException();

        return size;
    }

    @Override
    public String getString(int index) throws IOException, ProviderNotInitiaqlizedException{
        if( !initalized )
            throw new ProviderNotInitiaqlizedException();

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

    @Override
    public void init() throws IOException{
        File f = new File( filename );
        BufferedReader reader = new BufferedReader(new FileReader( filename ));
        while (reader.readLine() != null) size++;
        reader.close();
    }

    @Override
    public void close(){
        if( file != null )
            try {
                file.close();
            } catch( IOException e ) {
                e.printStackTrace();
            } finally {
                file = null;
            }
    }
    }
