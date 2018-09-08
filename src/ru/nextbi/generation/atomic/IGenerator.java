package ru.nextbi.generation.atomic;

import java.text.ParseException;

public interface IGenerator
{
    void setParamString( String rawParams) throws Exception;
    String getValue();
}
