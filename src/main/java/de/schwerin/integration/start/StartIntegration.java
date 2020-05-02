package de.schwerin.integration.start;

import de.schwerin.integration.business.DldTestCasesPersistenzHandler;
import de.schwerin.integration.jpa.TableTestCases;
import de.schwerin.integration.jpa.TableTestCasesErrors;
import de.schwerin.integration.util.Hbm2ddl_Auto;

public class StartIntegration {

	public static void main(String[] args) {
		
		DldTestCasesPersistenzHandler h = new DldTestCasesPersistenzHandler(Hbm2ddl_Auto.UPDATE.getValue());
		
		System.out.println("Anzahl Datensätze TableTestCases : " + h.numberOfRecords(TableTestCases.SELECT_COUNT));
		
		System.out.println("Anzahl Datensätze TableTestCasesError : " + h.numberOfRecords(TableTestCasesErrors.SELECT_COUNT));		

		h.persist(getPathToTestCasesFile());
		
		System.out.println("Anzahl Datensätze TableTestCases : " + h.numberOfRecords(TableTestCases.SELECT_COUNT));

		System.out.println("Anzahl Datensätze TableTestCasesError : " + h.numberOfRecords(TableTestCasesErrors.SELECT_COUNT));
	}

	private static String getPathToTestCasesFile() {

		if ("Windows 10".equalsIgnoreCase(System.getProperty("os.name"))) {
			return "D:/tmp/testcases.txt";
		} else {
			return "/home/mathias/temp/testcases.txt";
		}
	}

}
