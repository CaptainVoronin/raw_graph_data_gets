package ru.nextbi.writers.csv;

import ru.nextbi.generation.GraphObjectProperty;
import ru.nextbi.model.BaseVertex;
import ru.nextbi.model.Link;
import ru.nextbi.model.VertexDescription;

import java.util.List;
import java.util.Map;

public class VertexSerializer {
    public static String NULL_ALIAS = "@@numm@@@@--^6%()-####@@@@";
    char delimiter;
    private String nullValue;

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    public String vertexToString(VertexDescription vd, BaseVertex v) throws Exception {
        StringBuilder st = new StringBuilder();

        // Свойства
        Map<String, GraphObjectProperty> props = vd.getProperties();
        Map<String, String> values = v.getProperties();

        for (String name : props.keySet())
            st.append(values.get(name)).append(delimiter);

        st.deleteCharAt(st.length() - 1);
        st.append('\n');

        return st.toString();
    }

    public void setNullValueSequence(String nullValue) {
        this.nullValue = nullValue;
    }
}
