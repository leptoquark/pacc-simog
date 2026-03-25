package it.avlp.simog.validatore;

import it.avlp.simog.beans.SchedaState;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.ACCORDI;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.COLLAUDO;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.R129;
import it.avlp.simog.db.generated.SOSPENSIONI;
import it.avlp.simog.db.generated.STATI_AVANZ;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.db.generated.SUBAPPALTI;
import it.avlp.simog.db.generated.VARIANTI;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;


/**
 * @author ppientini
 *
 *	Classe per la verifica che una data scheda abbia una richiesta di annullamento o cancellazione attive
 */
public class RichAnnCancController {

	private Connection conn;
	private Logger logger;
	
	public RichAnnCancController(Connection conn, Logger logger) {
		this.conn = conn;
		this.logger = logger;
	}

	/**
	 * @param schedaName	nome della scheda come da IdentificativoSchede
	 * @param idScheda	
	 * @param dataInizioScheda
	 * @return
	 */
	public SchedaState getSchedaState(String schedaName, boolean multipla, long idScheda, Timestamp dataInizioScheda){
	
		Method m = null;
		SchedaState isp = null;
		
		try {
			if(schedaName != null){
				 m = this.getClass().getMethod(schedaName + (multipla ? "_mult" : ""), Long.class,Timestamp.class);
				 isp = (SchedaState)m.invoke(this, idScheda,dataInizioScheda);
	
				 // richiamo metodo per verifica richiesta di cancellazione in corso
				 m = this.getClass().getMethod(schedaName +"_del" + (multipla ? "_mult" : ""), Long.class,Timestamp.class);
				 if (m != null){
					 String ret = (String)m.invoke(this, idScheda,dataInizioScheda);
					 isp.setRichDelete(ret != null);
				 }
				 else
					 isp.setRichDelete(false);
			}
		}catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
		}
		
		return isp;
	}
	
	
	/***
	 * metodi per il controllo della richiesta di annullamento
	 * 
	 * utilizzano l'aggiudicazione per la verifica, per le schede multiple basta che almeno una abbia una richiesta in corso
	 * 
	 * sono richiamati dal WorkFloController WEB
	 */ 
	public SchedaState DATI_COMUNI(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(INFO_AGGIUDICAZIONI.TABLE_NAME,INFO_AGGIUDICAZIONI.ID_INFO, idAggiudicazione, dataInizioAggiudicazione, INFO_AGGIUDICAZIONI.ID_INFO, null);
	}

	public SchedaState AGGIUDICAZIONE(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(AGGIUDICAZIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}

	public SchedaState SOTTOSOGLIA(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(AGGIUDICAZIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	
	public SchedaState ESCLUSO(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(AGGIUDICAZIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
		
	public SchedaState FASE_INIZIALE(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(INIZIO_LAVORI.TABLE_NAME,INIZIO_LAVORI.ID_INIZIO, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	
	public SchedaState FINE_LAVORI(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(FINE_LAVORI.TABLE_NAME,FINE_LAVORI.ID_ULTIM, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	
	public SchedaState COLLAUDO(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(COLLAUDO.TABLE_NAME,COLLAUDO.ID_COLLAUDO, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}

	
	public SchedaState STATO_AVANZAMENTO(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(STATI_AVANZ.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}

	public SchedaState IPOTESI_RECESSO(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(R129.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	
	public SchedaState ACCORDO_BONARIO(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(ACCORDI.TABLE_NAME,ACCORDI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	
	public SchedaState SOSPENSIONE(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(SOSPENSIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	
	public SchedaState SUBAPPALTO(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(SUBAPPALTI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	
	public SchedaState VARIANTE(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(VARIANTI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	public SchedaState STIPULA(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(STIPULA.TABLE_NAME,STIPULA.ID_STIPULA, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	public SchedaState ADESIONE(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(AGGIUDICAZIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, null);
	}
	
	   /***
     * metodi per il controllo della richiesta di annullamento
     * 
     * utilizzano l'id scheda , sono solo per le singole, ma wrappati come multiple per usare la reflection
     * 
     * sono richiamati dal WorkFloController WEB
     */ 
    public SchedaState DATI_COMUNI_mult(Long id, Timestamp data) throws SQLException{
        return (new AccessiDB(conn, logger)).isEmptyRs(INFO_AGGIUDICAZIONI.TABLE_NAME,INFO_AGGIUDICAZIONI.ID_INFO, id, data, INFO_AGGIUDICAZIONI.ID_INFO, INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO);
    }

    public SchedaState AGGIUDICAZIONE_mult(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
        return (new AccessiDB(conn, logger)).isEmptyRs(AGGIUDICAZIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
    }

    public SchedaState SOTTOSOGLIA_mult(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
        return (new AccessiDB(conn, logger)).isEmptyRs(AGGIUDICAZIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
    }
    
    public SchedaState ESCLUSO_mult(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
        return (new AccessiDB(conn, logger)).isEmptyRs(AGGIUDICAZIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
    }
        
    public SchedaState ADESIONE_mult(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
       return (new AccessiDB(conn, logger)).isEmptyRs(AGGIUDICAZIONI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
   }
    
    public SchedaState FASE_INIZIALE_mult(Long id, Timestamp data) throws SQLException{
        return (new AccessiDB(conn, logger)).isEmptyRs(INIZIO_LAVORI.TABLE_NAME,INIZIO_LAVORI.ID_INIZIO, id, data, INIZIO_LAVORI.ID_INIZIO, INIZIO_LAVORI.DATA_INIZIO_INIZIO);
    }
    
    public SchedaState STIPULA_mult(Long id, Timestamp data)throws SQLException{
       return (new AccessiDB(conn, logger)).isEmptyRs(STIPULA.TABLE_NAME,STIPULA.ID_STIPULA, id, data, STIPULA.ID_STIPULA, STIPULA.DATA_INIZIO_STIPULA);
   }

    public SchedaState FINE_LAVORI_mult(Long id, Timestamp data)throws SQLException{
        return (new AccessiDB(conn, logger)).isEmptyRs(FINE_LAVORI.TABLE_NAME,FINE_LAVORI.ID_ULTIM, id, data, FINE_LAVORI.ID_ULTIM, FINE_LAVORI.DATA_INIZIO_ULTIM);
    }
    
    public SchedaState COLLAUDO_mult(Long id, Timestamp data)throws SQLException{
        return (new AccessiDB(conn, logger)).isEmptyRs(COLLAUDO.TABLE_NAME,COLLAUDO.ID_COLLAUDO, id, data, COLLAUDO.ID_COLLAUDO, COLLAUDO.DATA_INIZIO_COLL);
    }

	/***
	 * metodi per il controllo della singola scheda multipla, utilizzano gli identificativi della scheda 
	 * sono usati dal WorkFlowController MASSLOADER
	 */
	public SchedaState STATO_AVANZAMENTO_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(STATI_AVANZ.TABLE_NAME,STATI_AVANZ.ID_AVANZAMENTO, id, dataInizio, STATI_AVANZ.ID_AVANZAMENTO, null);
	}

	public SchedaState IPOTESI_RECESSO_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(R129.TABLE_NAME,R129.ID_RECORD, id, dataInizio, R129.ID_RECORD, null);
	}
	
	public SchedaState ACCORDO_BONARIO_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(ACCORDI.TABLE_NAME,ACCORDI.ID_ACCORDO, id, dataInizio, ACCORDI.ID_ACCORDO, null);
	}
	
	public SchedaState SOSPENSIONE_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(SOSPENSIONI.TABLE_NAME,SOSPENSIONI.ID_SOSPENSIONE, id, dataInizio, SOSPENSIONI.ID_SOSPENSIONE, null);
	}
	
	public SchedaState SUBAPPALTO_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(SUBAPPALTI.TABLE_NAME,SUBAPPALTI.ID_RECORD, id, dataInizio, SUBAPPALTI.ID_RECORD, null);
	}
	
	public SchedaState VARIANTE_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).isEmptyRs(VARIANTI.TABLE_NAME,VARIANTI.ID_VARIANTE, id, dataInizio, VARIANTI.ID_VARIANTE, null);
	}

	
	/***
	 * metodi per il controllo della richiesta di cancellazione 
	 */
	public String DATI_COMUNI_del(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(INFO_AGGIUDICAZIONI.TABLE_NAME, id, dataInizio, INFO_AGGIUDICAZIONI.ID_INFO, INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO);
	}
	
	public String AGGIUDICAZIONE_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(AGGIUDICAZIONI.TABLE_NAME,idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
	}
	public String SOTTOSOGLIA_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(AGGIUDICAZIONI.TABLE_NAME,idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
	}
	public String ESCLUSO_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(AGGIUDICAZIONI.TABLE_NAME,idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
	}	

	public String FASE_INIZIALE_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
			return (new AccessiDB(conn, logger)).checkRichDelete(INIZIO_LAVORI.TABLE_NAME, idAggiudicazione, dataInizioAggiudicazione, 
																		INIZIO_LAVORI.ID_INIZIO, INIZIO_LAVORI.DATA_INIZIO_INIZIO);
	}
	
	public String FINE_LAVORI_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDelete(FINE_LAVORI.TABLE_NAME, idAggiudicazione, dataInizioAggiudicazione, 
																		FINE_LAVORI.ID_ULTIM, FINE_LAVORI.DATA_FINE_ULTIM);
	}
	
	public String COLLAUDO_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDelete(COLLAUDO.TABLE_NAME, idAggiudicazione, dataInizioAggiudicazione, 
																		COLLAUDO.ID_COLLAUDO, COLLAUDO.DATA_INIZIO_COLL);
	}
	
	public String STATO_AVANZAMENTO_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDelete(STATI_AVANZ.TABLE_NAME, idAggiudicazione, dataInizioAggiudicazione, 
																		STATI_AVANZ.ID_AVANZAMENTO, STATI_AVANZ.DATA_INIZIO_AVANZAMENTO);
	}
	
	public String IPOTESI_RECESSO_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDelete(R129.TABLE_NAME, idAggiudicazione, dataInizioAggiudicazione, R129.ID_RECORD, R129.DATA_INIZIO);
	}
	
	public String ACCORDO_BONARIO_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDelete(ACCORDI.TABLE_NAME, idAggiudicazione, dataInizioAggiudicazione, ACCORDI.ID_ACCORDO, ACCORDI.DATA_ACCORDO);
	}
	
	public String SOSPENSIONE_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDelete(SOSPENSIONI.TABLE_NAME, idAggiudicazione, dataInizioAggiudicazione, SOSPENSIONI.ID_SOSPENSIONE, SOSPENSIONI.DATA_INIZIO_SOSP);
	}
	
	public String SUBAPPALTO_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDelete(SUBAPPALTI.TABLE_NAME, idAggiudicazione, dataInizioAggiudicazione, SUBAPPALTI.ID_RECORD, SUBAPPALTI.DATA_INIZIO_RECORD);
	}
	
	public String VARIANTE_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDelete(VARIANTI.TABLE_NAME,idAggiudicazione, dataInizioAggiudicazione, VARIANTI.ID_VARIANTE, VARIANTI.DATA_INIZIO_VAR);
	}
	
	public String STIPULA_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDelete(STIPULA.TABLE_NAME, idAggiudicazione, dataInizioAggiudicazione, 
				STIPULA.ID_STIPULA, STIPULA.DATA_INIZIO_STIPULA);
    }
	public String ADESIONE_del(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(AGGIUDICAZIONI.TABLE_NAME,idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
	}

    /***
     * metodi per il controllo della richiesta di cancellazione, wrappati come le multiple per usare id scheda
     */
    public String DATI_COMUNI_del_mult(Long id, Timestamp dataInizio)throws SQLException{
        return (new AccessiDB(conn, logger)).checkRichDeleteSingola(INFO_AGGIUDICAZIONI.TABLE_NAME, id, dataInizio, INFO_AGGIUDICAZIONI.ID_INFO, INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO);
    }
    
    public String AGGIUDICAZIONE_del_mult(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
        return (new AccessiDB(conn, logger)).checkRichDeleteSingola(AGGIUDICAZIONI.TABLE_NAME,idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
    }
    public String SOTTOSOGLIA_del_mult(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
        return (new AccessiDB(conn, logger)).checkRichDeleteSingola(AGGIUDICAZIONI.TABLE_NAME,idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
    }
    public String ESCLUSO_del_mult(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
        return (new AccessiDB(conn, logger)).checkRichDeleteSingola(AGGIUDICAZIONI.TABLE_NAME,idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
    }   

    public String ADESIONE_del_mult(Long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
       return (new AccessiDB(conn, logger)).checkRichDeleteSingola(AGGIUDICAZIONI.TABLE_NAME,idAggiudicazione, dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
   }

    public String FASE_INIZIALE_del_mult(Long id, Timestamp data) throws SQLException{
            return (new AccessiDB(conn, logger)).checkRichDelete(INIZIO_LAVORI.TABLE_NAME, id, data, 
                                                                        INIZIO_LAVORI.ID_INIZIO, INIZIO_LAVORI.DATA_INIZIO_INIZIO);
    }

    public String STIPULA_del_mult(Long id, Timestamp data) throws SQLException{
       return (new AccessiDB(conn, logger)).checkRichDelete(STIPULA.TABLE_NAME, id, data, 
               STIPULA.ID_STIPULA, STIPULA.DATA_INIZIO_STIPULA);
   }

    public String FINE_LAVORI_del_mult(Long id, Timestamp data)throws SQLException{
        return (new AccessiDB(conn, logger)).checkRichDelete(FINE_LAVORI.TABLE_NAME, id, data, 
                                                                        FINE_LAVORI.ID_ULTIM, FINE_LAVORI.DATA_FINE_ULTIM);
    }
    
    public String COLLAUDO_del_mult(Long id, Timestamp data)throws SQLException{
        return (new AccessiDB(conn, logger)).checkRichDelete(COLLAUDO.TABLE_NAME, id, data, 
                                                                        COLLAUDO.ID_COLLAUDO, COLLAUDO.DATA_INIZIO_COLL);
    }    
    
	/***
	 * metodi per il controllo della singola scheda multipla, utilizzano gli identificativi della scheda 
	 * sono usati dal WorkFlowController MASSLOADER
	 */
	public String STATO_AVANZAMENTO_del_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(STATI_AVANZ.TABLE_NAME, id, dataInizio, 
																		STATI_AVANZ.ID_AVANZAMENTO, STATI_AVANZ.DATA_INIZIO_AVANZAMENTO);
	}
	public String IPOTESI_RECESSO_del_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(R129.TABLE_NAME, id, dataInizio, R129.ID_RECORD, R129.DATA_INIZIO);
	}
	
	public String ACCORDO_BONARIO_del_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(ACCORDI.TABLE_NAME, id, dataInizio, ACCORDI.ID_ACCORDO, ACCORDI.DATA_ACCORDO);
	}
	
	public String SOSPENSIONE_del_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(SOSPENSIONI.TABLE_NAME, id, dataInizio, SOSPENSIONI.ID_SOSPENSIONE, SOSPENSIONI.DATA_INIZIO_SOSP);
	}
	
	public String SUBAPPALTO_del_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(SUBAPPALTI.TABLE_NAME, id, dataInizio, SUBAPPALTI.ID_RECORD, SUBAPPALTI.DATA_INIZIO_RECORD);
	}
	
	public String VARIANTE_del_mult(Long id, Timestamp dataInizio)throws SQLException{
		return (new AccessiDB(conn, logger)).checkRichDeleteSingola(VARIANTI.TABLE_NAME,id, dataInizio, VARIANTI.ID_VARIANTE, VARIANTI.DATA_INIZIO_VAR);
	}
}
