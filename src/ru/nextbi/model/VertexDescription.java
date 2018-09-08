package ru.nextbi.model;

/* VERTEX_START
        * class: OU [-1,20]
        * own: eployee [5,20]
        * prop: name
        * prop: lastname
        * label: lastname
        * VERTEX_END
*/

import ru.nextbi.generation.GraphObjectProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VertexDescription extends GraphElementDescription
{

    String parentClassName;
    List<ChildNodeDescriptor> dependent;
    List<String> posession;

    public VertexDescription() {
        super();
        dependent = new ArrayList<>();
        posession = new ArrayList<>();
    }

    public List<ChildNodeDescriptor> getDependent() {
        return dependent;
    }

    public void setDependent(List<ChildNodeDescriptor> dependent) {
        this.dependent = dependent;
    }

    public void addDependent(ChildNodeDescriptor chd)
    {
        dependent.add( chd );
    }

    public String getParentClassName() {
        return parentClassName;
    }

    public void setParentClassName(String parentClassName) {
        this.parentClassName = parentClassName;
    }

    public void addPosession( String value )
    {
        posession.add( value );
    }

    public List<String> getPosession(){
        return posession;
    }
}
