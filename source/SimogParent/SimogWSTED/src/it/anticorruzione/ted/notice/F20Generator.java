package it.anticorruzione.ted.notice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.xmlbeans.XmlAnySimpleType;

import eu.europa.publications.resource.authority.currency.TCurrencyTedschema;
import eu.europa.publications.resource.schema.ted.r209.reception.AcDefinition;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF20.AWARDEDCONTRACT;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF20;
import eu.europa.publications.resource.schema.ted.r209.reception.BodyF14;
import eu.europa.publications.resource.schema.ted.r209.reception.BodyF20;
import eu.europa.publications.resource.schema.ted.r209.reception.CiF03;
import eu.europa.publications.resource.schema.ted.r209.reception.CiF20;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactContractingBody;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactContractingBodyF14;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactContractor;
import eu.europa.publications.resource.schema.ted.r209.reception.CpvSet;
import eu.europa.publications.resource.schema.ted.r209.reception.DurationMD;
import eu.europa.publications.resource.schema.ted.r209.reception.DurationUnitMD;
import eu.europa.publications.resource.schema.ted.r209.reception.FormSection;
import eu.europa.publications.resource.schema.ted.r209.reception.Ft;
import eu.europa.publications.resource.schema.ted.r209.reception.ModificationsF20;
import eu.europa.publications.resource.schema.ted.r209.reception.ModificationsF20.DESCRIPTIONPROCUREMENT;
import eu.europa.publications.resource.schema.ted.r209.reception.ModificationsF20.INFOMODIFICATIONS;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectContractF14;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectContractF20;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectF03;
import eu.europa.publications.resource.schema.ted.r209.reception.TCeLanguageList;
import eu.europa.publications.resource.schema.ted.r209.reception.TCountryList;
import eu.europa.publications.resource.schema.ted.r209.reception.Val;
import eu.europa.publications.resource.schema.ted.r209.reception.ValRange;
import eu.europa.publications.resource.schema.ted.r209.reception.ACPRICEDocument.ACPRICE;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF03.AWARDEDCONTRACT.CONTRACTORS.CONTRACTOR2;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF20.AWARDEDCONTRACT.VALUES;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF20.AWARDEDCONTRACT.CONTRACTORS;
import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF20.AWARDEDCONTRACT.CONTRACTORS.CONTRACTOR1;
import eu.europa.publications.resource.schema.ted.r209.reception.F142014Document.F142014;
import eu.europa.publications.resource.schema.ted.r209.reception.F202014Document.F202014;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectF03.AC;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectF20;
import eu.europa.publications.resource.schema.ted.r209.reception.OriginalTranslation;
import eu.europa.publications.resource.schema.ted.r209.reception.ProcedureF20;
import eu.europa.publications.resource.schema.ted.x2021.nuts.TNutsCodeList;
import it.anticorruzione.ted.beans.AwardedContract;
import it.anticorruzione.ted.beans.Contractor;
import it.anticorruzione.ted.beans.LottoTED;
import it.anticorruzione.ted.enums.LegalBasisEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.util.UtilityClass;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.massload.xmlbeans.ReasonModificationType;
import it.avlp.simog.ws.beans.DataNotice;
import it.avlp.simog.ws.massload.xmlbeans.AddrS1Type;
import it.avlp.simog.ws.massload.xmlbeans.AddrS5Type;
import it.avlp.simog.ws.massload.xmlbeans.AltreInfoType;
import it.avlp.simog.ws.massload.xmlbeans.AppaltoTypeAgg;
import it.avlp.simog.ws.massload.xmlbeans.CPVSecondariaType;
import it.avlp.simog.ws.massload.xmlbeans.ContraenteType;
import it.avlp.simog.ws.massload.xmlbeans.ContraenteTypeMod;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;
import it.avlp.simog.ws.massload.xmlbeans.DescrizioneAppaltoType;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoAggiudicazione;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoModifica;
import it.avlp.simog.ws.massload.xmlbeans.LuogoNutsType;
import it.avlp.simog.ws.massload.xmlbeans.ModificaCpvSecType;
import it.avlp.simog.ws.massload.xmlbeans.ModificaType;

public class F20Generator extends AbstractNoticeGenerator {

	public F20Generator(DataNotice dataNotice, String version) {
		super(dataNotice,
				version, 
				LegalBasisEnum.DIR_201424EU, 
				TypeNoticeEnum.F20);
	}
	
	@Override
	public FormSection createFormSection() {
		DeltaGaraTED deltaGara = getDataNotice().getDeltaGaraTED();
		Gara gara = getDataNotice().getGara();
		
		
		FormSection formSec = FormSection.Factory.newInstance();
		F202014 f202014 = formSec.addNewF202014();
		f202014.setCATEGORY(OriginalTranslation.ORIGINAL);					//F20-3

		XmlAnySimpleType form = XmlAnySimpleType.Factory.newInstance();
		form.setStringValue(TypeNoticeEnum.F20.getTipo());
		f202014.setFORM(form);												//F20-4
		f202014.setLG(TCeLanguageList.IT);									//F20-5
		
		f202014.addNewLEGALBASIS().setVALUE(getLegalBasis());				//F20-6
        BodyF20 body = createContractingBody();								//F20-7
		
        f202014.setCONTRACTINGBODY(body);									//F20-8
        
      //Object Contract
        ObjectContractF20 objContract = createObjectContract(deltaGara,gara);
        f202014.setOBJECTCONTRACT(objContract);
        
        //Procedure
        ProcedureF20 procedure = f202014.addNewPROCEDURE();					//F20-32
        procedure.setNOTICENUMBEROJ(getDataNotice().getNoticeNumberOjs());	//F20-33
        f202014.setPROCEDURE(procedure);
        
        
        //Award Contract
        Lotto lotto = getDataNotice().getListaLotti().get(0).getLotto();
        AwardContractF20 awardContract =  f202014.addNewAWARDCONTRACT();	//F20-34

	
        Ft title = awardContract.addNewTITLE().addNewP().addNewFT();//F20-35
        title.setTYPE(Ft.TYPE.SUP);
        title.setStringValue(lotto.getOggetto());
        
        awardContract.setCONTRACTNO(lotto.getFullCIG());	//F20-36
        awardContract.setLOTNO(lotto.getFullCIG());			//F20-37
        
        LottoTED lottoTed = getDataNotice().getListaLotti().get(0);
        AwardedContract inputAwardedContract = getDataNotice().getAwardedContract();
        
        AWARDEDCONTRACT awardedContract = awardContract.addNewAWARDEDCONTRACT();//F20-38
        awardedContract.setDATECONCLUSIONCONTRACT(
        		UtilityClass.stringToCalendar(
        				inputAwardedContract.getDateConclusionContract(),"yyyy-MM-dd")
        		);//F20-39
        
      //CONTRACTORS
		CONTRACTORS contractors = awardedContract.addNewCONTRACTORS();						//F20-40
		if(inputAwardedContract.getListaContractor().size()>1)
			contractors.addNewAWARDEDTOGROUP();										//F20-42
		else
			contractors.addNewNOAWARDEDTOGROUP();									//F03-95
		
		 List<Contractor> listaContractor = inputAwardedContract.getListaContractor();
	        //Ciclo Award Contract
				for(int y=0;y<listaContractor.size();y++) {
					Contractor el = listaContractor.get(y);
						if(contractors.isSetNOAWARDEDTOGROUP()) {
						//F20-43
			    			eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF20.AWARDEDCONTRACT.CONTRACTORS.CONTRACTOR2 contr = contractors.addNewCONTRACTOR2();															//F03-97
		    			contr.setADDRESSCONTRACTOR(createContactContractor(el));	//F20-44
		    			if(!el.isSme())
		    				contr.addNewNOSME();			//F20-46																			
			    			else
		    				contr.addNewSME();		//F20-45																				
						} else {
						CONTRACTOR1 contr = contractors.addNewCONTRACTOR1();														
		    			contr.setADDRESSCONTRACTOR(createContactContractor(el));	 //F20-44
		    			if(!el.isSme())
		    				contr.addNewNOSME();		//F20-46																					
			    			else
		    				contr.addNewSME();	//F20-45	
					}
	        }
		
		//VALUES
		VALUES values = awardedContract.addNewVALUES();		//F20-47
		Val val = values.addNewVALTOTAL();				//F20-48
		if(inputAwardedContract.getValTotal()!=null) {
			val.setBigDecimalValue((inputAwardedContract.getValTotal()));
		} else {
		val.setBigDecimalValue(lottoTed.getAggiudicazione().getImportoAggiudicazione());
		}
		
		val.setCURRENCY(TCurrencyTedschema.EUR);
       
//        //Complementary Info
		f202014.setCOMPLEMENTARYINFO(createComplementaryInfo());
		
		//Modifications Contract
		f202014.setMODIFICATIONSCONTRACT(createModificationsContract());
		
		return formSec;
	}
	
	private BodyF20 createContractingBody() {
		BodyF20 body = BodyF20.Factory.newInstance();
		DeltaGaraTED deltaGaraTED = getDataNotice().getDeltaGaraTED();
		
		List<AddrS1Type > listaContatti = deltaGaraTED.getDATIAMMAGGIUDICATRICE();
		
		body.setADDRESSCONTRACTINGBODY(createContactContractingBody(listaContatti.get(0)));	//F20-8
		
		
		return body;
	}
	
	private ObjectContractF20 createObjectContract(DeltaGaraTED deltaGara, Gara gara) {
		ObjectContractF20 objContract = ObjectContractF20.Factory.newInstance();		//F20-9
		
		List<LottoTED> listaLotto = getDataNotice().getListaLotti();
		
		Ft ft = objContract.addNewTITLE().addNewP().addNewFT();						//F20-10
		ft.setTYPE(Ft.TYPE.SUP);
		ft.setStringValue(deltaGara.getENTITAAPPALTO().getTITOLOPROCEDURAGARA());
		
		//Non previsto
//		objContract.setREFERENCENUMBER("");											//F20-11
		
		//CPV
		CpvSet cpvmain = objContract.addNewCPVMAIN();								//F20-12
		cpvmain.addNewCPVCODE().setCODE(deltaGara.getENTITAAPPALTO().getCPVGARA().split("-")[0]);//F20-13
		
		//Non previsto
//		cpvmain.addNewCPVSUPPLEMENTARYCODE();										//F20-14
		
		//Tipo contratto gara
		objContract.addNewTYPECONTRACT().setCTYPE(getTypeContract(deltaGara.getENTITAAPPALTO().getTIPOCONTRATTOAPPALTO()));//F20-15
	
		DeltaLottoTED deltaLottoTed = listaLotto.get(0).getDeltaLottoTED();
		Lotto lotto = listaLotto.get(0).getLotto();
		objContract.setOBJECTDESCR(createObjectDescr(deltaGara,deltaLottoTed,lotto));
		
		return objContract;
	}
	
    private ObjectF20 createObjectDescr(DeltaGaraTED deltaGara, DeltaLottoTED deltaLottoTed,Lotto lotto) {
		
    	ObjectF20 objDescr = ObjectF20.Factory.newInstance();	//F20-16
    	DescrizioneAppaltoType descrizioneAppalto = deltaLottoTed.getDESCRIZIONEAPPALTO();
    	Ft title = objDescr.addNewTITLE().addNewP().addNewFT();	//F20-17
    	title.setTYPE(Ft.TYPE.SUP);
    	title.setStringValue(descrizioneAppalto.getTITOLOAPPALTO());
    	
    	objDescr.setLOTNO(lotto.getFullCIG());	//F20-18
    	
    	objDescr.addNewCPVADDITIONAL().addNewCPVCODE().setCODE(lotto.getId_CPV().split("-")[0]); 	//F20-19-20
		for(CpvLotto cpvLotto : lotto.getElencoCpvSecondarie()) {
			objDescr.addNewCPVADDITIONAL().addNewCPVCODE().setCODE(cpvLotto.getIdCpv().split("-")[0]);
		}
		
		
		objDescr.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(lotto.getLUOGO_NUTS()));	//F20-22
		Ft ft = objDescr.addNewMAINSITE().addNewP().addNewFT();								//F20-23
		ft.setTYPE(Ft.TYPE.SUB);
		ft.setStringValue(descrizioneAppalto.getLUOGOESECUZIONEPRINCIPALE());
		
		Ft ft2 = objDescr.addNewSHORTDESCR().addNewP().addNewFT();							//F20-24
		ft2.setTYPE(Ft.TYPE.SUB);
		ft2.setStringValue(lotto.getOggetto());
	
		DurationMD duration = DurationMD.Factory.newInstance();
		duration.setTYPE(DurationUnitMD.DAY);												//F20-26
		duration.setIntValue(lotto.getDurataAffidamentoGiorni()); 
		objDescr.setDURATION(duration);
		
		if(deltaGara.getDATIPROCEDURA().getNOTEAQQUATTROANNI()!=null) {
			Ft justification = objDescr.addNewJUSTIFICATION().addNewP().addNewFT();			//F20-29
			justification.setTYPE(Ft.TYPE.SUB);
			justification.setStringValue(deltaGara.getDATIPROCEDURA().getNOTEAQQUATTROANNI());
		}
		
		if(descrizioneAppalto.getFLAGAPPALTOPROGETTOUE().value().equals(Costanti.FLAG_VALORE_SI))
			objDescr.addNewNOEUPROGRRELATED();											//F20-30
		else {
			Ft ft6 = objDescr.addNewEUPROGRRELATED().addNewP().addNewFT();				//F20-31
			ft6.setTYPE(Ft.TYPE.SUB);
			ft6.setStringValue(descrizioneAppalto.getAPPALTOPROGETTOUE());
		}


		return objDescr;
	}
    
  	
	private CiF20 createComplementaryInfo() {
		
		CiF20 complementaryinfo = CiF20.Factory.newInstance();							//F03-110
		
		AltreInfoType  altreInfo = getDataNotice().getDeltaGaraTED().getALTREINFO();

		if(altreInfo.getINFOADD()!=null) {
			Ft ft2 = complementaryinfo.addNewINFOADD().addNewP().addNewFT();			//F03-111
			ft2.setTYPE(Ft.TYPE.SUP);
			ft2.setStringValue(altreInfo.getINFOADD());
		}
		
		complementaryinfo.setADDRESSREVIEWBODY(createContactReview(altreInfo.getORGANISMORICORSO()));		//F20-51
		
		if(altreInfo.getORGANISMOMEDIAZIONE()!=null)
		complementaryinfo.setADDRESSMEDIATIONBODY(createContactReview(altreInfo.getORGANISMOMEDIAZIONE()));	//F20-52
		
		if(altreInfo.getREVIEWPROCEDURE()!=null) {
			Ft ft3 = complementaryinfo.addNewREVIEWPROCEDURE().addNewP().addNewFT();	//F03-114
			ft3.setTYPE(Ft.TYPE.SUP);
			ft3.setStringValue(altreInfo.getREVIEWPROCEDURE());
		}
		
		if(altreInfo.getSERVIZIOINFORICORSO()!=null)
		complementaryinfo.setADDRESSREVIEWINFO(createContactReview(altreInfo.getSERVIZIOINFORICORSO()));	//F20-54
		complementaryinfo.setDATEDISPATCHNOTICE(UtilityClass.currentCalendar());							//F20-55
		
		return complementaryinfo;
	}
	
	private ModificationsF20 createModificationsContract() {
		ModificaType formMod = getDataNotice().getFormularioModifica().getMODIFICA();
		
		ModificationsF20 modifications = ModificationsF20.Factory.newInstance(); 					//F20-56
		DESCRIPTIONPROCUREMENT descProc = modifications.addNewDESCRIPTIONPROCUREMENT();				//F20-57
		descProc.addNewCPVMAIN().addNewCPVCODE().setCODE(formMod.getCPVPRINCIPALE().split("-")[0]);	//F20-58
		
		
		List<ModificaCpvSecType> cpvSec = formMod.getCPVSECONDARIE();
		for(ModificaCpvSecType cpv : cpvSec) {
			descProc.addNewCPVADDITIONAL().addNewCPVCODE().setCODE(cpv.getADDITIONALCPVCODE().split("-")[0]);		//F20-59
		}
		
		List<LuogoNutsType> listaNuts = formMod.getNUTS();
		for(LuogoNutsType nuts : listaNuts) {
			descProc.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(nuts.getValue()));		//F20-60
		}
		
		if(formMod.getLUOGOESECPRINCIPALE()!=null && !"".equals(formMod.getLUOGOESECPRINCIPALE())) {
			Ft mainSite = descProc.addNewMAINSITE().addNewP().addNewFT();					//F20-61
			mainSite.setStringValue(formMod.getLUOGOESECPRINCIPALE());
			mainSite.setTYPE(Ft.TYPE.SUP);
		}
		
		Ft shortDescr = descProc.addNewSHORTDESCR().addNewP().addNewFT();
		shortDescr.setStringValue(formMod.getDESCPROCUREMENT());					//F20-62
		shortDescr.setTYPE(Ft.TYPE.SUP);
		
		if(formMod.getDURATACONTRATTOMESI()!=null && formMod.getDURATACONTRATTOMESI()>0) {
			DurationMD duration = DurationMD.Factory.newInstance();
			duration.setTYPE(DurationUnitMD.MONTH);								//F20-63
			duration.setIntValue(formMod.getDURATACONTRATTOMESI());
			descProc.setDURATION(duration);
		} else if(formMod.getDURATACONTRATTOGIORNI()!=null && formMod.getDURATACONTRATTOGIORNI()>0) {
			DurationMD duration = DurationMD.Factory.newInstance();
			duration.setTYPE(DurationUnitMD.DAY);								//F20-64
			duration.setIntValue(formMod.getDURATACONTRATTOGIORNI());
			descProc.setDURATION(duration);
		} else { 
			if(formMod.getINIZIOCONTRATTOLOTTO()!=null) {
				Date dataInizio = formMod.getINIZIOCONTRATTOLOTTO().getValue();
				descProc.setDATESTART(UtilityClass.dateToCalendar(dataInizio)); 	//F20-65
			}
			if(formMod.getFINECONTRATTOLOTTO()!=null) {
				Date dataFine = formMod.getFINECONTRATTOLOTTO().getValue();
				descProc.setDATEEND(UtilityClass.dateToCalendar(dataFine)); 		//F20-66
			}
		}
		
		if(formMod.getJUSTIFICATION()!=null && !"".equals(formMod.getJUSTIFICATION())) {
			Ft title = descProc.addNewJUSTIFICATION().addNewP().addNewFT();			//F20-67
			title.setStringValue(formMod.getJUSTIFICATION());
			title.setTYPE(Ft.TYPE.SUP);
		}
		
		Val valTotal = descProc.addNewVALUES().addNewVALTOTAL();					//F20-68
		valTotal.setBigDecimalValue(formMod.getVALTOTAL().getValue());
		valTotal.setCURRENCY(TCurrencyTedschema.EUR);
		
		//F20-69
		eu.europa.publications.resource.schema.ted.r209.reception.ModificationsF20.DESCRIPTIONPROCUREMENT.CONTRACTORS modContractors = descProc.addNewCONTRACTORS();
		if(formMod.getCONTRAENTE().size()>1) {
			modContractors.addNewAWARDEDTOGROUP();		//F20-71
			for(ContraenteTypeMod modContr : formMod.getCONTRAENTE()) {
				//F20-72
				eu.europa.publications.resource.schema.ted.r209.reception.ModificationsF20.DESCRIPTIONPROCUREMENT.CONTRACTORS.CONTRACTOR1 contractor = modContractors.addNewCONTRACTOR1();
				contractor.setADDRESSCONTRACTOR(createAddressContractorMod(modContr.getADDRESSCONTRACTORMOD())); //F20-73
				if(Costanti.FLAG_VALORE_SI.equals(modContr.getAWARDEDISSME().value()))
					contractor.addNewSME();	//F20-74
				else
					contractor.addNewNOSME(); //F20-75
			}
		} else {
			modContractors.addNewNOAWARDEDTOGROUP(); //F20-70
			//F20-72
			eu.europa.publications.resource.schema.ted.r209.reception.ModificationsF20.DESCRIPTIONPROCUREMENT.CONTRACTORS.CONTRACTOR2 contractor = modContractors.addNewCONTRACTOR2();
			contractor.setADDRESSCONTRACTOR(createAddressContractorMod(formMod.getCONTRAENTE().get(0).getADDRESSCONTRACTORMOD())); //F20-73
			if(Costanti.FLAG_VALORE_SI.equals(formMod.getCONTRAENTE().get(0).getAWARDEDISSME().value()))
				contractor.addNewSME(); //F20-74
			else
				contractor.addNewNOSME();//F20-75
		}
		
		INFOMODIFICATIONS infoMod = modifications.addNewINFOMODIFICATIONS(); 	//F20-76
		
		Ft natureChanges = infoMod.addNewSHORTDESCR().addNewP().addNewFT();		//F20-77
		natureChanges.setTYPE(Ft.TYPE.SUP);
		natureChanges.setStringValue(formMod.getDESCNATURECHANGES());
		
		Ft descReason =null;
		if(formMod.getREASONMODIFICATION().getValue().equals("1"))
			descReason = infoMod.addNewADDITIONALNEED().addNewP().addNewFT();		//F20-78
		else
			descReason = infoMod.addNewUNFORESEENCIRCUMSTANCE().addNewP().addNewFT(); //F20-79
		
		descReason.setTYPE(Ft.TYPE.SUP);
		descReason.setStringValue(formMod.getDESCREASONMODIFICATION());
		
		//F20-80
		eu.europa.publications.resource.schema.ted.r209.reception.ModificationsF20.INFOMODIFICATIONS.VALUES values = infoMod.addNewVALUES();
		
		//F20-81
		Val valBefore = values.addNewVALTOTALBEFORE();
		valBefore.setBigDecimalValue(formMod.getVALTOTALBEFORE().getValue());
		valBefore.setCURRENCY(TCurrencyTedschema.EUR);
		
		//F20-82
		Val valAfter = values.addNewVALTOTALAFTER();
		valAfter.setBigDecimalValue(formMod.getVALTOTALAFTER().getValue());
		valAfter.setCURRENCY(TCurrencyTedschema.EUR);
		
		return modifications;
	}

}
