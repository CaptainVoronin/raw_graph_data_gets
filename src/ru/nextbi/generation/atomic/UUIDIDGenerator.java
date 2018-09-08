package ru.nextbi.generation.atomic;

import java.util.UUID;

public class UUIDIDGenerator implements  IIDGenerator {
    @Override
    public void setParamString(String rawParams) throws Exception{

    }

    @Override
    public String getValue(){
        return UUID.randomUUID().toString();
    }
}
