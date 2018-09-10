package ru.nextbi.generation.atomic;

import java.util.Map;

public class LongIDGenerator extends AbstractGenerator{
    long initial;
    long current;
    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{
        initial = 0L;
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
