package de.schwerin.integration.util;

/*
 * Hibernate Mapping to Data Definition Language
 */
public enum Hbm2ddl_Auto {

	CREATE("create"), CREATE_DROP("create-drop"), UPDATE("update"), VALIDATE("validate");

	private String value;

	Hbm2ddl_Auto(String str) {
		this.value = str;
	}

	public String getValue() {
		return value;
	}

}
