package de.schwerin.integration.data;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.schwerin.integration.dao.IntegrationDao;

public class FileDataReader implements DataReader {

	private String path;

	private static final String GRUPPE = "gruppe";

	private static final String KLASSE = "klasse";

	private static final String METHODE = "methode";

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
				map.put(GRUPPE, strArr[0]);
				map.put(KLASSE, strArr[1]);
				map.put(METHODE, strArr[2]);
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
