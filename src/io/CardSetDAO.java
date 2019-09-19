package io;

import java.util.List;

import cards.CardSet;

public interface CardSetDAO {
	
	public abstract CardSet read(String setName);
	
	public abstract boolean write(String setName, Object set, boolean overwrite);
	
	public abstract List<String> list();

}
