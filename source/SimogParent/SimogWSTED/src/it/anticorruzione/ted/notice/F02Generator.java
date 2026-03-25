package it.anticorruzione.ted.notice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.xmlbeans.XmlAnySimpleType;

import eu.europa.publications.resource.authority.currency.TCurrencyTedschema;
import eu.europa.publications.resource.schema.ted.r209.reception.ACPRICEDocument.ACPRICE;
import eu.europa.publications.resource.schema.ted.r209.reception.AcDefinition;
import eu.europa.publications.resource.schema.ted.r209.reception.BodyF02;
import eu.europa.publications.resource.schema.ted.r209.reception.CaActivity;
import eu.europa.publications.resource.schema.ted.r209.reception.CaType;
import eu.europa.publications.resource.schema.ted.r209.reception.CiF02;
import eu.europa.publications.resource.schema.ted.r209.reception.CondForOpeningTenders;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactContractingBody;
import eu.europa.publications.resource.schema.ted.r209.reception.CpvSet;
import eu.europa.publications.resource.schema.ted.r209.reception.DurationMD;
import eu.europa.publications.resource.schema.ted.r209.reception.DurationUnitMD;
import eu.europa.publications.resource.schema.ted.r209.reception.F022014Document.F022014;
import eu.europa.publications.resource.schema.ted.r209.reception.FormSection;
import eu.europa.publications.resource.schema.ted.r209.reception.FrameworkInfo;
import eu.europa.publications.resource.schema.ted.r209.reception.Ft;
import eu.europa.publications.resource.schema.ted.r209.reception.LeftiF02;
import eu.europa.publications.resource.schema.ted.r209.reception.LotDivisionF02;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectContractF02;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectF02;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectF02.AC;
import eu.europa.publications.resource.schema.ted.r209.reception.OriginalTranslation;
import eu.europa.publications.resource.schema.ted.r209.reception.ProcedureF02;
import eu.europa.publications.resource.schema.ted.r209.reception.ProcedureF02.DURATIONTENDERVALID;
import eu.europa.publications.resource.schema.ted.r209.reception.Services.CTYPE;
import eu.europa.publications.resource.schema.ted.r209.reception.TCeLanguageList;
import eu.europa.publications.resource.schema.ted.r209.reception.TCountryList;
import eu.europa.publications.resource.schema.ted.r209.reception.TLanguageList;
import eu.europa.publications.resource.schema.ted.r209.reception.Val;
import eu.europa.publications.resource.schema.ted.x2021.nuts.TNutsCodeList;
import it.anticorruzione.ted.beans.LottoTED;
import it.anticorruzione.ted.enums.LegalBasisEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.util.UtilityClass;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.ws.beans.DataNotice;
import it.avlp.simog.ws.massload.xmlbeans.AddrS1Type;
import it.avlp.simog.ws.massload.xmlbeans.AltreInfoType;
import it.avlp.simog.ws.massload.xmlbeans.CondizioniPartecipazioneType;
import it.avlp.simog.ws.massload.xmlbeans.DatiProceduraType;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;
import it.avlp.simog.ws.massload.xmlbeans.DescrizioneAppaltoType;
import it.avlp.simog.ws.massload.xmlbeans.EntitaAppaltoType;
import it.avlp.simog.ws.massload.xmlbeans.InfoAmministrativeType;

public class F02Generator extends AbstractNoticeGenerator {

	public F02Generator(DataNotice dataNotice, String version) {
		super(dataNotice,
				version, 
				LegalBasisEnum.DIR_201424EU, 
				TypeNoticeEnum.F02);
	}
	
	@Override
	public FormSection createFormSection() {
		DeltaGaraTED deltaGara = getDataNotice().getDeltaGaraTED();
		Gara gara = getDataNotice().getGara();
		
		FormSection formSec = FormSection.Factory.newInstance();
		F022014 f022014 = formSec.addNewF022014();
		f022014.setCATEGORY(OriginalTranslation.ORIGINAL);				//F02-3
		
		XmlAnySimpleType form = XmlAnySimpleType.Factory.newInstance();
		form.setStringValue(TypeNoticeEnum.F02.getTipo());				//F02-4
		f022014.setFORM(form);
		f022014.setLG(TCeLanguageList.IT);								//F02-5
		
        f022014.addNewLEGALBASIS().setVALUE(getLegalBasis());			//F02-6
        BodyF02 body = createContractingBody();
		
        f022014.setCONTRACTINGBODY(body);
        
        //Object Contract
        ObjectContractF02 objContract = createObjectContract(deltaGara, gara);
        f022014.setOBJECTCONTRACT(objContract);
        
        //LEFTI
        if(deltaGara.getCONDIZIONIPARTECIPAZIONE()!=null) {
	        LeftiF02 lefti = createLefti(deltaGara.getCONDIZIONIPARTECIPAZIONE(),deltaGara.getENTITAAPPALTO().getTIPOCONTRATTOAPPALTO());
	        f022014.setLEFTI(lefti);
        }
        
        //Procedure
        Lotto lotto = getDataNotice().getListaLotti().get(0).getLotto();
        ProcedureF02 procedure = createProcedure(lotto,deltaGara,gara);
        f022014.setPROCEDURE(procedure);
        
        //Complementary-info
        CiF02 complementaryinfo = createComplementaryInfo();
        f022014.setCOMPLEMENTARYINFO(complementaryinfo);
        
		return formSec;
	}
	


	

	private BodyF02 createContractingBody() {
		BodyF02 body = BodyF02.Factory.newInstance();
		DeltaGaraTED deltaGaraTED = getDataNotice().getDeltaGaraTED();
		
		List<AddrS1Type > listaContatti = deltaGaraTED.getDATIAMMAGGIUDICATRICE();
		
		body.setADDRESSCONTRACTINGBODY(createContactContractingBody(listaContatti.get(0)));     //F02-8
		
		if(listaContatti.size()>1) {
			for(int i=1;i<listaContatti.size();i++) {
				AddrS1Type contatto = listaContatti.get(i);
				ContactContractingBody additional = body.addNewADDRESSCONTRACTINGBODYADDITIONAL();   //F02-9
				additional.setOFFICIALNAME(contatto.getOFFICIALNAME());
				
				if(contatto.getNATIONALID()!=null && !"".equals(contatto.getNATIONALID()))
					additional.setNATIONALID(contatto.getNATIONALID());
				
				if(contatto.getADDRESS()!=null && !"".equals(contatto.getADDRESS()))
					additional.setADDRESS(contatto.getADDRESS());

				additional.setTOWN(contatto.getTOWN());
				
				if(contatto.getPOSTALCODE()!=null && !"".equals(contatto.getPOSTALCODE()))
					additional.setPOSTALCODE(contatto.getPOSTALCODE());

				additional.addNewCOUNTRY().setVALUE(TCountryList.IT);
				
				if(contatto.getCONTACTPOINT()!=null && !"".equals(contatto.getCONTACTPOINT()))
					additional.setCONTACTPOINT(contatto.getCONTACTPOINT());
				
				if(contatto.getPHONE()!=null && !"".equals(contatto.getPHONE().getValue().getValue().getValue()))
					additional.setPHONE(contatto.getPHONE().getValue().getValue().getValue());

				additional.setEMAIL(contatto.getEMAIL());
				
				if(contatto.getFAX()!=null && !"".equals(contatto.getFAX().getValue().getValue().getValue()))
					additional.setFAX(contatto.getFAX().getValue().getValue().getValue());

				additional.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(contatto.getNUTS().getValue())); 
				additional.setURLGENERAL(contatto.getURLGENERAL());
				
				if(contatto.getURLBUYER()!=null && !"".equals(contatto.getURLBUYER()))
					additional.setURLBUYER(contatto.getURLBUYER());



			}
			if(deltaGaraTED.getNORMATIVEAPPCONGIUNTO()!=null) {    
			    body.addNewJOINTPROCUREMENTINVOLVED(); //F02-10
			    Ft ftl = body.addNewPROCUREMENTLAW().addNewP().addNewFT(); //F02-11
			    ftl.setTYPE(Ft.TYPE.SUP);
			    ftl.setStringValue(deltaGaraTED.getNORMATIVEAPPCONGIUNTO());
			}
		}
		
		
		
		if(Costanti.FLAG_VALORE_SI.equals(deltaGaraTED.getAPPALTOCC().value()))
			body.addNewCENTRALPURCHASING();	//F02-12
		
		if(deltaGaraTED.getDOCUMENTIDISPONIBILI().getValue().equals("1"))
			body.addNewDOCUMENTFULL();	//F02-13a
		else
			body.addNewDOCUMENTRESTRICTED();	//F02-13b
				
		body.setURLDOCUMENT(deltaGaraTED.getURLDOCDISPONIBILI());	//F02-15
		
		if(deltaGaraTED.getINFOAGGIUNTIVE().getValue().equals("1"))
			body.addNewADDRESSFURTHERINFOIDEM();	//F02-16a
		else {
			ContactContractingBody partecipation = body.addNewADDRESSFURTHERINFO(); //F02-16b
			AddrS1Type contatto = deltaGaraTED.getALTROINDIRIZZOIA();
			partecipation.setOFFICIALNAME(contatto.getOFFICIALNAME());
			
			if(contatto.getNATIONALID()!=null && !"".equals(contatto.getNATIONALID()))
				partecipation.setNATIONALID(contatto.getNATIONALID());
			
			if(contatto.getADDRESS()!=null && !"".equals(contatto.getADDRESS()))
				partecipation.setADDRESS(contatto.getADDRESS());

			partecipation.setTOWN(contatto.getTOWN());
			
			if(contatto.getPOSTALCODE()!=null && !"".equals(contatto.getPOSTALCODE()))
				partecipation.setPOSTALCODE(contatto.getPOSTALCODE());

			partecipation.addNewCOUNTRY().setVALUE(TCountryList.Enum.forString(contatto.getCOUNTRY().getValue()));
			
			if(contatto.getCONTACTPOINT()!=null && !"".equals(contatto.getCONTACTPOINT()))
				partecipation.setCONTACTPOINT(contatto.getCONTACTPOINT());
			
			if(contatto.getPHONE()!=null && !"".equals(contatto.getPHONE().getValue().getValue().getValue()))
				partecipation.setPHONE(contatto.getPHONE().getValue().getValue().getValue());

			partecipation.setEMAIL(contatto.getEMAIL());
			
			if(contatto.getFAX()!=null && !"".equals(contatto.getFAX().getValue().getValue().getValue()))
				partecipation.setFAX(contatto.getFAX().getValue().getValue().getValue());

			partecipation.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(contatto.getNUTS().getValue())); 
			partecipation.setURLGENERAL(contatto.getURLGENERAL());
			
			if(contatto.getURLBUYER()!=null && !"".equals(contatto.getURLBUYER()))
				partecipation.setURLBUYER(contatto.getURLBUYER());


		}
		
		if(deltaGaraTED.getURLVERSIONEELETTRONICA()!=null)
			body.setURLPARTICIPATION(deltaGaraTED.getURLVERSIONEELETTRONICA()); //F02-18
		
		if(deltaGaraTED.getALTROINDIRIZZOPARTECIPAZIONE()==null)
			body.addNewADDRESSPARTICIPATIONIDEM();				//F02-19a
		else {
			AddrS1Type contatto = deltaGaraTED.getALTROINDIRIZZOPARTECIPAZIONE();	//F02-19b
			ContactContractingBody partecipation = body.addNewADDRESSPARTICIPATION();
			partecipation.setOFFICIALNAME(contatto.getOFFICIALNAME());
			
			if(contatto.getNATIONALID()!=null && !"".equals(contatto.getNATIONALID()))
				partecipation.setNATIONALID(contatto.getNATIONALID());
			
			if(contatto.getADDRESS()!=null && !"".equals(contatto.getADDRESS()))
				partecipation.setADDRESS(contatto.getADDRESS());

			partecipation.setTOWN(contatto.getTOWN());
			
			if(contatto.getPOSTALCODE()!=null && !"".equals(contatto.getPOSTALCODE()))
			partecipation.setPOSTALCODE(contatto.getPOSTALCODE());

			partecipation.addNewCOUNTRY().setVALUE(TCountryList.Enum.forString(contatto.getCOUNTRY().getValue()));
			
			if(contatto.getCONTACTPOINT()!=null && !"".equals(contatto.getCONTACTPOINT()))
				partecipation.setCONTACTPOINT(contatto.getCONTACTPOINT());
			
			if(contatto.getPHONE()!=null && !"".equals(contatto.getPHONE().getValue().getValue().getValue()))
				partecipation.setPHONE(contatto.getPHONE().getValue().getValue().getValue());

			partecipation.setEMAIL(contatto.getEMAIL());
			
			if(contatto.getFAX()!=null && !"".equals(contatto.getFAX().getValue().getValue().getValue()))
				partecipation.setFAX(contatto.getFAX().getValue().getValue().getValue());

			partecipation.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(contatto.getNUTS().getValue())); 
			partecipation.setURLGENERAL(contatto.getURLGENERAL());
			
			if(contatto.getURLBUYER()!=null && !"".equals(contatto.getURLBUYER()))
				partecipation.setURLBUYER(contatto.getURLBUYER());

		}
		
		if(deltaGaraTED.getURLSTRUMENTI()!=null)
			body.setURLTOOL(deltaGaraTED.getURLSTRUMENTI()); 	//F02-20
		
		if(!deltaGaraTED.getTIPOAMMAGG().getValue().equals("7")) {
			CaType catype = CaType.Factory.newInstance();		//F02-23a
			catype.setVALUE(getTipoAmministrazioneAggiudicatrice(deltaGaraTED.getTIPOAMMAGG().getValue()));
			body.setCATYPE(catype);
		} else {
			body.setCATYPEOTHER(deltaGaraTED.getALTROTIPOAMMAGG());	//F02-23b
		}
		
		if(!deltaGaraTED.getSETTOREPRINCIPALE().getValue().equals("11")) {
			CaActivity caactivity = CaActivity.Factory.newInstance();	//F02-26a
			caactivity.setVALUE(getTipoAttivita(deltaGaraTED.getSETTOREPRINCIPALE().getValue()));
			body.setCAACTIVITY(caactivity);
		} else {
			body.setCAACTIVITYOTHER(deltaGaraTED.getALTROSETTOREPRINCIPALE()); 	//F02-26b
		}
		
		return body;
	}
	
	private ObjectContractF02 createObjectContract(DeltaGaraTED deltaGara, Gara gara) {
		ObjectContractF02 objContract = ObjectContractF02.Factory.newInstance();
		EntitaAppaltoType entitaAppalto = deltaGara.getENTITAAPPALTO();

		List<LottoTED> listaLotto = getDataNotice().getListaLotti();
		
		
		Ft ft = objContract.addNewTITLE().addNewP().addNewFT();				//F02-29
		ft.setTYPE(Ft.TYPE.SUP);
		ft.setStringValue(entitaAppalto.getTITOLOPROCEDURAGARA());
		
		//CPV
		CpvSet cpvmain = objContract.addNewCPVMAIN();
		cpvmain.addNewCPVCODE().setCODE(entitaAppalto.getCPVGARA().split("-")[0]);	//F02-32
		
		//Tipo contratto gara
		objContract.addNewTYPECONTRACT().setCTYPE(getTypeContract(entitaAppalto.getTIPOCONTRATTOAPPALTO())); //F02-34
		
		Ft ft2 = objContract.addNewSHORTDESCR().addNewP().addNewFT();	//F02-35
		ft2.setTYPE(Ft.TYPE.SUB);
		ft2.setStringValue(gara.getOggetto());
		
		double valoreStimato = 0.0;
		for(LottoTED l : listaLotto) {
			valoreStimato+=(l.getLotto().getImporto_Lotto().doubleValue());

		}
		
		Val valest = objContract.addNewVALESTIMATEDTOTAL();	//F02-36
		valest.setCURRENCY(getCurrency());
		BigDecimal bdValoreStimato = new BigDecimal(valoreStimato);
		valest.setBigDecimalValue(bdValoreStimato.setScale(2,RoundingMode.DOWN));
		
		if(listaLotto.size()>1) {
			LotDivisionF02 ld02 = objContract.addNewLOTDIVISION(); //F02-37a
			String maxLottiPart = entitaAppalto.getMAXLOTTIPARTECIPAZIONE().getValue();
			if(maxLottiPart.equals("1"))
				ld02.addNewLOTALL();		//F02-40a
			if(maxLottiPart.equals("2")) 
				ld02.setLOTMAXNUMBER(entitaAppalto.getNUMMAXLOTTIPARTECIPAZIONE());	//F02-40b
			if(maxLottiPart.equals("3"))
				ld02.addNewLOTONEONLY(); //F02-40c
			
			ld02.setLOTMAXONETENDERER(entitaAppalto.getNUMMAXLOTTIOFFERENTE());	//F02-43
			
			if(Costanti.FLAG_VALORE_SI.equals(entitaAppalto.getFLAGSAAGGGRUPPILOTTI().value()) && entitaAppalto.getSAAGGGRUPPILOTTI()!=null) {
				Ft ft3 = ld02.addNewLOTCOMBININGCONTRACTRIGHT().addNewP().addNewFT();	//F02-44
				ft3.setTYPE(Ft.TYPE.SUB);
				ft3.setStringValue(entitaAppalto.getSAAGGGRUPPILOTTI());
			}
		} else
			objContract.addNewNOLOTDIVISION(); //F02-37b
		
		ObjectF02[] arrObjDesc = new ObjectF02[listaLotto.size()];
		boolean multilotto = listaLotto.size()>1;

		for(int i=0;i<listaLotto.size();i++) {
			LottoTED lottoTed = listaLotto.get(i);
			arrObjDesc[i] = createObjectDescr(lottoTed,i,multilotto);
		}

		objContract.setOBJECTDESCRArray(arrObjDesc);
		
		return objContract;
	}
	
	private ObjectF02 createObjectDescr(LottoTED lottoTed, int i, boolean multilotto) {
		
		Lotto lotto = lottoTed.getLotto();
		DeltaLottoTED deltaLottoTed = lottoTed.getDeltaLottoTED();
		
		ObjectF02 objDescr = ObjectF02.Factory.newInstance();	//F02-45
		objDescr.setITEM(i+1);
		
		DescrizioneAppaltoType descrizioneAppalto = deltaLottoTed.getDESCRIZIONEAPPALTO();
		
		if(multilotto) {
			 Ft tit = 	objDescr.addNewTITLE().addNewP().addNewFT();	//F02-46
			 tit.setTYPE(Ft.TYPE.SUB);
			 tit.setStringValue(descrizioneAppalto.getTITOLOAPPALTO());
			 
			 objDescr.setLOTNO(lotto.getFullCIG());	//F02-47
			 
			 CpvSet cpvMain = objDescr.addNewCPVADDITIONAL();
			 cpvMain.addNewCPVCODE().setCODE(lotto.getId_CPV().split("-")[0]);
		}
		
		
		for(CpvLotto cpvLotto : lotto.getElencoCpvSecondarie()) {
			CpvSet cpvadd = objDescr.addNewCPVADDITIONAL();
			cpvadd.addNewCPVCODE().setCODE(cpvLotto.getIdCpv().split("-")[0]);	//F02-49
		}
		
		objDescr.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(lotto.getLUOGO_NUTS()));		//F02-51
		Ft ft = objDescr.addNewMAINSITE().addNewP().addNewFT();									//F02-52
		ft.setTYPE(Ft.TYPE.SUB);
		ft.setStringValue(descrizioneAppalto.getLUOGOESECUZIONEPRINCIPALE());
		
		Ft ft2 = objDescr.addNewSHORTDESCR().addNewP().addNewFT();								//F02-53
		ft2.setTYPE(Ft.TYPE.SUB);
		ft2.setStringValue(lotto.getOggetto());
		
		AC ac = objDescr.addNewAC();															//F02-54
		
		if(descrizioneAppalto.getCRITERIOAGGLOTTO().getValue().equals("1")) {
			for(it.avlp.simog.ws.massload.xmlbeans.QualityCostCriteriaType qcct : deltaLottoTed.getDESCRIZIONEAPPALTO().getCRITERIOQUALITA()) {
				AcDefinition acd = ac.addNewACQUALITY();			//F02-55
				acd.setACCRITERION(qcct.getQPCCRITERIANAME());		//F02-56
				acd.setACWEIGHTING(qcct.getQPCCRITERIAWEIGHTING());	//F02-57
			}
			
			if(descrizioneAppalto.getTIPOCRITERIO().getValue().equals("1")) {
				for(it.avlp.simog.ws.massload.xmlbeans.QualityCostCriteriaType qcct : descrizioneAppalto.getCRITERIOCOSTO()) {
					AcDefinition acd = ac.addNewACCOST();				//F02-58
					acd.setACCRITERION(qcct.getQPCCRITERIANAME());		//F02-59
					acd.setACWEIGHTING(qcct.getQPCCRITERIAWEIGHTING());	//F02-60
				}
			} else if(descrizioneAppalto.getTIPOCRITERIO().getValue().equals("2")) {
					ACPRICE acprice = ac.addNewACPRICE();					//F02-61
					acprice.setACWEIGHTING(descrizioneAppalto.getCRITERIOPREZZO().getPCCRITERIAWEIGHTING());	//F02-62

			}	
		} else if(descrizioneAppalto.getCRITERIOAGGLOTTO().getValue().equals("2")) 
				ac.addNewACPROCUREMENTDOC();		//F02-63
		
		Val valest = objDescr.addNewVALOBJECT();	//F02-64
		valest.setCURRENCY(getCurrency());
		valest.setBigDecimalValue(lotto.getImporto_Lotto());
		
//		if(descrizioneAppalto.getDURATACONTRATTOMESI()>0) {
//			DurationMD duration = DurationMD.Factory.newInstance();
//			duration.setTYPE(DurationUnitMD.MONTH);	//F02-66a
//			duration.setIntValue(descrizioneAppalto.getDURATACONTRATTOMESI());
//			objDescr.setDURATION(duration);
//		} else 
		
				DurationMD duration = DurationMD.Factory.newInstance();
				duration.setTYPE(DurationUnitMD.DAY);	//F02-66b
				duration.setIntValue(lotto.getDurataAffidamentoGiorni()); 
				objDescr.setDURATION(duration);
		   
			
//		else { 
//			if(descrizioneAppalto.getINIZIOCONTRATTOLOTTO()!=null) {
//				Date dataInizio = descrizioneAppalto.getINIZIOCONTRATTOLOTTO().getValue();
//				objDescr.setDATESTART(UtilityClass.dateToCalendar(dataInizio));	//F02-68
//			}
//			if(descrizioneAppalto.getFINECONTRATTOLOTTO()!=null) {
//				Date dataFine = descrizioneAppalto.getFINECONTRATTOLOTTO().getValue();
//				objDescr.setDATEEND(UtilityClass.dateToCalendar(dataFine));	//F02-69
//			}
//		}

		if(descrizioneAppalto.getDESCRINNOVICONTR()!=null) {
			objDescr.addNewRENEWAL();										//F02-70a
			Ft ft3 = objDescr.addNewRENEWALDESCR().addNewP().addNewFT();	//F02-71
			ft3.setTYPE(Ft.TYPE.SUB);
			ft3.setStringValue(descrizioneAppalto.getDESCRINNOVICONTR());
		} else
		   objDescr.addNewNORENEWAL();			//F02-70b
		
		
		if(!lotto.getId_Scelta_Contraente().equals(String.valueOf(Costanti.PROC_APE))) {
			if(descrizioneAppalto.getNUMCANDIDATIPREVISTI()!=null && descrizioneAppalto.getNUMCANDIDATIPREVISTI()>0)
				objDescr.setNBENVISAGEDCANDIDATE(descrizioneAppalto.getNUMCANDIDATIPREVISTI());		//F02-74
			else {
				if(descrizioneAppalto.getMINNUMCANDIDATIPREVISTI() != null && descrizioneAppalto.getMINNUMCANDIDATIPREVISTI()>0)
					objDescr.setNBMINLIMITCANDIDATE(descrizioneAppalto.getMINNUMCANDIDATIPREVISTI());//F02-75
				if(descrizioneAppalto.getMAXNUMCANDIDATIPREVISTI() != null && descrizioneAppalto.getMAXNUMCANDIDATIPREVISTI()>0)
					objDescr.setNBMAXLIMITCANDIDATE(descrizioneAppalto.getMAXNUMCANDIDATIPREVISTI());//F02-76
				
				
			}
			
			if(objDescr.isSetNBENVISAGEDCANDIDATE() || objDescr.isSetNBMINLIMITCANDIDATE() || objDescr.isSetNBMAXLIMITCANDIDATE()) {
				if(descrizioneAppalto.getCRITERIMAXNUMCANDIDATI()!=null) {
					Ft ft4 = objDescr.addNewCRITERIACANDIDATE().addNewP().addNewFT();				//F02-77
					ft4.setTYPE(Ft.TYPE.SUB);
					ft4.setStringValue(descrizioneAppalto.getCRITERIMAXNUMCANDIDATI());
				}
			}
		}
		
		if(descrizioneAppalto.getACCETTATEVARIANTI().value().equals(Costanti.FLAG_VALORE_SI))
		    objDescr.addNewACCEPTEDVARIANTS();	//F02-79
		else
			objDescr.addNewNOACCEPTEDVARIANTS();//F02-80
		
		if(Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_PREVEDE_RIP())) {
		    objDescr.addNewOPTIONS();										//F02-81
		    Ft ft5 = objDescr.addNewOPTIONSDESCR().addNewP().addNewFT();	//F02-83
			ft5.setTYPE(Ft.TYPE.SUB);
			ft5.setStringValue(descrizioneAppalto.getDESCRIZIONEOPZIONI());
		}else
			objDescr.addNewNOOPTIONS();//F02-82
		
		if(descrizioneAppalto.getPRESOFFERTECATALOGOELETTRONICO().value().equals(Costanti.FLAG_VALORE_SI))
			objDescr.addNewECATALOGUEREQUIRED(); //F02-84
		if(descrizioneAppalto.getFLAGAPPALTOPROGETTOUE().value().equals(Costanti.FLAG_VALORE_NO))
			objDescr.addNewNOEUPROGRRELATED();	//F02-85
		else {
			Ft ft6 = objDescr.addNewEUPROGRRELATED().addNewP().addNewFT();	//F02-86
			ft6.setTYPE(Ft.TYPE.SUB);
			ft6.setStringValue(descrizioneAppalto.getAPPALTOPROGETTOUE());
		}

	
		if(descrizioneAppalto.getULTERIORIINFOLOTTO()!=null) {
			Ft ft7 = objDescr.addNewINFOADD().addNewP().addNewFT();		//F02-87
			ft7.setTYPE(Ft.TYPE.SUB);
			ft7.setStringValue(descrizioneAppalto.getULTERIORIINFOLOTTO());
		}
		
		return objDescr;
	}
	
	private LeftiF02 createLefti(CondizioniPartecipazioneType cpt, String tipoAppalto) {
		LeftiF02 lefti = LeftiF02.Factory.newInstance();								//F02-88
		
		if(cpt.getELENCOCONDIZIONI()!=null) {
			Ft ft = lefti.addNewSUITABILITY().addNewP().addNewFT();						//F02-89
			ft.setTYPE(Ft.TYPE.SUB);
			ft.setStringValue(cpt.getELENCOCONDIZIONI());
		}
		
		if(cpt.getCRITERIECONOMICI().value().equals(Costanti.FLAG_VALORE_SI))
			lefti.addNewECONOMICCRITERIADOC();											//F02-90
		else {
			if(cpt.getELENCOCRITERIECONOMICI()!=null) {
				Ft ft2 = lefti.addNewECONOMICFINANCIALINFO().addNewP().addNewFT();		//F02-91
				ft2.setTYPE(Ft.TYPE.SUB);
				ft2.setStringValue(cpt.getELENCOCRITERIECONOMICI());
			}
			if(cpt.getLIVELLICRITERIECONOMICI()!=null) {
				Ft ft3 = lefti.addNewECONOMICFINANCIALMINLEVEL().addNewP().addNewFT();	//F02-92
				ft3.setTYPE(Ft.TYPE.SUB);
				ft3.setStringValue(cpt.getLIVELLICRITERIECONOMICI());
			}
		}
		
		if(cpt.getCRITERITECNICI().value().equals(Costanti.FLAG_VALORE_SI))
			lefti.addNewTECHNICALCRITERIADOC();											//F02-93
		else {
			if(cpt.getELENCOCRITERITECNICI()!=null) {
				Ft ft2 = lefti.addNewTECHNICALPROFESSIONALINFO().addNewP().addNewFT();	//F02-94
				ft2.setTYPE(Ft.TYPE.SUB);
				ft2.setStringValue(cpt.getELENCOCRITERITECNICI());
			}
			if(cpt.getLIVELLICRITERITECNICI()!=null) {
				Ft ft3 = lefti.addNewTECHNICALPROFESSIONALMINLEVEL().addNewP().addNewFT();//F02-95
				ft3.setTYPE(Ft.TYPE.SUB);
				ft3.setStringValue(cpt.getLIVELLICRITERITECNICI());
			}
		}
		
		if(cpt.getINTEGRAZIONEDISABILI()!=null && cpt.getINTEGRAZIONEDISABILI().value().equals(Costanti.FLAG_VALORE_SI))
			lefti.addNewRESTRICTEDSHELTEREDWORKSHOP();									//F02-96
		
		if(cpt.getLAVORIPROTETTI()!= null && cpt.getLAVORIPROTETTI().value().equals(Costanti.FLAG_VALORE_SI))
			lefti.addNewRESTRICTEDSHELTEREDPROGRAM();									//F02-97
		
		if(tipoAppalto.equals("2")) {
			if(cpt.getFLAGPROFESSIONESERVIZI()!=null && cpt.getFLAGPROFESSIONESERVIZI().value().equals(Costanti.FLAG_VALORE_SI)) {
			   lefti.addNewPARTICULARPROFESSION().setCTYPE(CTYPE.SERVICES);
			   Ft ft4 =lefti.addNewREFERENCETOLAW().addNewP().addNewFT();				//F02-99
			   ft4.setTYPE(Ft.TYPE.SUB);
				ft4.setStringValue(cpt.getPROFESSIONESERVIZI());
			}
			
			if(cpt.getCONDIZIONIESECUZIONECONTRATTO()!=null) {
				Ft ft5 =lefti.addNewPERFORMANCECONDITIONS().addNewP().addNewFT();		//F02-100
				   ft5.setTYPE(Ft.TYPE.SUB);
					ft5.setStringValue(cpt.getCONDIZIONIESECUZIONECONTRATTO());
			}
			
			if(cpt.getOBBLIGONOMIESECUZIONECONTRATTO()!= null && cpt.getOBBLIGONOMIESECUZIONECONTRATTO().value().equals(Costanti.FLAG_VALORE_SI))
				lefti.addNewPERFORMANCESTAFFQUALIFICATION();							//F02-101
			
		}
		
		return lefti;
	}

	private ProcedureF02 createProcedure(Lotto lotto, DeltaGaraTED deltaGara, Gara gara) {
		
		DatiProceduraType datiProcedura = deltaGara.getDATIPROCEDURA();
		
		ProcedureF02 procedure = ProcedureF02.Factory.newInstance();							//F02-104
		
		
		if(datiProcedura.getTIPOPROCEDURA().equals("1"))
			procedure.addNewPTOPEN();															//F02-105
		else if(datiProcedura.getTIPOPROCEDURA().equals("2"))
				procedure.addNewPTRESTRICTED();													//F02-106
		else if(datiProcedura.getTIPOPROCEDURA().equals("3"))
				procedure.addNewPTCOMPETITIVENEGOTIATION();										//F02-107
		else if(datiProcedura.getTIPOPROCEDURA().equals("4"))
			procedure.addNewPTCOMPETITIVEDIALOGUE();											//F02-108
		else if(datiProcedura.getTIPOPROCEDURA().equals("5"))
			procedure.addNewPTINNOVATIONPARTNERSHIP();											//F02-109
		
		
		if(procedure.isSetPTOPEN() || procedure.isSetPTRESTRICTED() || procedure.isSetPTCOMPETITIVENEGOTIATION()) {
			if(datiProcedura.getFLAGPROCEDURAACCELLERATA().value().equals(Costanti.FLAG_VALORE_SI)) {
				Ft ft = procedure.addNewACCELERATEDPROC().addNewP().addNewFT();						//F02-110
				ft.setTYPE(Ft.TYPE.SUB);
				ft.setStringValue(datiProcedura.getMOTIVAZIONEPROCEDURAACCELLERATA());
			}
		}
		
		//TAG FRAMEWORK
		if(gara.getID_MODO_REAL()==Costanti.MODOREAL_ACCORDO_QUADRO || gara.getID_MODO_REAL()==Costanti.MODOREAL_CONVENZIONE) {
			FrameworkInfo framework = procedure.addNewFRAMEWORK();								//F02-111
			if(datiProcedura.getTIPOOPERATORIAQ().getValue().equals("1"))
				framework.addNewSINGLEOPERATOR();												//F02-112
			else if(datiProcedura.getTIPOOPERATORIAQ().getValue().equals("2")) {
				framework.addNewSEVERALOPERATORS();												//F02-113
				
				if(datiProcedura.getNUMMAXPARTECIPANTIAQ()!=null && datiProcedura.getNUMMAXPARTECIPANTIAQ().intValue()>0)
					framework.setNBPARTICIPANTS(datiProcedura.getNUMMAXPARTECIPANTIAQ().intValue()); //F02-114
			}
			
			if(datiProcedura.getNOTEAQQUATTROANNI()!=null) {
				Ft ft2 = framework.addNewJUSTIFICATION().addNewP().addNewFT();					//F02-115
				ft2.setTYPE(Ft.TYPE.SUP);
				ft2.setStringValue(datiProcedura.getNOTEAQQUATTROANNI());
			}
		}//FINE TAG FRAMEWORK
		
		
		if(gara.getID_SVOLGIMENTO()==Costanti.SVOLGIMENTO_SDA && procedure.isSetPTRESTRICTED()) {
			procedure.addNewDPS();																//F02-116
			if(datiProcedura.getALTRIACQUIRENTISISDINAMICO()!=null && datiProcedura.getALTRIACQUIRENTISISDINAMICO().value().equals(Costanti.FLAG_VALORE_SI))
					procedure.addNewDPSADDITIONALPURCHASERS();									//F02-117
		}
		
		if(procedure.isSetPTCOMPETITIVENEGOTIATION() || procedure.isSetPTCOMPETITIVEDIALOGUE() || procedure.isSetPTINNOVATIONPARTNERSHIP())
			if(datiProcedura.getREDUCTIONRECOURSE()!=null && datiProcedura.getREDUCTIONRECOURSE().value().equals(Costanti.FLAG_VALORE_SI))
					procedure.addNewREDUCTIONRECOURSE();												//F02-118
		
		if(procedure.isSetPTCOMPETITIVENEGOTIATION() && 
				datiProcedura.getAGGIUDICAZIONESENZANEGOZIAZIONE()!=null &&
				datiProcedura.getAGGIUDICAZIONESENZANEGOZIAZIONE().value().equals(Costanti.FLAG_VALORE_SI))
			procedure.addNewRIGHTCONTRACTINITIALTENDERS();										//F02-119
		
		
		if(gara.getID_SVOLGIMENTO()==Costanti.SVOLGIMENTO_ASTA_ELETTRONICA) {
			procedure.addNewEAUCTIONUSED();														//F02-120
			Ft ft3 = procedure.addNewINFOADDEAUCTION().addNewP().addNewFT();					//F02-121
			ft3.setTYPE(Ft.TYPE.SUP);
			ft3.setStringValue(datiProcedura.getNOTEASTAELETTRONICA());
		}
		
		if(datiProcedura.getFLAGAPP()!=null && datiProcedura.getFLAGAPP().value().equals(Costanti.FLAG_VALORE_SI))
			procedure.addNewCONTRACTCOVEREDGPA();												//F02-122
		else
			procedure.addNewNOCONTRACTCOVEREDGPA();												//F02-123
		
		//Non previsto																			//F02-124
//		procedure.setNOTICENUMBEROJ(arg0);
		
		//Se la data scadenza pagamenti è nulla, allora è perfezionata alla prima fase e va presa la data scadenza richiesta invito
		procedure.setDATERECEIPTTENDERS(lotto.getDATA_SCADENZA_PAGAMENTI() == null ?  
				UtilityClass.stringToCalendar(lotto.getDataScadenzaRichiestaInvito(),"yyyyMMdd")
				: 
					UtilityClass.stringToCalendar(lotto.getDATA_SCADENZA_PAGAMENTI(),"yyyyMMdd"));//F02-125
		procedure.setTIMERECEIPTTENDERS(getDataNotice().getOraScadenzaPag());								//F02-126
		
		if(!procedure.isSetPTOPEN() && lotto.getDataLetteraInvito()!=null) 
		   procedure.setDATEDISPATCHINVITATIONS(UtilityClass.stringToCalendar(lotto.getDataLetteraInvito(),"yyyyMMdd"));//F02-127
		
		procedure.addNewLANGUAGES().addNewLANGUAGE().setVALUE(TLanguageList.IT);					//F02-128-129
		
		InfoAmministrativeType infoAmm=deltaGara.getINFOAMMINISTRATIVE();
		
		if(infoAmm.getPERIODOVALIDITAOFFERTE()!=null) {
			Date dataPeriodoVal = infoAmm.getPERIODOVALIDITAOFFERTE().getValue();
			procedure.setDATETENDERVALID(UtilityClass.dateToCalendar(dataPeriodoVal));			//F02-130
		} else 
			if(infoAmm.getMESIVALIDITAOFFERTE()!=null && infoAmm.getMESIVALIDITAOFFERTE()>0) {
				DURATIONTENDERVALID dtv = procedure.addNewDURATIONTENDERVALID();					//F02-131
				XmlAnySimpleType form = XmlAnySimpleType.Factory.newInstance();
				form.setStringValue("MONTH");
				dtv.setTYPE(form);
				dtv.setIntValue(infoAmm.getMESIVALIDITAOFFERTE());
				procedure.setDURATIONTENDERVALID(dtv);
		}
			
		if(procedure.isSetPTOPEN()) {
			CondForOpeningTenders opCond = procedure.addNewOPENINGCONDITION();					//F02-132
			
			Date dataAperturaOff = infoAmm.getDATAAPERTURAOFFERTE().getValue();
			opCond.setDATEOPENINGTENDERS(UtilityClass.dateToCalendar(dataAperturaOff));			//F02-133
			opCond.setTIMEOPENINGTENDERS(infoAmm.getORAAPERTURAOFFERTE().getValue());//F02-134
			
			if(infoAmm.getLUOGOAPERTURAOFFERTE()!=null && !"".equals(infoAmm.getLUOGOAPERTURAOFFERTE())) {
				Ft ft4 = opCond.addNewPLACE().addNewP().addNewFT();						//F02-135
				ft4.setTYPE(Ft.TYPE.SUP);
				ft4.setStringValue(infoAmm.getLUOGOAPERTURAOFFERTE());
			}
			if(infoAmm.getPERSONEAPERTURAOFFERTE()!=null && !"".equals(infoAmm.getPERSONEAPERTURAOFFERTE())) {
				Ft ft5 = opCond.addNewINFOADD().addNewP().addNewFT();					//F02-136
				ft5.setTYPE(Ft.TYPE.SUP);
				ft5.setStringValue(infoAmm.getPERSONEAPERTURAOFFERTE());
			}
			
		}
		
		
		return procedure;
	}
	



	private CiF02 createComplementaryInfo() {
		
		CiF02 complementaryinfo = CiF02.Factory.newInstance();								//F02-137
		
		AltreInfoType  altreInfo = getDataNotice().getDeltaGaraTED().getALTREINFO();
		if(altreInfo.getAPPALTORINNOVABILE().value().equals(Costanti.FLAG_VALORE_SI)) {
			complementaryinfo.addNewRECURRENTPROCUREMENT();									//F02-138
			Ft ft1 = complementaryinfo.addNewESTIMATEDTIMING().addNewP().addNewFT();		//F02-140
			ft1.setTYPE(Ft.TYPE.SUP);
			ft1.setStringValue(altreInfo.getTEMPOSTIMATOPROSSIMIBANDI());
		} else
			complementaryinfo.addNewNORECURRENTPROCUREMENT();								//F02-139
		
		if(altreInfo.getORDINATIVOELETTRONICO().value().equals(Costanti.FLAG_VALORE_SI))
				complementaryinfo.addNewEORDERING();										//F02-141
		
		if(altreInfo.getFATTURAZIONEELETTRONICA().value().equals(Costanti.FLAG_VALORE_SI))
			complementaryinfo.addNewEINVOICING();											//F02-142

		if(altreInfo.getPAGAMENTIELETTRONICI().value().equals(Costanti.FLAG_VALORE_SI))
			complementaryinfo.addNewEPAYMENT();												//F02-143


		if(altreInfo.getINFOADD()!=null) {
			Ft ft2 = complementaryinfo.addNewINFOADD().addNewP().addNewFT();				//F02-144
			ft2.setTYPE(Ft.TYPE.SUP);
			ft2.setStringValue(altreInfo.getINFOADD());
		}
		
		complementaryinfo.setADDRESSREVIEWBODY(createContactReview(altreInfo.getORGANISMORICORSO()));	//F02-145
		
		if(altreInfo.getORGANISMOMEDIAZIONE()!=null)
		complementaryinfo.setADDRESSMEDIATIONBODY(createContactReview(altreInfo.getORGANISMOMEDIAZIONE()));//F02-146
		
		if(altreInfo.getREVIEWPROCEDURE()!=null && !"".equals(altreInfo.getREVIEWPROCEDURE())) {
			Ft ft3 = complementaryinfo.addNewREVIEWPROCEDURE().addNewP().addNewFT();					//F02-147
			ft3.setTYPE(Ft.TYPE.SUP);
			ft3.setStringValue(altreInfo.getREVIEWPROCEDURE());
		}
		
		if(altreInfo.getSERVIZIOINFORICORSO()!=null)
		complementaryinfo.setADDRESSREVIEWINFO(createContactReview(altreInfo.getSERVIZIOINFORICORSO()));//F02-148
		
		complementaryinfo.setDATEDISPATCHNOTICE(UtilityClass.currentCalendar());						//F02-149
		
		return complementaryinfo;
	}
	

}
