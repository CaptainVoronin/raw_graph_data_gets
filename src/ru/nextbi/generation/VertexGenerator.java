package ru.nextbi.generation;

import javafx.util.Pair;
import ru.nextbi.generation.atomic.IGenerator;
import ru.nextbi.generation.atomic.IntGenerator;
import ru.nextbi.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VertexGenerator
{
    protected VertexGenerator(){};

    /**
     * Генератся все ID всех вершин
     * @param graph
     * @param model
     * @param vd
     * @param generators
     * @throws Exception
     */
    public static final void generateIDs(Graph graph, GraphModel model, VertexDescription vd, String parentId, HashMap< String, IGenerator> generators ) throws Exception
    {
        IGenerator gen;
        Graph.ParentChild pc = new Graph.ParentChild();
        //HashMap<String, String> vp = generateProps( generators, vd );
        String id = generateID( generators, vd );
        pc.child = id;
        pc.parent = parentId;

        // Добавляем в граф
        graph.addVertexID( vd.getClassName(), pc );

        // Генерим дочерние вершины, если есть
        for ( ChildNodeDescriptor desc : vd.getDependent() )
        {
            VertexDescription dch = model.getVertexDescription( desc.childClassName );

            Pair<Integer, Integer> pair = getRange( desc.min, desc.max );
            for( int i = pair.getKey().intValue(); i <= pair.getValue().intValue(); i++ )
                generateIDs( graph, model, dch, id, generators );
        }

    }

    private static String generateID(HashMap<String,IGenerator> generators, VertexDescription vd ) throws Exception {

            GraphObjectProperty p = vd.getProperties().get( "id" );
            IGenerator gen = generators.get( p.generatorID );
            if( gen == null )
                throw new Exception( "Generator not found for '" + p.generatorName + "'" );
            return gen.getValue();
    }

    public static BaseVertex createVertex( Map<String, IGenerator> generators, GraphElementDescription eld, String id, String parentID ) throws Exception {
        BaseVertex v = new BaseVertex( parentID );
        v.setProperties(  generateProps( generators, eld ) );
        v.setProperty( "id", id );
        return v;
    }

    public static HashMap<String, String> generateProps( Map< String, IGenerator> generators, GraphElementDescription eld) throws Exception {
        IGenerator gen;

        HashMap<String, String> vp = new HashMap<>();

        // Сгенерить значения для свойств вершины
        Set<String> s = eld.getProperties().keySet();

        for( String key : s )
        {
            if( key.equalsIgnoreCase( "id") )
                continue;

            GraphObjectProperty p = eld.getProperties().get( key );

            // Получить генератор значений для свойства
            gen = generators.get( p.generatorID );
            if( gen == null )
                throw new Exception( "Generator not found for '" + p.generatorName + "'" );
            String val = gen.getValue();
            vp.put( p.name, val );
        }
        return vp;
    }

    public static Pair< Integer, Integer > getRange( int min, int max )
    {
        int upperRange, lowerRange;

        // Создавать в рамках того количества, который указан в описании дочернего узла
        if( min < 0 )
        {
            // Если min меньше нуля, это значит, что
            // надо создавать точно max элементов
            lowerRange = 0;
            upperRange = max - 1;
        }
        else
        {
            // Если дочек большей одной, то получить их количество
            // как случайное число
            upperRange = IntGenerator.getInt(min, max);
            lowerRange = min;
        }

        return new Pair<>( new Integer( lowerRange), new Integer( upperRange  ) );
    }
}
