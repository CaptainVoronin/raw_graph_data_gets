package ru.nextbi.generation.atomic;

import ru.nextbi.generation.GraphObjectProperty;

import java.io.IOException;
import java.util.Map;

/**
 * Формат описния
 * prop : <name> constant "<value>"
 */
public class ConstantGenerator implements IGenerator{

    final String value;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{
        value = params.get( "constant" );
    }

    @Override
    public String getValue() throws Exception{
        return value;
    }

    @Override
    public GraphObjectProperty.Type getDataType(){
        return return GraphObjectProperty.Type.VARCHAR;
    }
}
