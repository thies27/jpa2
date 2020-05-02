package de.schwerin.integration.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.schwerin.integration.business.DldTestCasesPersistenzHandler;
import de.schwerin.integration.dao.IntegrationDao;
import de.schwerin.integration.data.DataReader;
import de.schwerin.integration.data.impl.FileDataReader;
import de.schwerin.integration.util.Hbm2ddl_Auto;

public class TestIntegration {

	final static Logger logger = Logger.getLogger(TestIntegration.class);	
	

	@Test()
	public void testPersistenzHandler() {
				
		DldTestCasesPersistenzHandler h = new DldTestCasesPersistenzHandler(Hbm2ddl_Auto.UPDATE.getValue());
		
		h.persist(getPathToTestCasesFile());		
		
		Assert.assertTrue(h.numberOfRecords(TableTestCases.FIND_BY_GROUP) == 14);
	}
	
	@Test()
	public void testDataReader() {		
		
		DataReader dr = new FileDataReader(getPathToTestCasesFile());
		
		IntegrationDao dao = dr.readData(new IntegrationDao());
		
		Assert.assertTrue(dao != null);
		
		Assert.assertTrue(dao.getTestFälle().size() == 4);
	}

	@Test
	public void testCountDataBaseEntrys() {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegrationPU");
		Map<String, String> map = new HashMap<>();
		map.put("hibernate.hbm2ddl.auto", Hbm2ddl_Auto.UPDATE.getValue());
		EntityManager em = emf.createEntityManager(map);

		Query q = em.createNamedQuery(TableTestCases.SELECT_COUNT);
		Long r = (Long) q.getSingleResult();
		Assert.assertNotNull(r);
		Assert.assertTrue(r == 5);
		
		Query q1 = em.createNamedQuery(TableTestCasesErrors.SELECT_COUNT);
		Long r1 = (Long) q1.getSingleResult();
		Assert.assertNotNull(r1);
		Assert.assertTrue(r1 == 17);

	}
	
	
	private String getPathToTestCasesFile() {		
		
		if("Windows 10".equalsIgnoreCase(System.getProperty("os.name"))) {
			return "D:/tmp/testcases.txt";
		} else {
			return "/home/mathias/temp/testcases.txt";
		}
	}
}
