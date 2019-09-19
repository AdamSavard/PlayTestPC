package io;

import java.awt.Color;
import java.util.List;

import com.google.gson.internal.StringMap;

import cards.CardSet;

public class CardSetDAOFileSys implements CardSetDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public CardSet read(String setName){
		CardSet output = GsonFactory.getGson().fromJson(FileHandler.read(Paths.SET_PATH + setName + ".set"), CardSet.class);

		Object newData = null;
		StringMap<Object> tempStringMap;
		// Clean data
		for(int id : output.getCardIds()){
			for(String key : output.getCardByID(id).getData().keySet()){
				if(output.getCardByID(id).getData().get(key) instanceof StringMap){
					tempStringMap = (StringMap<Object>)output.getCardByID(id).getData().get(key);
					// If a field is a color, fix it
					if(tempStringMap.containsKey("value") && tempStringMap.containsKey("falpha")){
						newData = GsonFactory.getGson().fromJson(tempStringMap.toString(),Color.class);
					}
							output.getCardByID(id).setData(key, newData);
				}
			}
		}
		
		output.refresh();
		return output;
	}

	@Override
	public boolean write(String setName, Object set, boolean overwrite){
		return FileHandler.write(Paths.SET_PATH + setName + ".set", GsonFactory.getGson().toJson(set).toString(), overwrite);
	}

	@Override
	public List<String> list(){
		List<String> output = FileHandler.allFileNames(Paths.SET_PATH);
		for(int i = 0; i < output.size(); i++){
			output.set(i, output.get(i).substring(0, output.get(i).indexOf('.')));
		}
		return output;
	}

}
