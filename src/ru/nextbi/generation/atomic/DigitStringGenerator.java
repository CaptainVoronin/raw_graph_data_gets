package ru.nextbi.generation.atomic;

import ru.nextbi.generation.GraphObjectProperty;

import java.util.Map;

public class DigitStringGenerator implements IGenerator
{

    int length;

    public DigitStringGenerator()
    {
        length = 10;
    }

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{
        if( params.containsKey( "length" ) )
            length = Integer.parseInt( params.get( "length" ) );
    }

    @Override
    public String getValue()
    {
        return StringGenerator.randomNumeric( length );
    }

    @Override
    public void initialize() throws Exception{

    }

    @Override
    public void unInialize(){

    }

    @Override
    public GraphObjectProperty.Type getDataType() {
        return GraphObjectProperty.Type.VARCHAR;
    }
}
