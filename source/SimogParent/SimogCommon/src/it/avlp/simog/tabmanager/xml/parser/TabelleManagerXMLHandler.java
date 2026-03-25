package it.avlp.simog.tabmanager.xml.parser;


import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.APPALTI_PER_CATEGORIA;
import it.avlp.simog.db.generated.ART_ESCLUSIONE;
import it.avlp.simog.db.generated.ART_ESTREMA_URGENZA_SOMMA_URGENZA;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.CATEGORIA_SA;
import it.avlp.simog.db.generated.CLASSI_IMPORTO;
import it.avlp.simog.db.generated.CODICI_ISTAT;
import it.avlp.simog.db.generated.CODICI_NUTS;
import it.avlp.simog.db.generated.CONDIZIONI;
import it.avlp.simog.db.generated.CONTRAENTE_REGIONE;
import it.avlp.simog.db.generated.CPVEU;
import it.avlp.simog.db.generated.DELEGA_DATI_SIMOG;
import it.avlp.simog.db.generated.DETTAGLIO_REQUISITO;
import it.avlp.simog.db.generated.DISPOSTO_NORMATIVO;
import it.avlp.simog.db.generated.DOCUMENTO_REQUISITO;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.EAGG_MOTIVI;
import it.avlp.simog.db.generated.ESATTORECANALEPAGAMENTO;
import it.avlp.simog.db.generated.ESATTORESTATOPAGAMENTO;
import it.avlp.simog.db.generated.ESATTORETIPOUTENZA;
import it.avlp.simog.db.generated.FUNZIONI_DELEGATE;
import it.avlp.simog.db.generated.IMPORTI;
import it.avlp.simog.db.generated.INDICE_DISPERSIONE;
import it.avlp.simog.db.generated.MODALITA_GARA;
import it.avlp.simog.db.generated.MODALITA_INDIZIONE_ALLEGATO_IX;
import it.avlp.simog.db.generated.MODI_REALIZZAZIONE;
import it.avlp.simog.db.generated.MODO_INDIZIONE;
import it.avlp.simog.db.generated.MOTIVI_CANCELLAZIONE;
import it.avlp.simog.db.generated.MOTIVI_INTERRUZIONE;
import it.avlp.simog.db.generated.MOTIVI_RISOLUZIONE;
import it.avlp.simog.db.generated.MOTIVI_SOSPENSIONE;
import it.avlp.simog.db.generated.MOTIVI_VARIANTE;
import it.avlp.simog.db.generated.MOTIVI_VARIAZIONE_CO;
import it.avlp.simog.db.generated.MOTIVI_VARIAZIONE_SA;
import it.avlp.simog.db.generated.MOTIVO_COLLEGAMENTO;
import it.avlp.simog.db.generated.MOTIVO_DEROGA;
import it.avlp.simog.db.generated.ORGANI_COSTITUZIONALI;
import it.avlp.simog.db.generated.PRESTAZIONI_PER_CATEGORIA;
import it.avlp.simog.db.generated.REGIONE_PROVINCIA;
import it.avlp.simog.db.generated.REQUISITO;
import it.avlp.simog.db.generated.RUOLI_RESPONSABILE;
import it.avlp.simog.db.generated.RUOLI_RESP_SCHEDA;
import it.avlp.simog.db.generated.SCELTA_CONTRAENTE;
import it.avlp.simog.db.generated.STATI_ESTERI;
import it.avlp.simog.db.generated.STRUMENTI_SVOLGIMENTO_PROCEDURE;
import it.avlp.simog.db.generated.TIPI_APPALTI;
import it.avlp.simog.db.generated.TIPI_CATEGORIA;
import it.avlp.simog.db.generated.TIPI_PRESTAZIONI;
import it.avlp.simog.db.generated.TIPOLOGIA_PROCEDURA;
import it.avlp.simog.db.generated.TIPOLOGIA_SA;
import it.avlp.simog.db.generated.TIPO_AGGIUDICATARIO;
import it.avlp.simog.db.generated.TIPO_DOCUMENTO_REQ;
import it.avlp.simog.db.generated.TIPO_FINANZIAMENTO;
import it.avlp.simog.db.generated.TIPO_FONTE_DOCUMENTO;
import it.avlp.simog.db.generated.TIPO_REQUISITO;
import it.avlp.simog.db.generated.TIPO_STRUMENTO;
import it.avlp.simog.db.generated.TIPO_UNITA_MISURA;
import it.avlp.simog.db.generated.TIPO_USO;
import it.avlp.simog.db.generated.VARIANTE_CATEGORIA;
import it.avlp.simog.tabmanager.TabManager;

import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/***
 * esempio file di caricamento 
 * 

<?xml version="1.0" encoding="UTF-8"?>
<nometabella>								nome tabella reale
	<record key="valorecampochiave">  		se multiple, separati tramite puntovirgola
		<operazione>I,C,M</operazione>		I-nserimento M-odifica C-ancellazione
		<valori>                            la sezione no va indicata per la cancellazione
			<nome_campo></nome_campo>		nome campo
			<valore></valore>				valore da inserire/modificare
		</valori>
	</record>
</nometabella>

*/

public class TabelleManagerXMLHandler extends DefaultHandler {
	
	private static Hashtable chiaveTabella = new Hashtable();
	
	static {
		chiaveTabella.put ( ESATTORECANALEPAGAMENTO.TABLE_NAME, ESATTORECANALEPAGAMENTO.ID_CANALE_PAGAMENTO );
		chiaveTabella.put ( ESATTORESTATOPAGAMENTO.TABLE_NAME, ESATTORESTATOPAGAMENTO.ID_STATO_PAGAMENTO );
		chiaveTabella.put ( ESATTORETIPOUTENZA.TABLE_NAME, ESATTORETIPOUTENZA.ID_TIPO_UTENZA );
		chiaveTabella.put ( CATEGORIA.TABLE_NAME, CATEGORIA.ID_CATEGORIA );
		chiaveTabella.put ( IMPORTI.TABLE_NAME, IMPORTI.ID_IMPORTO );
		// PP chiaveTabella.put ( LOTTO.TABLE_NAME, LOTTO.ID_LOTTO );
		// PP chiaveTabella.put ( GARA.TABLE_NAME, GARA.ID_GARA );
		chiaveTabella.put ( CPVEU.TABLE_NAME, CPVEU.ID_DIV + ";" + CPVEU.ID_GRP + ";" + CPVEU.ID_CLS + ";" + CPVEU.ID_CTG + ";" + CPVEU.ID_VOX + ";" + CPVEU.VERSIONE);
		chiaveTabella.put ( SCELTA_CONTRAENTE.TABLE_NAME, MOTIVO_COLLEGAMENTO.ID_MOTIVO );
		//2846
		chiaveTabella.put ( MOTIVO_COLLEGAMENTO.TABLE_NAME, SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE );
		//2846
		// PP chiaveTabella.put ( TIPOLOGIA.TABLE_NAME, TIPOLOGIA.ID_TIPOLOGIA );
		chiaveTabella.put ( TIPI_CATEGORIA.TABLE_NAME, TIPI_CATEGORIA.ID_TIPO_CATEGORIA );
		chiaveTabella.put ( TIPI_APPALTI.TABLE_NAME, TIPI_APPALTI.ID_APPALTO );
		chiaveTabella.put ( TIPI_PRESTAZIONI.TABLE_NAME, TIPI_PRESTAZIONI.ID_PRESTAZIONE );
		chiaveTabella.put ( CLASSI_IMPORTO.TABLE_NAME, CLASSI_IMPORTO.ID_CODICE );
		chiaveTabella.put ( CATEGORIA_SA.TABLE_NAME, CATEGORIA_SA.ID_CATEG_SA );
		chiaveTabella.put ( TIPO_AGGIUDICATARIO.TABLE_NAME, TIPO_AGGIUDICATARIO.ID_TIPOAGG );
		chiaveTabella.put ( RUOLI_RESPONSABILE.TABLE_NAME, RUOLI_RESPONSABILE.ID_RUOLO );
		chiaveTabella.put ( CONDIZIONI.TABLE_NAME, CONDIZIONI.ID_CONDIZIONE );
		chiaveTabella.put ( TIPOLOGIA_SA.TABLE_NAME, TIPOLOGIA_SA.ID_TIPOLOGIA_SA );
		chiaveTabella.put ( MODO_INDIZIONE.TABLE_NAME, MODO_INDIZIONE.ID_MODO_GARA );
		chiaveTabella.put ( TIPO_FINANZIAMENTO.TABLE_NAME, TIPO_FINANZIAMENTO.ID_FINANZIAMENTO );
		
		chiaveTabella.put ( APPALTI_PER_CATEGORIA.TABLE_NAME, APPALTI_PER_CATEGORIA.ID_CATEGORIA + ";" 
				+ APPALTI_PER_CATEGORIA.ID_APPALTO + ";" + APPALTI_PER_CATEGORIA.ID_TIPO_CATEGORIA);
		
		chiaveTabella.put ( PRESTAZIONI_PER_CATEGORIA.TABLE_NAME, PRESTAZIONI_PER_CATEGORIA.ID_CATEGORIA + ";"
				+ PRESTAZIONI_PER_CATEGORIA.ID_PRESTAZIONE + ";" + PRESTAZIONI_PER_CATEGORIA.ID_TIPO_CATEGORIA);
		
		chiaveTabella.put ( RUOLI_RESP_SCHEDA.TABLE_NAME, RUOLI_RESP_SCHEDA.ID_SCHEDA + ";" + RUOLI_RESP_SCHEDA.ID_ENTE 
				+ ";" + RUOLI_RESP_SCHEDA.ID_CONTRATTO + ";" + RUOLI_RESP_SCHEDA.ID_RUOLO);
	
		chiaveTabella.put ( REGIONE_PROVINCIA.TABLE_NAME, REGIONE_PROVINCIA.ID_REGIONE + ";" 
				+ REGIONE_PROVINCIA.ID_PROVINCIA);
		
		chiaveTabella.put ( CODICI_ISTAT.TABLE_NAME, CODICI_ISTAT.ID_PROVINCIA  + ";" + CODICI_ISTAT.ID_COMUNE);
		chiaveTabella.put ( CODICI_NUTS.TABLE_NAME, CODICI_NUTS.ID_NUTS);
		
		//adds 26feb.2008 vl
		chiaveTabella.put ( MODALITA_GARA.TABLE_NAME, MODALITA_GARA.ID_MODALITA_GARA);
		chiaveTabella.put ( MOTIVI_INTERRUZIONE.TABLE_NAME, MOTIVI_INTERRUZIONE.ID_MOTIVO_INTERR);
		chiaveTabella.put ( MOTIVI_RISOLUZIONE.TABLE_NAME, MOTIVI_RISOLUZIONE.ID_MOTIVO_RISOL);
		chiaveTabella.put ( MOTIVI_SOSPENSIONE.TABLE_NAME, MOTIVI_SOSPENSIONE.ID_MOTIVO_SOSP);
		chiaveTabella.put ( MOTIVI_VARIANTE.TABLE_NAME, MOTIVI_VARIANTE.ID_MOTIVO_VAR);
		chiaveTabella.put ( TIPI_APPALTI.TABLE_NAME, TIPI_APPALTI.ID_APPALTO);
		chiaveTabella.put ( TIPI_CATEGORIA.TABLE_NAME, TIPI_CATEGORIA.ID_TIPO_CATEGORIA);
		chiaveTabella.put ( TIPO_STRUMENTO.TABLE_NAME, TIPO_STRUMENTO.ID_STRUMENTO);
		chiaveTabella.put ( VARIANTE_CATEGORIA.TABLE_NAME, VARIANTE_CATEGORIA.ID_CONTRATTO + ";" 				
				+ VARIANTE_CATEGORIA.ID_MOTIVO_VAR);
		
		chiaveTabella.put ( ART_ESCLUSIONE.TABLE_NAME, ART_ESCLUSIONE.ID_ESCLUSIONE);
		chiaveTabella.put ( MODI_REALIZZAZIONE.TABLE_NAME, MODI_REALIZZAZIONE.ID_MODO_REAL);
		chiaveTabella.put ( MOTIVI_CANCELLAZIONE.TABLE_NAME, MOTIVI_CANCELLAZIONE.ID_MOTIVO_CANC);
		chiaveTabella.put ( MOTIVO_DEROGA.TABLE_NAME, MOTIVO_DEROGA.ID_MOTIVO);

		//adds 23Set.2008 un
		chiaveTabella.put ( STATI_ESTERI.TABLE_NAME, STATI_ESTERI.ID_STATO);
		chiaveTabella.put ( INDICE_DISPERSIONE.TABLE_NAME, INDICE_DISPERSIONE.ANNO + ";"
				+ INDICE_DISPERSIONE.TIPO_SETTORE + ";" + INDICE_DISPERSIONE.TIPO_CONTRATTO);
		chiaveTabella.put ( TIPOLOGIA_PROCEDURA.TABLE_NAME, TIPOLOGIA_PROCEDURA.ID_TIPOLOGIA_PROCEDURA);
		chiaveTabella.put ( DELEGA_DATI_SIMOG.TABLE_NAME, DELEGA_DATI_SIMOG.ID_OSSERVATORIO);		
		chiaveTabella.put ( MOTIVI_VARIAZIONE_SA.TABLE_NAME, MOTIVI_VARIAZIONE_SA.ID_MOTIVO_VAR);		
		chiaveTabella.put ( ORGANI_COSTITUZIONALI.TABLE_NAME, ORGANI_COSTITUZIONALI.CODICE );
		
		chiaveTabella.put ( MOTIVI_VARIAZIONE_CO.TABLE_NAME, MOTIVI_VARIAZIONE_CO.ID_MOTIVO_VAR_CO );

		// is3025_REQUISITIActive
	    chiaveTabella.put ( TIPO_USO.TABLE_NAME, TIPO_USO.COD_TIPO_USO );
        chiaveTabella.put ( TIPO_REQUISITO.TABLE_NAME, TIPO_REQUISITO.COD_TIPO_REQUISITO );
	    chiaveTabella.put ( DISPOSTO_NORMATIVO.TABLE_NAME, DISPOSTO_NORMATIVO.COD_DISPOSTO_NORMATIVO );
        chiaveTabella.put ( TIPO_UNITA_MISURA.TABLE_NAME, TIPO_UNITA_MISURA.COD_TIPO_UNITA_MISURA );
        chiaveTabella.put ( TIPO_FONTE_DOCUMENTO.TABLE_NAME, TIPO_FONTE_DOCUMENTO.COD_TIPO_FONTE_DOC );
        chiaveTabella.put ( REQUISITO.TABLE_NAME, REQUISITO.COD_REQUISITO );
        chiaveTabella.put ( TIPO_DOCUMENTO_REQ.TABLE_NAME, TIPO_DOCUMENTO_REQ.COD_TIPO_DOC_REQ );
        chiaveTabella.put ( DETTAGLIO_REQUISITO.TABLE_NAME, DETTAGLIO_REQUISITO.COD_DETT_REQUISITO );
        chiaveTabella.put ( DOCUMENTO_REQUISITO.TABLE_NAME, DOCUMENTO_REQUISITO.COD_DOC_REQUISITO );
        
        // is3028_RFWEBGL00Active
        chiaveTabella.put ( CONTRAENTE_REGIONE.TABLE_NAME, CONTRAENTE_REGIONE.ID_RECORD );

        // is30350_RFWEBGL01Active
        chiaveTabella.put ( EAGG_MOTIVI.TABLE_NAME, EAGG_MOTIVI.COD_MOTIVO);
        chiaveTabella.put ( EAGG_CATEGORIE.TABLE_NAME, EAGG_CATEGORIE.COD_CATEGORIA);
        
        //TICKET ALM #664
        chiaveTabella.put(STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME, STRUMENTI_SVOLGIMENTO_PROCEDURE.ID_SVOLGIMENTO);
        //FINE TICKET ALM #664
        
        //TICKET ALM #3832
        chiaveTabella.put(ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME, ART_ESTREMA_URGENZA_SOMMA_URGENZA.ID_ESTREMA_URGENZA);
        //FINE TICKET ALM #3832
        
        //TICKET ALM #3834
        chiaveTabella.put(MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME, MODALITA_INDIZIONE_ALLEGATO_IX.ID_ALLEGATO_IX);
        //FINE TICKET ALM #3834
        
      //TICKET ALM - 3.04.3
        chiaveTabella.put(MOTIVO_COLLEGAMENTO.TABLE_NAME, MOTIVO_COLLEGAMENTO.ID_MOTIVO);
        
       //TICKET ALM #659 - 3.04.4
        chiaveTabella.put(FUNZIONI_DELEGATE.TABLE_NAME, FUNZIONI_DELEGATE.ID_F_DELEGATE);
	}

	private Logger logger = null;
	private TabellaLoaderObserver observer = null;
			
	private String tagCorrente = null;
	
	private boolean tagPrevisto = false;

	private boolean stessoTag = false;
	
	private String nomeTabella = null;
	
	private final static Vector tagDiInteresse = new Vector();
	private TableBeanRow currentRow = null;

	public final static String RECORD = "record";
	public final static String NOME_CAMPO = "nome_campo";	
	public final static String VALORE = "valore";
	public final static String OPERAZIONE = "operazione";
	public final static String VALORI = "valori";
	
	public final static String VALORE_CAMPO_CHIAVE = "valorecampochiave";
	
	private int recordCounter = 0;	
	
	private String operazioneValue = null;
	private String currentFieldName = null;
	private String currentFieldValue = null;
	private String idValue = null;

	static {
		tagDiInteresse.add( RECORD );
		tagDiInteresse.add( NOME_CAMPO );
		tagDiInteresse.add( VALORE );
		tagDiInteresse.add( OPERAZIONE );
		tagDiInteresse.add( VALORI );
		}

	public TabelleManagerXMLHandler( Logger logger, TabellaLoaderObserver currentObserver ) {
		this.logger  = logger;
		this.observer = currentObserver;
	}

    /**
     * Gestisce l'apertura dei TAG
     */
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) throws SAXException {

    	logger.debug ( "Analisi in corso tag [" + qName + "]" );
    	
    	tagCorrente = qName;
    	tagPrevisto = tagDiInteresse.contains(tagCorrente);
    	stessoTag = false;
    	
    	/* 
    	 * In base al formato convenuto
    	 * il primo tag ha la denominazione della tabella
    	 * di cui effettuare l'aggiornamento
    	 */    	
    	if ( nomeTabella == null ) {
    		nomeTabella = qName;
    		observer.setTableName ( nomeTabella );
    		logger.debug ( "Tabella corrente [" + nomeTabella + "]" );
    	}

    	if ( RECORD.equalsIgnoreCase( tagCorrente ) ) {
    		parseAttributes ( attributes );
    	}
	}

    
    /**
     * Analizza il contenuto del tag - ATTRIBUTI
     */
    public void parseAttributes(Attributes attrs) throws SAXException {

    	if (RECORD.equalsIgnoreCase(tagCorrente)) {
    		recordCounter++;
    		logger.debug ( "Ricevuto campo chiave valore [" + attrs.getValue(0) + "]" );
    		idValue = attrs.getValue(0);
    	}
    }

    /**
     * Analizza il contenuto del tag - TESTO
     */
	public void characters(char[] ch, int start, int length) throws SAXException {
    	
    	String currentValue = null;
    	if ( tagPrevisto ) {
    		if (ch[start] != '\n') {
    			currentValue = new String(ch, start, length).trim();
    			if ( currentValue != null ) {
    				logger.debug("Contenuto tag [" + tagCorrente + "] \t Valore [" + currentValue + "]");

    				if ( NOME_CAMPO.equalsIgnoreCase(tagCorrente) ) {
    					if ( currentValue.trim().length() > 0 ) {
    						if ( stessoTag )
    							currentFieldName += currentValue;
    						else
    							currentFieldName = currentValue;
	    					logger.debug ( "Campo corrente [" + currentFieldName + "]" );
    					}
    				} else if ( VALORE.equalsIgnoreCase(tagCorrente) ) {
    					logger.debug( "Campo corrente [" + currentFieldName + "] valore [" + currentValue + "]");
    					if ( currentFieldName != null ) {
    						logger.debug ( "Assegnato valore[" + currentValue + "] al campo (" + currentFieldName + ")" );
    						if ( stessoTag )
    						  currentFieldValue += currentValue;
    						else
    						  currentFieldValue = currentValue;
    						/* ********************************* */
    						// PP l'assegnazione � fatta sull'endelement
    						// PP currentRow.addFieldValue(currentFieldName, currentFieldValue);
    						/* ********************************* */
    					} 
    				} else if ( OPERAZIONE.equalsIgnoreCase(tagCorrente) ) {
						logger.debug ( "Assegnato valore operazione [" + currentValue + "]" );
						if ( stessoTag )
							operazioneValue += currentValue;
						else
							operazioneValue = currentValue;

						TableBean table = observer.getTableByOperazione(operazioneValue);

						if(!getKeyNameByTableName(nomeTabella).contains(";"))
							table.setKeyName(getKeyNameByTableName(nomeTabella));
							
						currentRow = new TableBeanRow ( table );
					} 
    			}
    		}
    	}
    	stessoTag = true;
    	//tagCorrente = null;
    }

	/**
	 * Gestisce la chiusura dei TAG
	 * In particolare deve completare la gestione della TUPLA corrente
	 */

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ( qName.equalsIgnoreCase(RECORD) ) {
			String keyName = getKeyNameByTableName(nomeTabella);
			String[] keyList = null;
			String[] values = null;
			if(keyName.contains(";")){
				keyList = keyName.split(";");
				values = idValue.split(";");
				for(int i = 0; i < keyList.length; i++){
					currentRow.addFieldValue(TabManager.KEY_PREFIX + keyList[i], values[i] );
					logger.debug("Aggiunta riga:" + keyList[i] + ":" + values[i]);
				}
			}
			else{
				currentRow.addFieldValue(TabManager.KEY_PREFIX  + keyName , idValue  );
				//String currentKeyName = getKeyNameByTableName ( nomeTabella );
				//observer.addRow( currentKeyName, idValue, currentFieldName, currentFieldValue, OPERAZIONE, operazioneValue );
				logger.debug("Aggiunta tupla [" + getKeyNameByTableName(nomeTabella) + "][" + idValue + "] (" + currentRow + ") operazione[" + operazioneValue + "]");
			}
		}else if ( qName.equalsIgnoreCase(VALORE) ) {
			// PP spostata assegnazione alla riga perc� mozzava i valori
			currentRow.addFieldValue(currentFieldName, currentFieldValue);
		}
	}

	public static String getKeyNameByTableName(Object tableName ) {
		String keyFieldName = ( String ) chiaveTabella.get ( tableName );
//		logger.debug ( "CAMPO CHIAVE [" + keyFieldName + "] per tabella[" + tableName + "]" );
		return keyFieldName;
	}
	
	  public static Hashtable getMappaTabelle( ) {
	      return chiaveTabella;
	   }
}