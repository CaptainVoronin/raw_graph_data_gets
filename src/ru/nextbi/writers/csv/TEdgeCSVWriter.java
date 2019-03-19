package ru.nextbi.writers.csv;

import ru.nextbi.generation.GraphObjectProperty;
import ru.nextbi.model.*;
import ru.nextbi.writers.IEdgeWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class TEdgeCSVWriter implements IEdgeWriter
{
    File dir;
    String filename;
    char delimiter;
    TEdgeSerializer serializer;
    TEdgeDescription ted;
    FileWriter fw;
    int flushLimit;
    int count;

    public TEdgeCSVWriter(File targetDir, GraphElementDescription ted, char delimiter ) throws IOException {
        dir = targetDir;
        this.filename = targetDir.getPath() + File.separator + ted.getClassName() + ".csv";
        this.delimiter = delimiter;
        this.ted = ( TEdgeDescription ) ted;
        serializer = new TEdgeSerializer ();
        serializer.setDelimiter( delimiter );
        File f = new File( filename );
        fw = new FileWriter( f );
        count = 0;
        flushLimit = 10000;
    }

    public void writeElement( BaseEdge edge ) throws Exception {
        fw.write( serializer.edgeToString( ted , edge ));
    }

    /**
     * Пишет заголовок файла.
     * ID, from, to и потом остальные свойства
     * @throws IOException
     */
    public void writeHeader( ) throws IOException {
        StringBuilder st = new StringBuilder();

        st.append( "from" ).append( delimiter );
        st.append( "to" ).append( delimiter );

        Map<String, GraphObjectProperty > props = ted.getProperties();

        for( String key : props.keySet() )
            st.append( props.get( key ).name ).append( delimiter );
        st.deleteCharAt( st.length() - 1 );
        st.append( '\n' );

        fw.write( st.toString() );
        if( count >= flushLimit )
        {
            fw.flush();
            count = 0;
        }
        else
            count++;
    }

    public void close()
    {
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
