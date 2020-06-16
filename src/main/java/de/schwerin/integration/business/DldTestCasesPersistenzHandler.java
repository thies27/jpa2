package de.schwerin.integration.business;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import de.schwerin.integration.dao.IntegrationDao;
import de.schwerin.integration.data.DataReader;
import de.schwerin.integration.data.impl.FileDataReader;
import de.schwerin.integration.jpa.TableTestCases;
import de.schwerin.integration.jpa.TableTestCasesErrors;
import de.schwerin.integration.util.Constants;

public class DldTestCasesPersistenzHandler {	

	private static final Integer ID_NOT_EXIST = -1;

	private EntityManager em;

	public DldTestCasesPersistenzHandler(String hbm2ddl_auto) {
		super();

		Map<String, String> map = new HashMap<>();
		map.put(Constants.HBM2DDL_AUTO, hbm2ddl_auto);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SynologiePU", map);
		this.em = emf.createEntityManager();
	}

	public void persist(String path) {
		
		DataReader reader = new FileDataReader(path);
		IntegrationDao dao = reader.readData(new IntegrationDao());

		em.getTransaction().begin();

		for (Map<String, String> map : dao.getTestFälle()) {

			Integer id = existInDatabase(map);
			if (id == ID_NOT_EXIST) {

				TableTestCases tc = new TableTestCases(map.get(Constants.GRUPPE), map.get(Constants.KLASSE), map.get(Constants.METHODE));
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
		q.setParameter(Constants.GRUPPE, map.get(Constants.GRUPPE));
		q.setParameter(Constants.KLASSE, map.get(Constants.KLASSE));
		q.setParameter(Constants.METHODE, map.get(Constants.METHODE));
		TableTestCases table;
		try {
			table = (TableTestCases) q.getSingleResult();
		} catch (NoResultException e) {
			return ID_NOT_EXIST;
		}

		return table.getId();
	}

	public Long numberOfRecords(String queryName) {

		Query q = em.createNamedQuery(queryName);

		Long anzahl = (Long) q.getSingleResult();

		return anzahl;
	}

}
