package it.avlp.simog.massload.util.conversion;

import it.avlp.simog.massload.esito.EsitoIds;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;
import it.avlp.simog.massload.xmlbeans.RecVarianteType;
import it.avlp.simog.massload.xmlbeans.RitardoType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.VarianteType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Una classe che serve a definire un stato di una scheda XML
 * 
 * @author vletizia
 *
 */
public class XmlSituationParser {

	
	/**
	 * Metodo che costruisce un'oggetto che permette di capire la situazione dei dati dell'xml
	 * a partire dall'oggetto xml in ingresso,<strong>l'oggetto in ingresso ha dei vincoli</strong>
	 * deve essere presente una sola scheda completa !
	 * 
	 * @param datiAggiudicazione
	 * @return
	 */
	public SituazioneAttualeSchedeXml parse(DatiAggiudicazioneType datiAggiudicazione){
		
		SituazioneAttualeSchedeXml situazione = new SituazioneAttualeSchedeXml();
				
		DatiComuniType datiComuni = datiAggiudicazione.getDatiComuni();	
		if(datiComuni != null){ 
			situazione.setPresentDatiComuni(true);
			situazione.setPresentDatiComuniIdLocale(datiComuni.isSetIDSCHEDALOCALE());
			situazione.setDatiComuniIdLocale(datiComuni.getIDSCHEDALOCALE());
			situazione.setPresentDatiComuniIdSimog(datiComuni.isSetIDSCHEDASIMOG());			
			situazione.setDatiComuniIdSimog(datiComuni.getIDSCHEDASIMOG());
		}
		
		if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
			
			situazione.setPresentSchedaCompleta(true);
			SchedaCompletaType schedaCompleta = datiAggiudicazione.getSchedaCompletaArray()[0];
			
			String CUI = schedaCompleta.getCUI();
			if(CUI != null && !"".equals(CUI.trim())) situazione.setPresentCUI(true);
			
			if(schedaCompleta.isSetAggiudicazione()){
				situazione.setPresentAggiudicazione(true);
				if(schedaCompleta.getAggiudicazione().getAppalto().isSetIDSCHEDALOCALE()){
					situazione.setPresentAggiudicazioneIdLocale(true);
					situazione.setAggiudicazioneIdLocale(schedaCompleta.getAggiudicazione().getAppalto().getIDSCHEDALOCALE());
				}
				if(schedaCompleta.getAggiudicazione().getAppalto().isSetIDSCHEDASIMOG()){
					situazione.setPresentAggiudicazioneIdSimog(true);
					situazione.setAggiudicazioneIdSimog(schedaCompleta.getAggiudicazione().getAppalto().getIDSCHEDASIMOG());
				}
				
			}
			
			if(schedaCompleta.isSetSottosoglia()){
				situazione.setPresentSottosoglia(true);
				if(schedaCompleta.getSottosoglia().getAppalto().isSetIDSCHEDALOCALE()){
					situazione.setPresentSottosogliaIdLocale(true);
					situazione.setSottosogliaIdLocale(schedaCompleta.getSottosoglia().getAppalto().getIDSCHEDALOCALE());
				}
				if(schedaCompleta.getSottosoglia().getAppalto().isSetIDSCHEDASIMOG()){
					situazione.setPresentSottosogliaIdSimog(true);
					situazione.setSottosogliaIdSimog(schedaCompleta.getSottosoglia().getAppalto().getIDSCHEDASIMOG());
				}
				
			}
			
			if(schedaCompleta.isSetEscluso()){
				situazione.setPresentEscluso(true);
				if(schedaCompleta.getEscluso().getAppalto().isSetIDSCHEDALOCALE()){
					situazione.setPresentEsclusoIdLocale(true);
					situazione.setEsclusoIdLocale(schedaCompleta.getEscluso().getAppalto().getIDSCHEDALOCALE());
				}
				if(schedaCompleta.getEscluso().getAppalto().isSetIDSCHEDASIMOG()){
					situazione.setPresentEsclusoIdSimog(true);
					situazione.setEsclusoIdSimog(schedaCompleta.getEscluso().getAppalto().getIDSCHEDASIMOG());
				}
				
			}
			
			if(schedaCompleta.isSetAdesione()){
				situazione.setPresentAdesione(true);
				if(schedaCompleta.getAdesione().getAppalto().isSetIDSCHEDALOCALE()){
					situazione.setPresentAdesioneIdLocale(true);
					situazione.setAdesioneIdLocale(schedaCompleta.getAdesione().getAppalto().getIDSCHEDALOCALE());
				}
				if(schedaCompleta.getAdesione().getAppalto().isSetIDSCHEDASIMOG()){
					situazione.setPresentAdesioneIdSimog(true);
					situazione.setAdesioneIdSimog(schedaCompleta.getAdesione().getAppalto().getIDSCHEDASIMOG());
				}
				
			}
			
			if(schedaCompleta.isSetDatiAccordi()){
				situazione.setPresentAccordi(true);
				EsitoIds esito = this.fillIds(schedaCompleta.getDatiAccordi().getAccordoBonarioArray(), AccordoBonarioType.class);
				situazione.setAccordiIdLocale(esito.getIdLocales());
				situazione.setAccordiIdSimog(esito.getIdSimogs());
				situazione.setIsPresentAccordiIdLocale(esito.getIsIdLocales());
				situazione.setIsPresentAccordiIdSimog(esito.getIsIdSimogs());
			}
			
			if(schedaCompleta.isSetDatiAvanzamenti()){
				situazione.setPresentAvanzamenti(true);
				EsitoIds esito = this.fillIds(schedaCompleta.getDatiAvanzamenti().getAvanzamentoArray(), AvanzamentoType.class);
				situazione.setAvanzamentiIdLocale(esito.getIdLocales());
				situazione.setAvanzamentiIdSimog(esito.getIdSimogs());
				situazione.setIsPresentAvanzamentiIdLocale(esito.getIsIdLocales());
				situazione.setIsPresentAvanzamentiIdSimog(esito.getIsIdSimogs());
			}
			
			if(schedaCompleta.isSetDatiCollaudo()){
				situazione.setPresentCollaudo(true);
				
				situazione.setPresentCollaudoIdLocale(schedaCompleta.getDatiCollaudo().getCollaudo().isSetIDSCHEDALOCALE());
				situazione.setCollaudoIdLocale(schedaCompleta.getDatiCollaudo().getCollaudo().getIDSCHEDALOCALE());
				
				situazione.setPresentCollaudoIdSimog(schedaCompleta.getDatiCollaudo().getCollaudo().isSetIDSCHEDASIMOG());
				situazione.setCollaudoIdSimog(schedaCompleta.getDatiCollaudo().getCollaudo().getIDSCHEDASIMOG());
			}
			
			if(schedaCompleta.isSetDatiConclusione()){
				situazione.setPresentConclusione(true);
				
				situazione.setPresentConclusioneIdLocale(schedaCompleta.getDatiConclusione().isSetIDSCHEDALOCALE());
				situazione.setConclusioneIdLocale(schedaCompleta.getDatiConclusione().getIDSCHEDALOCALE());
				
				situazione.setPresentConclusioneIdSimog(schedaCompleta.getDatiConclusione().isSetIDSCHEDASIMOG());
				situazione.setConclusioneIdSimog(schedaCompleta.getDatiConclusione().getIDSCHEDASIMOG());
			}
			
			if(schedaCompleta.isSetDatiInizio()){
				situazione.setPresentInizioLavori(true);
				
				situazione.setPresentInizioLavoriIdLocale(schedaCompleta.getDatiInizio().getInizio().isSetIDSCHEDALOCALE());
				situazione.setInizioLavoriIdLocale(schedaCompleta.getDatiInizio().getInizio().getIDSCHEDALOCALE());
				
				situazione.setPresentInizioLavoriIdSimog(schedaCompleta.getDatiInizio().getInizio().isSetIDSCHEDASIMOG());
				situazione.setInizioLavoriIdSimog(schedaCompleta.getDatiInizio().getInizio().getIDSCHEDASIMOG());
			}
			
			if(schedaCompleta.isSetDatiStipula()){
				situazione.setPresentStipula(true);
				
				situazione.setPresentStipulaIdLocale(schedaCompleta.getDatiStipula().getStipula().isSetIDSCHEDALOCALE());
				situazione.setStipulaIdLocale(schedaCompleta.getDatiStipula().getStipula().getIDSCHEDALOCALE());
				
				situazione.setPresentStipulaIdSimog(schedaCompleta.getDatiStipula().getStipula().isSetIDSCHEDASIMOG());
				situazione.setStipulaIdSimog(schedaCompleta.getDatiStipula().getStipula().getIDSCHEDASIMOG());
			}
			
			if(schedaCompleta.isSetDatiRitardi()){
				situazione.setPresentRitardo(true);
				EsitoIds esito = this.fillIds(schedaCompleta.getDatiRitardi().getRitardoArray(), RitardoType.class);
				situazione.setRitardoIdLocale(esito.getIdLocales());
				situazione.setRitardoIdSimog(esito.getIdSimogs());
				situazione.setIsPresentRitardoIdLocale(esito.getIsIdLocales());
				situazione.setIsPresentRitardoIdSimog(esito.getIsIdSimogs());
			}
			
			if(schedaCompleta.isSetDatiSospensioni()){
				situazione.setPresentSospensioni(true);
				EsitoIds esito = this.fillIds(schedaCompleta.getDatiSospensioni().getSospensioneArray(), SospensioneType.class);
				situazione.setSospensioniIdLocale(esito.getIdLocales());
				situazione.setSospensioniIdSimog(esito.getIdSimogs());
				situazione.setIsPresentSospensioniIdLocale(esito.getIsIdLocales());
				situazione.setIsPresentSospensioniIdSimog(esito.getIsIdSimogs());
			}
			
			if(schedaCompleta.isSetDatiSubappalti()){
				situazione.setPresentSubAppalti(true);
				EsitoIds esito = this.fillIds(schedaCompleta.getDatiSubappalti().getSubappaltoArray(), SubappaltoType.class);
				situazione.setSubAppaltiIdLocale(esito.getIdLocales());
				situazione.setSubAppaltiIdSimog(esito.getIdSimogs());
				situazione.setIsPresentSubAppaltiIdLocale(esito.getIsIdLocales());
				situazione.setIsPresentSubAppaltiIdSimog(esito.getIsIdSimogs());
			}
			
			if(schedaCompleta.isSetDatiVarianti()){
				situazione.setPresentVarianti(true);
				EsitoIds esito = this.fillIds(schedaCompleta.getDatiVarianti().getVarianteArray(), RecVarianteType.class);
				situazione.setVariantiIdLocale(esito.getIdLocales());
				situazione.setVariantiIdSimog(esito.getIdSimogs());
				situazione.setIsPresentVariantiIdLocale(esito.getIsIdLocales());
				situazione.setIsPresentVariantiIdSimog(esito.getIsIdSimogs());
			}
			
		}		
		return situazione;
	}
	
	/**
	 * Metodo che si occupa di valorizzare 4 array di cui due saranno valorizzati solo nelle posizioni 
	 * in corrispondenza di un true dell'array boolean che indica se il valore e' presente o meno.
	 * Ad Es. : 
	 * isIdLocales[i].true -> idLocales[i].value
	 * isIdLocales[i].false -> idLocales[i].null
	 * stesso discorso vale per le simog
	 * 
	 * @param list
	 * @param clazz
	 * @return
	 */
	private EsitoIds fillIds(Object[] list, Class<?> clazz){
		try{
			EsitoIds esitoIds = new EsitoIds();
			boolean[] isIdLocales = new boolean[list.length];
			boolean[] isIdSimogs = new boolean[list.length];
			String[] idLocales = new String[list.length];
			String[] idSimogs = new String[list.length];
			
			Method mIsIdLocale = clazz.getMethod("isSetIDSCHEDALOCALE", (Class[])null);
			Method mIsIdSimog = clazz.getMethod("isSetIDSCHEDASIMOG", (Class[])null);
			Method mGetIdLocale = clazz.getMethod("getIDSCHEDALOCALE", (Class[])null);
			Method mGetIdSimog = clazz.getMethod("getIDSCHEDASIMOG", (Class[])null);
			
			if(clazz.equals(SubappaltoType.class)){
				// devo vedere come carica l'array di stringhe perche non ho piu la relazione
				// string - boolean.
				int i = 0;
				int i1 = 0;
				i++;
				i1 = i + 1;
			}
			for(int i = 0; i < list.length; i++){
				// istanza sulla quale verranno invocati i metodi
				Object obj = list[i];
				// variante e' un caso particolare devo andare a prendere il sotto tipo per avere idSimog e idLocale
				if(clazz.equals(RecVarianteType.class)){
					obj = this.getSubType(VarianteType.class.cast(obj));
				}
				
				boolean isIdLocale = ((Boolean)mIsIdLocale.invoke(clazz.cast(obj), (Object[])null)).booleanValue();
				String idLocale = null;
				if(isIdLocale)  idLocale = (String)mGetIdLocale.invoke(clazz.cast(obj), (Object[])null);
				
				boolean isIdSimog = ((Boolean)mIsIdSimog.invoke(clazz.cast(obj), (Object[])null)).booleanValue();
				String idSimog = null;
				if(isIdSimog) idSimog = (String)mGetIdSimog.invoke(clazz.cast(obj), (Object[])null);
				
				isIdLocales[i] = isIdLocale;
				isIdSimogs[i] = isIdSimog;
				idLocales[i] = idLocale;
				idSimogs[i] = idSimog;
			}
			esitoIds.setIdLocales(idLocales);
			esitoIds.setIdSimogs(idSimogs);
			esitoIds.setIsIdLocales(isIdLocales);
			esitoIds.setIsIdSimogs(isIdSimogs);
			return esitoIds;
		}catch(Exception e){
			// logg ?
			return null;
		}
	}
	/**
	 * Ritorna il tipo nidificato
	 * @param variante
	 * @return
	 */
	private RecVarianteType getSubType(VarianteType variante){
		return variante.getVariante();
	}
	/**
	 * Metodo che usa la reflection si occupa di invocare della classe passata il  metodo "isSetIDSCHEDALOCALE()"
	 * NOTA: e' anche per quello che non risulta usato, invece lo e' !
	 * 
	 * @param list
	 * @param clazz
	 * @param method
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
//	private boolean isSetIdLocale(Object[] list, Class<?> clazz){
//		try{
//			boolean esito = true;
//			// recupero l'oggetto rappresentante il metodo, sulla class di cui argomento, tramite nome
//			Method method = clazz.getMethod("isSetIDSCHEDALOCALE", (Class[])null);
//			if(list == null || list.length == 0) return false;
//			
//			for(int i = 0; i < list.length; i++){
//				// L'istanza sulla quale si invoca il metodo e' "list[i]"
//				// il che equivale a dire ((TipoCorrente)list[i]).isSetIDSCHEDALOCALE()
//				esito = esito && ((Boolean)method.invoke(clazz.cast(list[i]), (Object[])null)).booleanValue();
//			}
//			return esito;
//		}catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}
//	}
	/**
	 * Metodo che usa la reflection si occupa di invocare della classe passata il  metodo "isSetIDSCHEDASIMOG()"
	 * NOTA: e' anche per quello che non risulta usato, invece lo e' !
	 * 
	 * @param list
	 * @param clazz
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
//	private boolean isSetIdSimog(Object[] list, Class<?> clazz ){
//		try{
//			boolean esito = true;
//			Method method = clazz.getMethod("isSetIDSCHEDASIMOG", (Class[])null);
//			if(list == null || list.length == 0) return false;
//			
//			for(int i = 0; i < list.length; i++){
//				// L'istanza sulla quale si invoca il metodo e' "list[i]"
//				// il che equivale a dire ((TipoCorrente)list[i]).isSetIDSCHEDASIMOG()
//				esito = esito && ((Boolean)method.invoke(clazz.cast(list[i]), (Object[])null)).booleanValue();
//			}
//			return esito;
//		}catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	/**
	 * Ritorna un intero con il numero delle "schede complete"
	 * 
	 * @param datiAggiudicazione
	 * @return
	 */
	public int containsSchedaCompleta(DatiAggiudicazioneType datiAggiudicazione){
		
		if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
			return datiAggiudicazione.getSchedaCompletaArray().length;
		}		
		return 0;
	}
	
	/**
	 * Crea un'istanza di DatiAggiudicazioneType dai parametri in ingresso
	 * 
	 * 
	 * @param schedaCompleta
	 * @param datiComuni
	 * @param pubblicazione
	 * @return
	 */
	public DatiAggiudicazioneType creaDatiAggiudicazione(SchedaCompletaType schedaCompleta, DatiComuniType datiComuni, PubblicazioneType pubblicazione){
		
		DatiAggiudicazioneType datiAggiudicazioneCorrente = DatiAggiudicazioneType.Factory.newInstance();
		SchedaCompletaType[] schedeComplete = new SchedaCompletaType[]{schedaCompleta};
		
		datiAggiudicazioneCorrente.setDatiComuni(datiComuni);
		datiAggiudicazioneCorrente.setPubblicazione(pubblicazione);
		datiAggiudicazioneCorrente.setSchedaCompletaArray(schedeComplete);
		
		return datiAggiudicazioneCorrente;
	}
}
