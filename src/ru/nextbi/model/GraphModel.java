package ru.nextbi.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Модель графа. Все просто.
 * Есть две структуры:
 *  - vertexDescriptionList
 *  - vertexDescriptionStructure
 *  Первая седержит плоский скписок всех вершин
 *  Вторая дерево с учетом зависимостей
 */
public class GraphModel
{
    Map<String, VertexDescription> vertexDescriptionList;


    Map<String, TEdgeDescription> tEdgeDescriptionList;


    public GraphModel()
    {
        vertexDescriptionList = new HashMap<>();
        tEdgeDescriptionList = new HashMap<>();
    }

    public VertexDescription getVertexDescription(String childClassName)
    {
        return vertexDescriptionList.get( childClassName );
    }

    public void addVertexDescription(VertexDescription vd )
    {
        vertexDescriptionList.put( vd.getClassName(), vd );
    }

    public final Map<String, VertexDescription> getVertexDescriptions()
    {
        return vertexDescriptionList;
    }

    public void addTEdgeDescription(TEdgeDescription ted)
    {
        tEdgeDescriptionList.put( ted.getClassName(), ted );
    }

    public Map<String, TEdgeDescription> getTEdgeDescriptionList() {
        return tEdgeDescriptionList;
    }

    public TEdgeDescription getTEdgeDescription(String key) {
        return tEdgeDescriptionList.get( key );
    }
}
