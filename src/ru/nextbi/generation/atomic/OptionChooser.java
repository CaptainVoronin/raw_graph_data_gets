package ru.nextbi.generation.atomic;

import java.io.IOException;
import java.util.*;

public class OptionChooser extends BaseDictionaryGenerator
{
    protected Dictionary options;
    //Random rand;
    public OptionChooser()
    {
    }

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{
      super.setParams( config, params );
      if( params.containsKey( "set" ) ) {
          String[] ops = params.get("set").split(",");
          options = new Dictionary( Arrays.asList( ops ) );
          if (options.getSize() == 0)
              throw new Exception("Error. Set contains no elements");
          else if (options.getSize() == 1)
              System.out.println("Warning. Set contains only one element " + options.getValue(0));
      }
    }

    @Override
    public String getValue() throws IOException, ProviderNotInitiaqlizedException{
        if( options.getSize() == 1 )
            return options.getValue(0);
        else
            return options.getRndValue( );
    }
}
