package it.anticorruzione.ted.notice;

import java.util.List;

import org.apache.xmlbeans.XmlAnySimpleType;

import eu.europa.publications.resource.authority.currency.TCurrencyTedschema;
import eu.europa.publications.resource.authority.legalBasis.TLegalBasisTedschema;
import eu.europa.publications.resource.schema.ted.r209.reception.CaActivity;
import eu.europa.publications.resource.schema.ted.r209.reception.CaType;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactContractingBody;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactContractingBodyF14;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactContractor;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactReview;
import eu.europa.publications.resource.schema.ted.r209.reception.FormSection;
import eu.europa.publications.resource.schema.ted.r209.reception.Sender;
import eu.europa.publications.resource.schema.ted.r209.reception.Sender.CONTACT;
import eu.europa.publications.resource.schema.ted.r209.reception.Sender.IDENTIFICATION;
import eu.europa.publications.resource.schema.ted.r209.reception.TCountryList;
import eu.europa.publications.resource.schema.ted.r209.reception.TedEsenders;
import eu.europa.publications.resource.schema.ted.r209.reception.TypeContract;
import eu.europa.publications.resource.schema.ted.x2021.nuts.TNutsCodeList;
import it.anticorruzione.ted.beans.Contractor;
import it.anticorruzione.ted.enums.LegalBasisEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.ws.beans.DataNotice;
import it.avlp.simog.ws.massload.xmlbeans.AddrS1Type;
import it.avlp.simog.ws.massload.xmlbeans.AddrS5Type;
import it.avlp.simog.ws.massload.xmlbeans.AddrS5TypeMod;
import it.avlp.simog.ws.massload.xmlbeans.AddrS6Type;

public abstract class AbstractNoticeGenerator {

	
	//Versione specifiche utilizzate
	private String version = "R2.0.9.S05";
	
	//Direttiva di riferimento
	private LegalBasisEnum legalBasis = LegalBasisEnum.DIR_201424EU;
	
	//Tipo di formulario
	private TypeNoticeEnum typeNotice;
	
	//Dati gara, lotto, delta
	private DataNotice dataNotice;
	
	/**
	 * Costruttore utilizzato solo per fini di test
	 * @param esenderlogin
	 * @param no_doc_ext
	 */
	public AbstractNoticeGenerator(DataNotice dataNotice,
									TypeNoticeEnum typeNotice) {
		this.dataNotice=dataNotice;
		this.typeNotice=typeNotice;
	}
	
	public AbstractNoticeGenerator(DataNotice dataNotice,
									String version, 
									LegalBasisEnum legalBasis,
									TypeNoticeEnum typeNotice) {
		this.dataNotice=dataNotice;
		this.version=version;
		this.legalBasis=legalBasis;
		this.typeNotice=typeNotice;
	}
	
	/**
	 * Metodo principale di creazione del formulario
	 * @return l'intero formulario
	 */
	public TedEsenders createNotice() {
		TedEsenders tedesenders = TedEsenders.Factory.newInstance();
		XmlAnySimpleType xversion = XmlAnySimpleType.Factory.newInstance();
		xversion.setStringValue(this.version);
		tedesenders.setVERSION(xversion);
		Sender sender = createSenderSection();
		FormSection form = createFormSection();
		
		tedesenders.setSENDER(sender);
		tedesenders.setFORMSECTION(form);
		
		return tedesenders;
	}
	
	/**
	 * Metodo di creazione dell'intestazione (comune in tutti i tipi di formulario)
	 * @return la struttura dell'intestazione
	 */
	public Sender createSenderSection() {
		Sender sender = Sender.Factory.newInstance();
		IDENTIFICATION id  = sender.addNewIDENTIFICATION();
		id.setESENDERLOGIN(this.dataNotice.getEsenderlogin());
		id.setNODOCEXT(this.dataNotice.getNoDocExt());
		CONTACT contact = sender.addNewCONTACT();
		contact.setORGANISATION(this.dataNotice.getOrganization());
		contact.addNewCOUNTRY().setVALUE("IT");
		contact.setEMAIL(this.dataNotice.getEmail());
		return sender;
	}
	
	/**
	 * Metodo di creazione del modulo di compilazione vero e proprio (diverso da ogni formulario e quindi da implementare)
	 * @return il modulo compilato
	 */
	public abstract FormSection createFormSection();
	
	/**
	 * Informazioni anagrafiche relative all'ente appaltatore
	 * @return
	 */
	public ContactContractingBody createContactContractingBody(AddrS1Type first) {
		ContactContractingBody ccb = ContactContractingBody.Factory.newInstance();
		
		ccb.setOFFICIALNAME(first.getOFFICIALNAME());
		if(first.getNATIONALID()!=null && !"".equals(first.getNATIONALID()))
			ccb.setNATIONALID(first.getNATIONALID());
		if(first.getADDRESS()!=null && !"".equals(first.getADDRESS()))
			ccb.setADDRESS(first.getADDRESS());
		ccb.setTOWN(first.getTOWN());
		if(first.getPOSTALCODE()!=null && !"".equals(first.getPOSTALCODE()))
			ccb.setPOSTALCODE(first.getPOSTALCODE());
		ccb.addNewCOUNTRY().setVALUE(TCountryList.Enum.forString(first.getCOUNTRY().getValue())); 
		if(first.getCONTACTPOINT()!=null && !"".equals(first.getCONTACTPOINT()))
			ccb.setCONTACTPOINT(first.getCONTACTPOINT());
		if(first.getPHONE()!=null && !"".equals(first.getPHONE().getValue().getValue().getValue()))
			ccb.setPHONE(first.getPHONE().getValue().getValue().getValue());
		ccb.setEMAIL(first.getEMAIL());
		if(first.getFAX()!=null && !"".equals(first.getFAX().getValue().getValue().getValue()))
			ccb.setFAX(first.getFAX().getValue().getValue().getValue());
		ccb.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(first.getNUTS().getValue())); 
		ccb.setURLGENERAL(first.getURLGENERAL());
		if(first.getURLBUYER()!=null && !"".equals(first.getURLBUYER()))
			ccb.setURLBUYER(first.getURLBUYER());

		return ccb;
	}
	
	public ContactContractingBodyF14 createContactContractingBody14(AddrS1Type first) {
		
		ContactContractingBodyF14 ccb = ContactContractingBodyF14.Factory.newInstance();
		
		ccb.setOFFICIALNAME(first.getOFFICIALNAME());
		if(first.getNATIONALID()!=null && !"".equals(first.getNATIONALID()))
			ccb.setNATIONALID(first.getNATIONALID());
		if(first.getADDRESS()!=null && !"".equals(first.getADDRESS()))
			ccb.setADDRESS(first.getADDRESS());
		ccb.setTOWN(first.getTOWN());
		if(first.getPOSTALCODE()!=null && !"".equals(first.getPOSTALCODE()))
			ccb.setPOSTALCODE(first.getPOSTALCODE());
		ccb.addNewCOUNTRY().setVALUE(TCountryList.Enum.forString(first.getCOUNTRY().getValue())); 
		if(first.getCONTACTPOINT()!=null && !"".equals(first.getCONTACTPOINT()))
			ccb.setCONTACTPOINT(first.getCONTACTPOINT());
		if(first.getPHONE()!=null && !"".equals(first.getPHONE().getValue().getValue().getValue()))
			ccb.setPHONE(first.getPHONE().getValue().getValue().getValue());
		ccb.setEMAIL(first.getEMAIL());
		if(first.getFAX()!=null && !"".equals(first.getFAX().getValue().getValue().getValue()))
			ccb.setFAX(first.getFAX().getValue().getValue().getValue());
		ccb.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(first.getNUTS().getValue())); 
		ccb.setURLGENERAL(first.getURLGENERAL());
		if(first.getURLBUYER()!=null && !"".equals(first.getURLBUYER()))
			ccb.setURLBUYER(first.getURLBUYER());

		
		return ccb;
	}
	
	/**
	 * Informazioni anagrafiche relative all'organo di revisione
	 * @param addrS6Type 
	 * @return
	 */
	public ContactReview createContactReview(AddrS6Type addrS6Type) {
		ContactReview cr = ContactReview.Factory.newInstance();
		
		cr.setOFFICIALNAME(addrS6Type.getOFFICIALNAME());
		if(addrS6Type.getADDRESS()!=null && !"".equals(addrS6Type.getADDRESS()))
			cr.setADDRESS(addrS6Type.getADDRESS());
		cr.setTOWN(addrS6Type.getTOWN());
		if(addrS6Type.getPOSTALCODE()!=null && !"".equals(addrS6Type.getPOSTALCODE()))
			cr.setPOSTALCODE(addrS6Type.getPOSTALCODE());
		cr.addNewCOUNTRY().setVALUE(TCountryList.Enum.forString(addrS6Type.getCOUNTRY().getValue())); 
		if(addrS6Type.getPHONE()!=null && !"".equals(addrS6Type.getPHONE().getValue().getValue().getValue()))
			cr.setPHONE(addrS6Type.getPHONE().getValue().getValue().getValue());
		if(addrS6Type.getEMAIL()!=null && !"".equals(addrS6Type.getEMAIL()))
			cr.setEMAIL(addrS6Type.getEMAIL());
		if(addrS6Type.getFAX()!=null && !"".equals(addrS6Type.getFAX().getValue().getValue().getValue()))
			cr.setFAX(addrS6Type.getFAX().getValue().getValue().getValue());
		if(addrS6Type.getURLSA()!=null && !"".equals(addrS6Type.getURLSA()))
			cr.setURL(addrS6Type.getURLSA());

		return cr;
	}
	

	protected TLegalBasisTedschema.Enum getLegalBasis() {
		TLegalBasisTedschema.Enum res;
		switch(legalBasis) {
		    case DIR_201424EU:
		    	res = TLegalBasisTedschema.X_32014_L_0024;
		    	break;
		    default:
		    	res = TLegalBasisTedschema.X_32014_L_0024;
		}
		
		return res;
		
	}
	
	protected DataNotice getDataNotice() {
		return this.dataNotice;
	}

	protected eu.europa.publications.resource.schema.ted.r209.reception.CaType.VALUE.Enum getTipoAmministrazioneAggiudicatrice(String tipoAmmAgg) {
		if(tipoAmmAgg.equals("1"))
			return CaType.VALUE.MINISTRY;
		if(tipoAmmAgg.equals("2"))
			return CaType.VALUE.NATIONAL_AGENCY;
		if(tipoAmmAgg.equals("3"))
			return CaType.VALUE.REGIONAL_AUTHORITY;
		if(tipoAmmAgg.equals("4"))
			return CaType.VALUE.REGIONAL_AGENCY;
		if(tipoAmmAgg.equals("5"))
			return CaType.VALUE.BODY_PUBLIC;
		if(tipoAmmAgg.equals("6"))
			return CaType.VALUE.EU_INSTITUTION;

		return null;
	}
	
	protected eu.europa.publications.resource.schema.ted.r209.reception.CaActivity.VALUE.Enum getTipoAttivita(String tipoAttivita) {
		if(tipoAttivita.equals("1"))
			return CaActivity.VALUE.GENERAL_PUBLIC_SERVICES;
		if(tipoAttivita.equals("2"))
			return CaActivity.VALUE.DEFENCE;
		if(tipoAttivita.equals("3"))
			return CaActivity.VALUE.PUBLIC_ORDER_AND_SAFETY;
		if(tipoAttivita.equals("4"))
			return CaActivity.VALUE.ENVIRONMENT;
		if(tipoAttivita.equals("5"))
			return CaActivity.VALUE.ECONOMIC_AND_FINANCIAL_AFFAIRS;
		if(tipoAttivita.equals("6"))
			return CaActivity.VALUE.HEALTH;
		if(tipoAttivita.equals("7"))
			return CaActivity.VALUE.HOUSING_AND_COMMUNITY_AMENITIES;
		if(tipoAttivita.equals("8"))
			return CaActivity.VALUE.SOCIAL_PROTECTION;
		if(tipoAttivita.equals("9"))
			return CaActivity.VALUE.RECREATION_CULTURE_AND_RELIGION;
		if(tipoAttivita.equals("10"))
			return CaActivity.VALUE.EDUCATION;
		

		return null;
	}
	
	protected TypeContract.CTYPE.Enum getTypeContract(String tipoContratto){
		if(tipoContratto.equals("1"))
			return TypeContract.CTYPE.WORKS;
		if(tipoContratto.equals("2"))
			return TypeContract.CTYPE.SERVICES;
		if(tipoContratto.equals("3"))
			return TypeContract.CTYPE.SUPPLIES;
		
		return null;
	}
	
	protected eu.europa.publications.resource.authority.currency.TCurrencyTedschema.Enum getCurrency(){
		return TCurrencyTedschema.EUR;
	}
	
	protected ContactContractor createContactContractor(List<AggiudicatarioBean> list, AddrS5Type addrS5type) {
		ContactContractor cc = ContactContractor.Factory.newInstance();
		
		for(AggiudicatarioBean agg : list) {
			SoggettoPartecipanteBean sogg = agg.getSoggettoPartecipante();
			if(sogg.getCodiceFiscale().equals(addrS5type.getNATIONALID())) {
				cc.setOFFICIALNAME(sogg.getDenominazione());
				cc.setNATIONALID(sogg.getCodiceFiscale()); 
				if(sogg.getIndirizzo()!=null && !"".equals(sogg.getIndirizzo()))
					cc.setADDRESS(sogg.getIndirizzo());
				cc.setTOWN(sogg.getCitta());
				cc.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(addrS5type.getNUTS().getValue())); 
				if(sogg.getCap()!=null && !"".equals(sogg.getCap()))
					cc.setPOSTALCODE(sogg.getCap());
				String idStato = sogg.getId_stato() == null || "".equals(sogg.getId_stato()) ? "IT" : sogg.getId_stato();
				cc.addNewCOUNTRY().setVALUE(TCountryList.Enum.forString(idStato)); 
				if(addrS5type.getEMAIL()!=null && !"".equals(addrS5type.getEMAIL()))
					cc.setEMAIL(addrS5type.getEMAIL());
				
				if(addrS5type.getPHONE()!=null && !"".equals(addrS5type.getPHONE().getValue().getValue().getValue()))
					cc.setPHONE(addrS5type.getPHONE().getValue().getValue().getValue());
				if(addrS5type.getFAX()!=null && !"".equals(addrS5type.getFAX().getValue().getValue().getValue()))
					cc.setFAX(addrS5type.getFAX().getValue().getValue().getValue());
				if(addrS5type.getURL()!=null && !"".equals(addrS5type.getURL()))
					cc.setURL(addrS5type.getURL());
			}
			

		}
		return cc;
	}
	
	protected ContactContractor createContactContractor(Contractor inputContractor) {
		ContactContractor cc = ContactContractor.Factory.newInstance();
		

			cc.setOFFICIALNAME(inputContractor.getOfficialname());
			if(inputContractor.getNationalid()!=null && !"".equals(inputContractor.getNationalid()))
				cc.setNATIONALID(inputContractor.getNationalid()); 
			if(inputContractor.getAddress()!=null && !"".equals(inputContractor.getAddress()))
				cc.setADDRESS(inputContractor.getAddress());
			cc.setTOWN(inputContractor.getTown());
			cc.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(inputContractor.getNuts())); 
			if(inputContractor.getPostalcode()!=null && !"".equals(inputContractor.getPostalcode()))
				cc.setPOSTALCODE(inputContractor.getPostalcode());
			cc.addNewCOUNTRY().setVALUE(TCountryList.Enum.forString(inputContractor.getCountry())); 
			if(inputContractor.getEmail()!=null && !"".equals(inputContractor.getEmail()))
				cc.setEMAIL(inputContractor.getEmail());
			if(inputContractor.getPhone()!=null && !"".equals(inputContractor.getPhone()))
				cc.setPHONE(inputContractor.getPhone());
			if(inputContractor.getFax()!=null && !"".equals(inputContractor.getFax()))
				cc.setFAX(inputContractor.getFax());
			if(inputContractor.getUrl()!=null && !"".equals(inputContractor.getUrl()))
				cc.setURL(inputContractor.getUrl());
			

		return cc;
	}
	
	protected ContactContractor createAddressContractorMod(AddrS5TypeMod anagr) {
		ContactContractor cc = ContactContractor.Factory.newInstance();
		
	
				cc.setOFFICIALNAME(anagr.getOFFICIALNAME());
				cc.setNATIONALID(anagr.getNATIONALID()); 
				if(anagr.getADDRESS()!=null && !"".equals(anagr.getADDRESS()))
					cc.setADDRESS(anagr.getADDRESS());
				cc.setTOWN(anagr.getTOWN());
				cc.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(anagr.getNUTS().getValue())); 
				if(anagr.getPOSTALCODE()!=null && !"".equals(anagr.getPOSTALCODE()))
					cc.setPOSTALCODE(anagr.getPOSTALCODE());
				cc.addNewCOUNTRY().setVALUE(TCountryList.Enum.forString(anagr.getCOUNTRY().getValue())); 
				if(anagr.getEMAIL()!=null && !"".equals(anagr.getEMAIL()))
					cc.setEMAIL(anagr.getEMAIL());
				if(anagr.getPHONE()!=null && !"".equals(anagr.getPHONE().getValue().getValue().getValue()))
					cc.setPHONE(anagr.getPHONE().getValue().getValue().getValue());
				if(anagr.getFAX()!=null && !"".equals(anagr.getFAX().getValue().getValue().getValue()))
					cc.setFAX(anagr.getFAX().getValue().getValue().getValue());
				if(anagr.getURL()!=null && !"".equals(anagr.getURL()))
					cc.setURL(anagr.getURL());
			
		return cc;
	}
}
