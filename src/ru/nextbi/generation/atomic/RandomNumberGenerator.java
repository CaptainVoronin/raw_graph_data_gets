package ru.nextbi.generation.atomic;

import java.util.Map;
import java.util.Random;

/**
 * Генератора целых чисел внутри заданного диапазона
 */
public class RandomNumberGenerator implements IGenerator
{
    int min;
    int max;

    final static Random rand = new Random();

    public RandomNumberGenerator(){
        min = 1;
        max = 100;
    };

    public final static int getInt( int min, int max )
    {
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void setParamString(String rawParams) throws Exception{
        Map<String,String> params = GeneratorUtils.parseParams( rawParams );
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
}
