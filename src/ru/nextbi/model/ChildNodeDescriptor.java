package ru.nextbi.model;

public class ChildNodeDescriptor
{
    public String childClassName;
    public int min;
    public int max;

    public ChildNodeDescriptor()
    {
        // Это будет считаться неинициализированными значениями
        min = -1;
        max = -1;
    }
}
