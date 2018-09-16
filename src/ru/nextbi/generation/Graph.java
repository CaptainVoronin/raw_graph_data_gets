package ru.nextbi.generation;

import ru.nextbi.model.BaseEdge;
import ru.nextbi.model.BaseTransitEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Graph {

    /**
     * IDы всех вершин
     */
    Map<String, List<String>> verticesIDs;
    Map<String, List<BaseEdge>> edges;
    Map< String, AtomicInteger > counters;
    public Graph ()
    {
        verticesIDs = new HashMap<>();
        edges = new HashMap<>();
        counters = new HashMap< String, AtomicInteger >();
    }

    public void addVertexID( String type, String id )
    {
        List<String> vts = verticesIDs.get( type );
        if( vts == null ) {
            vts = new ArrayList<String>();
            verticesIDs.put( type, vts );
            counters.put( type, new AtomicInteger( 0 ) );
        }
        vts.add( id );
    }

    public void addEdge( String type, BaseTransitEdge e )
    {
        List<BaseEdge> vts = edges.get( type );

        if( vts == null ) {
            vts = new ArrayList<BaseEdge>();
            edges.put( type, vts );
        }
        vts.add( e );
    }

    public String getNextIdFor( String className )
    {
        AtomicInteger index = counters.get( className );
        List<String> ids = getVerticesIDList( className );
        return ids.get( index.getAndIncrement() );
    }

    public List<String> getVerticesIDList(String className )
    {
        return verticesIDs.get( className );
    }

    public List<BaseEdge> getEdges( String className )
    {
        return edges.get( className );
    }

    public static class ParentChild
    {
        public String parent;
        public String child;
    }

}
