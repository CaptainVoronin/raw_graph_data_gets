package ru.nextbi.generation.atomic;

import ru.nextbi.generation.Graph;
import ru.nextbi.generation.GraphObjectProperty;

import java.util.Map;
import java.util.Random;

/**
 * Генератора целых чисел внутри заданного диапазона
 */
public class IntGenerator extends AbstractGenerator
{
    int min;
    int max;

    final static Random rand = new Random();

    public IntGenerator(){
        min = 1;
        max = 100;
    }

    public static int getInt(int min, int max )
    {
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{
        String dummy = params.get( "min" );
        if( dummy != null )
            min = Integer.parseInt( dummy );

        dummy = params.get( "max" );
        if( dummy != null )
            max = Integer.parseInt( dummy );
    }

    @Override
    public String getValue(){
        return Integer.toString( getInt( min, max ) );
    }

    @Override
    public GraphObjectProperty.Type getDataType()
    {
        return GraphObjectProperty.Type.INT;
    }

}
