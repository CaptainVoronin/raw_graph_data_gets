package ru.nextbi.writers.csv;

import ru.nextbi.GTDGenerator;
import ru.nextbi.model.GraphElementDescription;
import ru.nextbi.model.VertexDescription;
import ru.nextbi.writers.IEdgeWriter;
import ru.nextbi.writers.ILinkWriter;
import ru.nextbi.writers.IVertexWriter;
import ru.nextbi.writers.IWriterFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CSVWtiterFactory implements IWriterFactory
{
    private final File targetDir;
    private final Map<String, String> config;
    private final char delimiter;

    public CSVWtiterFactory(Map<String, String> config )
    {
        this.targetDir = new File( config.get(GTDGenerator.OUTPUT_DIR ) );
        this.config = config;
        this.delimiter = config.getOrDefault(  GTDGenerator.DELIMITER, "," ).charAt( 0 );
    }

    @Override
    public IVertexWriter createVertexWriter(GraphElementDescription vd) throws IOException{
        return new VertexCSVWriter(  targetDir, ( VertexDescription ) vd, config );
    }

    @Override
    public IEdgeWriter createEdgeWriter(GraphElementDescription ed) throws IOException{
        return new TEdgeCSVWriter( targetDir, ed, delimiter );
    }

    @Override
    public ILinkWriter createLinkWriter(String parentClassName, String className)
    {
        return new LinkWriter( targetDir, parentClassName, className, delimiter );
    }
}
