package ru.nextbi.generation.atomic;

import ru.nextbi.GTDGenerator;

import java.io.IOException;
import java.util.Map;

public abstract class BaseDictionaryGenerator extends AbstractGenerator
{
    public static String CASH_KEY = "cash";

    boolean noCash;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception {

        String buff = params.get( "noCash" );
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

}
