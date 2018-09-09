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

    public static final void generate( Graph graph, GraphModel model, VertexDescription vd, BaseVertex parent, HashMap< String, IGenerator> generators ) throws Exception
    {
        IGenerator gen;

        HashMap<String, String> vp = generateProps( generators, vd );

        // Создаем наконец вершину
        BaseVertex vertex = new BaseVertex( parent );

        // Втыкаем туда свойства
        vertex.setProperties( vp );

        // Добавляем в граф
        graph.addVertex( vd.getClassName(), vertex );

        // Генерим дочерние вершины, если есть
        for ( ChildNodeDescriptor desc : vd.getDependent() )
        {
            VertexDescription dch = model.getVertexDescription( desc.childClassName );

            Pair<Integer, Integer> pair = getRange( desc.min, desc.max );
            for( int i = pair.getKey().intValue(); i <= pair.getValue().intValue(); i++ )
                generate( graph, model, dch, vertex, generators );
        }

    }

    public static HashMap<String, String> generateProps( HashMap< String, IGenerator> generators, GraphElementDescription eld) throws Exception {
        IGenerator gen;

        HashMap<String, String> vp = new HashMap<>();

        // Сгенерить значения для свойств вершины
        Set<String> s = eld.getProperties().keySet();

        for( String key : s )
        {
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
