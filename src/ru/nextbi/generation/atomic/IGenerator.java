package ru.nextbi.generation.atomic;

import java.io.IOException;
import java.util.Map;

public interface IGenerator
{
    void setParams(Map<String, String> config, Map<String, String> params) throws Exception;
    String getValue() throws IOException, DictionaryNotInitiaqlizedException;
    void initialize() throws Exception;
    void unInialize();
}
