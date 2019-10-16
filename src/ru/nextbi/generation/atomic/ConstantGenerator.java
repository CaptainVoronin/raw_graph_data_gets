package ru.nextbi.generation.atomic;

import ru.nextbi.generation.GraphObjectProperty;

import java.io.IOException;
import java.util.Map;

/**
 * Формат описния
 * prop : <name> constant "<value>"
 */
public class ConstantGenerator implements IGenerator{
    @Override
    public void initialize() throws Exception{

    }

    @Override
    public void unInialize() throws IOException{

    }

    String value;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{
        value = params.get( "value" );
        if( value == null )
            value = "value";
    }

    @Override
    public String getValue() throws Exception{
        return value;
    }

    @Override
    public GraphObjectProperty.Type getDataType(){
        return GraphObjectProperty.Type.VARCHAR;
    }
}
