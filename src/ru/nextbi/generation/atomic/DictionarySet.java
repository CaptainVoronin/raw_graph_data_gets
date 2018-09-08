package ru.nextbi.generation.atomic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DictionarySet implements IGenerator
{
    String dictionary;
    boolean cashe;
    List<String> items;
    public DictionarySet()
    {
        cashe = true;
    }

    @Override
    public void setParamString( String rawParams ) throws Exception{
        Map<String, String> params = GeneratorUtils.parseParams( rawParams );
        String path = params.get( "dictionary" );
        String buff = params.get( "cashe" );
        if( buff!= null )
        {
            cashe = Boolean.parseBoolean( buff );
            System.out.println( "Parameter cache does not supported. Ignored" );
        }

        if( path != null )
        {
            File f = new File( path );
            if( !f.exists() )
                throw new ParseException( "Dictionary file not found", 0 );

            if( !f.isFile() )
                throw new ParseException( "Dictionary is not a file", 0 );
            dictionary = path;
            readItems();
        }
    }

    void readItems( ) throws Exception
    {
        BufferedReader br = new BufferedReader( new FileReader( dictionary ) );
        String buff;
        items = new ArrayList<>();
        while( ( buff = br.readLine() ) != null  )
            items.add( buff );
        br.close();
    }

    @Override
    public String getValue()
    {
        if ( items != null )
            return items.get( RandomNumberGenerator.getInt( 0, items.size() - 1 ));
        else
            return StringGenerator.randomAlphaNumeric( 10 );
    }
}