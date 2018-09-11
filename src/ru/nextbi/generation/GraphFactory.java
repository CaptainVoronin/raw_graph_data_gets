package ru.nextbi.generation;

import javafx.util.Pair;
import ru.nextbi.generation.atomic.IGenerator;
import ru.nextbi.generation.atomic.IntGenerator;
import ru.nextbi.model.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GraphFactory
{

    static int count = 0;

    protected GraphFactory(){}

    static void incCount()
    {
        count++;
        if( count >= 10000 )
        {
            System.out.println( "Generated " + count );
        }
    }
    public static final Graph createGraph(File dir, GraphModel model, HashMap< String, IGenerator> generators ) throws Exception
    {
        Graph graph = new Graph();

        // Сначала генераятся все вершины
        Set<String> classes = model.getVertexDescriptions().keySet();

        System.out.println( "Generating vertices IDs" );
        for( String key : classes )
        {
            VertexDescription desc = model.getVertexDescription( key );

            // Отсюда генерятся только вершины без родителей
            if ( desc.getParentClassName() != null )
                continue;

            Pair<Integer, Integer> pair = VertexGenerator.getRange( desc.getMin(), desc.getMax() );

            for( int i = pair.getKey().intValue(); i <= pair.getValue().intValue(); i++ )
                VertexGenerator.generateIDs( graph, model, desc, null, generators );
        }

        System.out.println( "Done" );

        // По готовым ID создаем и пишем экземпляры верш
        // ин
        System.out.println( "Generating vertices instances" );
        classes = model.getVertexDescriptions().keySet();

        for( String key : classes )
        {

            VertexDescription desc = model.getVertexDescription( key );

            // Берем IDы и идем по ним
            List<Graph.ParentChild> ids = graph.getVerticesIDList( key );

            for( Graph.ParentChild pc : ids )
            {
                BaseVertex v = VertexGenerator.createVertex( generators, desc, pc.child, pc.parent );
                incCount();
                resolveLinks( graph, desc, v );
                //generateInstances( graph, model, generators, parent, desc.getDependent() );
            }
        }

        System.out.println( "Done" );

        return graph;
    }

//    private static void generateInstances(Graph graph, GraphModel model, HashMap<String, IGenerator> generators, String parentID, List<ChildNodeDescriptor> dependent) throws Exception {
//
//        for(ChildNodeDescriptor child : dependent)
//        {
//            // Берем IDы и идем по ним
//            VertexDescription desc = model.getVertexDescription( child.childClassName );
//            List<String> ids = graph.getVerticesIDList( desc.getClassName() );
//
//            for( String id : ids )
//            {
//                BaseVertex v = VertexGenerator.createVertex( generators, desc, id, parentID );
//                incCount();
//                resolveLinks( graph, desc, v );
//                generateInstances( graph, model, generators, id, desc.getDependent() );
//            }
//
//        }
//    }

    private static void resolveLinks(Graph graph, VertexDescription desc, BaseVertex v)
    {
        List<String> links = desc.getLinks();
        for( String className : links )
        {
            List<Graph.ParentChild> ids = graph.getVerticesIDList( className );
            int index = IntGenerator.getInt( 0, ids.size() - 1 );
            v.addPosessor( className, ids.get( index ).child );
        }
    }

    public static void createAndWriteEdges(File dir, Graph graph, GraphModel model, HashMap< String, IGenerator> generators ) throws Exception
    {
        System.out.println( "Generating edges" );

        // Потом генерятся все ребра
        Set<String> classes = model.getTEdgeDescriptionList().keySet();

        for( String key : classes )
        {
            System.out.println( "Generate " + key );
            TEdgeDescription ted = model.getTEdgeDescription( key );

            Pair<Integer, Integer> pair = VertexGenerator.getRange( ted.getMin(), ted.getMax() );
            long count = 0;
            for( int i = pair.getKey().intValue(); i <= pair.getValue().intValue(); i++ )
                count += TEdgeGenerator.generate( dir, graph, model, ted, generators );

            System.out.println( "" + count + " has been written" );
        }
        System.out.println( "Done" );
    }

    private static void resolveLinks(Graph graph, GraphModel model){
        /*Set<String> classes = model.getVertexDescriptions().keySet();

        *//**
         * Имена классов вершин, на которые надо поставить ссылки
         *//*
        List<String> links;

        // Идкм по всем классам вершин, расставляем линки от них
        for( String key : classes )
        {
            // Взять описание вершины
            VertexDescription vd = model.getVertexDescription( key );
            if( ( links = vd.getLinks() ).size() == 0 )
                continue; // от данного класса вершин ссылок нет

            // есть ссылки, берем все экземпляры вершин этого класса
            List<BaseVertex> linked = graph.getVerticesIDList( vd.getClassName() );

            // И идем по ним
            for( BaseVertex vx : linked ) {
                for( String className : links ) {
                    List<BaseVertex> targets = graph.getVerticesIDList(className);
                    BaseVertex target = targets.get( 0 );
                    vx.addPosessor( className, target.getId() );
                }
            }
        }*/
    }
}
