package ru.nextbi.generation.atomic;

import ru.nextbi.GTDGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EMailAddressGenerator extends BaseDictionaryGenerator {
    static String SERVERS_KEY = "servers";
    static String MAIL_BOX_NAME_KEY = "name-file";
    static String defaultDomain = "ru";
    String namesDKey;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{

        super.setParams( config, params );

        if( params.containsKey( SERVERS_KEY ) )
            createServers( params.get( SERVERS_KEY ) );

        if( params.containsKey( MAIL_BOX_NAME_KEY ) )
            createNames( config.get(GTDGenerator.CURRENT_DIR_KEY ), params.get( MAIL_BOX_NAME_KEY ) );
    }

    private void createNames(String dir, String filename ) throws Exception{
        namesDKey = GeneratorUtils.makeAbsolutePath( dir, filename );
        addDictionary(namesDKey);
    }

    private void createServers(String s) throws IOException {
        List<String> items;
        String[] parts = s.split( "," );
        items = Arrays.asList( parts );
        Dictionary servers = new Dictionary( items );
        addDictionary( "servers", servers );
    }

    @Override
    public String getValue() throws IOException, DictionaryNotInitiaqlizedException{
        String name, server;
        Dictionary servers = getDictionary( "servers" );
        Dictionary names = getDictionary(namesDKey);

        if( servers != null )
            server = servers.getRndValue();
        else
            server = StringGenerator.randomNumeric( 8 ) + defaultDomain;

        if( names == null )
            name = StringGenerator.randomNumeric( 8 );
        else
            name = names.getRndValue();

        return name + "@" + server;
    }

}
