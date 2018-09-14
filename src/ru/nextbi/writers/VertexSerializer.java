package ru.nextbi.writers;

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

        // Если указан родитель
        if (v.getParent() != null) {
            String parentID = v.getParent();
            if (parentID == null || parentID.trim().length() == 0)
                throw new Exception("Incorrect parent id");
            st.append(v.getParent()).append(delimiter);
        } else if (vd.getParentClassName() != null)
            throw new Exception("Link violation!");

        // Свойства
        Map<String, GraphObjectProperty> props = vd.getProperties();
        Map<String, String> values = v.getProperties();

        for (String name : props.keySet()) {
            st.append(values.get(name)).append(delimiter);
        }

        for (Link link : vd.getLinks()) {
            for (Link.Target target : link.getTargets()) {
                try {

                    String value = v.getLinks().get(target.className);
                    if (!value.equals(VertexSerializer.NULL_ALIAS)) {
                        st.append(value).append(delimiter);
                    } else
                        st.append(nullValue).append(delimiter);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }

        st.deleteCharAt(st.length() - 1);
        st.append('\n');

        return st.toString();
    }

    public void setNullValueSequence(String nullValue) {
        this.nullValue = nullValue;
    }
}
