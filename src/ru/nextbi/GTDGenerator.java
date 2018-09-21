package ru.nextbi;

import ru.nextbi.generation.*;
import ru.nextbi.generation.atomic.GeneratorAlias;
import ru.nextbi.generation.atomic.GeneratorUtils;
import ru.nextbi.generation.atomic.IGenerator;
import ru.nextbi.model.*;
import ru.nextbi.writers.VertexCSVWriter;

import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class GTDGenerator {
    public static final String CURRENT_DIR_KEY = "current_dir";
    public static final String CASH_DEFAULT = "cash_default";
    public static final String SAVE_IDS = "save-ids";
    public static final String NULL_VALUE_SEQUENCE = "null_value";

    Map<String, String> config;

    public static void main(String[] args) throws IOException {
        Options ops = new Options();

        Option input = new Option("s", "scheme", true, "scheme file path");
        input.setRequired(false);
        ops.addOption(input);

        Option output = new Option("o", "outdir", true, "output directory");
        output.setRequired(false);
        ops.addOption(output);

        Option clear = new Option("c", "clear", false, "clear target directory");
        clear.setRequired(false);
        ops.addOption(clear);

        Option param = new Option("p", "parameter", true, "parameter value");
        param.setRequired(false);
        ops.addOption(param);

        Option save_ids = new Option("a", "save-ids", false, "save vertices IDs");
        save_ids.setRequired(false);
        ops.addOption(save_ids);

        Option version = new Option("v", "version", false, "show version");
        version.setRequired(false);
        ops.addOption(version);

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

        if( cmd.hasOption( "version" ))
        {
            System.out.println( "Version " + Version.getVersion() );
            System.exit( 0 );
        }

        Map<String, String> inputParams = new HashMap<>();
        String[] params = cmd.getOptionValues("parameter");
        if (params != null)
            parseInputParams(params, inputParams);

        if (cmd.hasOption("save-ids")) {
            inputParams.put(GTDGenerator.SAVE_IDS, "true");
        }

        String schemeFile = cmd.getOptionValue("scheme");
        String outputDirPath = cmd.getOptionValue("outdir");
        File scheme = new File(schemeFile);

        if (!scheme.exists()) {
            System.out.println("Scheme file not found " + schemeFile);
            System.exit(1);
        }

        File dir = new File(outputDirPath);
        if (!dir.exists()) {
            System.out.println("Output dir not found " + outputDirPath);
            System.exit(1);
        }

        if (!dir.isDirectory()) {
            System.out.println(outputDirPath + " is not a directory");
            System.exit(1);
        }

        if (cmd.hasOption("clear")) {
            clearTargerDir(dir);
        }

        String rawDesc = new String(Files.readAllBytes(Paths.get(schemeFile)));

        GTDGenerator gen = new GTDGenerator();

        try {

            Date start = Calendar.getInstance().getTime();
            SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String buff = fmt.format(start);

            System.out.println(buff);
            gen.generateGraph(inputParams, rawDesc, dir);

            Date end = Calendar.getInstance().getTime();
            buff = fmt.format(end);
            System.out.println(buff);
            System.out.println("It has been done within " + ((end.getTime() - start.getTime()) / 1000) + " seconds");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseInputParams(String[] params, Map<String, String> inputParams) {
        for (String par : params) {
            String[] parts = par.split("=");
            inputParams.put(parts[0].trim(), parts.length > 1 ? parts[1].trim() : "");
        }
    }

    private static void clearTargerDir(File dir) {
        File[] items = dir.listFiles();
        System.out.println("Clear target directory");
        for (File file : items)
            file.delete();
        System.out.println("Done");
    }

    void generateGraph(Map<String, String> params, String rawDescription, File dir) throws Exception {

        config = new HashMap<>();

        // Положить в конфиг рабочую директорию
        String buff = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
        buff = buff.substring(0, buff.length() - 1);
        config.put(CURRENT_DIR_KEY, buff);

        GraphModel model = GraphModelParser.parse(params, config, rawDescription);
        boolean result = GraphModelParser.check(model);

        if (!result)
            System.exit(1);

        try {

            HashMap<String, IGenerator> generators = createGenerators(model);
            Graph graph = GraphFactory.createGraph(config, dir, model, generators);
            GraphFactory.createAndWriteEdges(dir, graph, model, generators);
            //writeVertices(dir, model, graph);

            for (String key : generators.keySet())
                generators.get(key).unInialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    private void writeVertices(File dir, GraphModel model, Graph graph) throws Exception {
        *//*Map< String, VertexDescription > desc = model.getVertexDescriptions();

        for( String key : desc.keySet() )
        {
            VertexDescription vd = desc.get( key );
            List<BaseVertex> vertices = graph.getVerticesIDList( vd.getClassName() );
            String filename = vd.getClassName() + ".csv";
            VertexCSVWriter vw = new VertexCSVWriter( dir, filename, ',' );
            vw.write( vd, vertices  );
            vertices.clear();
        }*//*
    }*/

    private HashMap<String, IGenerator> createGenerators(GraphModel model) throws Exception {
        // Мапа генераторв
        HashMap<String, IGenerator> generators = new HashMap<>();

        // Генераторы для вершин
        Set<String> keys = model.getVertexDescriptions().keySet();

        // Пошли по вершинам
        for (String key : keys) {
            // Взять вершину
            VertexDescription vd = model.getVertexDescription(key);

            // Создать генераторы
            createGeneratorsForElement(vd, generators);
        }

        // Генераторы для вершин
        keys = model.getTEdgeDescriptionList().keySet();

        // Пошли по вершинам
        for (String key : keys) {
            // Взять вершину
            TEdgeDescription eld = model.getTEdgeDescription(key);

            // Создать генераторы
            createGeneratorsForElement(eld, generators);
        }

        return generators;
    }

    private void createGeneratorsForElement(GraphElementDescription eld, HashMap<String, IGenerator> generators) throws Exception {
        Set<String> names = eld.getProperties().keySet();
        for (String name : names) {
            GraphObjectProperty gep = eld.getProperties().get(name);
            String className = getGeneratorClassName(gep.generatorName);

            gep.generatorParams.put("class", gep.generatorName);
            String hash = GeneratorUtils.makeHash(gep.generatorParams);
            gep.generatorParams.remove("class");
            gep.generatorID = hash;
            if (generators.containsKey(hash))
                continue;
            else {
                IGenerator gen = createGenerator(className, gep.generatorParams);
                gen.initialize();
                generators.put(hash, gen);
            }
        }
    }

    /**
     * Получает имя класса генератора. Сделано чтобы можно было использовать псевдонимы для встроенных генераторов
     *
     * @param generatorName
     * @return
     */
    private String getGeneratorClassName(String generatorName) {
        String className = generatorName;
        // Это встроенный генератор?
        for (GeneratorAlias alias : GeneratorAlias.values())
            if (generatorName.equals(alias.toString())) {
                className = alias.className();
                break;
            }
        return className;
    }

    private IGenerator createGenerator(String name, Map<String, String> params) throws Exception {
        IGenerator gen = (IGenerator) Class.forName(name).newInstance();
        gen.setParams(config, params);
        return gen;
    }

}
