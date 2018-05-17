/**
 *
 * Ferreira
 * 8 de mai de 2018
 */
package br.com.goodmann.generator;

import br.com.goodmann.database.sqlite.Types;

/**
 * @description
 * @author Ferreira
 * @date 8 de mai de 2018
 */
public class Field {

	private String name;
	private String postgreType;
	private Types type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public String getPostgreType() {
		return postgreType;
	}

	public void setPostgreType(String postgreType) {
		this.postgreType = postgreType;
	}

}
