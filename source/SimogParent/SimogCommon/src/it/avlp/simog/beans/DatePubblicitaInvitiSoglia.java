package it.avlp.simog.beans;

import java.util.Date;

@Deprecated
public class DatePubblicitaInvitiSoglia {

	
	public String  guce                                 = null;
	public String  guri                                 = null;
	public String  bur                                  = null;
	public String  quotNazionali                        = null;
	public String  quotLocali                           = null;
	public Date    scadenzaPresentazManifestInteresse   = null;
	public Date    scadenzaPresentazioneRichiestaInvito = null;
	public Date    dataInvito                           = null;
	public Date    DataScadenzaPresentazioneOfferte     = null;
	public Integer NsoggettiManifestazioneInteresse     = null;
	public Integer NsoggettiPresentatoRichiestaInvito   = null;
	public Integer NsoggettiInvitatiPresentareOfferta   = null;
	public Integer NsoggettiHannoPresentatoOfferta      = null;
	public Integer NofferteAmmesse                      = null;
	public Float  offertaMaxRibasso                     = null;
	public Float  offertaMinRibasso                     = null;
	public Float  valoreSogliaAnomalia                  = null;
	public Integer NofferteMaggUgualeSogliaAnomalia     = null;
	public Integer NimpreseEscluseAutomaticamente       = null;
	public Integer NimpreseEsclusePerInsGiustificazioni = null;

	public DatePubblicitaInvitiSoglia(String  guce                                
						            , String  guri                                
						            , String  bur                                 
						            , String  quotNazionali                       
									, String  quotLocali                          
									, Date    scadenzaPresentazManifestInteresse  
									, Date    scadenzaPresentazioneRichiestaInvito
									, Date    dataInvito                          
									, Date    DataScadenzaPresentazioneOfferte    
									, Integer NsoggettiManifestazioneInteresse    
									, Integer NsoggettiPresentatoRichiestaInvito  
									, Integer NsoggettiInvitatiPresentareOfferta  
									, Integer NsoggettiHannoPresentatoOfferta     
									, Integer NofferteAmmesse                     
									, Float   offertaMaxRibasso                    
									, Float   offertaMinRibasso                    
									, Float   valoreSogliaAnomalia                 
									, Integer NofferteMaggUgualeSogliaAnomalia    
									, Integer NimpreseEscluseAutomaticamente      
									, Integer NimpreseEsclusePerInsGiustificazioni) {

					this.guce                                  =  guce                                ;
					this.guri                                  =  guri                                ;
					this.bur                                   =  bur                                 ;
					this.quotNazionali                         =  quotNazionali                       ;
					this.quotLocali                            =  quotLocali                          ;
					this.scadenzaPresentazManifestInteresse    =  scadenzaPresentazManifestInteresse  ;
					this.scadenzaPresentazioneRichiestaInvito  =  scadenzaPresentazioneRichiestaInvito;
					this.dataInvito                            =  dataInvito                          ;
					this.DataScadenzaPresentazioneOfferte      =  DataScadenzaPresentazioneOfferte    ;
					this.NsoggettiManifestazioneInteresse      =  NsoggettiManifestazioneInteresse    ;
					this.NsoggettiPresentatoRichiestaInvito    =  NsoggettiPresentatoRichiestaInvito  ;
					this.NsoggettiInvitatiPresentareOfferta    =  NsoggettiInvitatiPresentareOfferta  ;
					this.NsoggettiHannoPresentatoOfferta       =  NsoggettiHannoPresentatoOfferta     ;
					this.NofferteAmmesse                       =  NofferteAmmesse                     ;
					this.offertaMaxRibasso                     =  offertaMaxRibasso                   ;
					this.offertaMinRibasso                     =  offertaMinRibasso                   ;
					this.valoreSogliaAnomalia                  =  valoreSogliaAnomalia                ;
					this.NofferteMaggUgualeSogliaAnomalia      =  NofferteMaggUgualeSogliaAnomalia    ;
					this.NimpreseEscluseAutomaticamente        =  NimpreseEscluseAutomaticamente      ;
					this.NimpreseEsclusePerInsGiustificazioni  =  NimpreseEsclusePerInsGiustificazioni;

	}
	public DatePubblicitaInvitiSoglia() {
	}
	public String getBur() {
		return bur;
	}
	public void setBur(String bur) {
		this.bur = bur;
	}
	public Date getDataInvito() {
		return dataInvito;
	}
	public void setDataInvito(Date dataInvito) {
		this.dataInvito = dataInvito;
	}
	public Date getDataScadenzaPresentazioneOfferte() {
		return DataScadenzaPresentazioneOfferte;
	}
	public void setDataScadenzaPresentazioneOfferte(
			Date dataScadenzaPresentazioneOfferte) {
		DataScadenzaPresentazioneOfferte = dataScadenzaPresentazioneOfferte;
	}
	public String getGuce() {
		return guce;
	}
	public void setGuce(String guce) {
		this.guce = guce;
	}
	public String getGuri() {
		return guri;
	}
	public void setGuri(String guri) {
		this.guri = guri;
	}
	public Integer getNimpreseEscluseAutomaticamente() {
		return NimpreseEscluseAutomaticamente;
	}
	public void setNimpreseEscluseAutomaticamente(
			Integer nimpreseEscluseAutomaticamente) {
		NimpreseEscluseAutomaticamente = nimpreseEscluseAutomaticamente;
	}
	public Integer getNimpreseEsclusePerInsGiustificazioni() {
		return NimpreseEsclusePerInsGiustificazioni;
	}
	public void setNimpreseEsclusePerInsGiustificazioni(
			Integer nimpreseEsclusePerInsGiustificazioni) {
		NimpreseEsclusePerInsGiustificazioni = nimpreseEsclusePerInsGiustificazioni;
	}
	public Integer getNofferteAmmesse() {
		return NofferteAmmesse;
	}
	public void setNofferteAmmesse(Integer nofferteAmmesse) {
		NofferteAmmesse = nofferteAmmesse;
	}
	public Integer getNofferteMaggUgualeSogliaAnomalia() {
		return NofferteMaggUgualeSogliaAnomalia;
	}
	public void setNofferteMaggUgualeSogliaAnomalia(
			Integer nofferteMaggUgualeSogliaAnomalia) {
		NofferteMaggUgualeSogliaAnomalia = nofferteMaggUgualeSogliaAnomalia;
	}
	public Integer getNsoggettiHannoPresentatoOfferta() {
		return NsoggettiHannoPresentatoOfferta;
	}
	public void setNsoggettiHannoPresentatoOfferta(
			Integer nsoggettiHannoPresentatoOfferta) {
		NsoggettiHannoPresentatoOfferta = nsoggettiHannoPresentatoOfferta;
	}
	public Integer getNsoggettiInvitatiPresentareOfferta() {
		return NsoggettiInvitatiPresentareOfferta;
	}
	public void setNsoggettiInvitatiPresentareOfferta(
			Integer nsoggettiInvitatiPresentareOfferta) {
		NsoggettiInvitatiPresentareOfferta = nsoggettiInvitatiPresentareOfferta;
	}
	public Integer getNsoggettiManifestazioneInteresse() {
		return NsoggettiManifestazioneInteresse;
	}
	public void setNsoggettiManifestazioneInteresse(
			Integer nsoggettiManifestazioneInteresse) {
		NsoggettiManifestazioneInteresse = nsoggettiManifestazioneInteresse;
	}
	public Integer getNsoggettiPresentatoRichiestaInvito() {
		return NsoggettiPresentatoRichiestaInvito;
	}
	public void setNsoggettiPresentatoRichiestaInvito(
			Integer nsoggettiPresentatoRichiestaInvito) {
		NsoggettiPresentatoRichiestaInvito = nsoggettiPresentatoRichiestaInvito;
	}
	public Float getOffertaMaxRibasso() {
		return offertaMaxRibasso;
	}
	public void setOffertaMaxRibasso(Float offertaMaxRibasso) {
		this.offertaMaxRibasso = offertaMaxRibasso;
	}
	public Float getOffertaMinRibasso() {
		return offertaMinRibasso;
	}
	public void setOffertaMinRibasso(Float offertaMinRibasso) {
		this.offertaMinRibasso = offertaMinRibasso;
	}
	public String getQuotLocali() {
		return quotLocali;
	}
	public void setQuotLocali(String quotLocali) {
		this.quotLocali = quotLocali;
	}
	public String getQuotNazionali() {
		return quotNazionali;
	}
	public void setQuotNazionali(String quotNazionali) {
		this.quotNazionali = quotNazionali;
	}
	public Date getScadenzaPresentazioneRichiestaInvito() {
		return scadenzaPresentazioneRichiestaInvito;
	}
	public void setScadenzaPresentazioneRichiestaInvito(
			Date scadenzaPresentazioneRichiestaInvito) {
		this.scadenzaPresentazioneRichiestaInvito = scadenzaPresentazioneRichiestaInvito;
	}
	public Date getScadenzaPresentazManifestInteresse() {
		return scadenzaPresentazManifestInteresse;
	}
	public void setScadenzaPresentazManifestInteresse(
			Date scadenzaPresentazManifestInteresse) {
		this.scadenzaPresentazManifestInteresse = scadenzaPresentazManifestInteresse;
	}
	public Float getValoreSogliaAnomalia() {
		return valoreSogliaAnomalia;
	}
	public void setValoreSogliaAnomalia(Float valoreSogliaAnomalia) {
		this.valoreSogliaAnomalia = valoreSogliaAnomalia;
	}



	
}
