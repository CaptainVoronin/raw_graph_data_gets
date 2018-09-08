package ru.nextbi;

import ru.nextbi.generation.*;
import ru.nextbi.generation.atomic.GeneratorUtils;
import ru.nextbi.generation.atomic.IGenerator;
import ru.nextbi.model.*;
import ru.nextbi.writers.TEdgeCSVWriter;
import ru.nextbi.writers.VertexCSVWriter;

import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GTDGenerator {

    public static void main(String[] args) throws IOException {


        Options ops = new Options();

        Option input = new Option("s", "scheme", true, "scheme file path");
        input.setRequired(true);
        ops.addOption(input);

        Option output = new Option("o", "outdir", true, "output directory");
        output.setRequired(true);
        ops.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(ops, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", ops);
            System.exit(1);
            return;
        }

            String schemeFile = cmd.getOptionValue("scheme");
            String outputDirPath = cmd.getOptionValue("outdir");
            File scheme = new File( schemeFile  );

            if( !scheme.exists( ) )
            {
                System.out.println( "Scheme file not found " + schemeFile );
                System.exit( 1 );
            }

            File dir = new File( outputDirPath );
            if( !dir.exists() )
            {
                System.out.println( "Output dir not found " + outputDirPath );
                System.exit( 1 );
            }

            if( !dir.isDirectory() )
            {
                System.out.println( outputDirPath + " is not a directory" );
                System.exit( 1 );
            }

            String rawDesc = new String(Files.readAllBytes(Paths.get(schemeFile)));

            GTDGenerator gen = new GTDGenerator();

        try {
            gen.generateGraph( rawDesc, dir );
        } catch( Exception e ) {
            e.printStackTrace();
        }

    }

    void generateGraph( String rawDescription, File dir ) throws Exception{
        GraphModel model = GraphModelParser.parse( rawDescription );
        boolean result = GraphModelParser.check( model );

        if( !result )
            System.exit( 1 );


        try {
            HashMap<String, IGenerator> generators = createGenerators( model );
            Graph graph = GraphFactory.createGraph( model, generators );
            writeGraph( dir, model, graph );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    private static void writeGraph(File dir, GraphModel model, Graph graph) throws Exception
    {
        Map< String, VertexDescription > desc = model.getFlatVertexDescriptions();

        for( String key : desc.keySet() )
        {
            VertexDescription vd = desc.get( key );
            List<BaseVertex> vertices = graph.getVertices( vd.getClassName() );
            String filename = vd.getClassName() + ".csv";
            VertexCSVWriter vw = new VertexCSVWriter( dir, filename, ',' );
            vw.write( vd, vertices  );
        }

        Map< String, TEdgeDescription> teds = model.getTEdgeDescriptionFlatList();

        for( String key : teds.keySet() )
        {
            TEdgeDescription ted = teds.get( key );
            List<BaseEdge> edges = graph.getEdges( ted.getClassName() );
            String filename = ted.getClassName() + ".csv";
            TEdgeCSVWriter ew = new TEdgeCSVWriter( dir, filename, ',' );
            ew.write( ted, edges );
        }
    }

    private static HashMap<String,IGenerator> createGenerators(GraphModel model) throws Exception
    {
        // Мапа генераторв
        HashMap<String,IGenerator> generators = new HashMap<>();

        // Генераторы для вершин
        Set<String> keys = model.getFlatVertexDescriptions().keySet();

        // Пошли по вершинам
        for( String key : keys )
        {
            // Взять вершину
            VertexDescription vd = model.getVertexDescription( key );

            // Создать генераторы
            createGeneratorsForElement( vd, generators );
        }

        // Генераторы для вершин
        keys = model.getTEdgeDescriptionFlatList().keySet();

        // Пошли по вершинам
        for( String key : keys )
        {
            // Взять вершину
            TEdgeDescription eld = model.getTEdgeDescription( key );

            // Создать генераторы
            createGeneratorsForElement( eld, generators );
        }

        return generators;
    }

    private static void createGeneratorsForElement( GraphElementDescription eld, HashMap<String,IGenerator> generators ) throws Exception{
        Set<String> names = eld.getProperties().keySet();
        for( String name : names )
        {
            GraphObjectProperty gep = eld.getProperties().get( name );
            String hash = GeneratorUtils.makeHash( gep.generatorClassName + gep.generatorParams );
            gep.generatorID = hash;
            if( generators.containsKey( hash ) )
                continue;
            else {
                IGenerator gen = createGenerator( gep.generatorClassName, gep.generatorParams );
                generators.put( hash, gen );
            }
        }
    }

    private static IGenerator createGenerator(String name, String params) throws Exception
    {
        IGenerator gen = ( IGenerator ) Class.forName( name ).newInstance();
        gen.setParamString( params );
        return gen;
    }
}
