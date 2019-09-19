package io;

import java.util.List;

import cards.CardTemplate;

public interface CardTemplateDAO {

    public abstract CardTemplate read(String templateName);
	
	public abstract boolean write(String templateName, CardTemplate template, boolean overwrite);
	
	public abstract List<String> list();
}
