package ru.nextbi.generation.atomic;

import ru.nextbi.GTDGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class NameGenerator implements IGenerator
{
    static final String maleNameDict = "male-names";
    static final String maleSurameDict = "male-surnames";
    static final String femaleNameDict = "female-names";
    static final String femaleSurameDict = "female-surnames";
    static final String CASH_KEY = "noCash";

    Dictionary maleNames, maleSurNames, femaleNames, femaleSurNames;
    String maleNamesFile, maleSurNamesFile, femaleNamesFile, femaleSurNamesFile;
    boolean maleNamesNoCash, maleSurNamesNoCash, femaleNamesNoCash, femaleSurNamesNoCash;
    boolean noCashDefault;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception
    {
        String currentDir = config.get(GTDGenerator.CURRENT_DIR_KEY);

        if( config.get( GTDGenerator.CASH_DEFAULT ) != null )
            noCashDefault = !Boolean.parseBoolean( config.get( GTDGenerator.CASH_DEFAULT ) );
        else
            noCashDefault = false;

        String buff = params.get( maleNameDict );
        if( buff == null )
            throw new Exception ("Male names dictionary is not set" );

        maleNamesFile = GeneratorUtils.makeAbsolutePath( currentDir, buff );

        buff = params.get( maleSurameDict );

        if( buff == null )
            throw new Exception ("Male surnames dictionary is not set" );

        maleSurNamesFile = GeneratorUtils.makeAbsolutePath( currentDir, buff );

        buff = params.get( femaleNameDict );
        if( buff == null )
            throw new Exception ( "Female names dictionary is not set" );

        femaleNamesFile = GeneratorUtils.makeAbsolutePath( currentDir, buff );

        buff = params.get( femaleSurameDict );
        if( buff == null )
            throw new Exception ( "Female surnames dictionary is not set" );

        femaleSurNamesFile = GeneratorUtils.makeAbsolutePath( currentDir, buff );

        buff = params.get( CASH_KEY );

        if( buff == null ) {
            maleNamesNoCash = maleSurNamesNoCash = femaleNamesNoCash = femaleSurNamesNoCash = noCashDefault;
        }
        else
            parseCashParam( buff );
    }

    private void parseCashParam(String buff){
        String[] items = buff.split( ",");

        // Кэшировать все словри
        if( items[0].trim().equals( "all" ) )
            maleNamesNoCash = maleSurNamesNoCash = femaleNamesNoCash = femaleSurNamesNoCash = false;
        else if( items[0].trim().equals( "none" ) ) // не кэшировать ни одного
            maleNamesNoCash = maleSurNamesNoCash = femaleNamesNoCash = femaleSurNamesNoCash = true;
        else
        {
            maleNamesNoCash = maleSurNamesNoCash = femaleNamesNoCash = femaleSurNamesNoCash = noCashDefault;
            for( String key : items )
                if( key.trim().equals( maleNameDict ) )
                    maleNamesNoCash = false;
                else if( key.trim().equals( maleSurameDict ) )
                    maleSurNamesNoCash = false;
                else if( key.trim().equals( femaleNameDict ) )
                    femaleNamesNoCash = false;
                else if( key.trim().equals( femaleSurameDict ) )
                    femaleSurNamesNoCash = false;
                else
                {
                    System.out.println( "Unknown noCash directive " + key.trim() );
                }
        }

    }

    @Override
    public String getValue() throws IOException, DictionaryNotInitiaqlizedException{
        Dictionary names, surnames;
        if( !getGender() )
        {
            names = femaleNames;
            surnames = femaleSurNames;
        }
        else
        {
            names = maleNames;
            surnames = maleSurNames;
        }

        String name = names.getRndValue(  );
        String surname = surnames.getRndValue( );
        return name + " " + surname;
    }

    @Override
    public void initialize() throws Exception{
        maleNames = new Dictionary( maleNamesFile, maleNamesNoCash );
        maleNames.initialize();
        maleSurNames = new Dictionary( maleSurNamesFile, maleSurNamesNoCash );
        maleSurNames.initialize();
        femaleNames = new Dictionary( femaleNamesFile, femaleNamesNoCash );
        femaleNames.initialize();
        femaleSurNames = new Dictionary( femaleSurNamesFile, femaleSurNamesNoCash );
        femaleSurNames.initialize();
    }

    @Override
    public void unInialize()
    {
        try {
            if( maleNames != null )
                maleNames.close();

            if( maleSurNames != null )
                maleSurNames.close();

            if( femaleNames != null )
                femaleNames.close();

            if( femaleSurNames != null )
                femaleSurNames.close();

        }catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    private boolean getGender()
    {
        int res = IntGenerator.getInt( 0, 5 );
        return res <= 2;
    }


}
