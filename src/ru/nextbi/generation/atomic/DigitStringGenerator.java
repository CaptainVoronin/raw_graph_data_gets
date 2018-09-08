package ru.nextbi.generation.atomic;

import java.util.Map;

public class DigitStringGenerator implements IGenerator
{

    int length;

    public DigitStringGenerator()
    {
        length = 10;
    }

    @Override
    public void setParamString(String rawParams) throws Exception{
        Map<String,String> params = GeneratorUtils.parseParams( rawParams );
        if( params.containsKey( "length" ) )
        {
            length = Integer.parseInt( params.get( "length" ) );
        }
    }

    @Override
    public String getValue()
    {
        return StringGenerator.randomNumeric( length );
    }
}
