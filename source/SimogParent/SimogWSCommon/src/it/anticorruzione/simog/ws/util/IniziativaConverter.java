package it.anticorruzione.simog.ws.util;

import java.util.ArrayList;
import java.util.List;

import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.massload.xmlbeans.FlagSNType;
import it.avlp.simog.massload.xmlbeans.IniziativaWSDocument;
import it.avlp.simog.massload.xmlbeans.IniziativaWSDocument.IniziativaWS;
import it.avlp.simog.ws.massload.xmlbeans.AmbitoType;
import it.avlp.simog.ws.massload.xmlbeans.CategLottoType;
import it.avlp.simog.ws.massload.xmlbeans.CategoriaType;
import it.avlp.simog.ws.massload.xmlbeans.IniziativaType;
import it.avlp.simog.ws.massload.xmlbeans.LuogoIstatType;
import it.avlp.simog.ws.massload.xmlbeans.TerritorioType;

public class IniziativaConverter {

	
   public static IniziativaSoggAggr convertiIniziativa(IniziativaType iniziativaType) {
			IniziativaSoggAggr bean = new IniziativaSoggAggr();
			bean.setIdGara(iniziativaType.getIDGARAI()!=null ? iniziativaType.getIDGARAI().getValue() : 0);
			bean.setCIG(iniziativaType.getCIGI()!=null ? iniziativaType.getCIGI().getValue() : null);
			bean.setDescrizioneSoggAggr(iniziativaType.getDESCRIZIONESOGGAGGI());
			bean.setDescrizioneIniziativa(iniziativaType.getDESCRIZIONEINIZIATIVAI());
			bean.setSSAARif(iniziativaType.getSSAARIFI());
			bean.setStatoIniziativa(iniziativaType.getSTATOI()!=null ? iniziativaType.getSTATOI().toString() : null);
			bean.setFlagConfrontoComp(iniziativaType.getCONFRONTOCOMPETITIVOI()!=null ? iniziativaType.getCONFRONTOCOMPETITIVOI().toString() : null);
			bean.setNote(iniziativaType.getNOTEI());
			bean.setLink(iniziativaType.getURLI());
			
            bean.setListaCatIniziativa(IniziativaConverter.convertCategorie(iniziativaType.getCATEGORIEI()));
            bean.setListaTerritoriIniziativa(IniziativaConverter.convertTerritori(iniziativaType.getTERRITORII()));
            bean.setAmbitoLotto(IniziativaConverter.convertAmbiti(iniziativaType.getAMBITILOTTOI()));
            
			return bean;
		}
	
   /**
    * Converte da CategLottoType in una lista di stringhe. Il metodo si occupa anche di rimuovere i valori duplicati
    * @param categXml
    * @return
    */
	public static List<String> convertCategorie(CategLottoType categXml){
		List<String> listaCategorie = new ArrayList<String>();
		if(categXml!=null && categXml.getCATEGORIA()!=null && categXml.getCATEGORIA().size()>0) {
					for(CategoriaType catType : categXml.getCATEGORIA()) {
						boolean duplicate = false;
						for(int i=0;i<listaCategorie.size();i++) {
							if(listaCategorie.get(i).equals(catType.getValue())) {
								duplicate=true;
								break;
							}
						}
						if(!duplicate)
						   listaCategorie.add(catType.getValue());
					}
				}
		return listaCategorie;
	}
	
	/**
	 * Converte da TerritorioType in una lista di stringe. Il metodo si occupa anche di rimuovere i duplicati
	 * @param terrXml
	 * @return
	 */
	public static List<String> convertTerritori(TerritorioType terrXml){
		
		  List<String> listaTerritori = new ArrayList<String>();
			if(terrXml!=null && terrXml.getCodRegioneIstat() !=null  &&	terrXml.getCodRegioneIstat().size()>0) {
				for(LuogoIstatType luogoIstatType : terrXml.getCodRegioneIstat()) {
					boolean duplicate = false;
					for(int i=0;i<listaTerritori.size();i++) {
						if(listaTerritori.get(i).equals(luogoIstatType.getValue())) {
							duplicate=true;
							break;
						}
					}
					if(!duplicate)
					   listaTerritori.add(luogoIstatType.getValue());
				}
				
			}
		return listaTerritori;
	}
	
	/**
	 * Converte da AmbitoType in una lista di stringe.  Il metodo si occupa anche di rimuovere i duplicati.
	 * @param ambXml
	 * @return
	 */
	public static List<String> convertAmbiti(AmbitoType ambXml){
		  List<String> listaAmbiti = new ArrayList<String>();
			if(ambXml!=null && ambXml.getAmbitoLotto() !=null && ambXml.getAmbitoLotto().size()>0) {
				for(String ambito : ambXml.getAmbitoLotto()) {
					boolean duplicate = false;
					for(int i=0;i<listaAmbiti.size();i++) {
						if(listaAmbiti.get(i).equals(ambito)) {
							duplicate=true;
							break;
						}
					}
					if(!duplicate)
					    listaAmbiti.add(ambito);
				}
			}
			return listaAmbiti;
	}
	
	public static String convertListaIniziativeToXml(List<IniziativaSoggAggr> iniziative) {
	
		IniziativaWSDocument doc = IniziativaWSDocument.Factory.newInstance();	
		IniziativaWS iniziativaWS = doc.addNewIniziativaWS();
		
		for(IniziativaSoggAggr iniziativa : iniziative) {
			it.avlp.simog.massload.xmlbeans.IniziativaType iniziativaType = iniziativaWS.addNewIniziativa();
			
			iniziativaType.setIDGARAI(iniziativa.getIdGara());
			iniziativaType.setCIGI(iniziativa.getCIG());
			iniziativaType.setDESCRIZIONESOGGAGGI(iniziativa.getDescrizioneSoggAggr()!=null ? iniziativa.getDescrizioneSoggAggr() : "");
			iniziativaType.setDESCRIZIONEINIZIATIVAI(iniziativa.getDescrizioneIniziativa() != null ? iniziativa.getDescrizioneIniziativa() : "");
			iniziativaType.setSSAARIFI(iniziativa.getSSAARif() != null ? iniziativa.getSSAARif() : "");
			iniziativaType.setSTATOI(FlagSNType.Enum.forString(iniziativa.getStatoIniziativa()));
			if(iniziativa.getFlagConfrontoComp()!=null)
			   iniziativaType.setCONFRONTOCOMPETITIVOI(FlagSNType.Enum.forString(iniziativa.getFlagConfrontoComp()));
			iniziativaType.setNOTEI(iniziativa.getNote() != null ? iniziativa.getNote() : "");
			iniziativaType.setURLI(iniziativa.getLink() != null ? iniziativa.getLink() : "");
			
			//Carica lista territori
			it.avlp.simog.massload.xmlbeans.TerritorioType territorioType = iniziativaType.addNewTERRITORII();
            String[] territoriArray = new String[iniziativa.getListaTerritoriIniziativa().size()];
            for(int i=0;i<iniziativa.getListaTerritoriIniziativa().size();i++) {
            	territoriArray[i]=iniziativa.getListaTerritoriIniziativa().get(i);
            }
			territorioType.setCodRegioneIstatArray(territoriArray);
			
			//Carica lista categorie
			it.avlp.simog.massload.xmlbeans.CategLottoType categLottoType = iniziativaType.addNewCATEGORIEI();
			String[] categorieArray = new String[iniziativa.getListaCatIniziativa().size()];
			for(int i=0;i<iniziativa.getListaCatIniziativa().size();i++) {
				categorieArray[i]=iniziativa.getListaCatIniziativa().get(i);
            }
			categLottoType.setCATEGORIAArray(categorieArray);
			
			//Carica lista ambitilotto
			if(iniziativa.getAmbitoLotto().size()>0) {
					it.avlp.simog.massload.xmlbeans.AmbitoType ambitoType = iniziativaType.addNewAMBITILOTTOI();
					String[] ambitoArray = new String[iniziativa.getAmbitoLotto().size()];
					for(int i=0;i<iniziativa.getAmbitoLotto().size();i++) {
						ambitoArray[i]=iniziativa.getAmbitoLotto().get(i);
		            }
					ambitoType.setAmbitoLottoArray(ambitoArray);
			}
		}
		
		
		return doc.xmlText();
	}
	
}
