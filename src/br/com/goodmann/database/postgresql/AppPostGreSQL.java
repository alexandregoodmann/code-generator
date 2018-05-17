/**
 *
 * Ferreira
 * 17 de mai de 2018
 */
package br.com.goodmann.database.postgresql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.goodmann.generator.Schema;

/**
 * @description
 * @author Ferreira
 * @date 17 de mai de 2018
 */
public class AppPostGreSQL {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

		// Connect Database
		String user = "postgres";
		String password = "admin";
		String url = "jdbc:postgresql://localhost:5432/LabVision_V0.36";
		PostgreSQLJDBC post = new PostgreSQLJDBC(user, password, url);
		post.connect();

		// Getting all schemas
		String[] ignoreSchema = { "public", "pg_catalog", "information_schema" };
		String basePackage = "de.minitube.labvisionreport";
		String[] ignoreFields = { "id", "creation_time", "modification_time", "version" };
		List<Schema> schems = post.getSchemas(ignoreSchema, basePackage, ignoreFields);
		post.disConnect();

		/*
		 * schems.forEach(s -> { s.getTables().forEach(t -> {
		 * t.getProperties().forEach(p -> { System.out.println("*********");
		 * System.out.println("---" + s.getSchema() + "---" + t.getTable() + "---" +
		 * p.getFieldName()); System.out .println("---" + s.getPackageName() + "---" +
		 * t.getClazz() + "---" + p.getPropertieName()); }); }); });
		 */

		// Generate Classes
		String modelTemplate = "D:\\minitube\\code-generator\\resources\\template\\model.template";
		String gettersSettersTemplate = "D:\\minitube\\code-generator\\resources\\template\\getters_setters.template";
		String outDirectory = "D:\\minitube\\classes\\";
		ModelGenerator gen = new ModelGenerator();
		gen.writeModel(modelTemplate, gettersSettersTemplate, schems, outDirectory);

		System.out.println("--FEITO--");
	}
}
