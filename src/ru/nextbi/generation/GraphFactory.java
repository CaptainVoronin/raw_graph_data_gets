package ru.nextbi.generation;

import javafx.util.Pair;
import ru.nextbi.GTDGenerator;
import ru.nextbi.generation.atomic.IGenerator;
import ru.nextbi.generation.atomic.IntGenerator;
import ru.nextbi.model.*;
import ru.nextbi.writers.OmniWriter;
import ru.nextbi.writers.VertexSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphFactory {

    static int count = 0;

    protected GraphFactory() {
    }

    static void incCount() {
        count++;
        if (count >= 10000) {
            System.out.println("Generated " + count);
        }
    }

    public static final Graph createGraph(Map<String, String> config, File dir, GraphModel model, HashMap<String, IGenerator> generators) throws Exception {
        Graph graph = new Graph();
        OmniWriter omniWriter = new OmniWriter(config, dir);
        // Сначала генераятся все вершины
        Set<String> classes = model.getVertexDescriptions().keySet();

        System.out.println("Generating vertices IDs");
        for (String key : classes) {
            VertexDescription desc = model.getVertexDescription(key);

            // Отсюда генерятся только вершины без родителей
            if (desc.getParentClassName() != null)
                continue;

            Pair<Integer, Integer> pair = VertexGenerator.getRange(desc.getMin(), desc.getMax());

            for (int i = pair.getKey().intValue(); i <= pair.getValue().intValue(); i++)
                VertexGenerator.generateIDs(graph, model, desc, null, generators);
        }

        System.out.println("Done");

        if (config.containsKey(GTDGenerator.SAVE_IDS)) {
            System.out.println("Write IDs");
            saveIDs(config, dir, model, graph);
            System.out.println("Done");
        }

        // По готовым ID создаем и пишем экземпляры верш
        // ин
        System.out.println("Generating vertices instances");
        classes = model.getVertexDescriptions().keySet();

        for (String key : classes) {

            VertexDescription desc = model.getVertexDescription(key);

            // Берем IDы и идем по ним
            List<Graph.ParentChild> ids = graph.getVerticesIDList(key);

            for (Graph.ParentChild pc : ids) {
                BaseVertex v = VertexGenerator.createVertex(generators, desc, pc.child, pc.parent);
                incCount();
                resolveLinks(graph, desc, v);
                omniWriter.write(desc, v);
            }
            omniWriter.forget(key);
        }
        omniWriter.closeAll();
        System.out.println("Done");

        return graph;
    }

    private static void saveIDs(Map<String, String> config, File dir, GraphModel model, Graph graph) throws IOException {
        for (String className : model.getVertexDescriptions().keySet()) {
            List<Graph.ParentChild> ids = graph.getVerticesIDList(className);
            FileWriter fw = new FileWriter(dir.getPath() + File.separator + className + "_ids.csv");
            for (Graph.ParentChild pc : ids) {
                fw.write(pc.child + "\n");
            }
            fw.close();
        }
    }

    private static void resolveLinks(Graph graph, VertexDescription desc, BaseVertex v) {

        for (Link link : desc.getLinks()) {
            switch (link.getCondition()) {
                case MUST:
                    for (Link.Target target : link.getTargets()) {
                        List<Graph.ParentChild> ids = graph.getVerticesIDList(target.className);
                        v.addLink(target.className, ids.get(IntGenerator.getInt(0, ids.size() - 1)).child);
                    }
                    break;
                case OR: {
                    String id = VertexSerializer.NULL_ALIAS;
                    int rnd = IntGenerator.getInt(0, 99);
                    int sum = 0;

                    boolean hasHit = false;
                    for (Link.Target target : link.getTargets()) {
                        sum += target.probability;
                        if ( rnd <= sum && !hasHit ) {
                            List<Graph.ParentChild> ids = graph.getVerticesIDList(target.className);
                            id = ids.get(IntGenerator.getInt(0, ids.size() - 1)).child;
                            // Все, остальные ребра уже не проверяются
                            hasHit = true;
                        }
                        v.addLink(target.className, id);
                        id = VertexSerializer.NULL_ALIAS;
                    }
                }
                break;
                case MAY: {
                    String id = VertexSerializer.NULL_ALIAS;
                    for (Link.Target target : link.getTargets()) {

                        int rnd = IntGenerator.getInt(0, 99);
                        if (rnd <= target.probability) {
                            List<Graph.ParentChild> ids = graph.getVerticesIDList(target.className);
                            id = ids.get(IntGenerator.getInt(0, ids.size() - 1)).child;
                        }
                        v.addLink(target.className, id);
                    }
                }
                break;
                default:
                    break;
            }
        }
    }

    public static void createAndWriteEdges(File dir, Graph graph, GraphModel model, HashMap<String, IGenerator> generators) throws Exception {
        System.out.println("Generating edges");

        // Потом генерятся все ребра
        Set<String> classes = model.getTEdgeDescriptionList().keySet();

        for (String key : classes) {
            System.out.println("Generate " + key);
            TEdgeDescription ted = model.getTEdgeDescription(key);

            Pair<Integer, Integer> pair = VertexGenerator.getRange(ted.getMin(), ted.getMax());
            long count = 0;
            for (int i = pair.getKey().intValue(); i <= pair.getValue().intValue(); i++)
                count += TEdgeGenerator.generate(dir, graph, model, ted, generators);

            System.out.println("" + count + " has been written");
        }
        System.out.println("Done");
    }
}
