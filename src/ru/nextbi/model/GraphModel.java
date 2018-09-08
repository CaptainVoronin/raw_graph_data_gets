package ru.nextbi.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Модель графа. Все просто.
 * Есть две структуры:
 *  - vertexDescriptionFlatList
 *  - vertexDescriptionStructure
 *  Первая седержит плоский скписок всех вершин
 *  Вторая дерево с учетом зависимостей
 */
public class GraphModel
{
    Map<String, VertexDescription> vertexDescriptionFlatList;


    Map<String, TEdgeDescription> tEdgeDescriptionFlatList;


    public GraphModel()
    {
        vertexDescriptionFlatList = new HashMap<>();
        tEdgeDescriptionFlatList = new HashMap<>();
    }

    public VertexDescription getVertexDescription(String childClassName)
    {
        return vertexDescriptionFlatList.get( childClassName );
    }

    public void addVertexDescriptionToFlat(VertexDescription vd )
    {
        vertexDescriptionFlatList.put( vd.getClassName(), vd );
    }

    public final Map<String, VertexDescription> getFlatVertexDescriptions()
    {
        return vertexDescriptionFlatList;
    }

    public void addTEdgeDescription(TEdgeDescription ted)
    {
        tEdgeDescriptionFlatList.put( ted.getClassName(), ted );
    }

    public Map<String, TEdgeDescription> getTEdgeDescriptionFlatList() {
        return tEdgeDescriptionFlatList;
    }


    public TEdgeDescription getTEdgeDescription(String key) {
        return tEdgeDescriptionFlatList.get( key );
    }
}
