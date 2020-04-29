package de.schwerin.integration.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "dld_testcases_errors", schema = "integration")
public class TableTestCasesErrors {
	
	public TableTestCasesErrors() {
		super();
	}
	
	public TableTestCasesErrors(int testCaseId) {
		super();
		this.testCaseId = testCaseId;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "TESTCASE_ID")	
	private int testCaseId;
	
	@Column(name = "EXEC_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date executeDate;
	
	@PrePersist
	public void create() {
		executeDate = new Date();
	  }

	public int getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public int getId() {
		return id;
	}
	
	
}
