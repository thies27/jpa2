package de.schwerin.integration.data;

import de.schwerin.integration.dao.IntegrationDao;

public interface DataReader {
	
	IntegrationDao readData(IntegrationDao dao) ;

}
