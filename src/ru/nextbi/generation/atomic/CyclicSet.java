package ru.nextbi.generation.atomic;


import java.io.IOException;

public class CyclicSet extends OptionChooser {
    int counter ;

    public CyclicSet()
    {
        super();
        counter = 0;
    }
    @Override
    public String getValue() throws IOException, ProviderNotInitiaqlizedException{

        if( counter >= options.getSize() - 1 )
            counter = -1;

        return options.getValue( ++counter );
    }

}
