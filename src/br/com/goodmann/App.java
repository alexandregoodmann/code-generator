/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.goodmann.generator.Field;
import br.com.goodmann.generator.ModelTag;
import br.com.goodmann.generator.Table;
import br.com.goodmann.jdbc.SQLiteJDBC;
import br.com.goodmann.jdbc.Types;
import br.com.goodmann.util.FileUtil;
import br.com.goodmann.util.StringUtil;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public class App {

	private List<String> buildProperties(List<Field> fields) {
		List<String> properties = new ArrayList<String>();
		for (Field field : fields) {
			properties.add("	@Column(name = \"" + field.getName() + "\")");
			properties.add("	private " + Types.get(field.getType().toString()) + " " + field.getName() + ";");
			properties.add("");
		}
		return properties;
	}

	private List<String> buildGettersSetters(List<Field> fields, List<String> templateGetterSetters) {

		List<String> gettersSetters = new ArrayList<String>();
		for (Field field : fields) {
			for (String line : templateGetterSetters) {
				String lin = line.replaceAll(ModelTag.TYPE.tag, field.getType().getType());
				lin = lin.replaceAll(ModelTag.GET.tag, "get" + StringUtil.upper(field.getName()));
				lin = lin.replaceAll(ModelTag.SET.tag, "set" + StringUtil.upper(field.getName()));
				lin = lin.replaceAll(ModelTag.FIELD_NAME.tag, field.getName());
				gettersSetters.add(lin);
			}
		}
		return gettersSetters;
	}

	private List<String> changeTags(Map<ModelTag, String> map, List<String> modelTemplate, List<Field> fields,
			List<String> templateGetterSetters) {
		List<String> list = new ArrayList<String>();
		for (String line : modelTemplate) {
			if (line.contains(ModelTag.PACKAGE.tag)) {
				list.add(line.replaceAll(ModelTag.PACKAGE.tag, map.get(ModelTag.PACKAGE)));
			} else if (line.contains(ModelTag.TABLE_NAME.tag)) {
				list.add(line.replaceAll(ModelTag.TABLE_NAME.tag, map.get(ModelTag.TABLE_NAME)));
			} else if (line.contains(ModelTag.CLASS_NAME.tag)) {
				list.add(line.replaceAll(ModelTag.CLASS_NAME.tag, map.get(ModelTag.CLASS_NAME)));
			} else if (line.contains(ModelTag.PROPERTIES.tag)) {
				list.addAll(this.buildProperties(fields));
			} else if (line.contains(ModelTag.GETTERS_SETTERS.tag)) {
				list.addAll(this.buildGettersSetters(fields, templateGetterSetters));
			} else {
				list.add(line);
			}
		}
		return list;
	}

	private void writeModel() throws ClassNotFoundException, SQLException, IOException {

		// input
		String url = "jdbc:sqlite:/minitube/PRINTER_PROJECT/printer-api/printer-api/src/main/resources/printer.db";
		String schema = "main";
		String modelTemplate = "D:\\workspace\\code-generator\\resources\\template\\model.template";
		String gettersSettersTemplate = "D:\\workspace\\code-generator\\resources\\template\\getters_setters.template";
		String spackage = "de.minitube.printerapi";
		List<String> ignoreFields = new ArrayList<String>();
		ignoreFields.add("id");
		ignoreFields.add("creation_time");
		ignoreFields.add("modification_time");
		ignoreFields.add("version");

		// read tables from database
		SQLiteJDBC jdbc = new SQLiteJDBC(url, schema);
		List<Table> tables = jdbc.getTables(ignoreFields);

		// read model template and write class
		FileUtil fileUtil = new FileUtil();

		Map<ModelTag, String> map = new HashMap<ModelTag, String>();
		map.put(ModelTag.PACKAGE, spackage);

		List<String> model = fileUtil.readFile(modelTemplate);
		List<String> gettersSetters = fileUtil.readFile(gettersSettersTemplate);

		for (Table table : tables) {
			map.put(ModelTag.TABLE_NAME, table.getName());
			map.put(ModelTag.CLASS_NAME, table.getClassName());

			List<String> modelLines = this.changeTags(map, model, table.getFields(), gettersSetters);

			fileUtil.writeFile("D:\\minitube\\PRINTER_PROJECT\\printer-api\\printer-api\\src\\main\\java\\de\\minitube\\printerapi\\app\\model\\" + table.getClassName() + ".java", modelLines);
		}

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		App app = new App();
		app.writeModel();
		System.out.println("--FEITO--");
	}

}
