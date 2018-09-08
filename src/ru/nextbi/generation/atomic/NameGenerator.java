package ru.nextbi.generation.atomic;

import java.util.List;
import java.util.Map;

public class NameGenerator implements IGenerator
{
    static final String maleNameDict = "male-names";
    static final String maleSurameDict = "male-surnames";
    static final String femaleNameDict = "female-names";
    static final String femaleSurameDict = "female-surnames";

    List<String> maleNames;
    List<String> maleSurNames;

    List<String> femaleNames;
    List<String> femaleSurNames;

    @Override
    public void setParamString(String rawParams) throws Exception
    {
        Map<String, String> params = GeneratorUtils.parseParams( rawParams );
        String buff = params.get( maleNameDict );

        if( buff == null )
            throw new Exception ("Male names dictionary is not set" );

        maleNames = GeneratorUtils.readDictionary( buff );

        buff = params.get( maleSurameDict );

        if( buff == null )
            throw new Exception ("Male surnames dictionary is not set" );

        maleSurNames = GeneratorUtils.readDictionary( buff );

        buff = params.get( femaleNameDict );
        if( buff == null )
            throw new Exception ( "Female names dictionary is not set" );

        femaleNames = GeneratorUtils.readDictionary( buff );

        buff = params.get( femaleSurameDict );
        if( buff == null )
            throw new Exception ( "Female surnames dictionary is not set" );

        femaleSurNames = GeneratorUtils.readDictionary( buff );

    }

    @Override
    public String getValue(){
        List<String> names, surnames;
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

        String name = names.get( RandomNumberGenerator.getInt( 0, names.size() - 1 ) );
        String surname = surnames.get( RandomNumberGenerator.getInt( 0, surnames.size() - 1 ) );
        return name + " " + surname;
    }

    private boolean getGender()
    {
        int res = RandomNumberGenerator.getInt( 0, 5 );
        return res <= 2;
    }
}
