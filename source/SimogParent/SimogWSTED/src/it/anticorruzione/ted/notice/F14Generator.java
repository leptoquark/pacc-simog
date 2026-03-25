package it.anticorruzione.ted.notice;

import java.util.List;

import org.apache.xmlbeans.XmlAnySimpleType;

import eu.europa.publications.resource.schema.ted.r209.reception.BodyF14;
import eu.europa.publications.resource.schema.ted.r209.reception.ChangesF14;
import eu.europa.publications.resource.schema.ted.r209.reception.ChangesF14.CHANGE;
import eu.europa.publications.resource.schema.ted.r209.reception.ChangesF14.CHANGE.NEWVALUE;
import eu.europa.publications.resource.schema.ted.r209.reception.ChangesF14.CHANGE.OLDVALUE;
import eu.europa.publications.resource.schema.ted.r209.reception.CiF14;
import eu.europa.publications.resource.schema.ted.r209.reception.CiF14.ESENDERLOGIN;
import eu.europa.publications.resource.schema.ted.r209.reception.CiF14.NODOCEXT;
import eu.europa.publications.resource.schema.ted.r209.reception.ContactAddContractingBodyF14;
import eu.europa.publications.resource.schema.ted.r209.reception.CpvSet;
import eu.europa.publications.resource.schema.ted.r209.reception.DATEDISPATCHORIGINALDocument.DATEDISPATCHORIGINAL;
import eu.europa.publications.resource.schema.ted.r209.reception.F142014Document.F142014;
import eu.europa.publications.resource.schema.ted.r209.reception.FormSection;
import eu.europa.publications.resource.schema.ted.r209.reception.Ft;
import eu.europa.publications.resource.schema.ted.r209.reception.Ft.TYPE;
import eu.europa.publications.resource.schema.ted.r209.reception.NonPublished;
import eu.europa.publications.resource.schema.ted.r209.reception.ObjectContractF14;
import eu.europa.publications.resource.schema.ted.r209.reception.OriginalTranslation;
import eu.europa.publications.resource.schema.ted.r209.reception.TCeLanguageList;
import eu.europa.publications.resource.schema.ted.r209.reception.TCountryList;
import eu.europa.publications.resource.schema.ted.r209.reception.WHEREDocument.WHERE;
import eu.europa.publications.resource.schema.ted.x2021.nuts.TNutsCodeList;
import it.anticorruzione.ted.beans.LottoTED;
import it.anticorruzione.ted.enums.LegalBasisEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.util.UtilityClass;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.ws.beans.DataNotice;
import it.avlp.simog.ws.massload.xmlbeans.AddrS1Type;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoRettifica;
import it.avlp.simog.ws.massload.xmlbeans.RettificaCpvSecType;
import it.avlp.simog.ws.massload.xmlbeans.RettificaType;

public class F14Generator extends AbstractNoticeGenerator {

	public F14Generator(DataNotice dataNotice, String version) {
		super(dataNotice,
				version, 
				LegalBasisEnum.DIR_201424EU, 
				TypeNoticeEnum.F14);
	}
	
	
	@Override
	public FormSection createFormSection() {
		FormSection formSec = FormSection.Factory.newInstance();
	 try {
		DeltaGaraTED deltaGara = getDataNotice().getDeltaGaraTED();
		Gara gara = getDataNotice().getGara();
	
		F142014 f142014 = formSec.addNewF142014();
		f142014.setCATEGORY(OriginalTranslation.ORIGINAL);				//F14-3

		XmlAnySimpleType form = XmlAnySimpleType.Factory.newInstance();
		form.setStringValue(TypeNoticeEnum.F14.getTipo());
		f142014.setFORM(form);											//F14-4
		f142014.setLG(TCeLanguageList.IT);								//F14-5
		
		f142014.addNewLEGALBASIS().setVALUE(getLegalBasis());			//F14-6
        BodyF14 body = createContractingBody();							//F14-7-8-9
		
        f142014.setCONTRACTINGBODY(body);								
        		
        //Object Contract
        ObjectContractF14 objContract = createObjectContract(deltaGara,gara);
        f142014.setOBJECTCONTRACT(objContract);
        
        //ComplementaryInfo
        CiF14 complementaryInfo = createComplementaryInfo();
        f142014.setCOMPLEMENTARYINFO(complementaryInfo);
        
        ChangesF14 changes = f142014.addNewCHANGES();					//F14-25
        
        FormularioAvvisoRettifica formRett =  getDataNotice().getFormularioRettifica();
        
		NonPublished modor;
        if(formRett.getMOTIVORETTIFICA().getValue().equals("1")) {
        	modor = changes.addNewMODIFICATIONORIGINAL();				//F14-26
        } else {
        	modor = changes.addNewPUBLICATIONTED();						//F14-27
        }
        
        XmlAnySimpleType form2 = XmlAnySimpleType.Factory.newInstance();
		form2.setStringValue("NO");
        modor.setPUBLICATION(form2);

       List<RettificaType> rettifiche = formRett.getRETTIFICA();
        for(RettificaType rettifica  : rettifiche) {
        	CHANGE change = changes.addNewCHANGE();					
        	
        	WHERE where = change.addNewWHERE();											//F14-28
        	where.setSECTION(rettifica.getSECTIONNUMBER());								//F14-29
        	
        	//Se la sezione è III, IV, VI o VII il lot no non è richiesto
        	boolean checkSection = isLotNoAllowed(rettifica.getSECTIONNUMBER());
        	
        	if(!checkSection && rettifica.getCIGRETTIFICA()!=null)
        		where.setLOTNO(UtilityClass.getLotNo(getDataNotice().getListaLotti(),rettifica.getCIGRETTIFICA().getValue()));	//F14-30

        	
        	if(rettifica.getSECTIONTOMODIFY()!=null)
        		where.setLABEL(rettifica.getSECTIONTOMODIFY());							//F14-31
        	
        	OLDVALUE oldvalue = change.addNewOLDVALUE();								//F14-32
        	NEWVALUE newvalue = change.addNewNEWVALUE();								
        	
        	if(rettifica.getOLDVALUETEXT()!=null) {
        		Ft oldtxt =oldvalue.addNewTEXT().addNewP().addNewFT();					//F14-33
        		oldtxt.setTYPE(TYPE.SUP);
        		oldtxt.setStringValue(rettifica.getOLDVALUETEXT());
        	
	        	if(rettifica.getNEWVALUETEXT()!=null) {
	        		Ft newtxt = newvalue.addNewTEXT().addNewP().addNewFT();					//F14-34
	        		newtxt.setTYPE(TYPE.SUP);
	        		newtxt.setStringValue(rettifica.getNEWVALUETEXT());
	        	}
        	
        	} else
	        	if(rettifica.getOLDMAINCPV()!=null) {
	        		oldvalue.addNewCPVMAIN().addNewCPVCODE().setCODE(rettifica.getOLDMAINCPV().split("-")[0]);//F14-35
	        		
	        		if(rettifica.getNEWMAINCPV()!=null) 
	            		newvalue.addNewCPVMAIN().addNewCPVCODE().setCODE(rettifica.getNEWMAINCPV().split("-")[0]);//F14-36
	        	} else
		        	if(rettifica.getRETTIFICACPVSEC()!=null && rettifica.getRETTIFICACPVSEC().size()>0) {
		        		List<RettificaCpvSecType> cpvSecondarie = rettifica.getRETTIFICACPVSEC();
		        		for(RettificaCpvSecType el : cpvSecondarie) {
		        			oldvalue.addNewCPVADDITIONAL().addNewCPVCODE().setCODE(el.getOLDMAINCPVSEC().split("-")[0]);//F14-37
		        			newvalue.addNewCPVADDITIONAL().addNewCPVCODE().setCODE(el.getNEWMAINCPVSEC().split("-")[0]);//F14-38
		        		}
		        		
		    		}
		        	else if(rettifica.getOLDVALUEDATE()!=null) {
			        		oldvalue.setDATE(UtilityClass.dateToCalendar(rettifica.getOLDVALUEDATE().getValue())); //F14-39
			        		
			        		if(rettifica.getOLDVALUETIME() != null && rettifica.getOLDVALUETIME().getValue()!=null) {
			        			oldvalue.setTIME(rettifica.getOLDVALUETIME().getValue());
			        		}
			        		if(rettifica.getNEWVALUEDATE()!=null) {
				        		newvalue.setDATE(UtilityClass.dateToCalendar(rettifica.getNEWVALUEDATE().getValue()));//F14-40
				        		
				        		
				        		if(rettifica.getNEWVALUETIME() != null && rettifica.getNEWVALUETIME().getValue()!=null) {
				        			newvalue.setTIME(rettifica.getNEWVALUETIME().getValue());
				        		}
				        	}
			        	}
			        	
        }
        
        if(formRett.getINFOADDMODIFICA()!=null && !"".contentEquals(formRett.getINFOADDMODIFICA())) {
        	Ft infoadd = changes.addNewINFOADD().addNewP().addNewFT();
        	infoadd.setTYPE(TYPE.SUP);
        	infoadd.setStringValue(formRett.getINFOADDMODIFICA());
        }
        
		}catch(Exception e) {
			e.printStackTrace();
		}
		return formSec;
	}
	
	private boolean isLotNoAllowed(String sectionnumber) {
		
		return sectionnumber.contains("III") || 
				sectionnumber.contains("IV") || 
				sectionnumber.contains("VI") || 
				sectionnumber.contains("VII");
	}


	private BodyF14 createContractingBody() {
		BodyF14 body = BodyF14.Factory.newInstance();
		DeltaGaraTED deltaGaraTED = getDataNotice().getDeltaGaraTED();
		
		List<AddrS1Type > listaContatti = deltaGaraTED.getDATIAMMAGGIUDICATRICE();
		
		body.setADDRESSCONTRACTINGBODY(createContactContractingBody14(listaContatti.get(0)));
		
		if(listaContatti.size()>1) {
			for(int i=1;i<listaContatti.size();i++) {
				AddrS1Type contatto = listaContatti.get(i);
				ContactAddContractingBodyF14 additional = body.addNewADDRESSCONTRACTINGBODYADDITIONAL();
				additional.setOFFICIALNAME(contatto.getOFFICIALNAME());
				additional.setNATIONALID(contatto.getNATIONALID());
				additional.setADDRESS(contatto.getADDRESS());
				additional.setTOWN(contatto.getTOWN());
				additional.setPOSTALCODE(contatto.getPOSTALCODE());
				additional.addNewCOUNTRY().setVALUE(TCountryList.Enum.forString(contatto.getCOUNTRY().getValue())); 
				additional.setCONTACTPOINT(contatto.getCONTACTPOINT());
				additional.setPHONE(contatto.getPHONE().getValue().getValue().getValue());
				additional.setEMAIL(contatto.getEMAIL());
				additional.setFAX(contatto.getFAX().getValue().getValue().getValue());
				additional.addNewNUTS().setCODE(TNutsCodeList.Enum.forString(contatto.getNUTS().getValue())); 
				additional.setURLGENERAL(contatto.getURLGENERAL());
				additional.setURLBUYER(contatto.getURLBUYER());
			}
		}
		
		return body;
	}
	
	private ObjectContractF14 createObjectContract(DeltaGaraTED deltaGara, Gara gara) {
		ObjectContractF14 objContract = ObjectContractF14.Factory.newInstance();			//F14-10
		
		List<LottoTED> listaLotto = getDataNotice().getListaLotti();
		
		Ft ft = objContract.addNewTITLE().addNewP().addNewFT();
		ft.setTYPE(Ft.TYPE.SUP);
		ft.setStringValue(deltaGara.getENTITAAPPALTO().getTITOLOPROCEDURAGARA());			//F14-11
		
		//Non previsto
//		objContract.setREFERENCENUMBER("");													//F14-12
		
		//CPV
		CpvSet cpvmain = objContract.addNewCPVMAIN();										//F14-13
		cpvmain.addNewCPVCODE().setCODE(deltaGara.getENTITAAPPALTO().getCPVGARA().split("-")[0]);//F14-14
		
		//Non previsto
//		cpvmain.addNewCPVSUPPLEMENTARYCODE();												//F14-15
		
		//Tipo contratto gara
		objContract.addNewTYPECONTRACT().setCTYPE(getTypeContract(deltaGara.getENTITAAPPALTO().getTIPOCONTRATTOAPPALTO())); //F14-16
		
		Ft ft2 = objContract.addNewSHORTDESCR().addNewP().addNewFT();						//F14-17
		ft2.setTYPE(Ft.TYPE.SUP);
		ft2.setStringValue(gara.getOggetto());
		
		return objContract;
	}
	
private CiF14 createComplementaryInfo() {
		
		CiF14 complementaryinfo = CiF14.Factory.newInstance();								//F14-18
		complementaryinfo.setDATEDISPATCHNOTICE(UtilityClass.currentCalendar());			//F14-19
		
		XmlAnySimpleType form2 = XmlAnySimpleType.Factory.newInstance();
		form2.setStringValue("NO");
		complementaryinfo.addNewORIGINALTEDESENDER().setPUBLICATION(form2);					//F14-20
		ESENDERLOGIN esenderLogin = complementaryinfo.addNewESENDERLOGIN();
		esenderLogin.setPUBLICATION(form2);
		esenderLogin.setStringValue(getDataNotice().getEsenderlogin());						//F14-21
		
		NODOCEXT noDocEx = complementaryinfo.addNewNODOCEXT();								//F14-22
		noDocEx.setPUBLICATION(form2);
		noDocEx.setStringValue(getDataNotice().getOriginalNoDocExt());
		
		complementaryinfo.setNOTICENUMBEROJ(getDataNotice().getNoticeNumberOjs());			//F14-23
		DATEDISPATCHORIGINAL dateoriginal = complementaryinfo.addNewDATEDISPATCHORIGINAL();	//F14-24
		dateoriginal.setPUBLICATION(form2);
		dateoriginal.setStringValue(getDataNotice().getOriginalDataDispatch());
		
		
		return complementaryinfo;
		
	}

}
