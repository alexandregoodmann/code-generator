/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann.database.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.goodmann.generator.Field;
import br.com.goodmann.generator.Table;
import br.com.goodmann.util.StringUtil;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public class SQLiteJDBC {

	public SQLiteJDBC(String url, String schema) {
		this.url = url;
		this.schema = schema;
	}

	/// workspace/code-generator/resources/printer.db
	private String url;

	private String schema;

	public List<Table> getTables(List<String> ignoreFields) throws ClassNotFoundException, SQLException {

		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection(this.url);

		List<Table> tables = new ArrayList<Table>();

		// get tables from schema
		ResultSet rsTables = connection.getMetaData().getTables(null, this.schema, null, null);
		while (rsTables.next()) {

			// details of tables
			Table table = new Table();
			table.setName(rsTables.getString("TABLE_NAME").toUpperCase());
			table.setClassName(StringUtil.formatClassName(rsTables.getString("TABLE_NAME")));
			tables.add(table);

			ResultSet rsColumns = connection.getMetaData().getColumns(null, this.schema, table.getName(), null);
			while (rsColumns.next()) {
				String sfield = rsColumns.getString("COLUMN_NAME");
				if (!ignoreFields.contains(sfield.toLowerCase())) {
					Field field = new Field();
					field.setName(StringUtil.formatFieldName(sfield));
					field.setType(Types.valueOf(StringUtil.formatType(rsColumns.getString("TYPE_NAME"))));
					table.getFields().add(field);
				}
			}
			rsColumns.close();
		}

		rsTables.close();
		connection.close();
		return tables;

	}
	
}
