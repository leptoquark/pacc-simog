package it.avlp.simog.beans;

import java.math.BigDecimal;
import java.sql.Date;

import it.avlp.simog.db.generated.EAGG_CATEGORIE_SOGLIE;

public class EaggCategorieSoglie implements EAGG_CATEGORIE_SOGLIE {

	private long codcategoria;
	private BigDecimal importosoglia;
	private Date datainizio;
	private Date datafine;
	
	@Override
	public long getCOD_CATEGORIA() {
		return codcategoria;
	}
	
	public void setCOD_CATEGORIA(long codcategoria) {
		this.codcategoria=codcategoria;
	}

	@Override
	public BigDecimal getIMPORTO_SOGLIA() {
		return importosoglia;
	}

	public void setIMPORTO_SOGLIA(BigDecimal importosoglia) {
		this.importosoglia=importosoglia;
	}
	
	@Override
	public Date getDATA_INIZIO() {
		return datainizio;
	}

	@Override
	public Date getDATA_FINE_VALIDITA() {
		return datafine;
	}

	public void setDATA_INIZIO(Date datainizio) {
		this.datainizio = datainizio;
	}
	
	public void setDATA_FINE(Date datafine) {
		this.datafine = datafine;
	}
	
}
