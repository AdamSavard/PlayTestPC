package io;

import java.util.List;
import java.util.Map;

public class ExcelDAOFileSys implements ExcelDAO
{

	@Override
	public Map<String, Object[]> read(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean write(String fileName, List<List<Object>> data) {
		return FileHandler.writeExcel(fileName, data);
	}

}
