package ru.nextbi.writers;

import ru.nextbi.generation.GraphObjectProperty;
import ru.nextbi.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TEdgeCSVWriter {
    File dir;
    String filename;
    char delimiter;
    TEdgeSerializer serializer;
    TEdgeDescription ted;
    FileWriter fw;

    public TEdgeCSVWriter( File targetDir, TEdgeDescription ted, char delimiter ) throws IOException {
        dir = targetDir;
        this.filename = ted.getClassName() + File.separator + ".csv";
        this.delimiter = delimiter;
        this.ted = ted;
        serializer = new TEdgeSerializer ();
        serializer.setDelimiter( delimiter );
        File f = new File( dir.getPath() + File.separator + filename );
        fw = new FileWriter( f );
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
