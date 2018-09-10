package ru.nextbi.generation.atomic;

import ru.nextbi.GTDGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DictionarySet implements IGenerator
{
    Dictionary dictionary;
    boolean noCash;

    public DictionarySet()
    {
        noCash = true;
    }

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception
    {
        String buff = params.get( "noCash" );
        if( buff != null )
            noCash = !Boolean.parseBoolean( buff );
        else if( config.containsKey( GTDGenerator.CASH_DEFAULT ) )
                noCash = !Boolean.parseBoolean( config.get( GTDGenerator.CASH_DEFAULT ) );
        else
            noCash = false;


        String path = params.get( "dictionary" );
        if( path != null )
        {
            path = GeneratorUtils.makeAbsolutePath( config.get(GTDGenerator.CURRENT_DIR_KEY  ), path );
            dictionary = new Dictionary( path, noCash );
        }
    }

    @Override
    public String getValue() throws IOException, DictionaryNotInitiaqlizedException{
        return dictionary.getRndValue();
    }

    @Override
    public void initialize() throws Exception{
        dictionary.initialize();
    }

    @Override
    public void unInialize(){
        if( dictionary != null )
            try {
                dictionary.close();
            } catch( IOException e ) {
                e.printStackTrace();
            }
    }
}