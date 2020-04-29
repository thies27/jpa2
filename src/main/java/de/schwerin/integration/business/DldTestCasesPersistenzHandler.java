package de.schwerin.integration.business;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.schwerin.integration.dao.IntegrationDao;
import de.schwerin.integration.data.DataReader;
import de.schwerin.integration.data.FileDataReader;
import de.schwerin.integration.jpa.TableTestCases;
import de.schwerin.integration.jpa.TableTestCasesErrors;

public class DldTestCasesPersistenzHandler {
	
	private static final String GRUPPE = "gruppe";

	private static final String KLASSE = "klasse";

	private static final String METHODE = "methode";
	
	private static final Integer IS_NOT_EXIST = -1;

	private EntityManager em;

	public DldTestCasesPersistenzHandler(String hbm2ddl_auto) {
		super();
		
		Map<String, String> map = new HashMap<>();
		map.put("hibernate.hbm2ddl.auto", hbm2ddl_auto);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("IntegrationPU", map);
		this.em = emf.createEntityManager();
	}

	public void persist(String path) {

		DataReader reader = new FileDataReader(path);
		IntegrationDao dao = reader.readData(new IntegrationDao());

		em.getTransaction().begin();

		for (Map<String, String> map : dao.getTestFälle()) {

			Integer id = existInDatabase(map);
			if (id == IS_NOT_EXIST) {

				TableTestCases tc = new TableTestCases(map.get(GRUPPE), 
						map.get(KLASSE), 
						map.get(METHODE));
				em.persist(tc);
				
				TableTestCasesErrors errors = new TableTestCasesErrors(tc.getId());
				em.persist(errors);
			
			} else {
				
				TableTestCasesErrors errors = new TableTestCasesErrors(id);
				em.persist(errors);
			}
		}
		
		em.getTransaction().commit();
	}

	private Integer existInDatabase(Map<String, String> map) {

		Query q = em.createNamedQuery("TableTestCases.FindIdByGroup");
		q.setParameter(GRUPPE, map.get(GRUPPE));
		q.setParameter(KLASSE, map.get(KLASSE));
		q.setParameter(METHODE, map.get(METHODE));
		TableTestCases table;
		try {
			table = (TableTestCases) q.getSingleResult();
		} catch (NoResultException e) {
			return IS_NOT_EXIST;
		}

		return table.getId();
	}
	
	public int numberOfTestCases() {
		
		Query q = em.createNativeQuery("Select count(*) from dld_testcases");
		
		BigInteger anzahl = (BigInteger) q.getSingleResult();
		
		return anzahl.intValue();
	}

}
