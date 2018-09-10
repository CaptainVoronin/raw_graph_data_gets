package ru.nextbi.generation.atomic;

import ru.nextbi.GTDGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EMailAddressGenerator implements IGenerator
{
    static String SERVERS_KEY = "servers";
    static String MAIL_BOX_NAME_KEY = "name-file";
    static String CASH_KEY = "noCash";
    Dictionary servers;
    Dictionary names;
    static String defaultDomain = "ru";
    boolean noCash;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{

        if( !params.containsKey( CASH_KEY ) )
            noCash = !Boolean.parseBoolean( params.get( CASH_KEY ) );
        else
        {
            String buff = config.get( GTDGenerator.CASH_DEFAULT );
            if( buff != null )
                noCash = !Boolean.parseBoolean( config.get( GTDGenerator.CASH_DEFAULT ) );
            else
                noCash = true;
        }


        if( params.containsKey( SERVERS_KEY ) )
            createServers( params.get( SERVERS_KEY ) );

        if( params.containsKey( MAIL_BOX_NAME_KEY ) )
            createNames( config.get(GTDGenerator.CURRENT_DIR_KEY ),  params.get( MAIL_BOX_NAME_KEY ) );

    }

    private void createNames(String dir, String filename ) throws Exception{
        String path = GeneratorUtils.makeAbsolutePath( dir, filename );
        names = new Dictionary( path, noCash );
    }

    private void createServers(String s)
    {
        List<String> items;
        String[] parts = s.split( "," );
        items = Arrays.asList( parts );
        servers = new Dictionary( items );
    }

    @Override
    public String getValue() throws IOException, DictionaryNotInitiaqlizedException{
        if( servers == null )
            return StringGenerator.randomNumeric( 8 )+ "@" + StringGenerator.randomNumeric( 8 ) + defaultDomain;
        else
            return getAddressName() + "@" + servers.getRndValue();
    }

    @Override
    public void initialize() throws Exception{

        if(  servers != null )
            servers.initialize();

        if( names != null )
            names.initialize();
    }

    @Override
    public void unInialize(){
        try {
            if( servers != null )
                servers.close();

            if( names != null )
                names.close();
        } catch( Exception e  )
        {
            e.printStackTrace();
        }

    }

    public String getAddressName() throws IOException, DictionaryNotInitiaqlizedException{
        return names.getRndValue();
    }
}
