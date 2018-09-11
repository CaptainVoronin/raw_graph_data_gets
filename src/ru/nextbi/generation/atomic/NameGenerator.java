package ru.nextbi.generation.atomic;

import ru.nextbi.GTDGenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NameGenerator extends BaseDictionaryGenerator
{
    static final String maleNameDict = "male-names";
    static final String maleSurameDict = "male-surnames";
    static final String femaleNameDict = "female-names";
    static final String femaleSurameDict = "female-surnames";
    static final String CASH_KEY = "noCash";

    Dictionary maleNames, maleSurNames, femaleNames, femaleSurNames;
    //String maleNamesFile, maleSurNamesFile, femaleNamesFile, femaleSurNamesFile;
    boolean maleNamesNoCash, maleSurNamesNoCash, femaleNamesNoCash, femaleSurnamesNoCash;
    boolean noCashDefault;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception
    {
        String currentDir = config.get(GTDGenerator.CURRENT_DIR_KEY);

        if( config.get( GTDGenerator.CASH_DEFAULT ) != null )
            noCashDefault = !Boolean.parseBoolean( config.get( GTDGenerator.CASH_DEFAULT ) );
        else
            noCashDefault = false;

        String buff = params.get( CASH_KEY );

        Map<String, Boolean> noCashFlags;
        if( buff == null ) {
            noCashFlags = new HashMap<>();
            noCashFlags.put( maleNameDict, noCashDefault );
            noCashFlags.put( maleSurameDict, noCashDefault );
            noCashFlags.put( femaleSurameDict, noCashDefault);
            noCashFlags.put( femaleNameDict, noCashDefault );
        }
        else
            noCashFlags = parseCashParam( buff );

        String[] dNames = { maleNameDict, maleSurameDict, femaleNameDict, femaleSurameDict};

        for( String name : dNames )
        {
            buff = params.get(name);
            if (buff == null)
                throw new Exception("Dictionary for " + name + " is not set");
            String filename = GeneratorUtils.makeAbsolutePath( currentDir, buff);
            Dictionary d = new Dictionary( filename, noCashFlags.get( name ) );
            addDictionary( name, d );
        }
    }

    private Map<String, Boolean> parseCashParam(String buff){

        Map<String, Boolean> noCashFlags = new HashMap<>();

        String[] items = buff.split( ",");

        // Кэшировать все словри
        if( items[0].trim().equals( "all" ) )
            maleNamesNoCash = maleSurNamesNoCash = femaleNamesNoCash = femaleSurnamesNoCash = false;
        else if( items[0].trim().equals( "none" ) ) // не кэшировать ни одного
            maleNamesNoCash = maleSurNamesNoCash = femaleNamesNoCash = femaleSurnamesNoCash = true;
        else
        {
            maleNamesNoCash = maleSurNamesNoCash = femaleNamesNoCash = femaleSurnamesNoCash = noCashDefault;
            for( String key : items )
                if( key.trim().equals( maleNameDict ) )
                    maleNamesNoCash = false;
                else if( key.trim().equals( maleSurameDict ) )
                    maleSurNamesNoCash = false;
                else if( key.trim().equals( femaleNameDict ) )
                    femaleNamesNoCash = false;
                else if( key.trim().equals( femaleSurameDict ) )
                    femaleSurnamesNoCash = false;
                else
                {
                    System.out.println( "Unknown noCash directive " + key.trim() );
                }
        }
        noCashFlags.put( maleNameDict, maleNamesNoCash );
        noCashFlags.put( maleSurameDict, maleSurNamesNoCash );
        noCashFlags.put( femaleSurameDict, femaleSurnamesNoCash);
        noCashFlags.put( femaleNameDict, femaleNamesNoCash );
        return noCashFlags;
    }

    @Override
    public String getValue() throws IOException, ProviderNotInitiaqlizedException{
        Dictionary names, surnames;
        if( !getGender() )
        {
            names = getDictionary( femaleNameDict );
            surnames = getDictionary( femaleSurameDict );
        }
        else
        {
            names = getDictionary( maleNameDict );
            surnames = getDictionary( maleSurameDict );
        }

        String name = names.getRndValue(  );
        String surname = surnames.getRndValue( );
        return name + " " + surname;
    }

    private boolean getGender()
    {
        int res = IntGenerator.getInt( 0, 5 );
        return res <= 2;
    }
}
