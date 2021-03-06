package ru.nextbi.generation;

import javafx.util.Pair;
import ru.nextbi.generation.atomic.IGenerator;
import ru.nextbi.model.*;
import ru.nextbi.writers.IEdgeWriter;
import ru.nextbi.writers.csv.TEdgeCSVWriter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TEdgeGenerator{

    static Random rand = new Random();

    protected TEdgeGenerator(){
    }

    public static long generate(IEdgeWriter writer, Graph graph, GraphModel model, TEdgeDescription ted, HashMap<String, IGenerator> generators) throws Exception{
        long count = 0;
        List<String> fromList = graph.getVerticesIDList(ted.getFromVertex());
        List<String> toList = graph.getVerticesIDList(ted.getToVertex());

        // Записал заголовок
        writer.writeHeader();

        // Вообще говоря, поскольку количество вершин — величина случайная,
        // то их может не быть вообще. В этом случае ребро просто нельзя провести.
        if( fromList != null || toList != null )
            if( (!ted.getFromVertex().equals(ted.getToVertex()) && (fromList.size() > 0) && (toList.size() > 0)) ||
                    (ted.getFromVertex().equals(ted.getToVertex()) && fromList.size() >= 2) )
                // Бежим по всем вершинам from и добавляем ребра
                for( String vfrom : fromList ) {
                    String vto = getRandomVertex(toList, vfrom);
                    Pair<Integer, Integer> pair = VertexGenerator.getRange(ted.getMin(), ted.getMax());
                    for( int i = pair.getKey().intValue(); i < pair.getValue().intValue(); i++ ) {

                        HashMap<String, String> tedp = VertexGenerator.generateProps(generators, ted);
                        BaseTransitEdge edge = new BaseTransitEdge(vfrom, vto);
                        edge.setProperties(tedp);
                        writer.writeElement(edge);
                        count++;
                    }
                }
        writer.close();

        return count;
    }

    private static String getRandomVertex(List<String> array, String notEqual){
        String v = null;

        if( array.size() < 2 )
            throw new IllegalArgumentException("List size too small ");

        do {
            v = getRandomElement(array);
        } while( v.equals(notEqual) );
        return v;
    }

    private static String getRandomElement(List<String> array){
        int index = rand.nextInt(array.size());
        return array.get(index);
    }
}
