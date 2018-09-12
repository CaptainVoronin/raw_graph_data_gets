package ru.nextbi.model;

import javafx.util.Pair;
import ru.nextbi.generation.GraphObjectProperty;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 VERTEX_START
 class: OU [-1,20]
 own: eployee [5,20]
 prop: name
 VERTEX_END
 VERTEX_START
 class: employee
 own: e-mail [1,5]
 VERTEX_END
 VERTEX_START
 class: e-mail
 prop: name
 VERTEX_END
 *
 */

public class GraphModelParser
{
    static Pattern pattern_range_extractor = Pattern.compile( "\\[(.*?)\\]" );
    static Pattern pattern_get_generator_call = Pattern.compile( "(^[a-zA-Z][a-zA-Z0-9_]{0,100}\\s*)([a-zA-Z][a-zA-Z0-9_]{0,100}\\s*)(\\(\\s*)(.*)(\\s*\\))" );

    public static final GraphModel parse( Map<String, String> params, Map<String, String> config, String buff) throws Exception{
        System.out.println( "Start parsing" );

        GraphModel model = new GraphModel();

        String[] rows = buff.split( "\n" );

        // Распарсим конфиг
        parseConfig( rows, config  );

        //ДОбавим или перетрем в конфиге параметры из командной строки
        config.putAll( params );


        buff = substituteVariables( config, buff );

        rows = buff.split( "\n" );

        for( int i = 0; i < rows.length; i++ )
        {
            String row = rows[i].trim();

            // Клмментарии и пустые строки
            if( row.startsWith( "//") || row.length() == 0 )
                continue;

            if( row.equalsIgnoreCase( "VERTEX_START") ) {
                VertexDescription vd = new VertexDescription();
                i = parseVertex( rows, ++i, vd );
                model.addVertexDescription( vd );
            } else if(row.equalsIgnoreCase( "T_EDGE_START") )
            {
                TEdgeDescription ted = new TEdgeDescription();
                i = parseTEdge( rows, ++i, ted );
                model.addTEdgeDescription( ted );
            }
            else
                System.out.println( "Unknown directive " + row + ". Ignored" );
        }

        System.out.println( "Ended sucessfully" );
        return model;
    }

    private static String substituteVariables(Map<String, String> config, String buff)
    {
        for( String name : config.keySet() )
        {
            if( name.startsWith( "$")) {
                String pattern = "\\$" + name.substring(1, name.length());
                String value = config.get( name );
                buff = buff.replaceAll( pattern, Matcher.quoteReplacement( value ) );
            }
        }
        return buff;
    }

    private static void parseConfig(String[] rows, Map<String,String> config)
    {
        int i = 0;

        while ( !rows[i].trim().equals( "CONFIG_START") && i < rows.length )
            i++;

        for( int j = i + 1; j < rows.length; j++ )
        {

            String row = rows[j].trim();

            if (row.length() == 0)
                continue;
            if (row.equalsIgnoreCase("CONFIG_END"))
                break;
            else if (row.startsWith("//"))
                continue;
            else {
                String name = "", value = "";
                int index = row.indexOf( "=" );
                if( index == -1 )
                    name = row;
                else {
                    name = row.substring(0, index );
                    value = row.substring( index + 1, row.length() );
                }
                config.put( name, value );
            }
        }
    }

    private static int parseTEdge(String[] rows, int i, TEdgeDescription ted) throws Exception{
        int j = 0;
            Map<String, GraphObjectProperty> props = new HashMap<>();
            for (j = i; j < rows.length; j++) {
                String row = rows[j].trim();
                if (row.length() == 0)
                    continue;
                else if (row.startsWith("//"))
                    continue;
                else if (row.startsWith("class")) {
                    parseClassString(ted, row);
                } else if (row.startsWith("from")) {
                    ted.setFromVertex(getValueString(row).trim());
                } else if (row.startsWith("to")) {
                    ted.setToVertex(getValueString(row).trim());
                } else if (row.startsWith("prop")) {
                    GraphObjectProperty prop = getProperty( j, row);
                    props.put(prop.name, prop);
                } else if (row.equalsIgnoreCase("T_EDGE_END"))
                    break;
            }

            if (!props.containsKey(GraphElement.KEY_ID))
                throw new Exception("A graph element must have ID property. Row " + j);

            ted.setProperties(props);
            return j;
    }

    public static boolean check( GraphModel model )
    {
        // Проверка зависимостей
        boolean result = true;
        System.out.println( "Start checking" );

        Map<String, VertexDescription> vs = model.getVertexDescriptions();

        // Идем по  списку вершин
        for( String type : vs.keySet() )
        {
            VertexDescription vd = vs.get( type );

            List<ChildNodeDescriptor> children = vd.getDependent();

            for( ChildNodeDescriptor cnd : children )
            {
                if( !vs.containsKey( cnd.childClassName ) )
                {
                    System.out.println( "Error. Vertex type " + cnd.childClassName + " referenced by " + type + " not found " );
                    result = false;
                }
                else
                {
                    VertexDescription child = vs.get( cnd.childClassName );
                    child.setParentClassName( vd.getClassName() );
                }
            }
        }

        Map<String, TEdgeDescription> teds = model.getTEdgeDescriptionList();

        // Идем по  списку транзитных ребер
        // Проверяем, на что они сослались?
        for( String type : teds.keySet() )
        {
            TEdgeDescription ted = teds.get( type );
            if( !vs.containsKey( ted.getFromVertex() ) )
            {
                System.out.println("Error. Vertex type " + ted.getFromVertex() + " referenced by " + type + " not found ");
                result = false;
            }
            if( !vs.containsKey( ted.getToVertex() ) )
            {
                System.out.println("Error. Vertex type " + ted.getToVertex() + " referenced by " + type + " not found ");
                result = false;
            }
        }

        if( !result )
            return result;

        // Поиск циклических зависимостей
        result = checkCycles( model );

        if( result )
            System.out.println( "Model is good" );

        return result;
    }

    private static boolean checkCycles(GraphModel model)
    {
        boolean result = true;
        List<String> vertices = new ArrayList<>();
        for( String className : model.getVertexDescriptions().keySet() )
        {
            VertexDescription vd = model.getVertexDescription( className );
            List<ChildNodeDescriptor> children = vd.getDependent();
            if( children.size() == 0 )
                continue;
            else
            {
                vertices.add( className );
                if( !( result = findCicle( vertices, model, vd )  ) )
                    break;
                else
                    vertices.clear();
            }
        }
        if( !result )
        {
            String chain = "";
            for( String name : vertices )
            {
                chain += name + "=>";
            }
            chain = chain.substring( 0, chain.length() - 2 );
            System.out.println( "Circular dependece found: " + chain );
        }
        return result;
    }

    private static boolean findCicle(List<String> vertices, GraphModel model, VertexDescription vd) {
        boolean result = true;
        List<ChildNodeDescriptor> children = vd.getDependent();

        for( ChildNodeDescriptor child : children )
        {
            if( vertices.contains( child.childClassName )) {
                // положить для информации
                vertices.add( child.childClassName );
                return false;
            }
            else
            {
                VertexDescription vChild = model.getVertexDescription( child.childClassName );
                if( children.size() == 0 )
                    continue;
                else
                {
                    vertices.add( vChild.getClassName() );
                    if( !( result = findCicle( vertices, model, vChild )  ) )
                        break;
                    else
                        vertices.remove( vChild.getClassName() );
                }
            }
        }
        return result;
    }

    private static int parseVertex(String[] rows, int i, VertexDescription vd) throws Exception{
        int j;
        Map<String, GraphObjectProperty> props = new HashMap<>();
        for( j = i; j < rows.length; j++ )
        {
            String row = rows[j].trim();

            if ( row.length() == 0 )
                continue;
            else if (row.startsWith("//"))
                continue;
            else if( row.startsWith( "class" ) )
            {
                parseClassString( vd, row );
            }
            else if( row.startsWith( "prop" ) )
            {
                GraphObjectProperty prop = getProperty( j, row );
                props.put( prop.name, prop );
            }
            else if( row.startsWith( "own" ) )
            {
                parseOwnStatement( vd, row );
            }
            else if( row.startsWith( "link" ) )
            {
                parseLinkStatement( vd, row );
            }
            else if( row.equalsIgnoreCase( "VERTEX_END"))
                break;
        }
        if( !props.containsKey( GraphElement.KEY_ID ) )
            throw new ParseException( "A graph element must have ID property. Class" + vd.getClassName(), j );
        vd.setProperties( props );
        return j;
    }

    private static void parseLinkStatement(VertexDescription vd, String row)  {
        String val = getValueString( row ).trim();
        vd.addLink( val );
    }

    private static void parseClassString( GraphElementDescription eld , String row) throws Exception{
        Pattern p = Pattern.compile( "class\\s*:\\s*([a-zA-Z][a-zA-Z0-9_]*)\\s*(\\[\\s*(([-0-9]+)\\s*,\\s*([-0-9]+))\\s*\\])*");
        Matcher m = p.matcher( row.trim() );
        if( m.find() )
        {
//            for( int i = 0; i <=  m.groupCount(); i++ )
//                System.out.println( "#" + i + " " + m.group( i ) );

            // Имя класса
            if( m.group( 1 ) != null )
                eld.setClassName( m.group( 1 ).trim() );

            // У подчиненных вершин может быть не задано количество экземпляров для генерации
            if( m.group( 4 ) != null && m.group( 5 ) != null  )
            {
                try {
                    eld.setMin(Integer.parseInt(m.group(4)));
                    eld.setMax(Integer.parseInt(m.group(5)));
                } catch( Exception e )
                {
                    System.out.println( "Incorrect class declaration " + row );
                    throw e;
                }
            }
        }
        else
            throw new Exception( "Incorrect class declaration " + row );

    }

    private static void parseOwnStatement(VertexDescription vd, String row)
    {
        String val = getValueString( row ).trim();

        // Не ошибка, если владение заявлено, но не указано - чем.
        // Просто пропустим
        if( val.length() == 0 )
            return;
        String[] tk = val.split( " ");

        ChildNodeDescriptor chd = new ChildNodeDescriptor();

        // Нулевой элемент - имя класса дочерней вершины
        chd.childClassName = tk[0];

        // Сколько их генерить?
        Pair<Integer, Integer> r = getRange( row );
        chd.min = r.getKey().intValue();
        chd.max = r.getValue().intValue();

        vd.addDependent( chd );
    }

    private static GraphObjectProperty getProperty(int rowIndex, String row)
    {
        String val = getValueString( row );
        //val = "id long_id( initial=10000 )";

        Matcher m = pattern_get_generator_call.matcher( val );

        if( !m.find() )
            throw new IllegalArgumentException( "Error at line " + rowIndex + ". Incorrect syntax \"" + ( row + 1 ) + "\"" );

        GraphObjectProperty gProperty = new GraphObjectProperty();
        gProperty.name = m.group( 1 ).trim();

        // Имя класса или псевдоним генератора
        if( m.groupCount() < 2 ) {
            gProperty.generatorName = "ru.nextbi.generation.atomic.LongIDGenerator";
            return gProperty;
        }
        else
            gProperty.generatorName = m.group( 2 ).trim();

        Map<String, String> paramsMap = new HashMap<>();

        // Расщепить строку по запятым, но не тем, что находятся в кавычках
        // fomat="a,b,c" не расщепится
        String name, value;
        if( m.groupCount() > 4 ) {
            String[] args = m.group(4).split( ",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

            // Получили список пар вида name=value
            for( int k = 0; k < args.length; k++ )
            {
                // Если список аргументов пустой, то первым элементом массива будет пуста строка
                // Проверим
                if( args[0].trim().length() == 0 )
                    break;

                // Расщепить строку арнумента по знаку =
                value = "";
                int index = args[k].indexOf( "=" );

                if( index == -1 )
                    name = args[k];
                else
                {
                    name = args[k].substring( 0, index ).trim();
                    value = args[k].substring( index + 1, args[k].length() );

                    if( value != null )
                    {
                        value = value.trim();
                        if( value.startsWith( "\"") ) {
                            value = value.substring(1, value.length());

                            if (value.lastIndexOf("\"") == value.length() - 1)
                                value = value.substring(0, value.length() - 1);
                        }
                    }
                }
                paramsMap.put( name, value );
            }
        }

        gProperty.generatorParams = paramsMap;
        return gProperty;
    }

    /**
     * Возвращает строку после двоеточия. Тримит ее
     * @param row
     * @return
     */
    private static final String getValueString(String row )
    {
        int index = row.indexOf(  ":" );
        String buff = "";
        if( index != -1 )
            buff = row.substring( index +1 , row.length() );

        return buff.trim();
    }

    /**
     * Извлекает пару целых значений из строки вида [x,y]
     */
    final static Pair< Integer, Integer> getRange(String row )
    {
        Pair<Integer, Integer> range;
        Matcher m = pattern_range_extractor.matcher( row );

        if( !m.find() )
        {
            range = new Pair<>( -1, 1 );
        }
        else
        {
            String[] raws = m.group(1).split( "," );
            Integer min = Integer.parseInt( raws[0].trim() );
            Integer max = Integer.parseInt( raws[1].trim() );

            if( min.intValue() >= max.intValue() )
                throw new IllegalArgumentException( "Min value must be less than max value " + row );
            range = new Pair<>( min, max );
        }

        return range;
    }
}
