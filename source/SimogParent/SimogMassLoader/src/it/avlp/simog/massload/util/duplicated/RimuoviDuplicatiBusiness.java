package it.avlp.simog.massload.util.duplicated;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.massload.util.comparators.ComparatorFactory;
import it.avlp.simog.massload.util.duplicated.feedBack.AggiudicatarioDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.AnagraficaPartecipanteDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.AnagraficaResponsabileDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.CondizioneDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.DittaAusiliariaDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.EventiDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.FinanziamentoDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.IncaricatoDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.PosizioneDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.RequisitoDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.TipoAppaltoDuplicateFeedBack;
import it.avlp.simog.massload.util.multichecker.MultiAdder;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.CondizioneType;
import it.avlp.simog.massload.xmlbeans.DittaAusiliariaType;
import it.avlp.simog.massload.xmlbeans.FinanziamentoType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.PosizioneType;
import it.avlp.simog.massload.xmlbeans.RecMotivoVarType;
import it.avlp.simog.massload.xmlbeans.RequisitoType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;

public class RimuoviDuplicatiBusiness {

	/**
	 * Metodo centralizzato per il controllo delle multiple appartenenti a questa scheda
	 * 
	 * @param objects
	 * @param comparator_ref @see it.avlp.simog.massload.util.comparators.ComparatorFactory costanti
	 * @throws Exception
	 */
	public TipoAppaltoDuplicateFeedBack rimuoviDuplicatiAppaltoForniture(Object[] objects) throws ClassNotFoundException, Exception{
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.TIPOAPPALTOFORNITURE),objects.length);
		boolean controllo = false;
		//------------- tipo forniture -------------------

		TipiAppaltoType[] tipiAppaltoF;
		if(ma.containsDuplicate(objects)){
			tipiAppaltoF = new TipiAppaltoType[ma.getSize()];
			ma.setSenzaDuplicati(tipiAppaltoF, TipiAppaltoType.class);
			//adds warning to questa scheda
			controllo = true;
		}else{
			tipiAppaltoF = (TipiAppaltoType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.TIPOAPPALTOFORNITURE);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new TipoAppaltoDuplicateFeedBack(bean, controllo, tipiAppaltoF); 


	}
	public TipoAppaltoDuplicateFeedBack rimuoviDuplicatiAppaltoLavori(Object[] objects) throws Exception{				
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.TIPOAPPALTOLAVORI),objects.length);
		boolean controllo = false;

		//------------- tipo lavori -------------------
	
		TipiAppaltoType[] tipiAppaltoL;
		if(ma.containsDuplicate(objects)){
			tipiAppaltoL = new TipiAppaltoType[ma.getSize()];
			ma.setSenzaDuplicati(tipiAppaltoL, TipiAppaltoType.class);
			//adds warning to questa scheda
			controllo = true;
		}else{
			tipiAppaltoL = (TipiAppaltoType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.TIPOAPPALTOLAVORI);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new TipoAppaltoDuplicateFeedBack(bean, controllo, tipiAppaltoL); 

	}
	public CondizioneDuplicateFeedBack rimuoviDuplicatiCondizioni(Object[] objects) throws Exception{				
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.CONDIZIONI),objects.length);
		boolean controllo = false;
		//------------- condizioni -------------------

		CondizioneType[] condizioni;
		if(ma.containsDuplicate(objects)){
			//crea array delle dimensioni giuste
			condizioni = new CondizioneType[ma.getSize()];
			//valorizza array
			ma.setSenzaDuplicati(condizioni, CondizioneType.class);
			//adds warning to questa scheda
			controllo = true;
		}else{
			condizioni = (CondizioneType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.CONDIZIONI);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new CondizioneDuplicateFeedBack(bean, controllo, condizioni); 

	}
	public RequisitoDuplicateFeedBack rimuoviDuplicatiRequisiti(Object[] objects) throws Exception{				
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.REQUISITI),objects.length);
		boolean controllo = false;	
		//------------- requisiti -------------------	
		RequisitoType[] requisiti;
		if(ma.containsDuplicate(objects)){
			requisiti = new RequisitoType[ma.getSize()];
			ma.setSenzaDuplicati(requisiti, RequisitoType.class);
			//adds warning to questa scheda
			controllo = true;
		}else{
			requisiti = (RequisitoType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.REQUISITI);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new RequisitoDuplicateFeedBack(bean, controllo, requisiti); 

	}		
	public AggiudicatarioDuplicateFeedBack rimuoviDuplicatiAggiudicatari(Object[] objects) throws Exception{				
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.AGGIUDICATARI),objects.length);
		boolean controllo = false;	

		//------------- aggiudicatari -------------------	
		SoggAggiudicatarioType[] aggiudicatario;
		if(ma.containsDuplicate(objects)){
			aggiudicatario = new SoggAggiudicatarioType[ma.getSize()];
			ma.setSenzaDuplicati(aggiudicatario, SoggAggiudicatarioType.class);
			//adds warning to questa scheda
			controllo = true;
		}else{
			aggiudicatario = (SoggAggiudicatarioType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.AGGIUDICATARI);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new AggiudicatarioDuplicateFeedBack(bean, controllo, aggiudicatario); 

	}				
	
	public DittaAusiliariaDuplicateFeedBack rimuoviDuplicatiDitteAusiliarie(Object[] objects) throws Exception{				
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.DITTE_AUSILIARIE),objects.length);
		boolean controllo = false;	

		//------------- aggiudicatari -------------------	
		DittaAusiliariaType[] aggiudicatario;
		if(ma.containsDuplicate(objects)){
			aggiudicatario = new DittaAusiliariaType[ma.getSize()];
			ma.setSenzaDuplicati(aggiudicatario, DittaAusiliariaType.class);
			//adds warning to questa scheda
			controllo = true;
		}else{
			aggiudicatario = (DittaAusiliariaType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.DITTE_AUSILIARIE);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new DittaAusiliariaDuplicateFeedBack(bean, controllo, aggiudicatario); 

	}			
			
	public IncaricatoDuplicateFeedBack rimuoviDuplicatiIncaricati(Object[] objects) throws Exception{				
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.INCARICATI),objects.length);
		boolean controllo = false;		
		//-------------- incaricati -----------------------	
		IncaricatoType[] incaricato;
		if(ma.containsDuplicate(objects)){
			incaricato = new IncaricatoType[ma.getSize()];
			ma.setSenzaDuplicati(incaricato, IncaricatoType.class);
			//adds warning to questa scheda
			controllo = true;
		}else{
			incaricato = (IncaricatoType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.INCARICATI);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new IncaricatoDuplicateFeedBack(bean, controllo, incaricato); 

	}	
	/**
	 * Al momento e previsto sempre false
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public FinanziamentoDuplicateFeedBack rimuoviDuplicatiFinanziamenti(Object[] objects) throws Exception{				
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.FINANZIAMENTI),objects.length);
		boolean controllo = false;	
		//-------------- finanziamenti -----------------------

		FinanziamentoType[] finanziamento;
		if(ma.containsDuplicate(objects)){
			finanziamento = new FinanziamentoType[ma.getSize()];
			ma.setSenzaDuplicati(finanziamento, FinanziamentoType.class); 
			controllo = true;
		}else{
			finanziamento = (FinanziamentoType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.FINANZIAMENTI);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new FinanziamentoDuplicateFeedBack(bean, controllo, finanziamento); 

	}
	
	public PosizioneDuplicateFeedBack rimuoviDuplicatiPosizioni(Object[] objects) throws Exception{				
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.POSIZIONI),objects.length);
		boolean controllo = false;	
		//-------------- posizioni --------------------------
		PosizioneType[] posizione;
		if(ma.containsDuplicate(objects)){
			posizione = new PosizioneType[ma.getSize()];
			ma.setSenzaDuplicati(posizione, PosizioneType.class);
			//adds warning to questa scheda
			controllo = true;
		}else{
			posizione = (PosizioneType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.POSIZIONI);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new PosizioneDuplicateFeedBack(bean, controllo, posizione); 

	}
	
	public EventiDuplicateFeedBack rimuoviDuplicatiEventi(Object[] objects) throws Exception{				
		ValidationBean bean = null;
		MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.EVENTIMOTIVIVARIANTI),objects.length);
		boolean controllo = false;	
		//-------------- eventi --------------------------
		RecMotivoVarType[] rmvt;
		if(ma.containsDuplicate(objects)){
			rmvt = new RecMotivoVarType[ma.getSize()];
			ma.setSenzaDuplicati(rmvt, RecMotivoVarType.class);
			//adds warning to questa scheda
			controllo = true;
		}else{
			rmvt = (RecMotivoVarType[])objects;
		}
		if(controllo){
			String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.EVENTIMOTIVIVARIANTI);
			bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
			
		}
		return new EventiDuplicateFeedBack(bean, controllo, rmvt); 
	}
	
	/**
	 * @param objects
	 * @return
	 */
	public AnagraficaResponsabileDuplicateFeedBack rimuoviDuplicatiAnaResponsabili(Object[] objects){
		try{
			ValidationBean bean = null;
			MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.ANAGRAFICARESPONSABILE),objects.length);
			boolean controllo = false;	
			ResponsabileType[] arrayOfAnagraficheResponsabili;
			if(ma.containsDuplicate(objects)){
				arrayOfAnagraficheResponsabili = new ResponsabileType[ma.getSize()];
				ma.setSenzaDuplicati(arrayOfAnagraficheResponsabili, ResponsabileType.class);
				//adds warning to questa scheda
				controllo = true;
			}else{
				arrayOfAnagraficheResponsabili = (ResponsabileType[])objects;
			}
			if(controllo){
				String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.ANAGRAFICARESPONSABILE);
				bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
				
			}return new AnagraficaResponsabileDuplicateFeedBack(bean, controllo, arrayOfAnagraficheResponsabili);
			
		}catch (ClassNotFoundException cnfe) {
			// handle exception : non si e' usato correttamente ComparatorFactory.getInstance(ComparatorFactory.CONSTANT)
			cnfe.printStackTrace();
		}catch (Exception e) {
			// handle exception: L'istanziazione del validation bean e' stata fatta con parametri non validi
			e.printStackTrace();
		}
		return  null;
		
	}
	/**
	 * ATTENZIONE IL COMPARATORE RITORNA SEMPRE FALSE, probabilmente non si e' trovato
	 * un discriminante valido.
	 * @param objects
	 * @return
	 */
	public AnagraficaPartecipanteDuplicateFeedBack rimuoviDuplicatiAnaPartecipanti(Object[] objects){
		try{
			ValidationBean bean = null;
			MultiAdder ma = new MultiAdder(ComparatorFactory.getInstance(ComparatorFactory.ANAGRAFICAPARTECIPANTE),objects.length);
			boolean controllo = false;	
			AggiudicatarioType[] arrayOfAnagrafichePartecipanti;
			if(ma.containsDuplicate(objects)){
				arrayOfAnagrafichePartecipanti = new AggiudicatarioType[ma.getSize()];
				ma.setSenzaDuplicati(arrayOfAnagrafichePartecipanti, AggiudicatarioType.class);
				//adds warning to questa scheda
				controllo = true;
			}else{
				arrayOfAnagrafichePartecipanti = (AggiudicatarioType[])objects;
			}
			if(controllo){
				String messaggio = Messaggi.SIMOG_MASSLOADER_188.replace("$1",ComparatorFactory.ANAGRAFICAPARTECIPANTE);
				bean = new ValidationBean(messaggio, ValidationBean.VALBEAN_SEV_WARN, ma.getPosizionePrimoDuplicato());
				
			}return new AnagraficaPartecipanteDuplicateFeedBack(bean, controllo, arrayOfAnagrafichePartecipanti); 
		
		}catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
		
	}
}
