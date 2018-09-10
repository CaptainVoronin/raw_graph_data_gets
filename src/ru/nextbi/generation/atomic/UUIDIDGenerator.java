package ru.nextbi.generation.atomic;

import java.util.Map;
import java.util.UUID;

public class UUIDIDGenerator implements  IIDGenerator {
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
}
