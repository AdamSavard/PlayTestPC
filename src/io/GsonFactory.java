package io;

import model.gson.JsonMarshal;
import cards.components.CardComponent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

	private static Gson gsonSingle;
	
    protected static Gson getGson(){
        if (null == gsonSingle) {
            synchronized (Gson.class){
            	if (null != gsonSingle) return gsonSingle;
            	GsonBuilder builder = new GsonBuilder();
        		builder.registerTypeAdapter(CardComponent.class, new JsonMarshal());
            	gsonSingle = builder.create();
            }
        }
        return gsonSingle;
    }
}
