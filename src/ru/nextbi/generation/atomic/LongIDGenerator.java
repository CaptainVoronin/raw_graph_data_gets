package ru.nextbi.generation.atomic;

import java.util.Map;

public class LongIDGenerator implements IIDGenerator {
    long initial;
    long current;
    @Override
    public void setParamString(String rawParams) throws Exception{
        initial = 0L;
        Map< String, String > params = GeneratorUtils.parseParams( rawParams );
        String buff;
        if( ( buff = params.get( "initial" ) ) != null )
            initial = Long.parseLong(buff);

        current = initial;
    }

    @Override
    public String getValue(){
        return Long.toString( ++current );
    }
}
