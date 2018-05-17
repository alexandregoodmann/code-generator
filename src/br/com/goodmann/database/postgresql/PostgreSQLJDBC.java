/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann.database.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.goodmann.generator.Field;
import br.com.goodmann.generator.Table;
import br.com.goodmann.util.StringUtil;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public class PostgreSQLJDBC {

	private String user;
	private String password;
	private String url;
	private Connection connection = null;
	private PostGreTypes types = new PostGreTypes();

	public PostgreSQLJDBC(String user, String password, String url) {
		this.user = user;
		this.password = password;
		this.url = url;
	}

	public List<String> getSchemas(String[] ignoreSchema) {

		List<String> list = new ArrayList<String>();

		try {
			ResultSet set = this.connection.getMetaData().getSchemas();
			while (set.next()) {
				String schema = set.getString("TABLE_SCHEM");
				List<String> ignore = Arrays.asList(ignoreSchema);
				if (!ignore.contains(schema)) {
					list.add(schema);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Table> getTables(List<String> schems, List<String> ignoreFields) {

		List<Table> tables = new ArrayList<Table>();

		try {
			// create list of tables for each schema
			for (String schem : schems) {
				ResultSet rsTables = connection.getMetaData().getTables(null, schem, null, new String[] { "TABLE" });
				while (rsTables.next()) {
					String tableName = rsTables.getString("TABLE_NAME");
					Table table = new Table();
					table.setSchema(schem);
					table.setName(tableName.toUpperCase());
					table.setClassName(StringUtil.formatClassName(tableName));

					// Get all field for each table
					ResultSet rsColumns = connection.getMetaData().getColumns(null, schem, tableName, null);
					while (rsColumns.next()) {
						String sfield = rsColumns.getString("COLUMN_NAME");
						if (!ignoreFields.contains(sfield.toLowerCase())) {
							String typeName = rsColumns.getString("TYPE_NAME");
							Field field = new Field();
							field.setName(StringUtil.formatFieldName(sfield));
							field.setPostgreType(this.types.getType(typeName));
							table.getFields().add(field);
						}
					}
					tables.add(table);
					rsColumns.close();
				}
				rsTables.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tables;
	}

	private void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			this.connection = (Connection) DriverManager.getConnection(this.url, this.user, this.password);
			// JOptionPane.showMessageDialog(null, "Conexão realizada com Sucesso!");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void disConnect() {
		try {
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String user = "postgres";
		String password = "admin";
		String url = "jdbc:postgresql://localhost:5432/LabVision_V0.36";
		String[] ignoreSchema = { "public", "pg_catalog", "information_schema" };
		String[] ignoreFields = { "id", "creation_time", "modification_time", "version" };

		PostgreSQLJDBC post = new PostgreSQLJDBC(user, password, url);

		post.connect();

		List<String> schems = post.getSchemas(ignoreSchema);
		List<Table> tables = post.getTables(schems, Arrays.asList(ignoreFields));
		for (Table t : tables) {
			for (Field f : t.getFields()) {
				System.out.println("--->>>" + t.getSchema() + "--->>" + t.getName() + "--->" + f.getName());
			}
		}

		post.disConnect();
	}
}
