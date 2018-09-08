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

    public TEdgeCSVWriter( File targetDir, String filename, char delimiter )
    {
        dir = targetDir;
        this.filename = filename;
        this.delimiter = delimiter;
    }

    public void write(TEdgeDescription ted, List<BaseEdge> edges ) throws Exception
    {
        System.out.println( "Start writing " + ted.getClassName() );
        File f = new File( dir.getPath() + "/" + filename );
        FileWriter fw = new FileWriter( f );
        writeHeader(  fw, ted );
        writeElements( fw, ted, edges );
        fw.close();
        System.out.println( "Done" );
    }

    private void writeElements(FileWriter fw, TEdgeDescription ted, List<BaseEdge> edges) throws Exception {
        TEdgeSerializer vs = new TEdgeSerializer();
        vs.setDelimiter( delimiter );
        for( BaseEdge v : edges )
            fw.write( vs.edgeToString( ted , v));
    }

    /**
     * Пишет заголовок файла.
     * ID, from, to и потом остальные свойства
     * @param fw
     * @param desc
     * @throws IOException
     */
    private void writeHeader(FileWriter fw, TEdgeDescription desc) throws IOException {
        StringBuilder st = new StringBuilder();

        st.append( "id" ).append( delimiter );
        st.append( "from" ).append( delimiter );
        st.append( "to" ).append( delimiter );

        Map<String, GraphObjectProperty > props = desc.getProperties();

        for( String key : props.keySet() )
            st.append( props.get( key ).name ).append( delimiter );
        st.deleteCharAt( st.length() - 1 );
        st.append( '\n' );

        fw.write( st.toString() );
    }
}
