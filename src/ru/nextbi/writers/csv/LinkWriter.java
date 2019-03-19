package ru.nextbi.writers.csv;

import ru.nextbi.writers.ALinkWriter;
import ru.nextbi.writers.ILinkWriter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class LinkWriter extends ALinkWriter{

    File targetDir;
    FileWriter writer;
    char delimiter;
    StringBuilder stringBuilder;

    public LinkWriter(File targetDir, String parent, String child, char delimiter )
    {
        super( parent, child );
        this.targetDir = targetDir;
        this.delimiter = delimiter;
        stringBuilder = new StringBuilder();
    }

    public void init( ) throws IOException
    {
        String filename = targetDir.getPath() + File.separator + getLinkName() + ".csv";
        writer = new FileWriter( filename );
        stringBuilder.append( getParentClassName() ).append( "_id" ).append( delimiter ).append( getChildClassName() ).append( "_id" ).append( '\n' );
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
