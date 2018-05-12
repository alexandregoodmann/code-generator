/**
 *
 * Ferreira
 * 11 de mai de 2018
 */
package br.com.goodmann.util;

/**
 * @description
 * @author Ferreira
 * @date 11 de mai de 2018
 */
public class StringUtil {

	public static String formatClassName(String table) {
		String[] vet = table.split("_");
		String ret = "";
		for (String s : vet) {
			ret = ret + upper(s);
		}
		return ret;
	}

	public static String formatType(String type) {
		if (type.contains("VARCHAR")) {
			type = "VARCHAR";
		} else if (type.contains("CHAR")) {
			type = "CHAR";
		}
		return type;
	}

	public static String formatFieldName(String field) {
		String[] vet = field.toLowerCase().split("_");
		String ret = vet[0];
		for (int i = 1; i < vet.length; i++) {
			ret = ret + upper(vet[i]);
		}
		return ret;
	}

	public static String upper(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

}
