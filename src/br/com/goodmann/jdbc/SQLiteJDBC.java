/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.goodmann.generator.Field;
import br.com.goodmann.generator.Table;
import br.com.goodmann.util.StringUtil;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public class SQLiteJDBC {

	public SQLiteJDBC(String databaseFile, String schema) {
		this.databaseFile = databaseFile;
		this.schema = schema;
	}

	/// workspace/code-generator/resources/printer.db
	private String databaseFile;

	private String schema;

	public List<Table> getTables(List<String> ignoreFields) throws ClassNotFoundException, SQLException {

		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseFile);

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
