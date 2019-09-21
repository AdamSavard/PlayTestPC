package io;

import java.util.List;
import java.util.Map;

public interface ExcelDAO {
	
    public abstract Map<String, Object[]> read(String fileName);
    
	public abstract boolean write(String fileName, List<List<Object>> data);
}
