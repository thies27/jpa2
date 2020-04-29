package de.schwerin.integration.dao;

import java.util.HashSet;
import java.util.Map;

public class IntegrationDao {	

	public IntegrationDao() {
		testFälle = new HashSet<Map<String,String>>();
	}

	private HashSet<Map<String, String>> testFälle;

	public HashSet<Map<String, String>> getTestFälle() {
		return testFälle;
	}

	public void setTestFälle(HashSet<Map<String, String>> testFälle) {
		this.testFälle = testFälle;
	}	
	
	

}
