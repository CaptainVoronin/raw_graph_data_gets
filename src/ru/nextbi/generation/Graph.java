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
    Map<String, List<ParentChild>> verticesIDs;
    Map<String, List<BaseEdge>> edges;
    Map< String, AtomicInteger > counters;
    public Graph ()
    {
        verticesIDs = new HashMap<>();
        edges = new HashMap<>();
        counters = new HashMap< String, AtomicInteger >();
    };

    public void addVertexID( String type, ParentChild pc  )
    {
        List<ParentChild> vts = verticesIDs.get( type );
        if( vts == null ) {
            vts = new ArrayList<ParentChild>();
            verticesIDs.put( type, vts );
            counters.put( type, new AtomicInteger( 0 ) );
        }
        vts.add( pc );
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

    public ParentChild getNextIdFor( String className )
    {
        AtomicInteger index = counters.get( className );
        List<ParentChild> ids = getVerticesIDList( className );
        ParentChild id = ids.get( index.getAndIncrement() );
        return id;
    }

    public List<ParentChild> getVerticesIDList(String className )
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
