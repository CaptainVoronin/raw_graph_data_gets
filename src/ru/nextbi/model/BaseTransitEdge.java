package ru.nextbi.model;

public class BaseTransitEdge extends BaseEdge
{

    public BaseTransitEdge( String src, String dst  )
    {
        super( src, dst );
    }

    public EdgeKind getEdgeKind()
    {
        return EdgeKind.TRANSIT;
    }

}
