package ru.nextbi.model;

public class SimpleEdge
{
    public VertexDescription  source;
    public VertexDescription  target;

    public SimpleEdge( VertexDescription src, VertexDescription trg )
    {
        source = src;
        target = trg;
    }

    public String getName()
    {
        return "link_" + source.getClassName() + "_" + target.getClassName();
    }

}
