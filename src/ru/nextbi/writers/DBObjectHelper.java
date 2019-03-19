package ru.nextbi.writers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sun.javafx.geom.Edge;
import org.bson.Document;
import ru.nextbi.generation.GraphObjectProperty;
import ru.nextbi.model.*;

import java.util.Map;

public class DBObjectHelper {
    private DBObjectHelper()
    {

    }

    public static final Document edgeToDBObject(GraphElementDescription ted, Map<String, String> values)
    {
        // Свойства
        Map<String, GraphObjectProperty> props = ted.getProperties();

        Document doc = new Document();

        for (String name : props.keySet() )
            doc.append( name, values.get( name ) );

        return doc;
    }

    public static Document linkToObject(String parentClassName, String childClassName, String parent, String child)
    {
        Document doc = new Document();
        return doc.append( parentClassName, parent ).append( childClassName, child  );
    }
}
