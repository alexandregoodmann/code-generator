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
import br.com.goodmann.generator.Schema;
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

	/**
	 * 
	 * @param ignoreSchema
	 *            - List of schemas to be ignored
	 * @param basePackage
	 *            - base package - com.comnay.model
	 * @return
	 */
	public List<Schema> getSchemas(String[] ignoreSchema, String basePackage, String[] ignoreFields) {

		List<Schema> list = new ArrayList<Schema>();

		try {
			ResultSet set = this.connection.getMetaData().getSchemas();
			while (set.next()) {
				String schema = set.getString("TABLE_SCHEM");
				List<String> ignore = Arrays.asList(ignoreSchema);
				if (!ignore.contains(schema)) {
					Schema schem = new Schema();
					schem.setPackageName(basePackage);
					schem.setSchema(schema);
					schem.getTables().addAll(this.getTables(schema, ignoreFields));
					list.add(schem);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private List<Table> getTables(String schem, String[] ignoreFields) throws SQLException {
		List<Table> tables = new ArrayList<Table>();
		ResultSet rsTables = connection.getMetaData().getTables(null, schem, null, new String[] { "TABLE" });
		while (rsTables.next()) {
			String tableName = rsTables.getString("TABLE_NAME");
			Table table = new Table();
			table.setTable(tableName);
			table.setClazzTable(tableName.toUpperCase());
			table.setClazz(StringUtil.formatClassName(tableName));
			table.getProperties().addAll(this.getFields(schem, tableName, ignoreFields));
			tables.add(table);
		}
		return tables;
	}

	private List<Field> getFields(String schem, String tableName, String[] ignoreFields) throws SQLException {
		List<Field> list = new ArrayList<Field>();
		ResultSet rsColumns = connection.getMetaData().getColumns(null, schem, tableName, null);
		while (rsColumns.next()) {
			String fieldName = rsColumns.getString("COLUMN_NAME");
			String fieldType = rsColumns.getString("TYPE_NAME");
			if (!Arrays.asList(ignoreFields).contains(fieldName)) {
				Field f = new Field();
				f.setFieldName(fieldName);
				f.setFieldType(fieldType);
				f.setJavaType(this.types.getType(fieldType));
				f.setPropertieName(StringUtil.formatPropertieName(fieldName));
				list.add(f);
			}
		}
		return list;
	}

	public void connect() {
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

	public void disConnect() {
		try {
			this.connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
