package ru.nextbi.model;

import ru.nextbi.generation.GraphObjectProperty;

import java.util.Map;

public class TEdgeDescription extends GraphElementDescription
{

    String fromVertex;
    String toVertex;

    public String getFromVertex() {
        return fromVertex;
    }

    public void setFromVertex(String fromVertex) {
        this.fromVertex = fromVertex;
    }

    public String getToVertex() {
        return toVertex;
    }

    public void setToVertex(String toVertex) {
        this.toVertex = toVertex;
    }
}
