package it.anticorruzione.ted.notice;

import java.math.BigDecimal;
import java.util.List;

import org.apache.xmlbeans.XmlAnySimpleType;

import eu.europa.publications.resource.schema.ted.r209.reception.ACPRICEDocument.ACPRICE;
import eu.europa.publications.resource.schema.ted.r209.reception.AcDefinition;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF03;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF03.AWARDEDCONTRACT;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF03.AWARDEDCONTRACT.CONTRACTORS;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF03.AWARDEDCONTRACT.CONTRACTORS.CONTRACTOR1;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF03.AWARDEDCONTRACT.CONTRACTORS.CONTRACTOR2;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF03.AWARDEDCONTRACT.TENDERS;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF03.AWARDEDCONTRACT.VALUES;
import eu.europa.publications.resource.schema.ted.r209.reception.BodyF03;
import eu.europa.publications.resource.schema.ted.r209.reception.CaActivity;
import eu.europa.publications.resource.schema.ted.r209.reception.CaType;
import eu.europa.publications.resource.schema.ted.r209.reception.CiF03;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactContractingBody;
import eu.europa.publications.resource.schema.ted.r209.reception.CpvSet;
import eu.europa.publications.resource.schema.ted.r209.reception.DATEDISPATCHORIGINALDocument.DATEDISPATCHORIGINAL;
import eu.europa.publications.resource.schema.ted.r209.reception.F032014Document.F032014;
import eu.europa.publications.resource.schema.ted.r209.reception.FormSection;
import eu.europa.publications.resource.schema.ted.r209.reception.Ft;
import eu.europa.publications.resource.schema.ted.r209.reception.NoAward;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectContractF03;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectF03;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectF03.AC;
import eu.europa.publications.resource.schema.ted.r209.reception.OriginalTranslation;
import eu.europa.publications.resource.schema.ted.r209.reception.PROCUREMENTDISCONTINUEDDocument.PROCUREMENTDISCONTINUED;
import eu.europa.publications.resource.schema.ted.r209.reception.PROCUREMENTDISCONTINUEDDocument.PROCUREMENTDISCONTINUED.ESENDERLOGIN;
import eu.europa.publications.resource.schema.ted.r209.reception.PROCUREMENTDISCONTINUEDDocument.PROCUREMENTDISCONTINUED.NODOCEXT;
import eu.europa.publications.resource.schema.ted.r209.reception.ProcedureF03;
import eu.europa.publications.resource.schema.ted.r209.reception.TCeLanguageList;
import eu.europa.publications.resource.schema.ted.r209.reception.TCountryList;
import eu.europa.publications.resource.schema.ted.r209.reception.Val;
import eu.europa.publications.resource.schema.ted.r209.reception.ValRange;
import eu.europa.publications.resource.schema.ted.x2021.nuts.TNutsCodeList;
import it.anticorruzione.ted.beans.LottoTED;
import it.anticorruzione.ted.enums.LegalBasisEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.util.UtilityClass;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.ws.beans.DataNotice;
import it.avlp.simog.ws.massload.xmlbeans.AddrS1Type;
import it.avlp.simog.ws.massload.xmlbeans.AltreInfoType;
import it.avlp.simog.ws.massload.xmlbeans.AppaltoTypeAgg;
import it.avlp.simog.ws.massload.xmlbeans.ContraenteType;
import it.avlp.simog.ws.massload.xmlbeans.DatiProceduraType;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;
import it.avlp.simog.ws.massload.xmlbeans.DescrizioneAppaltoType;
import it.avlp.simog.ws.massload.xmlbeans.EntitaAppaltoType;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoAggiudicazione;
import it.avlp.simog.ws.massload.xmlbeans.InfoAmministrativeTypeAgg;

public class F03Generator extends AbstractNoticeGenerator {

	public F03Generator(DataNotice dataNotice, String version) {
		super(dataNotice,
				version, 
				LegalBasisEnum.DIR_201424EU, 
				TypeNoticeEnum.F03);
	}
	
	@Override
	public FormSection createFormSection() {
		DeltaGaraTED deltaGara = getDataNotice().getDeltaGaraTED();
		Gara gara = getDataNotice().getGara();
		
		FormSection formSec = FormSection.Factory.newInstance();
		F032014 f032014 = formSec.addNewF032014();
		f032014.setCATEGORY(OriginalTranslation.ORIGINAL);					//F03-3
		
		XmlAnySimpleType form = XmlAnySimpleType.Factory.newInstance();
		form.setStringValue(TypeNoticeEnum.F03.getTipo());					//F03-4
		f032014.setFORM(form);
		f032014.setLG(TCeLanguageList.IT);									//F03-5
		
		f032014.addNewLEGALBASIS().setVALUE(getLegalBasis());				//F03-6
        BodyF03 body = createContractingBody();								//F03-7
		
        //Non previsto
//        f032014.addNewLEGALBASISOTHER();
        
        f032014.setCONTRACTINGBODY(body);
        
        //Object Contract
        ObjectContractF03 objContract = createObjectContract(deltaGara, gara);
        f032014.setOBJECTCONTRACT(objContract);

//        //Procedure
        Lotto lotto = getDataNotice().getListaLotti().get(0).getLotto();
        ProcedureF03 procedure = createProcedure(lotto,deltaGara, gara);
        f032014.setPROCEDURE(procedure);

        
        List<AppaltoTypeAgg> listaAppalto = getDataNotice().getFormularioAgg().getAPPALTOAVVAGG();
        //Ciclo Award Contract
        for(int i=0;i<listaAppalto.size();i++) {
        	AppaltoTypeAgg app = listaAppalto.get(i);
        	
        	AwardContractF03 awardContract = f032014.addNewAWARDCONTRACT();		//F03-72
        	awardContract.setITEM(i+1);
        	LottoTED lottoTed = UtilityClass.getLottoFromCIG(getDataNotice().getListaLotti(),app.getCIGAGG().getValue());
        	AggiudicazioneBean aggSimog = lottoTed.getAggiudicazione();
        	
        	
        	awardContract.setCONTRACTNO(lottoTed.getLotto().getFullCIG()); 	//F03-73
        	
//        	if(lottoTed.getDeltaLottoTED().getNOLOT()!=null)
        	     awardContract.setLOTNO(lottoTed.getLotto().getFullCIG());	//F03-74
        	
        	Ft title = awardContract.addNewTITLE().addNewP().addNewFT();		//F03-75
        	title.setTYPE(Ft.TYPE.SUB);
        	title.setStringValue(lottoTed.getLotto().getOggetto());
        	
        	if(Costanti.FLAG_VALORE_NO.equals(app.getAWARDEDCONTRACT().value())) {
        		NoAward noAward = awardContract.addNewNOAWARDEDCONTRACT();		//F03-76
        		if(app.getPROCUREMENTUNSUCCESSFUL().getValue().equals("1")) 
        			noAward.addNewPROCUREMENTUNSUCCESSFUL();					//F03-77
        		else {
        			PROCUREMENTDISCONTINUED pd = noAward.addNewPROCUREMENTDISCONTINUED();	//F03-78
        			XmlAnySimpleType form2 = XmlAnySimpleType.Factory.newInstance();
        			form2.setStringValue("NO");
        			pd.addNewORIGINALTEDESENDER().setPUBLICATION(form2);					//F03-79
        			ESENDERLOGIN esenderLogin = pd.addNewESENDERLOGIN();
        			esenderLogin.setPUBLICATION(form2);
        			esenderLogin.setStringValue(getDataNotice().getEsenderlogin());			//F03-82
        			
        			NODOCEXT noDocEx = pd.addNewNODOCEXT();
        			noDocEx.setPUBLICATION(form2);
        			noDocEx.setStringValue(getDataNotice().getOriginalNoDocExt());			//F03-84
        			
        			DATEDISPATCHORIGINAL datedispatc = pd.addNewDATEDISPATCHORIGINAL();		//F03-85
        			datedispatc.setPUBLICATION(form2);
        			datedispatc.setStringValue(getDataNotice().getOriginalDataDispatch());
        		}
        	} else {
        		AWARDEDCONTRACT awarded =  awardContract.addNewAWARDEDCONTRACT();			//F03-86
        		awarded.setDATECONCLUSIONCONTRACT(UtilityClass.dateToCalendar(app.getDATECONCLUSIONCONTRACT().getValue()));//F03-87
        		TENDERS tenders = awarded.addNewTENDERS();									//F03-88

        		tenders.setNBTENDERSRECEIVED(aggSimog.getNumOfferteAmmesse());					//F03-89
        		
        		if(app.getNBTENDERSRECEIVEDSME() != null && app.getNBTENDERSRECEIVEDSME()>0)
        			tenders.setNBTENDERSRECEIVEDSME(app.getNBTENDERSRECEIVEDSME());			//F03-90
        		if(app.getNBTENDERSRECEIVEDOTHEREU() != null && app.getNBTENDERSRECEIVEDOTHEREU()>0)
        			tenders.setNBTENDERSRECEIVEDOTHEREU(app.getNBTENDERSRECEIVEDOTHEREU());	//F03-91
        		if(app.getNBTENDERSRECEIVEDNONEU() != null && app.getNBTENDERSRECEIVEDNONEU()>0)
        			tenders.setNBTENDERSRECEIVEDNONEU(app.getNBTENDERSRECEIVEDNONEU());		//F03-92
        		if(app.getNBTENDERSRECEIVEDEMEANS() != null && app.getNBTENDERSRECEIVEDEMEANS()>0)
        			tenders.setNBTENDERSRECEIVEDEMEANS(app.getNBTENDERSRECEIVEDEMEANS());	//F03-93
        		
        		//CONTRACTORS
        		CONTRACTORS contractors = awarded.addNewCONTRACTORS();						//F03-94
        		if(lottoTed.getAggiudicatari().size()>1)
        			contractors.addNewAWARDEDTOGROUP();										//F03-96
        		else
        			contractors.addNewNOAWARDEDTOGROUP();									//F03-95
        		
        		for(int y=0;y<app.getAWARDEDNOTICE().size();y++) {
        			ContraenteType el = app.getAWARDEDNOTICE().get(y);
        			if(contractors.isSetNOAWARDEDTOGROUP()) {
	        			CONTRACTOR2 contr = contractors.addNewCONTRACTOR2();															//F03-97
	        			contr.setADDRESSCONTRACTOR(createContactContractor(lottoTed.getAggiudicatari(), el.getADDRESSCONTRACTOR()));	//F03-98
	        			if(Costanti.FLAG_VALORE_NO.equals(el.getAWARDEDISSME().value()))
	        				contr.addNewNOSME();																						//F03-100
	        			else
	        				contr.addNewSME();																							//F03-99
        			} else {
        				CONTRACTOR1 contr = contractors.addNewCONTRACTOR1();															//F03-97
	        			contr.setADDRESSCONTRACTOR(createContactContractor(lottoTed.getAggiudicatari(), el.getADDRESSCONTRACTOR()));	//F03-98
	        			if(Costanti.FLAG_VALORE_NO.equals(el.getAWARDEDISSME().value()))
	        				contr.addNewNOSME();																						//F03-100
	        			else
	        				contr.addNewSME();	
        			}
        		}
        		
        		  //VALUES
        		  VALUES values = awarded.addNewVALUES();										//F03-101
        		  Val valEstimated = values.addNewVALESTIMATEDTOTAL();					    	//F03-102
        		  valEstimated.setCURRENCY(getCurrency());  
        		  valEstimated.setBigDecimalValue(UtilityClass.roundDecimal(lottoTed.getLotto().getImporto_Lotto()));
        		  
        		  //Se presente un solo aggiudicatario, prendere l'importo di aggiudicazione
        		  if(lottoTed.getAggiudicatari().size()==1) {
        			Val valTotal = values.addNewVALTOTAL();					    	//F03-102
        			valTotal.setCURRENCY(getCurrency());  
        			valTotal.setBigDecimalValue(UtilityClass.roundDecimal(lottoTed.getAggiudicazione().getImportoAggiudicazione()));
        		} else {   
        			//In caso di piu' aggiudicatari, verificare se sono presenti almeno due importi e recuperare il più basso e più alto
        			BigDecimal[] importi = UtilityClass.getLowHighTotal(lottoTed.getAggiudicatari());
        			if(importi!=null) {
	        			ValRange vr = values.addNewVALRANGETOTAL();		
	        			vr.setCURRENCY(getCurrency());
	        			vr.setLOW(UtilityClass.roundDecimal(importi[0]));						//F03-104
	        			vr.setHIGH(UtilityClass.roundDecimal(importi[1]));						//F03-105
        			} else { //Altrimenti recupera sempre l'importo di aggiudicazione
        				Val valTotal = values.addNewVALTOTAL();					    	//F03-102
            			valTotal.setCURRENCY(getCurrency());  
            			valTotal.setBigDecimalValue(UtilityClass.roundDecimal(lottoTed.getAggiudicazione().getImportoAggiudicazione()));
        			}
        		}
        		
        		//INFO SUBAPPALTO
        		if(Costanti.FLAG_VALORE_SI.equals(app.getLIKELYSUBCONTRACTED().value())) {
        			awarded.addNewLIKELYSUBCONTRACTED();									//F03-106
        			Val valSub = awarded.addNewVALSUBCONTRACTING();					    	//F03-107
        			valSub.setCURRENCY(getCurrency());  
        			valSub.setBigDecimalValue(UtilityClass.roundDecimal(app.getVALSUBCONTRACTING().getValue()));
        			
        			if(app.getPCTSUBCONTRACTING()>0)
        				awarded.setPCTSUBCONTRACTING(app.getPCTSUBCONTRACTING());			//F03-108
        			if(app.getINFOADDSUBCONTRACTING()!=null) {
        				Ft infoSub = awarded.addNewINFOADDSUBCONTRACTING().addNewP().addNewFT();//F03-109
        				infoSub.setTYPE(Ft.TYPE.SUB);
        				infoSub.setStringValue(app.getINFOADDSUBCONTRACTING());
        			}
        		}
        		
        	}
        	
        }
        
//        //Complementary-info
        CiF03 complementaryinfo = createComplementaryInfo();
        f032014.setCOMPLEMENTARYINFO(complementaryinfo);
        
		return formSec;
	}
	
	
	private BodyF03 createContractingBody() {
		BodyF03 body = BodyF03.Factory.newInstance();
		DeltaGaraTED deltaGaraTED = getDataNotice().getDeltaGaraTED();
		
		List<AddrS1Type > listaContatti = deltaGaraTED.getDATIAMMAGGIUDICATRICE();
		
		body.setADDRESSCONTRACTINGBODY(createContactContractingBody(listaContatti.get(0)));			//F03-8
		
		if(listaContatti.size()>1) {
			for(int i=1;i<listaContatti.size();i++) {
				AddrS1Type contatto = listaContatti.get(i);
				ContactContractingBody additional = body.addNewADDRESSCONTRACTINGBODYADDITIONAL();	//F03-9
				additional.setOFFICIALNAME(contatto.getOFFICIALNAME());
				additional.setNATIONALID(contatto.getNATIONALID());
				additional.setADDRESS(contatto.getADDRESS());
				additional.setTOWN(contatto.getTOWN());
				additional.setPOSTALCODE(contatto.getPOSTALCODE());
				additional.addNewCOUNTRY().setVALUE(TCountryList.IT); 
				additional.setCONTACTPOINT(contatto.getCONTACTPOINT());
				additional.setPHONE(contatto.getPHONE().getValue().getValue().getValue());
				additional.setEMAIL(contatto.getEMAIL());
				additional.setFAX(contatto.getFAX().getValue().getValue().getValue());
				additional.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(contatto.getNUTS().getValue())); 
				additional.setURLGENERAL(contatto.getURLGENERAL());
				additional.setURLBUYER(contatto.getURLBUYER());
			}
			
			if(deltaGaraTED.getNORMATIVEAPPCONGIUNTO()!=null) {
			    body.addNewJOINTPROCUREMENTINVOLVED();									//F03-10
			    Ft ftl = body.addNewPROCUREMENTLAW().addNewP().addNewFT();				//F03-11
			    ftl.setTYPE(Ft.TYPE.SUP);
			    ftl.setStringValue(deltaGaraTED.getNORMATIVEAPPCONGIUNTO());
			}
		}
		
	
		if(Costanti.FLAG_VALORE_SI.equals(deltaGaraTED.getAPPALTOCC().value()))
			body.addNewCENTRALPURCHASING();											//F03-12
			
		
		if(!deltaGaraTED.getTIPOAMMAGG().getValue().equals("7")) {
			CaType catype = CaType.Factory.newInstance();						//F03-13
			catype.setVALUE(getTipoAmministrazioneAggiudicatrice(deltaGaraTED.getTIPOAMMAGG().getValue()));//F03-14
			body.setCATYPE(catype);
		} else {
			body.setCATYPEOTHER(deltaGaraTED.getALTROTIPOAMMAGG());	//F03-15
		}
		
		if(!deltaGaraTED.getSETTOREPRINCIPALE().getValue().equals("11")) {
			CaActivity caactivity = CaActivity.Factory.newInstance();								//F03-16
			caactivity.setVALUE(getTipoAttivita(deltaGaraTED.getSETTOREPRINCIPALE().getValue()));	//F03-17
			body.setCAACTIVITY(caactivity);
		} else {
			body.setCAACTIVITYOTHER(deltaGaraTED.getALTROSETTOREPRINCIPALE()); 	//F03-18
		}
		
		
		
		
		return body;
	}
	
	private ObjectContractF03 createObjectContract(DeltaGaraTED deltaGara, Gara gara) {
		ObjectContractF03 objContract = ObjectContractF03.Factory.newInstance();		//F03-19
		EntitaAppaltoType entitaAppalto = deltaGara.getENTITAAPPALTO();
		
		List<LottoTED> listaLotto = getDataNotice().getListaLotti();
		FormularioAvvisoAggiudicazione formularioAgg = getDataNotice().getFormularioAgg();
		
		Ft ft = objContract.addNewTITLE().addNewP().addNewFT();						//F03-20
		ft.setTYPE(Ft.TYPE.SUP);
		ft.setStringValue(entitaAppalto.getTITOLOPROCEDURAGARA());
		
		//Non previsto per ora
//		objContract.setREFERENCENUMBER("");											//F03-21
		
		//CPV
		CpvSet cpvmain = objContract.addNewCPVMAIN();												//F03-22
		cpvmain.addNewCPVCODE().setCODE(entitaAppalto.getCPVGARA().split("-")[0]);	//F03-23
		
		//non previsto per ora
//		cpvmain.addNewCPVSUPPLEMENTARYCODE();										//F03-24
		
		//Tipo contratto gara
		objContract.addNewTYPECONTRACT().setCTYPE(getTypeContract(entitaAppalto.getTIPOCONTRATTOAPPALTO()));//F03-25
		
		Ft ft2 = objContract.addNewSHORTDESCR().addNewP().addNewFT();			//F03-26
		ft2.setTYPE(Ft.TYPE.SUB);
		ft2.setStringValue(gara.getOggetto());
		
		if(formularioAgg.getVALOREAPPALTO()!=null) {
		BigDecimal bdval = formularioAgg.getVALOREAPPALTO().getVALTOTAL() != null ? formularioAgg.getVALOREAPPALTO().getVALTOTAL().getValue() : null;
		if(bdval != null && bdval.doubleValue()>0) {
			Val valtot = objContract.addNewVALTOTAL();					//F03-27
			valtot.setCURRENCY(getCurrency());
				valtot.setBigDecimalValue(UtilityClass.roundDecimal(bdval));
		} else {
			
				ValRange range = objContract.addNewVALRANGETOTAL();
				range.setCURRENCY(getCurrency());
				range.setLOW(formularioAgg.getVALOREAPPALTO().getVALRANGETOTALLOW().getValue());//F03-29
				range.setHIGH(formularioAgg.getVALOREAPPALTO().getVALRANGETOTALHIGH().getValue()); //F03-30
				range.setCURRENCY(getCurrency());			//F03-28
			
			}
		}
		

		if(listaLotto.size()>1) 
			objContract.addNewLOTDIVISION();	//F03-31
		 else
			objContract.addNewNOLOTDIVISION();	//F03-32
		
		ObjectF03[] arrObjDesc = new ObjectF03[listaLotto.size()];
		boolean multilotto = listaLotto.size()>1;
		for(int i=0;i<listaLotto.size();i++) {
			DeltaLottoTED deltaLottoTed = listaLotto.get(i).getDeltaLottoTED();
			Lotto lotto = listaLotto.get(i).getLotto();
			arrObjDesc[i] = createObjectDescr(deltaLottoTed,lotto,i,multilotto);
		}
		
		objContract.setOBJECTDESCRArray(arrObjDesc);
		
		return objContract;
	}
	
	private ObjectF03 createObjectDescr(DeltaLottoTED deltaLottoTed,Lotto lotto, int i, boolean multilotto) {
		
		ObjectF03 objDescr = ObjectF03.Factory.newInstance();		
		objDescr.setITEM(i+1);									//F03-33
		DescrizioneAppaltoType descrizioneAppalto = deltaLottoTed.getDESCRIZIONEAPPALTO();
		
		if(multilotto) {
			Ft title = objDescr.addNewTITLE().addNewP().addNewFT();	//F03-34
			title.setStringValue(descrizioneAppalto.getTITOLOAPPALTO());
			title.setTYPE(Ft.TYPE.SUP);
			
			objDescr.setLOTNO(String.valueOf(deltaLottoTed.getNOLOT()));	//F03-35
		}
		
			CpvSet cpvadd = objDescr.addNewCPVADDITIONAL();					//F03-36
			cpvadd.addNewCPVCODE().setCODE(lotto.getId_CPV().split("-")[0]);//F03-37
			
			//Non previsto
//			cpvadd.addNewCPVSUPPLEMENTARYCODE();						//F03-38
		
		
		
		objDescr.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(lotto.getLUOGO_NUTS()));			//F03-39
		
		Ft ft = objDescr.addNewMAINSITE().addNewP().addNewFT();										//F03-40
		ft.setTYPE(Ft.TYPE.SUB);
		ft.setStringValue(descrizioneAppalto.getLUOGOESECUZIONEPRINCIPALE());
		
		Ft ft2 = objDescr.addNewSHORTDESCR().addNewP().addNewFT();									//F03-41
		ft2.setTYPE(Ft.TYPE.SUB);
		ft2.setStringValue(lotto.getOggetto());
		
		AC ac = objDescr.addNewAC();															//F03-42
		
		if(descrizioneAppalto.getCRITERIOAGGLOTTO().getValue().equals("1")) {
			for(it.avlp.simog.ws.massload.xmlbeans.QualityCostCriteriaType qcct : descrizioneAppalto.getCRITERIOQUALITA()) {
				AcDefinition acd = ac.addNewACQUALITY();				//F03-43
				acd.setACCRITERION(qcct.getQPCCRITERIANAME());			//F03-44
				acd.setACWEIGHTING(qcct.getQPCCRITERIAWEIGHTING());		//F03-45
			}
			
			if(descrizioneAppalto.getTIPOCRITERIO().getValue().equals("1")) {
				for(it.avlp.simog.ws.massload.xmlbeans.QualityCostCriteriaType qcct : descrizioneAppalto.getCRITERIOCOSTO()) {
					AcDefinition acd = ac.addNewACCOST();				//F03-46
					acd.setACCRITERION(qcct.getQPCCRITERIANAME());		//F03-47
					acd.setACWEIGHTING(qcct.getQPCCRITERIAWEIGHTING());	//F03-48
				}
			} else if(descrizioneAppalto.getTIPOCRITERIO().getValue().equals("2")) {
					ACPRICE acprice = ac.addNewACPRICE();					//F03-49
					acprice.setACWEIGHTING(descrizioneAppalto.getCRITERIOPREZZO().getPCCRITERIAWEIGHTING());	//F03-50

			}	
		} else {
			ACPRICE acprice = ac.addNewACPRICE();
			acprice.setACWEIGHTING("Criteria stated in proc docs");
			//MAD 38061
			AcDefinition acd = ac.addNewACQUALITY();
			acd.setACCRITERION("Criteria stated in proc docs");	
			acd.setACWEIGHTING("Criteria stated in proc docs");
		}
		
		if(Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_PREVEDE_RIP())) {
		    objDescr.addNewOPTIONS();									//F03-51
		    Ft ft5 = objDescr.addNewOPTIONSDESCR().addNewP().addNewFT();//F03-53
			ft5.setTYPE(Ft.TYPE.SUB);
			ft5.setStringValue(descrizioneAppalto.getDESCRIZIONEOPZIONI());
		}else
			objDescr.addNewNOOPTIONS();									//F03-52
		
		if(!descrizioneAppalto.getFLAGAPPALTOPROGETTOUE().value().equals(Costanti.FLAG_VALORE_SI))
			objDescr.addNewNOEUPROGRRELATED();							//F03-54
		else {
			Ft ft6 = objDescr.addNewEUPROGRRELATED().addNewP().addNewFT();//F03-55
			ft6.setTYPE(Ft.TYPE.SUP);
			ft6.setStringValue(descrizioneAppalto.getAPPALTOPROGETTOUE());
		}

	
		if(descrizioneAppalto.getULTERIORIINFOLOTTO()!=null) {
			Ft ft7 = objDescr.addNewINFOADD().addNewP().addNewFT();			//F03-56
			ft7.setTYPE(Ft.TYPE.SUP);
			ft7.setStringValue(descrizioneAppalto.getULTERIORIINFOLOTTO());
		}
		
		return objDescr;
	}
	
	
	private ProcedureF03 createProcedure(Lotto lotto, DeltaGaraTED deltaGara, Gara gara) {
		DatiProceduraType datiProcedura = deltaGara.getDATIPROCEDURA();
		ProcedureF03 procedure = ProcedureF03.Factory.newInstance();	//F03-57
		
		if(datiProcedura.getTIPOPROCEDURA().equals("1"))
			procedure.addNewPTOPEN();									//F03-58
		else if(datiProcedura.getTIPOPROCEDURA().equals("2"))
				procedure.addNewPTRESTRICTED();							//F03-59
		else if(datiProcedura.getTIPOPROCEDURA().equals("3"))
				procedure.addNewPTCOMPETITIVENEGOTIATION();				//F03-60
		else if(datiProcedura.getTIPOPROCEDURA().equals("4"))
			procedure.addNewPTCOMPETITIVEDIALOGUE();					//F03-61
		else if(datiProcedura.getTIPOPROCEDURA().equals("5"))
			procedure.addNewPTINNOVATIONPARTNERSHIP();					//F03-62
		
		
		if(procedure.isSetPTOPEN() || procedure.isSetPTRESTRICTED() || procedure.isSetPTCOMPETITIVENEGOTIATION()) {
			if(datiProcedura.getFLAGPROCEDURAACCELLERATA().value().equals(Costanti.FLAG_VALORE_SI)) {
				Ft ft = procedure.addNewACCELERATEDPROC().addNewP().addNewFT();											//F03-63
				ft.setTYPE(Ft.TYPE.SUB);
				ft.setStringValue(datiProcedura.getMOTIVAZIONEPROCEDURAACCELLERATA());
			}
		}
		
		if(gara.getID_MODO_REAL()==Costanti.MODOREAL_ACCORDO_QUADRO || gara.getID_MODO_REAL()==Costanti.MODOREAL_CONVENZIONE) {
			 procedure.addNewFRAMEWORK();							//F03-64
		}
		

		if(gara.getID_SVOLGIMENTO()==Costanti.SVOLGIMENTO_SDA && procedure.isSetPTRESTRICTED()) {
			procedure.addNewDPS();//F03-65

		}
		

		if(gara.getID_SVOLGIMENTO()==Costanti.SVOLGIMENTO_ASTA_ELETTRONICA) {
			procedure.addNewEAUCTIONUSED();							//F03-66
		}
		
		if(datiProcedura.getFLAGAPP()!=null && datiProcedura.getFLAGAPP().value().equals(Costanti.FLAG_VALORE_SI))
			procedure.addNewCONTRACTCOVEREDGPA();					//F03-67
		else
			procedure.addNewNOCONTRACTCOVEREDGPA();					//F03-68
		
		procedure.setNOTICENUMBEROJ(getDataNotice().getNoticeNumberOjs());	//F03-69
	
		InfoAmministrativeTypeAgg infoagg = getDataNotice().getFormularioAgg().getINFOAMMINISTRATIVEAGG();
	    if(infoagg!=null) {
	    	if(procedure.isSetDPS() && infoagg.getINFOSDA()!=null && Costanti.FLAG_VALORE_SI.equals(infoagg.getINFOSDA().value()))
	    		procedure.addNewTERMINATIONDPS();		//F03-70
	    	
	    	if(infoagg.getINFOAVVPRE()!=null && Costanti.FLAG_VALORE_SI.equals(infoagg.getINFOAVVPRE().value()))
	    		procedure.addNewTERMINATIONPIN();		//F03-71
	    }
		
		
		return procedure;
	}
	
	private CiF03 createComplementaryInfo() {
		
		CiF03 complementaryinfo = CiF03.Factory.newInstance();							//F03-110
		
		AltreInfoType  altreInfo = getDataNotice().getDeltaGaraTED().getALTREINFO();

		if(altreInfo.getINFOADD()!=null) {
			Ft ft2 = complementaryinfo.addNewINFOADD().addNewP().addNewFT();			//F03-111
			ft2.setTYPE(Ft.TYPE.SUP);
			ft2.setStringValue(altreInfo.getINFOADD());
		}
		
		complementaryinfo.setADDRESSREVIEWBODY(createContactReview(altreInfo.getORGANISMORICORSO()));		//F03-112
		
		if(altreInfo.getORGANISMOMEDIAZIONE()!=null)
		complementaryinfo.setADDRESSMEDIATIONBODY(createContactReview(altreInfo.getORGANISMOMEDIAZIONE()));	//F03-113
		
		if(altreInfo.getREVIEWPROCEDURE()!=null) {
			Ft ft3 = complementaryinfo.addNewREVIEWPROCEDURE().addNewP().addNewFT();	//F03-114
			ft3.setTYPE(Ft.TYPE.SUP);
			ft3.setStringValue(altreInfo.getREVIEWPROCEDURE());
		}
		
		if(altreInfo.getSERVIZIOINFORICORSO()!=null)
		complementaryinfo.setADDRESSREVIEWINFO(createContactReview(altreInfo.getSERVIZIOINFORICORSO()));	//F03-115
		complementaryinfo.setDATEDISPATCHNOTICE(UtilityClass.currentCalendar());							//F03-116
		
		return complementaryinfo;
	}
	


}
