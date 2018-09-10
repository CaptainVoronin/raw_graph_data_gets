package ru.nextbi.generation.atomic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DateTimeGenerator  extends AbstractGenerator{

    Date start;
    Date end;
    SimpleDateFormat formatter;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception
    {
        end = Calendar.getInstance().getTime();
        String format = params.getOrDefault( "format", "dd.MM.yyyy HH:mm" );
        formatter = new SimpleDateFormat( format );
        String buff = params.get( "start" );
        if( buff == null )
        {
            System.out.println( "Start value is omitted. Set to 01.01.1970 00:00" );
            buff = "01.01.1970 00:00";
        }
        start = formatter.parse( buff );
        if( ( buff = params.get( "end" )) != null )
            end = formatter.parse( buff );
        else
            System.out.println( "Start value is omitted. Set to " + formatter.format( end )  );

        if( start.getTime() >= end.getTime() )
            throw new Exception( "Incorrect range: start is greate or equal than end" );
    }

    @Override
    public String getValue()
    {
        Date dt = new Date();
        long res = ThreadLocalRandom.current().nextLong( start.getTime(), end.getTime() );
        dt.setTime( res );
        return formatter.format( dt );
    }

}
