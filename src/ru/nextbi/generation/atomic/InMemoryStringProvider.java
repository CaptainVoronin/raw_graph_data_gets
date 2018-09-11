package ru.nextbi.generation.atomic;

import java.util.ArrayList;
import java.util.List;

public class InMemoryStringProvider implements  IStringProvider{
    List<String> items;

    public InMemoryStringProvider( List<String> items )
    {
        this.items = new ArrayList<>( items );
    }

    @Override
    public int getSize(){
        return items.size();
    }

    @Override
    public String getString(int index){
        return items.get( index );
    }

    @Override
    public void init(){

    }

    @Override
    public void close()
    {
        items.clear();
    }
}
