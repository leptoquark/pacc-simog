package test.manager.done.complex;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avcp.simog.managers.aggiudicazione.FinanziamentoManager;
import it.avcp.simog.managers.aggiudicazione.RequisitiManager;
import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import test.manager.SuperTest;


public class TestAggiudicazione extends SuperTest{

	
	public void test(){
		super.initAll();
		
		AggiudicazioniManager am = new AggiudicazioniManager(dbm.getCurrentActiveConnection(),logger);
		
		AggiudicatarioManager ariom = new AggiudicatarioManager(dbm.getCurrentActiveConnection(),logger);
		ResponsabileManager respm = new ResponsabileManager(dbm.getCurrentActiveConnection(),logger);
		CondizioniManager condm = new CondizioniManager(dbm.getCurrentActiveConnection(),logger);
		FinanziamentoManager finam = new FinanziamentoManager(dbm.getCurrentActiveConnection(),logger);
		TipoAppaltoManager tipom = new TipoAppaltoManager(dbm.getCurrentActiveConnection(),logger);
		RequisitiManager reqm = new RequisitiManager(dbm.getCurrentActiveConnection(),logger);
		
		int opNumber = 0;
		AggiudicazioneBean ab = null;
		
		System.out.println("------- Load Test For Aggiudicazioni BEGIN------------");
		try{
			
//			testLoad(am, ab, opNumber);
			testAnnulla(am, ariom, respm, condm, finam, tipom, reqm, ab, opNumber);
			
		}catch(SQLException sqle){
			
			System.out.println("An error occur during op nr.:" +opNumber);
			sqle.printStackTrace();
		}
		System.out.println("------- Load Test For Aggiudicazioni END------------");
	}
	/**
	 * Metodo dedicato al test dei Load
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testLoad(AggiudicazioniManager am, AggiudicazioneBean ab,int opNumber) throws SQLException{
		/****/
		System.out.println("\t------- Load Test Record esistenti ------------");
		opNumber = 1;
		ab = am.getAggiudicazioni(super.getLong(1929), super.getDate("17/03/2009 11.51.24"), false);
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 2;
		ab = am.loadByIdLocale("1", "0164932A2E-1");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 3;
		ab = am.loadByIdSimog(new Long(1929).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		/****/
		System.out.println("\t------- Load Test Record NON esistenti ------------");
		opNumber = 4;
		ab = am.getAggiudicazioni(super.getLong(1929), super.getDate("17/03/2009 11.51.00"), false);
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
		
		opNumber = 5;
		ab = am.loadByIdLocale("2", "0164932A2E-1");
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));
	
		opNumber = 6;
		ab = am.loadByIdSimog(new Long(2929).longValue());
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+ObjectIntrospector.propertiesInfo(AccordoBean.class, ab));		
	}

	/**
	 * Testa load + annulla dei vari manager collegati (1->n)
	 * 
	 * @param am
	 * @param ab
	 * @param opNumber
	 * @throws SQLException
	 */
	public void testAnnulla(AggiudicazioniManager am,
			AggiudicatarioManager ariom, 
			ResponsabileManager respm,
			CondizioniManager condm,
			FinanziamentoManager finam,
			TipoAppaltoManager tipom,
			RequisitiManager reqm,
			AggiudicazioneBean ab, 
			int opNumber) throws SQLException{
		
		/** NOTA: faccio il load per non dover ripetere i parametri **/
		boolean esito =  false;
		
		System.out.println("\t------- Annulla Test Record NON esistenti ------------");
		opNumber = 10;
		ab = am.getAggiudicazioni(super.getLong(1929), super.getDate("17/03/2009 11.51.00"), false);
		if(ab != null){
			esito = am.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(),"test");
		}
		if(esito){
			esito = ariom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione AggiudicatarioManager:"+esito);
			esito = respm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione ResponsabileManager:"+esito);
			esito = condm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione CondizioniManager:"+esito);
			esito = finam.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione FinanziamentoManager:"+esito);
			esito = tipom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione TipoAppaltoManager:"+esito);
			esito = reqm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione RequisitiManager:"+esito);			
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		
		
		opNumber = 11;
		ab = am.loadByIdLocale("2", "0164932A2E-1");
		esito = am.annulla(ab.getIdLocale(), "0164932A2E-1","test");
		if(esito){
			esito = ariom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione AggiudicatarioManager:"+esito);
			esito = respm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione ResponsabileManager:"+esito);
			esito = condm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione CondizioniManager:"+esito);
			esito = finam.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione FinanziamentoManager:"+esito);
			esito = tipom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione TipoAppaltoManager:"+esito);
			esito = reqm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione RequisitiManager:"+esito);			
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		
		
		opNumber = 11;
		ab = am.loadByIdSimog(new Long(2929).longValue());
		esito = am.annulla(ab.getIdAggiudicazione(),"test");	
		if(esito){
			esito = ariom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione AggiudicatarioManager:"+esito);
			esito = respm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione ResponsabileManager:"+esito);
			esito = condm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione CondizioniManager:"+esito);
			esito = finam.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione FinanziamentoManager:"+esito);
			esito = tipom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione TipoAppaltoManager:"+esito);
			esito = reqm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
			System.out.println("\t\t\tEsito Operazione RequisitiManager:"+esito);			
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/
		
		
		System.out.println("\t------- Annulla Test Record esistenti ------------");
		opNumber = 7;
		ab = am.getAggiudicazioni(super.getLong(1929), super.getDate("17/03/2009 11.51.24"), false);
		esito = am.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(),"test");
		if(esito){
			if(ariom.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = ariom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione AggiudicatarioManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione AggiudicatarioManager: Nessun Aggiudicatario Trovato");
			}
			if(respm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione()).size() > 0){
				esito = respm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione ResponsabileManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione ResponsabileManager: Nessun Responsabile Trovato");
			}
			if(condm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = condm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione CondizioniManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione CondizioniManager: Nessuna Condizione Trovata");
			}
			if(finam.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = finam.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione FinanziamentoManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione FinanziamentoManager: Nessuna Finanziamento Trovato");
			}
			List<TipoAppaltoAggBean> l = new LinkedList<TipoAppaltoAggBean>();
			l.addAll(tipom.loadManyFS(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "O", false));
			l.addAll(tipom.loadManyFS(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "S", false));
			l.addAll(tipom.loadManyL(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "O", false));
			l.addAll(tipom.loadManyL(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "S", false));
			if(l.size() > 0){
				esito = tipom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione TipoAppaltoManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione TipoAppaltoManager: Nessun Tipo Appalto Trovato");
			}
			if(reqm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = reqm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione RequisitiManager:"+esito);		
			}else{
				System.out.println("\t\t\tEsito Operazione RequisitiManager: Nessun Requisito Trovato");
			}		
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		
		
		
		opNumber = 8;
		ab = am.loadByIdLocale("1", "0164932A2E-1");
		esito = am.annulla(ab.getIdLocale(), "0164932A2E-1","test");
		if(esito){
			if(ariom.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = ariom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione AggiudicatarioManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione AggiudicatarioManager: Nessun Aggiudicatario Trovato");
			}
			if(respm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione()).size() > 0){
				esito = respm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione ResponsabileManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione ResponsabileManager: Nessun Responsabile Trovato");
			}
			if(condm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = condm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione CondizioniManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione CondizioniManager: Nessuna Condizione Trovata");
			}
			if(finam.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = finam.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione FinanziamentoManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione FinanziamentoManager: Nessuna Finanziamento Trovato");
			}
			List<TipoAppaltoAggBean> l = new LinkedList<TipoAppaltoAggBean>();
			l.addAll(tipom.loadManyFS(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "O", false));
			l.addAll(tipom.loadManyFS(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "S", false));
			l.addAll(tipom.loadManyL(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "O", false));
			l.addAll(tipom.loadManyL(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "S", false));
			if(l.size() > 0){
				esito = tipom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione TipoAppaltoManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione TipoAppaltoManager: Nessun Tipo Appalto Trovato");
			}
			if(reqm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = reqm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione RequisitiManager:"+esito);		
			}else{
				System.out.println("\t\t\tEsito Operazione RequisitiManager: Nessun Requisito Trovato");
			}
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
	
		
		
		opNumber = 9;
		ab = am.loadByIdSimog(new Long(1929).longValue());
		esito = am.annulla(ab.getIdAggiudicazione(),"test");
		if(esito){
			if(ariom.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = ariom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione AggiudicatarioManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione AggiudicatarioManager: Nessun Aggiudicatario Trovato");
			}
			if(respm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione()).size() > 0){
				esito = respm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione ResponsabileManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione ResponsabileManager: Nessun Responsabile Trovato");
			}
			if(condm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = condm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione CondizioniManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione CondizioniManager: Nessuna Condizione Trovata");
			}
			if(finam.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = finam.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione FinanziamentoManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione FinanziamentoManager: Nessuna Finanziamento Trovato");
			}
			List<TipoAppaltoAggBean> l = new LinkedList<TipoAppaltoAggBean>();
			l.addAll(tipom.loadManyFS(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "O", false));
			l.addAll(tipom.loadManyFS(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "S", false));
			l.addAll(tipom.loadManyL(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "O", false));
			l.addAll(tipom.loadManyL(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), "S", false));
			if(l.size() > 0){
				esito = tipom.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione TipoAppaltoManager:"+esito);
			}else{
				System.out.println("\t\t\tEsito Operazione TipoAppaltoManager: Nessun Tipo Appalto Trovato");
			}
			if(reqm.loadMany(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione(), false).size() > 0){
				esito = reqm.annulla(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
				System.out.println("\t\t\tEsito Operazione RequisitiManager:"+esito);		
			}else{
				System.out.println("\t\t\tEsito Operazione RequisitiManager: Nessun Requisito Trovato");
			}		
		}
		System.out.println("\t\tEsito Operazione nr.:"+opNumber+" -> "+esito);
		/****/

	}

	public static void main(String[] args) {
		TestAggiudicazione ta = new TestAggiudicazione();
		ta.test();

	}

}
