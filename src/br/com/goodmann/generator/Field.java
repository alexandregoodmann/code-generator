/**
 *
 * Ferreira
 * 17 de mai de 2018
 */
package br.com.goodmann.generator;

/**
 * @description
 * @author Ferreira
 * @date 17 de mai de 2018
 */
public class Field {

	private String fieldType;
	private String fieldName;

	private String javaType;
	private String propertieName;

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getPropertieName() {
		return propertieName;
	}

	public void setPropertieName(String propertieName) {
		this.propertieName = propertieName;
	}

}
