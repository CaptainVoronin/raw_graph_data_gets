package ru.nextbi.model;

import ru.nextbi.generation.GraphObjectProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphElementDescription
{
    protected String className;
    protected int min;
    protected int max;
    protected  Map<String, GraphObjectProperty> props;
    GraphElement.ELEMENT_TYPE type;

    public GraphElement.ELEMENT_TYPE getType(){
        return type;
    }

    public void setType(GraphElement.ELEMENT_TYPE type){
        this.type = type;
    }

    public GraphElementDescription( GraphElement.ELEMENT_TYPE type )
    {
        props = new HashMap<>();
        min = -1;
        max = 1;
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Map<String, GraphObjectProperty> getProperties() {
        return props;
    }

    public void setProperties(Map<String, GraphObjectProperty> props) {
        this.props = props;
    }

}
