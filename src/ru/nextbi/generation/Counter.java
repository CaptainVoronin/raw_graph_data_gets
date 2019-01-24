package ru.nextbi.generation;

import ru.nextbi.generation.atomic.IntGenerator;

public class Counter{
    int limit;
    int count;

    public Counter ( int min, int max )
    {
        calcCount( min, max );
        count = 0;
        limit = 0;
    }

    public Counter (  )
    {
        count = 0;
        limit = 0;
    }

    public boolean hasNext()
    {
        boolean result = false;
        if( count < limit )
        {
            result = true;
            count++;
        }
        return result;
    }

    void calcCount( int min, int max )
    {
        // Создавать в рамках того количества, который указан в описании дочернего узла
        if (min < 0) {
            // Если min меньше нуля, это значит, что
            // надо создавать точно max элементов
            limit = max;
        } else {
            // Если дочек большей одной, то получить их количество
            // как случайное число
            limit = IntGenerator.getInt(min, max);
        }
    }

    public void init( int min, int max )
    {
        calcCount( min, max );
        count = 0;
    }
}
