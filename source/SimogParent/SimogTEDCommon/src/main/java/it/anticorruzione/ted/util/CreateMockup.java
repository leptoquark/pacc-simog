package it.anticorruzione.ted.util;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import it.anticorruzione.ted.util.UtilityClass;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.massload.xmlbeans.AddrS1Type;
import it.avlp.simog.massload.xmlbeans.AddrS5Type;
import it.avlp.simog.massload.xmlbeans.AddrS5TypeMod;
import it.avlp.simog.massload.xmlbeans.AddrS6Type;
import it.avlp.simog.massload.xmlbeans.AltreInfoType;
import it.avlp.simog.massload.xmlbeans.AltroIndirizzoType;
import it.avlp.simog.massload.xmlbeans.AmmAggType;
import it.avlp.simog.massload.xmlbeans.AppaltoTypeAgg;
import it.avlp.simog.massload.xmlbeans.CondizioniPartecipazioneType;
import it.avlp.simog.massload.xmlbeans.ContraenteType;
import it.avlp.simog.massload.xmlbeans.ContraenteTypeMod;
import it.avlp.simog.massload.xmlbeans.CountryType;
import it.avlp.simog.massload.xmlbeans.CriteriaType;
import it.avlp.simog.massload.xmlbeans.CriterioAggLottoType;
import it.avlp.simog.massload.xmlbeans.DatiProceduraType;
import it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument;
import it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument;
import it.avlp.simog.massload.xmlbeans.DescrizioneAppaltoType;
import it.avlp.simog.massload.xmlbeans.DocDisponibiliType;
import it.avlp.simog.massload.xmlbeans.EntitaAppaltoType;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazioneWSDocument;
import it.avlp.simog.massload.xmlbeans.FormularioAvvisoModificaWSDocument;
import it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettificaWSDocument;
import it.avlp.simog.massload.xmlbeans.InfoAmministrativeType;
import it.avlp.simog.massload.xmlbeans.InfoAmministrativeTypeAgg;
import it.avlp.simog.massload.xmlbeans.MaxLottiPartecipazioneType;
import it.avlp.simog.massload.xmlbeans.ModificaType;
import it.avlp.simog.massload.xmlbeans.MotivoRettificaType;
import it.avlp.simog.massload.xmlbeans.OperatoriAQType;
import it.avlp.simog.massload.xmlbeans.PriceCriteriaType;
import it.avlp.simog.massload.xmlbeans.ProcurementUnsuccessfulType;
import it.avlp.simog.massload.xmlbeans.QualityCostCriteriaType;
import it.avlp.simog.massload.xmlbeans.ReasonModificationType;
import it.avlp.simog.massload.xmlbeans.RettificaCpvSecType;
import it.avlp.simog.massload.xmlbeans.RettificaType;
import it.avlp.simog.massload.xmlbeans.SettorePrincipaleType;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraWS;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoWS;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoAggiudicazione;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoAggiudicazioneWS;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoModifica;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoModificaWS;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoRettifica;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoRettificaWS;
import it.avlp.simog.ws.massload.xmlbeans.ValoreAppaltoType;


/**
 * Creazione dati mockup per finalità di test
 *
 */
public class CreateMockup {

	
	public DeltaGaraWS createDeltaGaraTED() {
		DeltaGaraTED doc = new DeltaGaraTED();
		String packageName = doc.getClass().getPackage().getName();
		System.out.println(packageName);
		JAXBContext jc = null;
		Unmarshaller u = null;
		DeltaGaraWS deltaGaraTED = null;
		try {
			 jc = JAXBContext.newInstance(packageName);
			 u = jc.createUnmarshaller();
	
		
		DeltaGaraWSDocument root = it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument.Factory.newInstance();		
		it.avlp.simog.massload.xmlbeans.DeltaGaraTED test = root.addNewDeltaGaraWS().addNewDeltaGara();
		
		//ENTITA APPALTO (J)
		EntitaAppaltoType eat = test.addNewENTITAAPPALTO();
		eat.setTIPOCONTRATTOAPPALTO(EntitaAppaltoType.TIPOCONTRATTOAPPALTO.X_1);
		eat.setTITOLOPROCEDURAGARA("TITOLO");
		eat.setCPVGARA("45113000");
		eat.setMAXLOTTIPARTECIPAZIONE(MaxLottiPartecipazioneType.X_3);
		if(eat.getMAXLOTTIPARTECIPAZIONE().intValue()==2)
			eat.setNUMMAXLOTTIPARTECIPAZIONE(1);
		
		eat.setNUMMAXLOTTIOFFERENTE(1);
		eat.setFLAGSAAGGGRUPPILOTTI(FlagSNType.S);
		if(eat.getFLAGSAAGGGRUPPILOTTI().equals(FlagSNType.S))
			eat.setSAAGGGRUPPILOTTI("SA GRUPPI");
		
		AddrS1Type datiamm = test.addNewDATIAMMAGGIUDICATRICE();
		createAddrS1Type(datiamm,"test1");
		AddrS1Type datiamm2 = test.addNewDATIAMMAGGIUDICATRICE();
		createAddrS1Type(datiamm2,"test2");
		test.setNORMATIVEAPPCONGIUNTO("testnorm");
		test.setAPPALTOCC(FlagSNType.S);
		test.setDOCUMENTIDISPONIBILI(DocDisponibiliType.X_2);
		test.setURLDOCDISPONIBILI("http://www.tes.com");
		test.setINFOAGGIUNTIVE(AltroIndirizzoType.X_2);
		

		AddrS1Type altroIndirizzoIA = test.addNewALTROINDIRIZZOIA();
		createAddrS1Type(altroIndirizzoIA,"test3");
		
		
		test.setURLVERSIONEELETTRONICA("http://www.versioneelecro.com");
		

			AddrS1Type altroIndirizzoPart = test.addNewALTROINDIRIZZOPARTECIPAZIONE();
			createAddrS1Type(altroIndirizzoPart,"test4");
		
		test.setURLSTRUMENTI("http://www.stru.com");
		
		test.setTIPOAMMAGG(AmmAggType.X_1);
		if(test.getTIPOAMMAGG().intValue()==7) {
			test.setALTROTIPOAMMAGG("Altro tipo amm");
		}
		
		test.setSETTOREPRINCIPALE(SettorePrincipaleType.X_1);
		if(test.getSETTOREPRINCIPALE().intValue()==11)
			test.setALTROSETTOREPRINCIPALE("Altro settore p");
		
		DatiProceduraType dpt = test.addNewDATIPROCEDURA();
		dpt.setFLAGPROCEDURAACCELLERATA(FlagSNType.N);
		if(dpt.getFLAGPROCEDURAACCELLERATA().equals(FlagSNType.S))
		   dpt.setMOTIVAZIONEPROCEDURAACCELLERATA("procedura ac");
		
		//test nel caso in cui e' accordo quadro
		if(false) {
			dpt.setTIPOOPERATORIAQ(OperatoriAQType.X_1);
			if(dpt.getTIPOOPERATORIAQ().equals(OperatoriAQType.X_2)) {
				dpt.setNUMMAXPARTECIPANTIAQ(1);
				
			}
			
			dpt.setNOTEAQQUATTROANNI("Note AQ");
		}
		
		//Test se e' appalto congiunto ed e' sistema dinamico di acquisizione
		if(true) {
			dpt.setALTRIACQUIRENTISISDINAMICO(FlagSNType.S);
			
		}
		
		dpt.setAGGIUDICAZIONESENZANEGOZIAZIONE(FlagSNType.S);
		
		//Se strumenti svolgimento=2
		if(false)
		    dpt.setNOTEASTAELETTRONICA("Note asta elett");
		
		dpt.setFLAGAPP(FlagSNType.N);
		
		InfoAmministrativeType iat = test.addNewINFOAMMINISTRATIVE();
		
//		iat.setMESIVALIDITAOFFERTE(4);
		iat.setPERIODOVALIDITAOFFERTE(Calendar.getInstance());
		
		//Condizioni
		CondizioniPartecipazioneType condizioni = test.addNewCONDIZIONIPARTECIPAZIONE();
		condizioni.setELENCOCONDIZIONI("Elenco condizioni");
		condizioni.setCRITERIECONOMICI(FlagSNType.S);
		if(condizioni.getCRITERIECONOMICI().equals(FlagSNType.N)) {
			condizioni.setELENCOCRITERIECONOMICI("Elenco Criteri economici");
			condizioni.setLIVELLICRITERIECONOMICI("Livelli criteri economici");
		
		}
		condizioni.setCRITERITECNICI(FlagSNType.S);
		if(condizioni.getCRITERITECNICI().equals(FlagSNType.N)) {
			condizioni.setELENCOCRITERITECNICI("Elenco Criteri tech");
			condizioni.setLIVELLICRITERITECNICI("Livelli criteri tech");
		
		}
		condizioni.setINTEGRAZIONEDISABILI(FlagSNType.S);
		condizioni.setLAVORIPROTETTI(FlagSNType.S);
		condizioni.setFLAGPROFESSIONESERVIZI(FlagSNType.S);
		if(condizioni.getFLAGPROFESSIONESERVIZI().equals(FlagSNType.S))
			condizioni.setPROFESSIONESERVIZI("Prof servizi");
		
		condizioni.setCONDIZIONIESECUZIONECONTRATTO("Condizioni esec contratto");
		//Fine Condizioni
		
		AltreInfoType ait = test.addNewALTREINFO();
		ait.setAPPALTORINNOVABILE(FlagSNType.N);
		
		if(ait.getAPPALTORINNOVABILE().equals(FlagSNType.S))
			ait.setTEMPOSTIMATOPROSSIMIBANDI("TEMPO");
		
		ait.setORDINATIVOELETTRONICO(FlagSNType.S);
		ait.setFATTURAZIONEELETTRONICA(FlagSNType.S);
		ait.setPAGAMENTIELETTRONICI(FlagSNType.S);
		ait.setINFOADD("info compl");
		
		String dataap = "20200401";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date data = new Date();
		try {
			data = sdf.parse(dataap);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MONTH, 2);
		iat.setDATAAPERTURAOFFERTE(cal);
		iat.setORAAPERTURAOFFERTE("09:00");
		iat.setLUOGOAPERTURAOFFERTE("TORINO");
		iat.setPERSONEAPERTURAOFFERTE("PERSONA");
		
		AddrS6Type addr6 =  ait.addNewORGANISMORICORSO();
		createAddrS6Type(addr6, "test4");
		AddrS6Type addr62 =  ait.addNewORGANISMOMEDIAZIONE();
		createAddrS6Type(addr62, "test5");
		ait.setREVIEWPROCEDURE("scadenza");
		AddrS6Type addrs63 = ait.addNewSERVIZIOINFORICORSO();
		createAddrS6Type(addrs63, "test7");
		
		
		
		String xmlTe = root.xmlText();
		//ByteArrayInputStream is = new ByteArrayInputStream(xmlTe.getBytes());
		
		//Source source = new StreamSource(is);
		//JAXBElement<DeltaGaraWS> root = null;

		ByteArrayInputStream is = new ByteArrayInputStream(xmlTe.getBytes());
	
			//root = u.unmarshal(source, DeltaGaraWS.class);
			
			     jc = JAXBContext.newInstance(packageName);
				 u = jc.createUnmarshaller();
				

				 deltaGaraTED  = (DeltaGaraWS) u.unmarshal(is);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		DeltaGaraWS deltaGaraTED = root.getValue();
		
	
		
		return deltaGaraTED;
	}
	
	public DeltaLottoWS createDeltaLotto() {
		DeltaLottoTED doc = new DeltaLottoTED();
		String packageName = doc.getClass().getPackage().getName();

		JAXBContext jc = null;
		Unmarshaller u = null;
		try {
			 jc = JAXBContext.newInstance(packageName);
			 u = jc.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DeltaLottoWSDocument root = DeltaLottoWSDocument.Factory.newInstance();
		
		it.avlp.simog.massload.xmlbeans.DeltaLottoTED test = root.addNewDeltaLottoWS().addNewDeltaLotto();

		
		DescrizioneAppaltoType dat = test.addNewDESCRIZIONEAPPALTO();
		dat.setTITOLOAPPALTO("Titolo appalto");
		dat.setLUOGOESECUZIONEPRINCIPALE("ROMA");
		dat.setCRITERIOAGGLOTTO(CriterioAggLottoType.X_1);
		
		//Criteri
		if(dat.getCRITERIOAGGLOTTO().equals(CriterioAggLottoType.X_1)) {
			//Criterio qualità
//			QualityCostCriteriaType qualita = dat.addNewCRITERIOQUALITA();
//			qualita.setQPCCRITERIANAME("CritQ1");
//			qualita.setQPCCRITERIAWEIGHTING("Crit1Pes");
			
//			QualityCostCriteriaType qualita2 = dat.addNewCRITERIOQUALITA();
//			qualita2.setQPCCRITERIANAME("CritQ2");
//			qualita2.setQPCCRITERIAWEIGHTING("Crit2Pes");
			//Fine criterio qualità
			
			//Criterio costo
			dat.setTIPOCRITERIO(CriteriaType.X_1);
			if(dat.getTIPOCRITERIO().equals(CriteriaType.X_1)) {
				QualityCostCriteriaType costo = dat.addNewCRITERIOCOSTO();
				costo.setQPCCRITERIANAME("CritCosto1");
				costo.setQPCCRITERIAWEIGHTING("PesCritCosto1");
				QualityCostCriteriaType costo2 = dat.addNewCRITERIOCOSTO();
				costo2.setQPCCRITERIANAME("CritCosto4");
				costo2.setQPCCRITERIAWEIGHTING("PesCritCosto4");
			}
			if(dat.getTIPOCRITERIO().equals(CriteriaType.X_2)) {
				PriceCriteriaType prezzo = dat.addNewCRITERIOPREZZO();
				prezzo.setPCCRITERIAWEIGHTING("Prezzo1");

			}
		}
		

//		dat.setDESCRINNOVICONTR("descrizione rinnovi");
		
		dat.setACCETTATEVARIANTI(FlagSNType.N);
		
		//Se flag prevede rip
		if(false)
			dat.setDESCRIZIONEOPZIONI("Desc Opzioni");
		
		dat.setPRESOFFERTECATALOGOELETTRONICO(FlagSNType.S);
		dat.setFLAGAPPALTOPROGETTOUE(FlagSNType.N);
		dat.setAPPALTOPROGETTOUE("4324235");
		dat.setULTERIORIINFOLOTTO("ulteriori");
//		dat.setNUMCANDIDATIPREVISTI(3);
		dat.setMINNUMCANDIDATIPREVISTI(2);
		dat.setMAXNUMCANDIDATIPREVISTI(4);
		dat.setCRITERIMAXNUMCANDIDATI("Criteri max num cand");
		
		test.setNOLOT(1);

		String xmlTe = root.xmlText();

		ByteArrayInputStream is = new ByteArrayInputStream(xmlTe.getBytes());
		DeltaLottoWS deltaLottoWS = null;
		try {
			deltaLottoWS = (DeltaLottoWS) u.unmarshal(is);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deltaLottoWS;
	}
	
	
	public FormularioAvvisoAggiudicazioneWS creaFormularioAvvisoAggiudicazione() {
		FormularioAvvisoAggiudicazione doc = new FormularioAvvisoAggiudicazione();
		
		String packageName = doc.getClass().getPackage().getName();

		JAXBContext jc = null;
		Unmarshaller u = null;
		try {
			 jc = JAXBContext.newInstance(packageName);
			 u = jc.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FormularioAvvisoAggiudicazioneWSDocument root = FormularioAvvisoAggiudicazioneWSDocument.Factory.newInstance();
		it.avlp.simog.massload.xmlbeans.FormularioAvvisoAggiudicazione test = root.addNewFormularioAvvisoAggiudicazioneWS().addNewFormularioAvvisoAggiudicazione();
	
		//Valore Appalto
		it.avlp.simog.massload.xmlbeans.ValoreAppaltoType valappalto = test.addNewVALOREAPPALTO();
		valappalto.setVALTOTAL(new BigDecimal(10000));
		if(!valappalto.isSetVALTOTAL()) {
			valappalto.setVALRANGETOTALLOW(new BigDecimal(8000));
			valappalto.setVALRANGETOTALHIGH(new BigDecimal(12000));
		}
		
		//InfoAmministrative
		InfoAmministrativeTypeAgg infoAmm = test.addNewINFOAMMINISTRATIVEAGG();
		//Da valorizzare se è procedura ristretta e sistema dinamico di acquisizione
		if(true) {
			infoAmm.setINFOSDA(FlagSNType.S);
		}
		infoAmm.setINFOAVVPRE(FlagSNType.S);
		
		//AppaltoTypeAgg
		AppaltoTypeAgg appalto1 = test.addNewAPPALTOAVVAGG();
		appalto1.setCIGAGG("4583434342");
		appalto1.setAWARDEDCONTRACT(FlagSNType.S);
		appalto1.setNBTENDERSRECEIVEDSME(10);
		appalto1.setNBTENDERSRECEIVEDOTHEREU(2);
		appalto1.setNBTENDERSRECEIVEDNONEU(1);
		appalto1.setNBTENDERSRECEIVEDEMEANS(15);
		ContraenteType con1 = appalto1.addNewAWARDEDNOTICE();
		AddrS5Type datiCon1 = con1.addNewADDRESSCONTRACTOR();
		createContactContractor("231231",datiCon1);
		con1.setAWARDEDISSME(FlagSNType.S);
		
//		ContraenteType con2 = appalto1.addNewAWARDEDNOTICE();
//		AddrS5Type datiCon2 = con2.addNewADDRESSCONTRACTOR();
//		createContactContractor("231232",datiCon2);
//		con2.setAWARDEDISSME(FlagSNType.N);
		
		appalto1.setLIKELYSUBCONTRACTED(FlagSNType.N);
		appalto1.setDATECONCLUSIONCONTRACT(UtilityClass.currentCalendar());
		
		AppaltoTypeAgg appalto2 = test.addNewAPPALTOAVVAGG();
		appalto2.setCIGAGG("4583433342");
		appalto2.setAWARDEDCONTRACT(FlagSNType.N);
		appalto2.setPROCUREMENTUNSUCCESSFUL(ProcurementUnsuccessfulType.X_2);
		appalto2.setNBTENDERSRECEIVEDSME(8);
		appalto2.setNBTENDERSRECEIVEDOTHEREU(1);
		appalto2.setNBTENDERSRECEIVEDNONEU(1);
		appalto2.setNBTENDERSRECEIVEDEMEANS(45);
		
//		AppaltoTypeAgg appalto3 = test.addNewAPPALTOAVVAGG();
//		appalto3.setCIGAGG("4583432342");
//		appalto3.setAWARDEDCONTRACT(FlagSNType.S);
//		ContraenteType con3 = appalto3.addNewAWARDEDNOTICE();
//		AddrS5Type datiCon3 = con3.addNewADDRESSCONTRACTOR();
//		createContactContractor("231233",datiCon3);
//		con3.setAWARDEDISSME(FlagSNType.N);
//		appalto3.setVALRANGETOTALLOW(new BigDecimal(2323));
//		appalto3.setVALRANGETOTALHIGH(new BigDecimal(12323));
//		appalto3.setLIKELYSUBCONTRACTED(FlagSNType.S);
//		appalto3.setVALSUBCONTRACTING(new BigDecimal(200));
//		appalto3.setPCTSUBCONTRACTING(20);
//		appalto3.setINFOADDSUBCONTRACTING("Info subappalto");
//		Calendar fineCon2 = Calendar.getInstance();
//		fineCon2.add(Calendar.YEAR, 2);
//		appalto3.setDATECONCLUSIONCONTRACT(UtilityClass.currentCalendar());
		//FINE AppaltoTypeAgg
		
		String xmlTe = root.xmlText();
		ByteArrayInputStream is = new ByteArrayInputStream(xmlTe.getBytes());
		FormularioAvvisoAggiudicazioneWS formAvvAgg = null;
		try {
			formAvvAgg = (FormularioAvvisoAggiudicazioneWS) u.unmarshal(is);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return formAvvAgg;
		
	}
	

	
	public FormularioAvvisoRettificaWS creaFormularioRettifica() {
		FormularioAvvisoRettifica doc = new FormularioAvvisoRettifica();
		
		String packageName = doc.getClass().getPackage().getName();

		JAXBContext jc = null;
		Unmarshaller u = null;
		try {
			 jc = JAXBContext.newInstance(packageName);
			 u = jc.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		FormularioAvvisoRettificaWSDocument root = FormularioAvvisoRettificaWSDocument.Factory.newInstance();
		it.avlp.simog.massload.xmlbeans.FormularioAvvisoRettifica test = root.addNewFormularioAvvisoRettificaWS().addNewFormularioAvvisoRettifica();
		
		//Motivo Rettifica
		test.setMOTIVORETTIFICA(MotivoRettificaType.X_2);
		
		//Rettifica
		RettificaType rettificaTxt = test.addNewRETTIFICA();
		rettificaTxt.setSECTIONNUMBER("I.5");
		rettificaTxt.setCIGRETTIFICA("4583434342");
		rettificaTxt.setSECTIONTOMODIFY("Oggetto gara");
		rettificaTxt.setOLDVALUETEXT("Old text");
		rettificaTxt.setNEWVALUETEXT("New text");
		
		RettificaType rettificaData = test.addNewRETTIFICA();
		rettificaData.setSECTIONNUMBER("II.3");
		rettificaData.setCIGRETTIFICA("4583434342");
		rettificaData.setSECTIONTOMODIFY("Data conclusione");
		rettificaData.setOLDVALUEDATE(Calendar.getInstance());
		rettificaData.setNEWVALUEDATE(Calendar.getInstance());
		rettificaData.setOLDVALUETIME("09:00");
		rettificaData.setNEWVALUETIME("10:00");
		
		RettificaType rettificaCpv = test.addNewRETTIFICA();
		rettificaCpv.setSECTIONNUMBER("II.3");
		rettificaCpv.setCIGRETTIFICA("4583434342");
		rettificaCpv.setSECTIONTOMODIFY("CPV Sec");
		rettificaCpv.setOLDMAINCPV("03000000");
		rettificaCpv.setNEWMAINCPV("03115130");
		
		RettificaCpvSecType[] cpvSecondarie = new RettificaCpvSecType[2];
		
		RettificaCpvSecType cpvSecondaria1  = RettificaCpvSecType.Factory.newInstance();
		cpvSecondaria1.setOLDMAINCPVSEC("03100000");
		cpvSecondaria1.setNEWMAINCPVSEC("03110000");
		cpvSecondarie[0] = cpvSecondaria1;
		RettificaCpvSecType cpvSecondaria2 =  RettificaCpvSecType.Factory.newInstance();
		cpvSecondaria2.setOLDMAINCPVSEC("03111000");
		cpvSecondaria2.setNEWMAINCPVSEC("03111100");
		cpvSecondarie[1] = cpvSecondaria2;
		
	    rettificaCpv.setRETTIFICACPVSECArray(cpvSecondarie);
		
		
		//Info Add Modifica
		test.setINFOADDMODIFICA("Info modifica");
		
		String xmlTe = root.xmlText();
		ByteArrayInputStream is = new ByteArrayInputStream(xmlTe.getBytes());
		FormularioAvvisoRettificaWS formAvvRett = null;
		try {
			formAvvRett = (FormularioAvvisoRettificaWS) u.unmarshal(is);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return formAvvRett;
	}
	
	public FormularioAvvisoModificaWS creaFormularioModifica() {
		FormularioAvvisoModifica doc = new FormularioAvvisoModifica();
		String packageName = doc.getClass().getPackage().getName();

		JAXBContext jc = null;
		Unmarshaller u = null;
		try {
			 jc = JAXBContext.newInstance(packageName);
			 u = jc.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		FormularioAvvisoModificaWSDocument root = FormularioAvvisoModificaWSDocument.Factory.newInstance();
		it.avlp.simog.massload.xmlbeans.FormularioAvvisoModifica test = root.addNewFormularioAvvisoModificaWS().addNewFormularioAvvisoModifica();
		
		ModificaType modifica = test.addNewMODIFICA();
		modifica.setCPVPRINCIPALE("71222000-1");
		modifica.addNewCPVSECONDARIE().setADDITIONALCPVCODE("03115130-1");
		modifica.addNewCPVSECONDARIE().setADDITIONALCPVCODE("03111100-1");
		modifica.addNewNUTS().setStringValue("ITC42");
		modifica.addNewNUTS().setStringValue("ITC43");
		modifica.setLUOGOESECPRINCIPALE("COMO");
		modifica.setDESCPROCUREMENT("Desc procuremente");
//		modifica.setDURATACONTRATTOMESI(12);
//		if(!modifica.isSetDURATACONTRATTOMESI())
//			modifica.setDURATACONTRATTOGIORNI(90);
		if(!modifica.isSetDURATACONTRATTOGIORNI()) {
			modifica.setINIZIOCONTRATTOLOTTO(Calendar.getInstance());
			Calendar datafin = Calendar.getInstance();
			datafin.add(Calendar.MONTH, 6);
			modifica.setFINECONTRATTOLOTTO(datafin);
		}
		
		modifica.setJUSTIFICATION("Justification");
		
		modifica.setVALTOTAL(new BigDecimal(12000));
		
		ContraenteTypeMod contr1 = modifica.addNewCONTRAENTE();
		AddrS5TypeMod addrs5typemode1 = contr1.addNewADDRESSCONTRACTORMOD();
		createAddrS5TypeMod(addrs5typemode1,"contraente 1mod");
		contr1.setAWARDEDISSME(FlagSNType.N);
		
		ContraenteTypeMod contr2 = modifica.addNewCONTRAENTE();
		AddrS5TypeMod addrs5typemode2 = contr2.addNewADDRESSCONTRACTORMOD();
		createAddrS5TypeMod(addrs5typemode2,"contraente 2mod");
		contr2.setAWARDEDISSME(FlagSNType.S);
		
		modifica.setDESCNATURECHANGES("Desc Nature");
		modifica.setREASONMODIFICATION(ReasonModificationType.X_2);
		modifica.setDESCREASONMODIFICATION("Desc Reason mod");
		modifica.setVALTOTALBEFORE(new BigDecimal(1200));
		modifica.setVALTOTALAFTER(new BigDecimal(5000));
		
		String xmlTe = root.xmlText();
		ByteArrayInputStream is = new ByteArrayInputStream(xmlTe.getBytes());
		FormularioAvvisoModificaWS formAvvMod = null;
		try {
			formAvvMod = (FormularioAvvisoModificaWS) u.unmarshal(is);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return formAvvMod;
		
	}
	
	private void createAddrS5TypeMod(AddrS5TypeMod datiamm, String name) {
		datiamm.setOFFICIALNAME(name);
		datiamm.setNATIONALID("2434324");
		datiamm.setADDRESS("TEST TESTTEST");
		datiamm.setTOWN("TOWN");
		datiamm.setNUTS("ITC43");
		datiamm.setPOSTALCODE("POSTAL");
		datiamm.setCOUNTRY(CountryType.IT);
		datiamm.setEMAIL("test@test.com");
		datiamm.setPHONE("+39 123231");
		datiamm.setURL("http://www.google.com");
		datiamm.setFAX("+39 233434");
	}
	
	private void createAddrS1Type(AddrS1Type datiamm, String name) {
		datiamm.setOFFICIALNAME(name);
		datiamm.setNATIONALID("2434324");
		datiamm.setADDRESS("TEST TESTTEST");
		datiamm.setTOWN("TOWN");
		datiamm.setNUTS("ITC43");
		datiamm.setPOSTALCODE("POSTAL");
		datiamm.setCOUNTRY(CountryType.IT);
		datiamm.setCONTACTPOINT("Contact");
		datiamm.setPHONE("+39 123231");
		datiamm.setFAX("+39 233434");
		datiamm.setEMAIL("test@test.com");
		datiamm.setURLGENERAL("http://www.google.com");
		datiamm.setURLBUYER("http://www.gogle.com");
	}
	
	private void createAddrS6Type(AddrS6Type datiamm, String name) {
		datiamm.setOFFICIALNAME(name);
		datiamm.setADDRESS("TEST TEST");
		datiamm.setTOWN("TOWN");
		datiamm.setPOSTALCODE("POSTAL");
		datiamm.setCOUNTRY(CountryType.IT);
		datiamm.setPHONE("+39 123231");
		datiamm.setFAX("+39 233434");
		datiamm.setEMAIL("test@test.com");
		datiamm.setURLSA("http://www.gogle.com");
	}
	
	
	public Gara createGara() {
		Gara gara = new Gara(123);
		
		gara.setOggetto("oggetto gara");
		gara.setIMPORTO_GARA(new BigDecimal(1234));
		gara.setID_MODO_REAL(1);
		gara.setID_SVOLGIMENTO(8);
		
		
		return gara;
	}
	
	public Lotto createLotto(String cig) {
		Lotto lotto = new Lotto();
		lotto.setTIPO_CONTRATTO_LOTTO("S");
		lotto.setId_CPV("71222000-1");
		lotto.setLUOGO_NUTS("ITC43");
		lotto.setOggetto("ogg lotto "+cig);
		lotto.setImporto_Lotto(new BigDecimal(54500));
		lotto.setId_Scelta_Contraente("29");
		lotto.setDataScadenzaPagamenti("20210505");
		lotto.setDataLetteraInvito("20210505");
		lotto.setCig(cig);
		lotto.setCig_kkk("342");
		lotto.setDurataRipetizioni(10);
		
		lotto.setElencoCpvSecondarie(new ArrayList<CpvLotto>());
		
		return lotto;
	}
	
	private void createContactContractor(String natid, AddrS5Type addrS5type) {
		addrS5type.setNATIONALID(natid);
		addrS5type.setNUTS("ITC43");
		addrS5type.setEMAIL("test@anac.it");
		addrS5type.setPHONE("+39 23232313");
		addrS5type.setURL("http://www.test.it");
		addrS5type.setFAX("+39 34243243");

	}
	
//	public static void main(String[] args) {
//		CreateMockup mock = new CreateMockup();
//		
//		DeltaLottoWS deltalotto = mock.createDeltaLotto();
//		
//		System.out.println(deltalotto.getDeltaLotto().getDATIPROCEDURA().getNOTEASTAELETTRONICA());
//	}
	
}
