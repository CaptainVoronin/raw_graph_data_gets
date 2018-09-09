package ru.nextbi.generation.atomic;

import java.util.Map;

public interface IGenerator
{
    void setParams(Map<String, String> config, Map<String, String> params) throws Exception;
    String getValue();
}
