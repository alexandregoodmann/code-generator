/**
 *
 * Ferreira
 * 17 de mai de 2018
 */
package br.com.goodmann.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author Ferreira
 * @date 17 de mai de 2018
 */
public class Table {

	private String table;
	private String clazz;
	private String clazzTable;
	private List<Field> properties = new ArrayList<Field>();

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public List<Field> getProperties() {
		return properties;
	}

	public void setProperties(List<Field> properties) {
		this.properties = properties;
	}

	public String getClazzTable() {
		return clazzTable;
	}

	public void setClazzTable(String clazzTable) {
		this.clazzTable = clazzTable;
	}

}
