/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann.generator;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public enum ModelTag {
	PACKAGE("<package>"), TABLE_NAME("<table_name>"), CLASS_NAME("<class_name>"), PROPERTIES(
			"<properties>"), GETTERS_SETTERS(
					"<getters_setters>"), FIELD_NAME("<field_name>"), TYPE("<type>"), SET("<set>"), GET("<get>");

	public String tag;

	private ModelTag(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}