package ru.nextbi.model;

import java.util.ArrayList;
import java.util.List;

public class VertexDescription extends GraphElementDescription
{

    List<String> parents;
    List<ChildNodeDescriptor> dependent;
    List<Link> links;

    public VertexDescription() {
        super( GraphElement.ELEMENT_TYPE.VERTEX );
        dependent = new ArrayList<>();
        links = new ArrayList<>();
        parents = new ArrayList<>();

    }

    public List<ChildNodeDescriptor> getDependent() {
        return dependent;
    }

//    public void setDependent(List<ChildNodeDescriptor> dependent) {
//        this.dependent = dependent;
//    }

    public void addDependent(ChildNodeDescriptor chd)
    {
        dependent.add( chd );
    }

    public List<String> getParents()
    {
        return parents;
    }

    public void addParentClassName(String parentClassName) {
         parents.add( parentClassName );
    }

    public void addLink( Link value )
    {
        links.add( value );
    }

    public List<Link> getLinks(){
        return links;
    }

}
