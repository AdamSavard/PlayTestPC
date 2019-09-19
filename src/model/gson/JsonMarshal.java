package model.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonMarshal implements JsonSerializer<Object>, JsonDeserializer<Object> {

private static final String CLASS_META_KEY = "CLASS_META_KEY";

	@Override
	public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext){
	    JsonObject jsonObj = jsonElement.getAsJsonObject();
	    String className = jsonObj.get(CLASS_META_KEY).getAsString();
	    try {
	        Class<?> cls = Class.forName(className);
	        return jsonDeserializationContext.deserialize(jsonElement, cls);
	    } catch (ClassNotFoundException e) {
	        throw new JsonParseException(e);
	    }
	}

	@Override
	public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
	    JsonElement jsonEle = jsonSerializationContext.serialize(object, object.getClass());
	    jsonEle.getAsJsonObject().addProperty(CLASS_META_KEY,
	            object.getClass().getCanonicalName());
	    return jsonEle;
	}

}
