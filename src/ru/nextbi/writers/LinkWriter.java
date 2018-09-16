package ru.nextbi.writers;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LinkWriter {

    File targetDir;
    String parentName;
    String childName;
    FileWriter writer;
    char delimiter;
    StringBuilder stringBuilder;

    public LinkWriter(File targetDir, String parent, String child, char delimiter )
    {
        this.targetDir = targetDir;
        parentName = parent + "_id";
        childName = child + "_id";
        this.delimiter = delimiter;
        stringBuilder = new StringBuilder();
    }

    public void init( ) throws IOException
    {
        String filename = targetDir.getPath() + File.separator + parentName + "-" + childName + ".csv";
        writer = new FileWriter( filename );
        stringBuilder.append( parentName ).append( delimiter ).append( childName ).append( '\n' );
        writer.write( stringBuilder.toString() );
        stringBuilder.setLength( 0 );
    }

    public void write( String parent, String child ) throws IOException {
        writer.write( parent + delimiter + child + "\n" );
    }

    public void close() {
        if( writer != null ) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
