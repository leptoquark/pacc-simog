package it.avlp.simog.util;

import it.avlp.simog.common.action.BaseSharedAction;
import it.avlp.simog.common.action.BaseSharedAction.AVCPassStatus;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.AFFIDAMENTI_RISERVATI;
import it.avlp.simog.db.generated.ART_ESCLUSIONE;
import it.avlp.simog.db.generated.ART_ESTREMA_URGENZA_SOMMA_URGENZA;
import it.avlp.simog.db.generated.ART_REGIMI_PARTICOLARI_DI_APPALTO;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.CATEGORIA_SA;
import it.avlp.simog.db.generated.CLASSI_IMPORTO;
import it.avlp.simog.db.generated.CONDIZIONI;
import it.avlp.simog.db.generated.DETTAGLIO_REQUISITO;
import it.avlp.simog.db.generated.DISPOSTO_NORMATIVO;
import it.avlp.simog.db.generated.DOCUMENTO_REQUISITO;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.EAGG_MOTIVI;
import it.avlp.simog.db.generated.FUNZIONI_DELEGATE;
import it.avlp.simog.db.generated.MODALITA_GARA;
import it.avlp.simog.db.generated.MODALITA_INDIZIONE_ALLEGATO_IX;
import it.avlp.simog.db.generated.MODI_REALIZZAZIONE;
import it.avlp.simog.db.generated.MODI_RIAGGIUD;
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
import it.avlp.simog.db.generated.REQUISITO;
import it.avlp.simog.db.generated.RUOLI_RESPONSABILE;
import it.avlp.simog.db.generated.SCELTA_CONTRAENTE;
import it.avlp.simog.db.generated.STATI_ESTERI;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.STRUMENTI_SVOLGIMENTO_PROCEDURE;
import it.avlp.simog.db.generated.TIPI_APPALTI;
import it.avlp.simog.db.generated.TIPI_PRESTAZIONI;
import it.avlp.simog.db.generated.TIPOLOGIA;
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
import it.avlp.simog.garamanager.RequisitiGLManager;
import it.avlp.simog.tabmanager.TabManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.generationjava.io.WritingException;
import com.generationjava.io.xml.XmlWriter;

public class ListTypesBuilder {
	
    private static int OP_NESSUNA = 0;
	private static int OP_CONTINUA = 1;
	private static int OP_CHIUDI = 2;
	
	public static void write(Writer wrt, SimpleDbManager dbm, boolean allCodes, SimogProperties configuration)
			throws WritingException, IOException {
		XmlWriter out = new XmlWriter(wrt);

		wrt.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		out.writeEntity("xsd:schema");
		out.writeAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
		out.writeAttribute("xmlns:simog", "xmlbeans.massload.simog.avlp.it");

		out.writeAttribute("targetNamespace", "xmlbeans.massload.simog.avlp.it");
		out.writeAttribute("elementFormDefault", "unqualified");
		out.writeAttribute("attributeFormDefault", "qualified");

		// ModoGaraType

		scriviSezione(out, dbm, "ModoGaraType", MODALITA_GARA.TABLE_NAME, MODALITA_GARA.DATA_FINE_VALIDITA,
				MODALITA_GARA.ID_MODALITA_GARA, MODALITA_GARA.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null,
				null);

		// RuoloAggiudicatarioType
		String[] keys = { "1", "2" };
		String[] val = { "Mandataria", "Mandante" };

		scriviSezioneNoDB(out, "RuoloAggiudicatarioType", keys, val);

		// TipoAggiudicatarioType

		scriviSezione(out, dbm, "TipoAggiudicatarioType", TIPO_AGGIUDICATARIO.TABLE_NAME,
				TIPO_AGGIUDICATARIO.DATA_FINE_VALIDITA, TIPO_AGGIUDICATARIO.ID_TIPOAGG, TIPO_AGGIUDICATARIO.DESCRIZIONE,
				null, allCodes, null, null, OP_NESSUNA, null, null);

		// RuoloResponsabileType

		scriviSezione(out, dbm, "RuoloResponsabileType", RUOLI_RESPONSABILE.TABLE_NAME,
				RUOLI_RESPONSABILE.DATA_FINE_VALIDITA, RUOLI_RESPONSABILE.ID_RUOLO, RUOLI_RESPONSABILE.DESCRIZIONE,
				null, allCodes, null, null, OP_NESSUNA, null, null);

      if(!SimogFlags.is3028_RFWEBGL00Active()){
		// SceltaContraenteType

			scriviSezione(out, dbm, "SceltaContraenteType", SCELTA_CONTRAENTE.TABLE_NAME,
					SCELTA_CONTRAENTE.DATA_FINE_VALIDITA, SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE,
				SCELTA_CONTRAENTE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		} else {
           String listaCampi = "id_scelta_contraente, descrizione,isnull((select distinct '(valida solo per codice regione: '+id_osservatorio+')' " 
                 + "from contraente_regione where id_scelta_avcp = id_scelta_contraente"
          
					+ " and id_scelta_avcp <> id_equivalente),'') as desc2" + ","
					+ SCELTA_CONTRAENTE.DATA_FINE_VALIDITA;
			scriviSezione(out, dbm, "SceltaContraenteType", SCELTA_CONTRAENTE.TABLE_NAME,
					SCELTA_CONTRAENTE.DATA_FINE_VALIDITA, SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE,
					SCELTA_CONTRAENTE.DESCRIZIONE, null, allCodes, "desc2", null, OP_NESSUNA, "1=1", listaCampi);
        }

		// 2846
		if (!SimogFlags.is3028_RFWEBGL00Active()) {
			// MotivoCollegamentoType
			scriviSezione(out, dbm, "MotivoCollegamentoType", MOTIVO_COLLEGAMENTO.TABLE_NAME,
					MOTIVO_COLLEGAMENTO.DATA_FINE_VALIDITA, MOTIVO_COLLEGAMENTO.ID_MOTIVO,
					MOTIVO_COLLEGAMENTO.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
		} else {
			String listaCampi = "id_scelta_contraente, descrizione," + MOTIVO_COLLEGAMENTO.DATA_FINE_VALIDITA;
			scriviSezione(out, dbm, "MotivoCollegamentoType", MOTIVO_COLLEGAMENTO.TABLE_NAME,
					MOTIVO_COLLEGAMENTO.DATA_FINE_VALIDITA, MOTIVO_COLLEGAMENTO.ID_MOTIVO,
					MOTIVO_COLLEGAMENTO.DESCRIZIONE, null, allCodes, "desc2", null, OP_NESSUNA, "1=1", listaCampi);
		}
		// 2846
		
		// CategSAType

		scriviSezione(out, dbm, "CategSAType", CATEGORIA_SA.TABLE_NAME, CATEGORIA_SA.DATA_FINE_VALIDITA,
				CATEGORIA_SA.ID_CATEG_SA, CATEGORIA_SA.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// CondizioneAggType

		scriviSezione(out, dbm, "CondizioneAggType", CONDIZIONI.TABLE_NAME, CONDIZIONI.DATA_FINE_VALIDITA,
				CONDIZIONI.ID_CONDIZIONE, CONDIZIONI.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// CategoriaType

		scriviSezione(out, dbm, "CategoriaType", CATEGORIA.TABLE_NAME, CATEGORIA.DATA_FINE_VALIDITA,
				CATEGORIA.ID_CATEGORIA, CATEGORIA.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// ClasseImportoType
		if(SimogFlags.is3028_NRFDBDT04Active()){
 
			String listaCampi = CLASSI_IMPORTO.ID_CODICE + "," + CLASSI_IMPORTO.TITOLO + "+' ('+convert(varchar,"
					+ CLASSI_IMPORTO.IMPORTO_DA + ")+' - '+convert(varchar," + CLASSI_IMPORTO.IMPORTO_A + ")+')' "
					+ " AS " + CLASSI_IMPORTO.IMPORTO_DA + "," + SCELTA_CONTRAENTE.DATA_FINE_VALIDITA;
			scriviSezione(out, dbm, "ClasseImportoType", CLASSI_IMPORTO.TABLE_NAME, CLASSI_IMPORTO.DATA_FINE_VALIDITA,
					CLASSI_IMPORTO.ID_CODICE, CLASSI_IMPORTO.IMPORTO_DA, null, allCodes, null, null, OP_NESSUNA, "1=1",
					listaCampi);
		} else {
			scriviSezione(out, dbm, "ClasseImportoType", CLASSI_IMPORTO.TABLE_NAME, CLASSI_IMPORTO.DATA_FINE_VALIDITA,
					CLASSI_IMPORTO.ID_CODICE, CLASSI_IMPORTO.IMPORTO_DA, null, allCodes, CLASSI_IMPORTO.IMPORTO_A, null,
					OP_NESSUNA, null, null);
		}

		// TipoPrestazioneType

		scriviSezione(out, dbm, "TipoPrestazioneType", TIPI_PRESTAZIONI.TABLE_NAME, TIPI_PRESTAZIONI.DATA_FINE_VALIDITA,
				TIPI_PRESTAZIONI.ID_PRESTAZIONE, TIPI_PRESTAZIONI.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA,
				null, null);

		// TipoAppaltoType

		scriviSezione(out, dbm, "TipoAppaltoType", TIPI_APPALTI.TABLE_NAME, TIPI_APPALTI.DATA_FINE_VALIDITA,
				TIPI_APPALTI.ID_APPALTO, TIPI_APPALTI.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// TipoFinanziamentoType

		scriviSezione(out, dbm, "TipoFinanziamentoType", TIPO_FINANZIAMENTO.TABLE_NAME,
				TIPO_FINANZIAMENTO.DATA_FINE_VALIDITA, TIPO_FINANZIAMENTO.ID_FINANZIAMENTO,
				TIPO_FINANZIAMENTO.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// Tipologia SA

		scriviSezione(out, dbm, "TipologiaSAType", TIPOLOGIA_SA.TABLE_NAME, TIPOLOGIA_SA.DATA_FINE_VALIDITA,
				TIPOLOGIA_SA.ID_TIPOLOGIA_SA, TIPOLOGIA_SA.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null,
				null);

		// Modo indizione

		scriviSezione(out, dbm, "ModoIndizioneType", MODO_INDIZIONE.TABLE_NAME, MODO_INDIZIONE.DATA_FINE_VALIDITA,
				MODO_INDIZIONE.ID_MODO_GARA, MODO_INDIZIONE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null,
				null);

		// Motivi interruzione

		scriviSezione(out, dbm, "MotivoInterruzioneType", MOTIVI_INTERRUZIONE.TABLE_NAME,
				MOTIVI_INTERRUZIONE.DATA_FINE_VALIDITA, MOTIVI_INTERRUZIONE.ID_MOTIVO_INTERR,
				MOTIVI_INTERRUZIONE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// Motivi deroga
			scriviSezione(out, dbm, "MotivoDerogaType", MOTIVO_DEROGA.TABLE_NAME,
					MOTIVO_DEROGA.DATA_FINE_VALIDITA, MOTIVO_DEROGA.ID_MOTIVO,
					MOTIVO_DEROGA.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
		
		// Motivi risoluzione

		scriviSezione(out, dbm, "MotivoRisoluzioneType", MOTIVI_RISOLUZIONE.TABLE_NAME,
				MOTIVI_RISOLUZIONE.DATA_FINE_VALIDITA, MOTIVI_RISOLUZIONE.ID_MOTIVO_RISOL,
				MOTIVI_RISOLUZIONE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// Motivi sospensione

		scriviSezione(out, dbm, "MotivoSospensioneType", MOTIVI_SOSPENSIONE.TABLE_NAME,
				MOTIVI_SOSPENSIONE.DATA_FINE_VALIDITA, MOTIVI_SOSPENSIONE.ID_MOTIVO_SOSP,
				MOTIVI_SOSPENSIONE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// Motivi variante

		scriviSezione(out, dbm, "MotivoVarianteType", MOTIVI_VARIANTE.TABLE_NAME, MOTIVI_VARIANTE.DATA_FINE_VALIDITA,
				MOTIVI_VARIANTE.ID_MOTIVO_VAR, MOTIVI_VARIANTE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA,
				null, null);

		// Tipo strumento

		scriviSezione(out, dbm, "TipoStrumentoType", TIPO_STRUMENTO.TABLE_NAME, TIPO_STRUMENTO.DATA_FINE_VALIDITA,
				TIPO_STRUMENTO.ID_STRUMENTO, TIPO_STRUMENTO.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null,
				null);

		// Codice Stato

		scriviSezione(out, dbm, "StatoEsteroType", STATI_ESTERI.TABLE_NAME, STATI_ESTERI.DATA_FINE_VALIDITA,
				STATI_ESTERI.ID_STATO, STATI_ESTERI.DESCRIZIONE, "*** VALORE ASSENTE ***", allCodes, null, null,
				OP_NESSUNA, null, null);

		// Modo realizzazione
		scriviSezione(out, dbm, "ModoRealizzazioneType", MODI_REALIZZAZIONE.TABLE_NAME,

				MODI_REALIZZAZIONE.DATA_FINE_VALIDITA, MODI_REALIZZAZIONE.ID_MODO_REAL, MODI_REALIZZAZIONE.DESCRIZIONE,
				null, allCodes, null, null, OP_NESSUNA, null, null);
		
		// Articolo esclusione

		scriviSezione(out, dbm, "ArtEsclusioneType", ART_ESCLUSIONE.TABLE_NAME, ART_ESCLUSIONE.DATA_FINE_VALIDITA,
				ART_ESCLUSIONE.ID_ESCLUSIONE, ART_ESCLUSIONE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null,
				null);

		// Motivi cancellazione
		scriviSezione(out, dbm, "MotiviCancellazioneType", MOTIVI_CANCELLAZIONE.TABLE_NAME,
				MOTIVI_CANCELLAZIONE.DATA_FINE_VALIDITA, MOTIVI_CANCELLAZIONE.ID_MOTIVO_CANC,
				MOTIVI_CANCELLAZIONE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// Tipologia procedura
		scriviSezione(out, dbm, "TipologiaProceduraType", TIPOLOGIA_PROCEDURA.TABLE_NAME,
				TIPOLOGIA_PROCEDURA.DATA_FINE_VALIDITA, TIPOLOGIA_PROCEDURA.ID_TIPOLOGIA_PROCEDURA,
				TIPOLOGIA_PROCEDURA.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// Id Stato Gara

		scriviSezione(out, dbm, "StatoCigType", STATI_SCHEDA.TABLE_NAME, STATI_SCHEDA.DATA_FINE_VALIDITA,
				STATI_SCHEDA.ID_STATO, STATI_SCHEDA.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);

		// Modo riaggiudicazione

		scriviSezione(out, dbm, "ModoRiaggiudType", MODI_RIAGGIUD.TABLE_NAME, MODI_RIAGGIUD.DATA_FINE_VALIDITA,
				MODI_RIAGGIUD.ID_MODO_RIAGGIUD, MODI_RIAGGIUD.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null,
				null);

		// PP B302.2.0
		if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive()){
			// Motivi variazione C.O.
			scriviSezione(out, dbm, "MotiviVariazioneCOType", MOTIVI_VARIAZIONE_CO.TABLE_NAME,
					MOTIVI_VARIAZIONE_CO.DATA_FINE_VALIDITA, MOTIVI_VARIAZIONE_CO.ID_MOTIVO_VAR_CO,
					MOTIVI_VARIAZIONE_CO.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
		}
	
        // PP B302.2.3.3
        if(SimogFlags.is30233_RFMLVS00Active()){
            // Motivi variazione S.A.
            scriviSezione(out, dbm, "MotiviVariazioneSAType", MOTIVI_VARIAZIONE_SA.TABLE_NAME,
                  MOTIVI_VARIAZIONE_SA.DATA_FINE_VALIDITA, MOTIVI_VARIAZIONE_SA.ID_MOTIVO_VAR,
                  MOTIVI_VARIAZIONE_SA.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
        }
        
        // PP B302.5
        if(SimogFlags.is3025_REQUISITIActive()
              && configuration.getDataRequisiti().compareTo(PageHelper.getCurrentDate())<=0){

           // Tipo documento

			scriviSezione(out, dbm, "CodTipoDocType", TIPO_DOCUMENTO_REQ.TABLE_NAME, TIPO_DOCUMENTO_REQ.DATA_INIZIO,
					TIPO_DOCUMENTO_REQ.COD_TIPO_DOC_REQ, TIPO_DOCUMENTO_REQ.CODICE, null, allCodes,
					TIPO_DOCUMENTO_REQ.DESCRIZIONE, TIPO_DOCUMENTO_REQ.DATA_FINE, OP_NESSUNA, null, null);

           // Dettaglio requisito

			scriviSezione(out, dbm, "CodRequisitoType", DETTAGLIO_REQUISITO.TABLE_NAME, DETTAGLIO_REQUISITO.DATA_INIZIO,
					DETTAGLIO_REQUISITO.COD_DETT_REQUISITO, DETTAGLIO_REQUISITO.CODICE, null, allCodes,
					DETTAGLIO_REQUISITO.DESCRIZIONE, DETTAGLIO_REQUISITO.DATA_FINE, OP_NESSUNA, null, null);

           // StatoAVCPASSType
           ArrayList<String> keys2 = new ArrayList<String>();
           ArrayList<String> val2 = new ArrayList<String>();
              
           for (AVCPassStatus curr : BaseSharedAction.AVCPassStatus.values()) {
              keys2.add(curr.codice());
              val2.add(curr.descrizione() + " - " + curr.getSemaforo().descrizione());
           }
           scriviSezioneNoDB(out, "StatoAVCPASSType", keys2.toArray(), val2.toArray());
      }    
        
      // is30350_RFWSGL01Active
      if(SimogFlags.is30350_RFWSGL01Active() && configuration.isEAGGAttivo(PageHelper.getCurrentDate())){
         // Motivi richiesta cig
         scriviSezione(out, dbm, "MotiviRichiestaCigType", EAGG_MOTIVI.TABLE_NAME,
               EAGG_MOTIVI.DATA_INIZIO_VALIDITA, EAGG_MOTIVI.COD_MOTIVO, EAGG_MOTIVI.CODICE, null, allCodes, 
               EAGG_MOTIVI.DESCRIZIONE, EAGG_MOTIVI.DATA_FINE_VALIDITA, OP_NESSUNA, null, null);
         scriviSezione(out, dbm, "CategorieMerceologicheType", EAGG_CATEGORIE.TABLE_NAME,
               EAGG_CATEGORIE.DATA_INIZIO_VALIDITA, EAGG_CATEGORIE.COD_CATEGORIA, EAGG_CATEGORIE.CODICE, null, allCodes,
               EAGG_CATEGORIE.DESCRIZIONE,  EAGG_CATEGORIE.DATA_FINE_VALIDITA, OP_NESSUNA, null, null);
      }

      if(SimogFlags.isINT85_RFWEBGL01Active()){
         // Motivi richiesta cig comuni lista fissa da costanti
         
         ArrayList<String> keys2 = new ArrayList<String>();
         ArrayList<String> val2 = new ArrayList<String>();
            
         keys2.add(Costanti.LEGGE89_1);
         val2.add(Costanti.LEGGE89_1_DICH);

         keys2.add(Costanti.LEGGE89_2);
         val2.add(Costanti.LEGGE89_2_DICH);

         scriviSezioneNoDB(out, "MotiviRichiestaCigComuniType", keys2.toArray(), val2.toArray());
      }
         
      //TICKET ALM #664
      scriviSezione(out, dbm, "StrumentoSvolgimentoType", STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME,
    		  STRUMENTI_SVOLGIMENTO_PROCEDURE.DATA_FINE_VALIDITA, STRUMENTI_SVOLGIMENTO_PROCEDURE.ID_SVOLGIMENTO,
    		  STRUMENTI_SVOLGIMENTO_PROCEDURE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
      //FINE TICKET ALM #664
      
      //TICKET ALM #3832
      scriviSezione(out, dbm, "ArtEstremaUrgenzaType", ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME,
    		  ART_ESTREMA_URGENZA_SOMMA_URGENZA.DATA_FINE_VALIDITA, ART_ESTREMA_URGENZA_SOMMA_URGENZA.ID_ESTREMA_URGENZA,
    		  ART_ESTREMA_URGENZA_SOMMA_URGENZA.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
      //FINE TICKET ALM #3832
      
    //TICKET ALM #3834
      scriviSezione(out, dbm, "AllegatoIXType", MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME,
    		  MODALITA_INDIZIONE_ALLEGATO_IX.DATA_FINE_VALIDITA, MODALITA_INDIZIONE_ALLEGATO_IX.ID_ALLEGATO_IX,
    		  MODALITA_INDIZIONE_ALLEGATO_IX.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
      //FINE TICKET ALM #3834
      
      //TICKET ALM - 3.04.2 NG
      scriviSezione(out, dbm, "AffidamentiRiservatiType", AFFIDAMENTI_RISERVATI.TABLE_NAME,
    		  AFFIDAMENTI_RISERVATI.DATA_FINE_VALIDITA, AFFIDAMENTI_RISERVATI.ID_AFF_RISERVATI,
    		  AFFIDAMENTI_RISERVATI.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
      
      
      scriviSezione(out, dbm, "CondizioneLottoType", CONDIZIONI.TABLE_NAME, CONDIZIONI.DATA_FINE_VALIDITA,
				CONDIZIONI.ID_CONDIZIONE, CONDIZIONI.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
      
      scriviSezione(out, dbm, "ArtRegimeType", ART_REGIMI_PARTICOLARI_DI_APPALTO.TABLE_NAME,
    		  ART_REGIMI_PARTICOLARI_DI_APPALTO.DATA_FINE_VALIDITA, ART_REGIMI_PARTICOLARI_DI_APPALTO.ID_ART_REGIME,
    		  ART_REGIMI_PARTICOLARI_DI_APPALTO.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
    //FINE TICKET ALM - 3.04.2 NG
      
    //TICKET ALM - 3.04.3
      scriviSezione(out, dbm, "MotivoCollegamentoType", MOTIVO_COLLEGAMENTO.TABLE_NAME,
    		  MOTIVO_COLLEGAMENTO.DATA_FINE_VALIDITA, MOTIVO_COLLEGAMENTO.ID_MOTIVO,
    		  MOTIVO_COLLEGAMENTO.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
      //FINE TICKET - 3.04.3
      
      //TICKET ALM #659 - 3.04.4
      scriviSezione(out, dbm, "FunzioniDelegateType", FUNZIONI_DELEGATE.TABLE_NAME,
    		  FUNZIONI_DELEGATE.DATA_FINE_VALIDITA, FUNZIONI_DELEGATE.ID_F_DELEGATE,
    		  FUNZIONI_DELEGATE.DESCRIZIONE, null, allCodes, null, null, OP_NESSUNA, null, null);
      
      
	   // schema
		out.endEntity(true);

		out.close();
		wrt.close();
	}


	static void scriviSezione(XmlWriter out, SimpleDbManager dbm, String sezione, String tabella, String campoVal,
			String campoKey, String campoDesc, String valoreVuoto, boolean allCodes, String campoDesc2,
			String campoFinVal, int operazione, String where, String listaCampi) {

		TableBean mdg = null;

		// patch per gestire alias su campoKey
		String lCampoKey = campoKey;
		String lCampoAlias = campoKey;
		String [] campi = campoKey.split(" AS");
		if(campi.length >1){
		   lCampoKey = campi[0];
		   lCampoAlias = campi[1];
		}
		
	
		try {
		    if(campoFinVal == null){
		       if(where == null)
		          mdg = dbm.executeSelect(tabella, campoVal, lCampoKey, 
		                   (allCodes ? AccessiDB.DATA_NULLA : PageHelper.getCurrentDate()), false);
		       else
		          mdg = dbm.executeSelectWhere(tabella, campoVal, lCampoKey, 
                        (allCodes ? AccessiDB.DATA_NULLA : PageHelper.getCurrentDate()), where, listaCampi);

			} else
               mdg = dbm.executeSelect(tabella, campoVal, lCampoKey, 
                     (allCodes ? null : PageHelper.getSqlDateFromYMD(PageHelper.getCurrentDate())), false,
                     campoFinVal);
		       
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
//			if (mdg.getRowsCount() > 0) {
		    if(operazione == OP_NESSUNA || operazione == OP_CONTINUA){
				out.writeEntity("xsd:simpleType");
				out.writeAttribute("name", sezione);
				out.writeEntity("xsd:restriction");
				out.writeAttribute("base", "xsd:string");
			}

			if (valoreVuoto != null) {
				out.writeEntity("xsd:enumeration");
				out.writeAttribute("value", "");
				out.writeEntity("xsd:annotation");
				out.writeTextEntity("xsd:documentation", valoreVuoto);
				out.endEntity(true);
				out.endEntity(true);
			}

			for (int i = 0; i < mdg.getRowsCount(); i++) {
				out.writeEntity("xsd:enumeration");
				out.writeAttribute("value", mdg.getField(lCampoAlias, i));

                out.writeEntity("xsd:annotation");

				if (campoDesc != null) {
				   // patch per DETTAGLIO_REQUISITO, mi serve anche la descrizione del tipo uso
				   String uso = "";
				   if(tabella.equals(DETTAGLIO_REQUISITO.TABLE_NAME)){
                      RequisitiGLManager rqm = new RequisitiGLManager(dbm.getCurrentActiveConnection(), dbm.getLogger());
                      uso = " USO: " + rqm.getDescTipoUso(Long.valueOf(mdg.getField(lCampoAlias, i)));
                      out.writeEntity("xsd:documentation"); 
                      out.writeText(uso);

                      out.endEntity(false);
                   }
                   
					out.writeEntity("xsd:documentation"); 
					
					String desc2 = campoDesc2 != null ? mdg.getField(campoDesc2, i).trim() : "";
					if(!"".equals(desc2))
					   desc2 = " - " + desc2;
					

					out.writeText(mdg.getField(campoDesc, i).trim() + desc2);

					out.endEntity(false);

					// patch per controllo annullati su tabelle requisiti
					// aggiunte tabelle per soggetti aggregatori

					if (TIPO_USO.TABLE_NAME.equals(tabella) || DETTAGLIO_REQUISITO.TABLE_NAME.equals(tabella)
					      || DOCUMENTO_REQUISITO.TABLE_NAME.equals(tabella) 

							|| TIPO_REQUISITO.TABLE_NAME.equals(tabella) || TIPO_UNITA_MISURA.TABLE_NAME.equals(tabella)
					      || TIPO_FONTE_DOCUMENTO.TABLE_NAME.equals(tabella)

							|| DISPOSTO_NORMATIVO.TABLE_NAME.equals(tabella) || REQUISITO.TABLE_NAME.equals(tabella)
					      || TIPO_DOCUMENTO_REQ.TABLE_NAME.equals(tabella)

							|| EAGG_CATEGORIE.TABLE_NAME.equals(tabella) || EAGG_MOTIVI.TABLE_NAME.equals(tabella)) {
						if (mdg.getField(campoFinVal, i) != null && !"null".equals(mdg.getField(campoFinVal, i))
								&& !"2099".equals(mdg.getField(campoFinVal, i).substring(0, 4))) {
                          out.writeEntity("xsd:documentation"); 
 
							out.writeText("*** ATTENZIONE *** codice annullato dal " + PageHelper
									.getFormattedDate(mdg.getField(campoFinVal, i).replace("-", "").substring(0, 8)));
                          out.endEntity(false);		
                       }

					} else {
      					if(mdg.getField(TIPOLOGIA.DATA_FINE_VALIDITA, i) != null 
      					      && !"null".equals(mdg.getField(TIPOLOGIA.DATA_FINE_VALIDITA, i))){
      						out.writeEntity("xsd:documentation"); 

							out.writeText("*** ATTENZIONE *** codice annullato dal "
									+ PageHelper.getFormattedDate(mdg.getField(TIPOLOGIA.DATA_FINE_VALIDITA, i)));
      						out.endEntity(false);
      					}	
					}					
					out.endEntity(true);
				}
				out.endEntity(true);
			}

//			if (mdg.getRowsCount() > 0) {
			if(operazione == OP_NESSUNA || operazione == OP_CHIUDI){			
				out.endEntity(true);
				out.endEntity(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	static void scriviSezioneNoDB(XmlWriter out, String sezione, Object[] objects, Object[] objects2) {

		try {
			out.writeEntity("xsd:simpleType");
			out.writeAttribute("name", sezione);
			out.writeEntity("xsd:restriction");
			out.writeAttribute("base", "xsd:string");

			for (int i = 0; i < objects.length; i++) {
				out.writeEntity("xsd:enumeration");
				out.writeAttribute("value", (String) objects[i]);

				out.writeEntity("xsd:annotation");
				out.writeTextEntity("xsd:documentation", ((String) objects2[i]).trim());
				out.endEntity(true);
				out.endEntity(true);
			}

			out.endEntity(true);
			out.endEntity(true);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	
 
	
    public static void writeTabellaXML(Writer wrt, SimpleDbManager dbm, String nomeTabella, String campoChiave, String campoValidita)
          throws WritingException, IOException, SQLException {
       
       TabManager tabManager = new TabManager(dbm.getCurrentActiveConnection(), Logger.getLogger("ListTypesBuilder"));
       
       String info = tabManager.getTabellaInfo(nomeTabella).replace("<br>","\n").replace("&nbsp;", "");
       

		TableBean mdg = dbm.executeSelect(nomeTabella, campoValidita, campoChiave, AccessiDB.DATA_NULLA, false);
       
       wrt.append("<!--\n" + info + "-->\n");
       
       PrintWriter printerOut = new PrintWriter(wrt);

       mdg.printXMLTable(printerOut, true, "-NULL-", nomeTabella);
       
    }
    
   
//	public static void main(String args[]) {
//	   
//	   try {
//	   

	// final String JDBC_DRIVER =
	// "jdbc:sqlserver://localhost:1433;user=sa;password=admin2011;SelectMethod=cursor;DatabaseName=SIMOG_MEV_30;sendStringParametersAsUnicode=false;lockTimeout=60000";
//      	   
//      	   Connection conn = DriverManager.getConnection(JDBC_DRIVER);
//      	   

	// SimpleDbManager dbm = new SimpleDbManager(conn,
	// Logger.getLogger("ListTypesBuilder"));
//      	   
//      	   StringWriter sw = new StringWriter();
//      	   

	// writeTabellaXML(sw, dbm, AGGIUDICAZIONI.TABLE_NAME,
	// AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);
//      	   
//      	   System.out.println(sw);
//	   
//       } catch (Exception e) {
//          e.printStackTrace();
//       }	   
//	   
//   }
	
}
