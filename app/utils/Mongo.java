package utils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.cache.EntityCache;

public class Mongo {

    private static final Morphia morphia = new Morphia();

    private static final Datastore datastore = morphia.createDatastore(new MongoClient(), "react-app");
    
    private static final Mapper mapper = new Mapper();
    
    private static final EntityCache cache = mapper.createEntityCache();
    
    public static Datastore datastore() {
        return datastore;
    }
    
    public static <T> T fromDBObject(Class<T> t, BasicDBObject dbo) {
    	return mapper.fromDBObject(t, dbo, cache);
    }
    
}
