package ru.nextbi.generation.atomic;

import ru.nextbi.generation.GraphObjectProperty;

import java.util.Map;
import java.util.UUID;

public class UUIDIDGenerator extends AbstractGenerator{
    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{

    }

    @Override
    public String getValue(){
        return UUID.randomUUID().toString();
    }

    @Override
    public void initialize() throws Exception{

    }

    @Override
    public GraphObjectProperty.Type getDataType() {
        return GraphObjectProperty.Type.UUID;
    }
}
