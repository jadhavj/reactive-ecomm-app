package utils;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by jujadhav on 8/1/16.
 */
public class Mongo {

    private static final Morphia morphia = new Morphia();

    private static final Datastore datastore = morphia.createDatastore(new MongoClient(), "react-app");

    public static Datastore datastore() {
        return datastore;
    }
}
