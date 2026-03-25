package it.avcp.simog.managers.variazioneSA;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadConclusione;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.MOTIVI_INTERRUZIONE;
import it.avlp.simog.db.generated.MOTIVI_RISOLUZIONE;
import it.avlp.simog.db.generated.MOTIVI_VARIAZIONE_SA;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Classe per la gestione dei dati dele conclusioni
 *
 */
public class VariazioneSAManager extends AccessiDB {
	
	public static String CLAZZ = "ConclusioniManager";
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public VariazioneSAManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	/***************************************************************************************************
	 * Carica i motivi di variazione della stazione appaltante
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadMotiviVariazioneSA()throws ActionException{
		try{
			return getTipologica(MOTIVI_VARIAZIONE_SA.TABLE_NAME, MOTIVI_VARIAZIONE_SA.ID_MOTIVO_VAR, MOTIVI_VARIAZIONE_SA.DESCRIZIONE, MOTIVI_VARIAZIONE_SA.DATA_FINE_VALIDITA,getNow());

		}catch(SQLException e){
			throw new ActionException(e);
		}
	}

}