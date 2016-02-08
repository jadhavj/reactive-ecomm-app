package utils;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

public class Mongo {

    private static final Morphia morphia = new Morphia();

    private static final Datastore datastore = morphia.createDatastore(new MongoClient(new MongoClientURI("mongodb://CloudFoundry_iilvoggr_6v94fuh0_gt47v18a:2xg9hSA41Xw3fucUtpFiEiI72yPFiIBF@ds055565.mongolab.com:55565/CloudFoundry_iilvoggr_6v94fuh0")), "CloudFoundry_iilvoggr_6v94fuh0");
    
    public static class ObjectIdSerializer extends JsonSerializer<ObjectId> {

		@Override
		public void serialize(ObjectId value, JsonGenerator jgen, SerializerProvider arg2)
				throws IOException, JsonProcessingException {
			jgen.writeString(value.toString());
		}
    }


    @SuppressWarnings("serial")
	public static class CustomObjectMapper extends ObjectMapper {

        public CustomObjectMapper() {
            SimpleModule module = new SimpleModule("ObjectIdmodule");
            module.addSerializer(ObjectId.class, new ObjectIdSerializer());
            this.registerModule(module);
        }

    }
    
    private static final CustomObjectMapper mapper = new CustomObjectMapper();

    private static com.mongodb.async.client.MongoClient aClient = com.mongodb.async.client.MongoClients.create("mongodb://CloudFoundry_iilvoggr_6v94fuh0_gt47v18a:2xg9hSA41Xw3fucUtpFiEiI72yPFiIBF@ds055565.mongolab.com:55565/CloudFoundry_iilvoggr_6v94fuh0");
    
    private static com.mongodb.async.client.MongoDatabase aDatabase = aClient.getDatabase("CloudFoundry_iilvoggr_6v94fuh0");
    
    
    
    static {
    	init();
    }
    
    public static void init() {
    	mapper.getSerializationConfig().withSerializationInclusion(Include.NON_NULL);
    	mapper.getSerializationConfig().withSerializationInclusion(Include.NON_EMPTY);
    	mapper.setSerializationInclusion(Include.NON_DEFAULT);
    }
    
    public static Datastore datastore() {
        return datastore;
    }

    public static <T> T getEntityFromJson(String json, Class<T> t) {
    	try {
			return mapper.readValue(json, t);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static <T> T getEntityFromDbo(BasicDBObject dbo, Class<T> t) {
    	try {
			return mapper.readValue(dbo.toJson(), t);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }

    public static String getEntityAsJson(Object t) {
    	try {
			return mapper.writeValueAsString(t);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }

    public static BasicDBObject getEntityAsDbo(Object t) {
    	try {
			return (BasicDBObject) JSON.parse(mapper.writeValueAsString(t));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public static com.mongodb.async.client.MongoDatabase asyncDatabase() {
    	return aDatabase;
    }
}
