/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann.generator;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public class Properties {

	private String sPackage;
	private String tableName;
	private String className;
	private String outputDirectory;
	private String[] properties;
	private String[] gettersetters;

	public String getsPackage() {
		return sPackage;
	}

	public void setsPackage(String sPackage) {
		this.sPackage = sPackage;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String[] getProperties() {
		return properties;
	}

	public void setProperties(String[] properties) {
		this.properties = properties;
	}

	public String[] getGettersetters() {
		return gettersetters;
	}

	public void setGettersetters(String[] gettersetters) {
		this.gettersetters = gettersetters;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

}
