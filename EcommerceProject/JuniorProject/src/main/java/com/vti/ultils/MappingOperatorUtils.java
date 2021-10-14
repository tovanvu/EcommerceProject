package com.vti.ultils;

import java.util.HashMap;

public class MappingOperatorUtils {
	@SuppressWarnings("serial")
	private static final HashMap<String, String> map = new HashMap<>() {
		{
			put("eq", "==");
			put("gt", ">");
			put("ge", ">=");
			put("le", "<=");
			put("lt", "<");
		}
	};

	public static String get(String key) {
		return map.get(key);
	}
}
