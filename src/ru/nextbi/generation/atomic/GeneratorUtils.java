package ru.nextbi.generation.atomic;

import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import java.util.regex.Pattern;

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

    public static String makeHash( Map<String, String> params ) throws NoSuchAlgorithmException{

        if( digest == null )
            digest = MessageDigest.getInstance( "MD5" );

        StringBuilder sb = new StringBuilder();

        for( String key : params.keySet() )
            sb.append( key ).append( params.get( key ) );

        digest.update( sb.toString().getBytes());
        return new String(digest.digest());
    }

    public static List<String> readDictionary( String path ) throws IOException{

            File f = new File( path );
            if( !f.exists() )
                throw new IOException( "Dictionary file not found. " + path );

            if( !f.isFile() )
                throw new IOException( "Dictionary is not a file." + path );

            BufferedReader br = new BufferedReader( new FileReader( path ) );
            String buff;
            List<String> items = new ArrayList<>();
            while( ( buff = br.readLine() ) != null  )
                items.add( buff );
            br.close();
            return items;
    }

    public static String makeAbsolutePath( String currentDir, String buff ) throws Exception {
        if( !Paths.get( buff ).isAbsolute() )
        {
            buff = currentDir + buff;
            if( !Paths.get( buff ).isAbsolute() )
                throw new Exception( "Can't make a path from " + currentDir + " and " + buff );
        }

        return buff;
    }
}
