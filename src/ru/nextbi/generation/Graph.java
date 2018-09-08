package ru.nextbi.generation;

import ru.nextbi.model.BaseEdge;
import ru.nextbi.model.BaseTransitEdge;
import ru.nextbi.model.BaseVertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    Map<String, List<BaseVertex>> vertices;
    Map<String, List<BaseEdge>> edges;

    public Graph ()
    {
        vertices = new HashMap<>();
        edges = new HashMap<>();
    };

    public void addVertex( String type, BaseVertex v )
    {
        List<BaseVertex> vts = vertices.get( type );
        if( vts == null ) {
            vts = new ArrayList<BaseVertex>();
            vertices.put( type, vts );
        }

        vts.add( v );
    }

    public void addEdge( String type, BaseTransitEdge e )
    {
        List<BaseEdge> vts = edges.get( type );

        if( vts == null ) {
            vts = new ArrayList<BaseEdge>();
            edges.put( type, vts );
        }
        vts.add( e );
    };

    public List<BaseVertex> getVertices( String className )
    {
        return vertices.get( className );
    }

    public List<BaseEdge> getEdges( String className )
    {
        return edges.get( className );
    }

}
