package ru.nextbi.generation.atomic;

import ru.nextbi.generation.GraphObjectProperty;

import java.io.IOException;

public abstract class AbstractGenerator implements IGenerator{

    @Override
    public void initialize() throws Exception{

    }

    @Override
    public void unInialize() throws IOException {

    }

    @Override
    public GraphObjectProperty.Type getDataType() {
        return GraphObjectProperty.Type.VARCHAR;
    }
}
