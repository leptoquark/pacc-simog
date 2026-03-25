package it.anticorruzione.ted.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AwardedContract {

	private String dateConclusionContract;
	private List<Contractor> listaContractor;
	private BigDecimal valTotal;

	
	public AwardedContract() {
		listaContractor = new ArrayList<Contractor>();
	}
	
	public String getDateConclusionContract() {
		return dateConclusionContract;
	}
	public void setDateConclusionContract(String dateConclusionContract) {
		this.dateConclusionContract = dateConclusionContract;
	}
	public List<Contractor> getListaContractor() {
		return listaContractor;
	}
	public void setListaContractor(List<Contractor> listaContractor) {
		this.listaContractor = listaContractor;
	}
	public BigDecimal getValTotal() {
		return valTotal;
	}
	public void setValTotal(BigDecimal valTotal) {
		this.valTotal = valTotal;
	}
	
	public void addContractor(Contractor contractor) {
		listaContractor.add(contractor);
	}

	
}
