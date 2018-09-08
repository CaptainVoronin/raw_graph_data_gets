package ru.nextbi.generation;

import javafx.util.Pair;
import ru.nextbi.generation.atomic.IGenerator;
import ru.nextbi.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TEdgeGenerator {

    static Random rand = new Random();

    protected TEdgeGenerator(){};

    public static final void generate(Graph graph, GraphModel model, TEdgeDescription ted, HashMap< String, IGenerator> generators ) throws Exception
    {
        List<BaseVertex> fromList = graph.getVertices( ted.getFromVertex() );
        List<BaseVertex> toList = graph.getVertices( ted.getToVertex() );

        // Бежим по всем вершинам from и добавляем ребра
        for( BaseVertex vfrom : fromList )
        {
            BaseVertex vto = getRandomVertex( toList, vfrom );

            Pair<Integer, Integer> pair = VertexGenerator.getRange(ted.getMin(), ted.getMax());

            for( int i = pair.getKey().intValue(); i < pair.getValue().intValue(); i++ ) {

                HashMap<String, String> tedp = VertexGenerator.generateProps(generators, ted);

                BaseTransitEdge edge = new BaseTransitEdge(vfrom, vto);
                edge.setProperties(tedp);
                // Добавляем в граф
                graph.addEdge(ted.getClassName(), edge);
            }
        }
    }

    private static BaseVertex getRandomVertex( List<BaseVertex> array, BaseVertex notEqual )
    {
        BaseVertex v;
        do {
            v = getRandomElement( array );
        } while( v == notEqual );
        return v;
    }

    private static BaseVertex getRandomElement(List<BaseVertex> array)
    {
        if( array.size() < 2 )
            throw new IllegalArgumentException( "List size too small " );

        int index = rand.nextInt( array.size() );

        return array.get( index );
    }
}
