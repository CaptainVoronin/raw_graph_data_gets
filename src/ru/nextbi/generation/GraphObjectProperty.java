package ru.nextbi.generation;

import java.util.Map;

public class GraphObjectProperty
{
    public static enum Type { INT, FLOAT, VARCHAR, TIMESTAMP, LONG, DATE, TIME, UUID }
    public String name;
    public Type type;
    public String generatorName;
    public Map<String, String> generatorParams;
    public String generatorID;
}
