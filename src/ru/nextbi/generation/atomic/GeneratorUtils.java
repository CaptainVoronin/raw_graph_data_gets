package ru.nextbi.generation.atomic;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;

public final class GeneratorUtils
{
    static MessageDigest digest;


    public  static String getRightPart( String raw )
    {
        String[] arr = raw.split( "=" );
        if( arr.length < 2 )
            throw new IllegalArgumentException ( "Incorrect argument specification" + raw );
        return arr[1].trim();
    }

    public static Map<String, String> parseParams(String rawParams) throws ParseException
    {
        Map<String, String> params = new HashMap<>();

        String buff = rawParams;
        if( buff == null )
            return params;

        String[] parts = buff.trim().split(" ");

        for (int i = 0; i < parts.length; i++) {
            String dummy = parts[i].trim();
            if( dummy.length() == 0 )
                continue;

            String[] pair = dummy.split("=");
            if( pair.length < 2 )
                params.put( pair[0], "true" );
            else if( pair.length == 2 )
                params.put( pair[0], pair[1] );
            else
            {
                params.put( pair[0], pair[1] );
                System.out.println( "It seems param the string contains excessive '=' character" );
            }
        }
        return params;
    }

    public static String makeHash( String buff ) throws NoSuchAlgorithmException{
        if( digest == null )
            digest = MessageDigest.getInstance( "MD5" );
        digest.update( buff.getBytes());
        return new String(digest.digest());
    }

    public static List<String> readDictionary( String path ) throws IOException{
            File f = new File( path );
            if( !f.exists() )
                throw new IOException( "Dictionary file not found" );

            if( !f.isFile() )
                throw new IOException( "Dictionary is not a file" );

            BufferedReader br = new BufferedReader( new FileReader( path ) );
            String buff;
            List<String> items = new ArrayList<>();
            while( ( buff = br.readLine() ) != null  )
                items.add( buff );
            br.close();
            return items;
    }
}
