/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann.database.postgresql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.goodmann.generator.Field;
import br.com.goodmann.generator.ModelTag;
import br.com.goodmann.generator.Schema;
import br.com.goodmann.generator.Table;
import br.com.goodmann.util.FileUtil;
import br.com.goodmann.util.StringUtil;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public class ModelGenerator {

	private FileUtil fileUtil = new FileUtil();

	private List<String> buildProperties(List<Field> fields) {
		List<String> properties = new ArrayList<String>();
		for (Field field : fields) {
			properties.add("	@Column(name = \"" + field.getFieldName().toUpperCase() + "\")");
			properties.add("	private " + field.getJavaType() + " " + field.getPropertieName() + ";");
			properties.add("");
		}
		return properties;
	}

	private List<String> buildGettersSetters(List<Field> fields, List<String> templateGetterSetters) {

		List<String> gettersSetters = new ArrayList<String>();
		for (Field field : fields) {
			for (String line : templateGetterSetters) {
				String lin = line.replaceAll(ModelTag.TYPE.tag, field.getJavaType());
				lin = lin.replaceAll(ModelTag.GET.tag, "get" + StringUtil.upper(field.getPropertieName()));
				lin = lin.replaceAll(ModelTag.SET.tag, "set" + StringUtil.upper(field.getPropertieName()));
				lin = lin.replaceAll(ModelTag.FIELD_NAME.tag, field.getPropertieName());
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

	/**
	 * 
	 * @param spackage
	 *            - The Class package
	 * @param modelTemplate
	 *            - Model Template file path
	 * @param gettersSettersTemplate-
	 *            Getters and Setters Template path
	 * @param tables
	 *            - List of the tables readed from database
	 * @param outDirectory
	 *            - The output directory to save java classess
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void writeModel(String modelTemplate, String gettersSettersTemplate, List<Schema> schems,
			String outDirectory) throws ClassNotFoundException, SQLException, IOException {

		List<String> model = fileUtil.readFile(modelTemplate);
		List<String> gettersSetters = fileUtil.readFile(gettersSettersTemplate);

		for (Schema s : schems) {
			for (Table table : s.getTables()) {

				Map<ModelTag, String> map = new HashMap<ModelTag, String>();
				map.put(ModelTag.PACKAGE, s.getPackageName());
				map.put(ModelTag.TABLE_NAME, table.getClazzTable());
				map.put(ModelTag.CLASS_NAME, table.getClazz());

				List<String> modelLines = this.changeTags(map, model, table.getProperties(), gettersSetters);

				fileUtil.writeFile(outDirectory + table.getClazz() + ".java", modelLines);
			}
		}
	}

}
