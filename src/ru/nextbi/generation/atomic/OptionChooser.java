package ru.nextbi.generation.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OptionChooser implements IGenerator
{
    protected List<String> options;
    Random rand;
    public OptionChooser()
    {
        options = null;
        rand = new Random();
    }

    @Override
    public void setParamString(String rawParams) throws Exception{
      Map<String, String> params = GeneratorUtils.parseParams( rawParams );
      if( !params.containsKey( "set" ) )
          throw new Exception( "Options must be set");
      String[] ops = params.get( "set" ).split( "," );
      options = new ArrayList<String>( );
      for( int i = 0; i < ops.length; i++ )
          options.add( ops[i] );
    }

    @Override
    public String getValue()
    {
        if( options.size() == 1 )
            return options.get(0);
        else
            return options.get( rand.nextInt( options.size() ));
    }
}
