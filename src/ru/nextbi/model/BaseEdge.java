package ru.nextbi.model;

public abstract class BaseEdge extends GraphElement
{
    public enum EdgeKind { TRANSIT, LINK };

    BaseVertex from;
    BaseVertex to;

    public void setFrom(BaseVertex from) {
        this.from = from;
    }

    public void setTo(BaseVertex to) {
        this.to = to;
    }

    public BaseEdge(BaseVertex from, BaseVertex to)
    {
        this.from = from;
        this.to = to;
    }

    public ELEMENT_TYPE getElementType()
    {
        return ELEMENT_TYPE.EDGE;
    }

    public final BaseVertex getFrom()
    {
        return from;
    }

    public final BaseVertex getTo()
    {
        return to;
    }

    public EdgeKind getEdgeKind()
    {
        return EdgeKind.LINK;
    }
}
