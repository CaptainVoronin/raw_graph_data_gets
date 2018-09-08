package ru.nextbi.model;

public class BaseTransitEdge extends BaseEdge
{

    public BaseTransitEdge( BaseVertex src, BaseVertex dst  )
    {
        super( src, dst );
    }

    public EdgeKind getEdgeKind()
    {
        return EdgeKind.TRANSIT;
    }

}
