package ru.nextbi.generation.atomic;

public class CyclicSet extends OptionChooser {
    int counter ;

    public CyclicSet()
    {
        super();
        counter = 0;
    }
    @Override
    public String getValue()
    {
        if( counter >= options.size() - 1 )
            counter = -1;

        return options.get( ++counter );
    }

}
