package ru.nextbi.generation.atomic;

import ru.nextbi.generation.GraphObjectProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DateTimeGenerator  extends AbstractGenerator{

    Date start;
    Date end;
    SimpleDateFormat inFormatter, outFormatter;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception
    {
        String inFormat= "dd.MM.yyyy HH:mm";
        String outFormat = inFormat;

        end = Calendar.getInstance().getTime();

        if( params.containsKey( "format" ) )
            inFormat = outFormat = params.get("format");

        if( params.containsKey( "in-format" ) )
            inFormat = params.get("in-format");

        if( params.containsKey( "out-format" ) )
            outFormat = params.get("out-format");

        inFormatter = new SimpleDateFormat( inFormat );
        outFormatter = new SimpleDateFormat( outFormat );

        String buff = params.get( "start" );
        if( buff == null )
        {
            System.out.println( "Start value is omitted. Set to 01.01.1970 00:00" );
            buff = "01.01.1970 00:00";
        }
        start = inFormatter.parse( buff );
        if( ( buff = params.get( "end" )) != null )
            end = inFormatter.parse( buff );
        else
            System.out.println( "Start value is omitted. Set to " + inFormatter.format( end )  );

        if( start.getTime() >= end.getTime() )
            throw new Exception( "Incorrect range: start is greate or equal than end" );
    }

    @Override
    public String getValue()
    {
        Date dt = new Date();
        long res = ThreadLocalRandom.current().nextLong( start.getTime(), end.getTime() );
        dt.setTime( res );
        return outFormatter.format( dt );
    }

    @Override
    public GraphObjectProperty.Type getDataType() {
        return GraphObjectProperty.Type.TIMESTAMP;
    }
}
