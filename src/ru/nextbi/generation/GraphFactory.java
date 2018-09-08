package ru.nextbi.generation;

import javafx.util.Pair;
import ru.nextbi.generation.atomic.IGenerator;
import ru.nextbi.model.BaseVertex;
import ru.nextbi.model.GraphModel;
import ru.nextbi.model.TEdgeDescription;
import ru.nextbi.model.VertexDescription;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GraphFactory
{
    protected GraphFactory(){}

    public static final Graph createGraph(GraphModel model, HashMap< String, IGenerator> generators ) throws Exception
    {
        Graph graph = new Graph();

        // Сначала генераятся все вершины
        Set<String> classes = model.getFlatVertexDescriptions().keySet();
        System.out.println( "Generating vertices" );
        for( String key : classes )
        {
            VertexDescription desc = model.getVertexDescription( key );

            // Отсюда генерятся только вершины без родителей
            if ( desc.getParentClassName() != null )
                continue;

            Pair<Integer, Integer> pair = VertexGenerator.getRange( desc.getMin(), desc.getMax() );

            for( int i = pair.getKey().intValue(); i <= pair.getValue().intValue(); i++ )
                VertexGenerator.generate( graph, model, desc, null, generators );
        }
        System.out.println( "Done" );

        System.out.println( "Resolving links" );

        // Потом разрешаются все всязи типа posession
        resolveLinks( graph, model );

        System.out.println( "Done" );

        System.out.println( "Generating edges" );
        // Потом генерятся все ребра
        classes = model.getTEdgeDescriptionFlatList().keySet();

        for( String key : classes )
        {
            TEdgeDescription ted = model.getTEdgeDescription( key );

            Pair<Integer, Integer> pair = VertexGenerator.getRange( ted.getMin(), ted.getMax() );

            for( int i = pair.getKey().intValue(); i <= pair.getValue().intValue(); i++ )
                TEdgeGenerator.generate( graph, model, ted, generators );
        }
        System.out.println( "Done" );
        return graph;
    }

    private static void resolveLinks(Graph graph, GraphModel model){
        Set<String> classes = model.getFlatVertexDescriptions().keySet();

        /**
         * Имена классов вершин, на которые надо поставить ссылки
         */
        List<String> links;

        // Идкм по всем классам вершин, расставляем линки от них
        for( String key : classes )
        {
            // Взять описание вершины
            VertexDescription vd = model.getVertexDescription( key );
            if( ( links = vd.getPosession() ).size() == 0 )
                continue; // от данного класса вершин ссылок нет

            // есть ссылки, берем все экземпляры вершин этого класса
            List<BaseVertex> linked = graph.getVertices( vd.getClassName() );

            // И идем по ним
            for( BaseVertex vx : linked ) {
                for( String className : links ) {
                    List<BaseVertex> targets = graph.getVertices(className);
                    BaseVertex target = targets.get( 0 );
                    vx.addPosessor( className, target.getId() );
                }
            }
        }
    }
}
