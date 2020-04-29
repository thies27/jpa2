package de.schwerin.integration.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.schwerin.integration.business.DldTestCasesPersistenzHandler;
import de.schwerin.integration.dao.IntegrationDao;
import de.schwerin.integration.data.DataReader;
import de.schwerin.integration.data.FileDataReader;

public class TestIntegration {

	final static Logger logger = Logger.getLogger(TestIntegration.class);

	
	
	
	@Test()
	public void testPersistenzHandler() {
				
		DldTestCasesPersistenzHandler h = new DldTestCasesPersistenzHandler("update");
		
		h.persist(getPathToTestCasesFile());		
		
		Assert.assertTrue(h.numberOfTestCases() == 5);
	}
	
	@Test()
	public void testDataReader() {		
		
		DataReader dr = new FileDataReader(getPathToTestCasesFile());
		
		IntegrationDao dao = dr.readData(new IntegrationDao());
		
		Assert.assertTrue(dao != null);
		
		Assert.assertTrue(dao.getTestFälle().size() == 4);
	}

	@Test
	public void createDataBaseEntrys() {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegrationPU");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		TableTestCases tc = new TableTestCases("gruppe", "klasse", "methode");
		em.persist(tc);

		TableTestCasesErrors errors = new TableTestCasesErrors(tc.getId());
		em.persist(errors);

		em.getTransaction().commit();

		TableTestCasesErrors e2 = em.find(TableTestCasesErrors.class, errors.getId());

		Assert.assertTrue(tc.getId() == e2.getTestCaseId(), "Die ID aus TestCases mit testCaseId übereinstimmen.  ");

		Assert.assertNotNull(e2.getExecuteDate(), "Das Datum darf nicht leer sein.  ");

	}
	
	
	private String getPathToTestCasesFile() {		
		
		if("Windows 10".equalsIgnoreCase(System.getProperty("os.name"))) {
			return "D:/tmp/testcases.txt";
		} else {
			return "/home/mathias/temp/testcases.txt";
		}
	}
}
