/**
 *
 * Ferreira
 * 17 de mai de 2018
 */
package br.com.goodmann.database.postgresql;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author Ferreira
 * @date 17 de mai de 2018
 */
public class PostGreTypes {

	private Map<String, String> map = new HashMap<String, String>();

	public PostGreTypes() {
		this.map.put("bigserial", "Long");
		this.map.put("bool", "Boolean");
		this.map.put("bytea", "Byte");
		this.map.put("date", "Date");
		this.map.put("e_device_type", "String");
		this.map.put("e_functional_workspace_type", "String");
		this.map.put("e_gender", "String");
		this.map.put("e_messaging_destination_type", "String");
		this.map.put("e_orientation", "String");
		this.map.put("e_predilution_type", "String");
		this.map.put("e_resource_status", "String");
		this.map.put("e_salutation", "String");
		this.map.put("float8", "Float");
		this.map.put("int4", "Integer");
		this.map.put("text", "String");
		this.map.put("timestamptz", "DateTime");
		this.map.put("uuid", "String");
		this.map.put("varchar", "String");
	}

	public String getType(String postgreType) {
		String ret = this.map.get(postgreType);
		if (ret == null) {
			throw new RuntimeException("PostGreSQL Type not found: " + postgreType);
		}
		return ret;
	}
}
