/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public class Table {

	private String schema;
	private String name;
	private String className;
	private List<Field> fields = new ArrayList<Field>();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

}
