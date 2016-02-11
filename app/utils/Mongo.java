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

    private static String host = play.Configuration.root().getString("mongo.host");
    private static String port = play.Configuration.root().getString("mongo.port");
    private static String user = play.Configuration.root().getString("mongo.user");
    private static String password = play.Configuration.root().getString("mongo.password");
    private static String db = play.Configuration.root().getString("mongo.db");

    private static final Datastore datastore = morphia.createDatastore(new MongoClient("localhost"), "react-app");
    
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

    private static com.mongodb.async.client.MongoClient aClient = com.mongodb.async.client.MongoClients.create();
    
    private static com.mongodb.async.client.MongoDatabase aDatabase = aClient.getDatabase("react-app");
    
    
    
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
