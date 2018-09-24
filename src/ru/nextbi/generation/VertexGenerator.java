package ru.nextbi.generation;

import javafx.util.Pair;
import ru.nextbi.generation.atomic.IGenerator;
import ru.nextbi.generation.atomic.IntGenerator;
import ru.nextbi.model.*;
import ru.nextbi.writers.OmniWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VertexGenerator {
    protected VertexGenerator() {
    }

    /**
     * Генератся все ID всех вершин
     *
     * @param graph
     * @param model
     * @param vd
     * @param generators
     * @throws Exception
     */
    public static void generateIDs(OmniWriter omniWriter, Graph graph, GraphModel model, VertexDescription vd, String parentClassName, String parentId, HashMap<String, IGenerator> generators ) throws Exception {
        IGenerator gen;

        String id = generateID(generators, vd);

        if( parentClassName != null )
            omniWriter.writeOwnership ( parentClassName, vd.getClassName(), parentId, id );

        graph.addVertexID(vd.getClassName(), id);

        // Генерим дочерние вершины, если есть
        for (ChildNodeDescriptor desc : vd.getDependent()) {
            VertexDescription dch = model.getVertexDescription(desc.childClassName);

            Pair<Integer, Integer> pair = getRange(desc.min, desc.max);
            for (int i = pair.getKey().intValue(); i <= pair.getValue().intValue(); i++)
                generateIDs( omniWriter, graph, model, dch, vd.getClassName(), id, generators );
        }
    }

    private static String generateID(HashMap<String, IGenerator> generators, VertexDescription vd) throws Exception {

        GraphObjectProperty p = vd.getProperties().get("id");
        IGenerator gen = generators.get(p.generatorID);
        if (gen == null)
            throw new Exception("Generator not found for '" + p.generatorName + "'");
        return gen.getValue();
    }

    public static BaseVertex createVertex(Map<String, IGenerator> generators, GraphElementDescription eld, String id/*, String parentID*/) throws Exception {
        BaseVertex v = new BaseVertex(/*parentID*/);
        v.setProperties(generateProps(generators, eld));
        v.setProperty("id", id);
        return v;
    }

    public static HashMap<String, String> generateProps(Map<String, IGenerator> generators, GraphElementDescription eld) throws Exception {
        IGenerator gen;

        HashMap<String, String> vp = new HashMap<>();

        // Сгенерить значения для свойств вершины
        Set<String> s = eld.getProperties().keySet();

        for (String key : s) {

            if (eld.getType().equals(GraphElement.ELEMENT_TYPE.VERTEX) && key.equalsIgnoreCase("id"))
                continue;

            GraphObjectProperty p = eld.getProperties().get(key);

            // Получить генератор значений для свойства
            gen = generators.get(p.generatorID);
            if (gen == null)
                throw new Exception("Generator not found for '" + p.generatorName + "'");
            String val = gen.getValue();
            vp.put(p.name, val);
        }
        return vp;
    }

    /**
     * Надо помнить, что вершины, для которых количество не задано явно, имеют min = max = -1
     * @param min
     * @param max
     * @return
     */
    public static Pair<Integer, Integer> getRange(int min, int max) {
        int upperRange, lowerRange;

        if( max < 0 )
            max = 1;

        // Создавать в рамках того количества, который указан в описании дочернего узла
        if (min < 0) {
            // Если min меньше нуля, это значит, что
            // надо создавать точно max элементов
            lowerRange = 0;
            upperRange = max - 1;
        } else {
            // Если дочек большей одной, то получить их количество
            // как случайное число
            upperRange = IntGenerator.getInt(min, max);
            lowerRange = min;
        }

        return new Pair<>(new Integer(lowerRange), new Integer(upperRange));
    }
}