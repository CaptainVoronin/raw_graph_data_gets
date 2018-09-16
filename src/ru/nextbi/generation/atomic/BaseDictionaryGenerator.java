package ru.nextbi.generation.atomic;

import ru.nextbi.GTDGenerator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseDictionaryGenerator extends AbstractGenerator
{
    public static String CASH_KEY = "cash";

    boolean noCash;

    Map<String, Dictionary> dictionaryMap;

    public BaseDictionaryGenerator()
    {
        dictionaryMap = new HashMap<>();
    }

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception {

        String buff = params.get( CASH_KEY );
        if( buff != null )
            noCash = !Boolean.parseBoolean( buff );
        else if( config.containsKey( GTDGenerator.CASH_DEFAULT ) )
            noCash = !Boolean.parseBoolean( config.get( GTDGenerator.CASH_DEFAULT ) );
        else
            noCash = false;
    }

    public final boolean noCash() {
        return noCash;
    }

    protected final Dictionary getDictionary( String name )
    {
        return dictionaryMap.get( name );
    }

    protected final Dictionary getDictionary( ) throws Exception {
        Dictionary d = null;

        if( dictionaryMap.keySet().size() > 1 )
            throw new Exception( "Ambiguity. Don't know which dictionary must be chosen");

        if( dictionaryMap.keySet().size() == 0 )
            throw new Exception( "There is no dictionary");

        for( Dictionary dd : dictionaryMap.values() )
            d = dd;

        return d;
    }

    protected final void addDictionary( String name, Dictionary d ) throws IOException {

        if( dictionaryMap.containsKey( name ) )
        {
            Dictionary old = dictionaryMap.get( name );
            old.close();
        }

        dictionaryMap.put( name, d );
    }

    protected final Dictionary addDictionary( String path ) throws IOException {
        Dictionary d = new Dictionary( path, noCash() );
        if( dictionaryMap.containsKey( path ) )
        {
            Dictionary old = dictionaryMap.get( path );
            old.close();
        }

        dictionaryMap.put( path, d );
        return d;
    }

    @Override
    public void initialize() throws Exception {
        for( Dictionary d : dictionaryMap.values() )
            d.initialize();
    }

    @Override
    public void unInialize() throws IOException {
        for( Dictionary d : dictionaryMap.values() )
            d.close();
    }
}
