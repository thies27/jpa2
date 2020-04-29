package de.schwerin.integration.jpa;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "dld_testcases", schema = "integration", uniqueConstraints=@UniqueConstraint(columnNames = {"GRUPPE", "KLASSE", "METHODE"}))
@NamedQueries({@NamedQuery(name="TableTestCases.FindIdByGroup", query="select tc from TableTestCases tc "
		+ "where gruppe = :gruppe and klasse = :klasse and methode = :methode")})
public class TableTestCases {
	
	public TableTestCases() {
		super();
		
	}

	public TableTestCases(String gruppe, String klasse, String methode) {
		super();
		this.gruppe = gruppe;
		this.klasse = klasse;
		this.methode = methode;
	}
	
	

	@Id
	@Column(name = "ID")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "GRUPPE")	
	private String gruppe;

	@Column(name = "KLASSE")
	private String klasse;
	
	@Column(name="METHODE")
	private String methode;
	
	@OneToMany(cascade = CascadeType.ALL)	
	@JoinColumn(name = "testCaseId")
	private Set<TableTestCasesErrors> errors;
	
	public String getGruppe() {
		return gruppe;
	}

	public void setGruppe(String gruppe) {
		this.gruppe = gruppe;
	}

	public String getKlasse() {
		return klasse;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}

	public String getMethode() {
		return methode;
	}

	public void setMethode(String methode) {
		this.methode = methode;
	}

	public int getId() {
		return id;
	}

	

}
