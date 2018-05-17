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
public class Schema {

	private String schema;
	private String packageName;
	private List<Table> tables = new ArrayList<Table>();

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

}
