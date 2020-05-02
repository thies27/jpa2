package de.schwerin.integration.data.impl;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.schwerin.integration.dao.IntegrationDao;
import de.schwerin.integration.data.DataReader;
import de.schwerin.integration.util.Constants;

public class FileDataReader implements DataReader {

	private String path;	

	public FileDataReader(String path) {
		super();
		this.path = path;
	}

	public IntegrationDao readData(IntegrationDao dao) {

		FileSystem fs = FileSystems.getDefault();
		Path p = fs.getPath(path);
		
		try {
			List<String> liste = Files.readAllLines(p);						
			
			liste.forEach(zeile -> {String[] strArr = zeile.split("\\.");
			Map<String, String> map = new HashMap<>();
				map.put(Constants.GRUPPE, strArr[0]);
				map.put(Constants.KLASSE, strArr[1]);
				map.put(Constants.METHODE, strArr[2]);
				dao.getTestFälle().add(map);
				
			});			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dao;

	}

	public void setPath(String path) {
		this.path = path;
	}

}
