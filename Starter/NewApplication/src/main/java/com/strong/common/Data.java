package com.strong.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Data {
	//public Map<String, List<Map>> table = new LinkedHashMap<String, List<Map>>();
	public LinkedHashMap<String, List<LinkedHashMap<String, Object>>> data = new LinkedHashMap<String, List<LinkedHashMap<String, Object>>>();
	
	public boolean hasData(){
		return data.containsKey("table");
	}	
	
	public String getTableField(int tableIndex, int rowIndex, String columnName) {
		String key = tableIndex == 0 ? "table" : "table" + Integer.toString(tableIndex);
		return data.get(key).get(rowIndex).get(columnName.toLowerCase()).toString();
	}
	
	public Object getTableFieldObject(int tableIndex, int rowIndex, String columnName) {
		String key = tableIndex == 0 ? "table" : "table" + Integer.toString(tableIndex);
		return data.get(key).get(rowIndex).get(columnName.toLowerCase());
	}
	
	//Get field if only one table one row
	public String getTableField(String columnName) {
		return this.getTableField(0, 0, columnName);
	}
	
	public Object getTableFieldObject(String columnName) {
		return this.getTableFieldObject(0, 0, columnName);
	}	
}
