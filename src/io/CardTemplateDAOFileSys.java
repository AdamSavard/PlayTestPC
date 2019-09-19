package io;

import java.util.List;

import cards.CardTemplate;

public class CardTemplateDAOFileSys implements CardTemplateDAO {
	
	@Override
	public CardTemplate read(String templateName){
		CardTemplate output = GsonFactory.getGson().fromJson(FileHandler.read(Paths.TEMPLATE_PATH + templateName + ".ccg"), CardTemplate.class);
		output.setName(templateName);
		return output;
	}

	@Override
	public boolean write(String templateName, CardTemplate template, boolean overwrite){
		template.buildOrderedIdList();
		return FileHandler.write(Paths.TEMPLATE_PATH + templateName + ".ccg", GsonFactory.getGson().toJson(template).toString(), overwrite);
	}

	@Override
	public List<String> list(){
		List<String> output = FileHandler.allFileNames(Paths.TEMPLATE_PATH);
		for(int i = 0; i < output.size(); i++){
			output.set(i, output.get(i).substring(0, output.get(i).indexOf('.')));
		}
		return output;
	}

}
