package ru.nextbi.model;

public abstract class BaseEdge extends GraphElement
{
    public enum EdgeKind { TRANSIT, LINK };

    String from;
    String to;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BaseEdge(String from, String to)
    {
        super( ELEMENT_TYPE.EDGE );
        this.from = from;
        this.to = to;
    }

    @Override
    public ELEMENT_TYPE getType()
    {
        return ELEMENT_TYPE.EDGE;
    }

    public final String getFrom()
    {
        return from;
    }

    public final String getTo()
    {
        return to;
    }

    public EdgeKind getEdgeKind()
    {
        return EdgeKind.LINK;
    }
}
