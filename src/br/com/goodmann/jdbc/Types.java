/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann.jdbc;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public enum Types {

	INTEGER("Integer"), VARCHAR("String"), DATETIME("Date"), INT("Integer"), TEXT("String"), CHAR("String"), BOOLEAN(
			"Boolean"), BLOB("String"), DOUBLE("Double");

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private Types(java.lang.String type) {
		this.type = type;
	}

	private static final Map<String, String> lookup = new HashMap<String, String>();

	static {
		for (Types d : Types.values())
			lookup.put(d.toString(), d.getType());
	}

	public static String get(String key) {
		return (String) lookup.get(key);
	}
}
