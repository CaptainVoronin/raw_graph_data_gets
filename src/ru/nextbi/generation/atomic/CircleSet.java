package ru.nextbi.generation.atomic;

public class CircleSet extends OptionChooser {
    int counter ;

    public CircleSet()
    {
        super();
        counter = 0;
    }
    @Override
    public String getValue()
    {
        if( counter == options.size() - 1 )
            counter = 0;
        return options.get( ++counter );
    }

}
