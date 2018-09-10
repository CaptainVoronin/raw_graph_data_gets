package ru.nextbi.generation.atomic;

import ru.nextbi.GTDGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DictionarySet extends BaseDictionaryGenerator {

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception
    {
        super.setParams( config, params );

        String path = params.get( "dictionary" );
        if( path != null )
        {
            path = GeneratorUtils.makeAbsolutePath( config.get(GTDGenerator.CURRENT_DIR_KEY  ), path );
            addDictionary( path );
        }
    }

    @Override
    public String getValue() throws Exception {
        return getDictionary().getRndValue();
    }
}