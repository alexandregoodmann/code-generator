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

	public static String formatPackage(String spackage) {
		String[] vet = spackage.split("_");
		String ret = "";
		for (String s : vet) {
			ret = ret + s;
		}
		return ret.toLowerCase();

	}

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

	/**
	 * Parse from field name on database table to a java propertie name
	 * @param field
	 * @return
	 */
	public static String formatPropertieName(String field) {
		String[] vet = field.toLowerCase().split("_");
		String ret = vet[0];
		for (int i = 1; i < vet.length; i++) {
			ret = ret + upper(vet[i]);
		}
		return ret;
	}

	/**
	 * Makes the first letter of each word in UpperCase
	 * 
	 * @param str
	 * @return
	 */
	public static String upper(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

}
