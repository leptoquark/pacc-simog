package it.avlp.simog.errormessage;

import it.avlp.simog.db.Costanti;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;

public interface Messaggi {

	// 3031 Errori generici servizi
	public final static String SIMOG_SERVIZI_001 = "SIMOG_SERVIZI_001 - Servizio DIPE-CUP Errore restituito: $1";

	// 3031 Errori generici servizi
	public final static String SIMOG_SERVIZI_002 = "SIMOG_SERVIZI_002 - Servizio RGS Errore restituito: $1";

	// Errori generici SQL
	public final static String SIMOG_SQL_000 = "SIMOG_SQL_000 - Impossibile Inizializzare le risorse Applicative";
	public final static String SIMOG_SQL_001 = "SIMOG_SQL_001 - Impossibile stabilire la connessione al DB";
	public final static String SIMOG_SQL_006 = "SIMOG_SQL_006 - Errore nell'esecuzione del rollback";
	public final static String SIMOG_SQL_008 = "SIMOG_SQL_008 - Errori bloccanti durante l'operazione $1";

	// Errori generici di IO e Autenticazione
	public final static String SIMOG_LOGIN_001 = "SIMOG_LOGIN_001 - Impossibile Contattare il sistema di autenticazione";
	public final static String SIMOG_LOGIN_002 = "SIMOG_LOGIN_002 - Credenziali non valide";
	public final static String SIMOG_LOGIN_003 = "SIMOG_LOGIN_003 - Accesso non eseguito o sessione scaduta";
	public final static String SIMOG_LOGIN_004 = "SIMOG_LOGIN_004 - Non si dispone di autorizzazioni sufficienti per questa risorsa";
	public final static String SIMOG_LOGIN_005 = "SIMOG_LOGIN_005 - Il servizio non e' disponibile al momento";
	public final static String SIMOG_LOGIN_006 = "SIMOG_LOGIN_006 - Accesso negato per carenza di informazioni relative all' Osservatorio di competenza";
	public final static String SIMOG_LOGOUT_COMPLETED = "Logout eseguito correttamente";

	// Errori in creazione/visualizza/elimina nuova procedura di gara
	public final static String SIMOG_GARA_001 = "SIMOG_GARA_001 - Errore durante l'inserimento gara";
	public final static String SIMOG_GARA_002 = "SIMOG_GARA_002 - Errore durante il perfezionamento della gara";
	public final static String SIMOG_GARA_005 = "SIMOG_GARA_005 - Errore durante la ricerca della Gara";
	public final static String SIMOG_GARA_006 = "SIMOG_GARA_006 - Errore generico nel recupero dei documenti";
	public final static String SIMOG_GARA_008 = "SIMOG_GARA_008 - Il campo Oggetto Gara deve essere valorizzato";
	public final static String SIMOG_GARA_009 = "SIMOG_GARA_009 - Il campo CPV e' obbligatorio";
	public final static String SIMOG_GARA_010 = "SIMOG_GARA_010 - Verificare la correttezza del CIG";
	public final static String SIMOG_GARA_011 = "SIMOG_GARA_011 - Verificare l'intervallo della data di pubblicazione";
	public final static String SIMOG_LOTTO_011a = "SIMOG_LOTTO_011a - Il campo 'Prima annualita' dell'ultimo programma nel quale e' stato inserito l'intervento o l'acquisto' deve essere valorizzato"; // TICKET
																																																		// ALM
																																																		// #2845
	public final static String SIMOG_LOTTO_011b = "SIMOG_LOTTO_011b - verificare la correttezza dell'annualita'"; // TICKET
																													// ALM
																													// #2845
	public final static String SIMOG_GARA_012 = "SIMOG_GARA_012 - Verificare l'intervallo della data di scadenza";
	public final static String SIMOG_GARA_013 = "SIMOG_GARA_013 - Il campo Importo deve essere valorizzato";
//	public final static String SIMOG_GARA_014 = "SIMOG_GARA_014 -  Inserimento della gara $1 completato";
	//MEV XXXXX 3.04.11
	public final static String SIMOG_GARA_014 = "SIMOG_GARA_014 - Inserimento della gara $1 completato; Si ricorda che gli eventuali CIG generati sono validi solo per i casi disciplinati dalla " 
			+ "<a href=\"https://www.anticorruzione.it/-/delibera-n.-582-del-13-dicembre-2023-adozione-comunicato-relativo-avvio-processo-digitalizzazione\" target=\"_blank\"> delibera 582 del 13 dicembre 2023 </a> ovvero che sono riconducibili a procedure i cui bandi o avvisi siano stati pubblicati o le cui lettere di invito sono state inviate entro il 31 dicembre 2023. Si ricorda che per effetto della medesima delibera i CIG non pubblicati entro 48 ore dalla relativa data di creazione saranno automaticamente cancellati.";
	public final static String SIMOG_GARA_015 = "SIMOG_GARA_015 - Salvataggio della gara $1 completato";
	public final static String SIMOG_GARA_016 = "SIMOG_GARA_016 - Conferma della gara $1 completata";
	public final static String SIMOG_GARA_017 = "SIMOG_GARA_017 - Cancellazione della gara completata";
	public final static String SIMOG_GARA_017e = "SIMOG_GARA_017e - Errore nella Cancellazione della gara";
	public final static String SIMOG_GARA_018 = "SIMOG_GARA_018 - Verificare la correttezza del Numero Gara";
	public final static String SIMOG_GARA_019 = "SIMOG_GARA_019 - Pubblicazione $1 della gara $2 completata";
	public final static String SIMOG_GARA_020 = "SIMOG_GARA_020 - Perfezionamento di gara $1 e dei suoi lotti completato";
	// public final static String SIMOG_GARA_021 = "SIMOG_GARA_021 - Incongruenza
	// tra la procedura di scelta del contraente indicata e la comunicazione dei
	// dati della pubblicazione";
	public final static String SIMOG_GARA_022 = "SIMOG_GARA_022 - I dati relativi alla pubblicita' dell'appalto non devono essere inseriti";
	public final static String SIMOG_GARA_023 = "SIMOG_GARA_023 - Sblocco della gara $1 completato con successo";
	public final static String SIMOG_GARA_024 = "SIMOG_GARA_024 - Modalità realizzazione della gara $1 cambiata con successo";
	// INVITATI
	public static final String SIMOG_GARA_025 = "SIMOG_GARA_025 - Operazione effettuata correttamente";

	// TICKET ALM - 3.04.3 #659
	public static final String SIMOG_GARA_026 = "SIMOG_GARA_026 - Selezionare SA a cui assegnare la gara";
	public static final String SIMOG_GARA_027 = "SIMOG_GARA_027 - La gara nr. $1 e' stata assegnata con successo alla SA $2";
	public static final String SIMOG_GARA_028 = "SIMOG_GARA_028 - Operazione non consentita: $1";

	// Errori in creazione/visualizza/elimina lotto
	public final static String SIMOG_LOTTO_002 = "SIMOG_LOTTO_002 - Errore nell'inizializzazione del lotto";
	public final static String SIMOG_LOTTO_005 = "SIMOG_LOTTO_005 - Errore nel recupero del lotto";
	public final static String SIMOG_LOTTO_011 = "SIMOG_LOTTO_011 - Errore visualizzazione lotto";
	public final static String SIMOG_LOTTO_012 = "SIMOG_LOTTO_012 - Aggiornamento lotto correttamente eseguito";
	public final static String SIMOG_LOTTO_012e = "SIMOG_LOTTO_012e - Non ci sono lotti da aggiornare";
	public final static String SIMOG_LOTTO_013 = "SIMOG_LOTTO_013 - Il campo Oggetto Lotto deve essere valorizzato";
	public final static String SIMOG_LOTTO_013a = "SIMOG_LOTTO_013a - Il campo Scelta Contraente deve essere valorizzato";
	public final static String SIMOG_LOTTO_014 = "SIMOG_LOTTO_014 - Il campo Importo Lotto deve essere valorizzato";
	public final static String SIMOG_LOTTO_016 = "SIMOG_LOTTO_016 - Impossibile inserire il lotto";
	public final static String SIMOG_LOTTO_017 = "SIMOG_LOTTO_017 - Cancellazione logica del lotto eseguita correttamente";
	public final static String SIMOG_LOTTO_017e = "SIMOG_LOTTO_017e - Impossibile procedere alla cancellazione logica";
	public final static String SIMOG_LOTTO_018 = "SIMOG_LOTTO_018 - Il perfezionamento del lotto e' stato correttamente completato";
	public final static String SIMOG_LOTTO_018e = "SIMOG_LOTTO_018e - Verificare che la Data di scadenza per la presentazione delle offerte sia successiva alla Data di Pubblicazione";

	public final static String SIMOG_LOTTO_019 = "SIMOG_LOTTO_019 - Il valore del CPV Prevalente non e' corretto";
	public final static String SIMOG_LOTTO_020 = "SIMOG_LOTTO_020 - Inserimento del lotto completato";
	public final static String SIMOG_LOTTO_021a = "SIMOG_LOTTO_021a - Data pubblicazione non valida";
	public final static String SIMOG_LOTTO_021b = "SIMOG_LOTTO_021b - Data scadenza non valida";
	public final static String SIMOG_LOTTO_020c = "SIMOG_LOTTO_020c - AVVISO: Si ricorda che il CIG generato è valido solo per i casi disciplinati dalla "
			+ "<a href=\"https://www.anticorruzione.it/-/delibera-n.-582-del-13-dicembre-2023-adozione-comunicato-relativo-avvio-processo-digitalizzazione\" target=\"_blank\">delibera 582 del 13 dicembre 2023 </a> ovvero che sono riconducibili a procedure i cui bandi o avvisi siano stati pubblicati o le cui lettere di invito sono state inviate entro il 31 dicembre 2023. Si ricorda che per effetto della medesima delibera i CIG non pubblicati entro 48 ore dalla relativa data di creazione saranno automaticamente cancellati."; //MEV 44995 3.04.11
	public final static String SIMOG_LOTTO_020d = "SIMOG_LOTTO_020d - AVVISO: Impossibile perfezionare, si prega di prendere visione della " 
			+ "<a href=\"https://www.anticorruzione.it/-/delibera-n.-582-del-13-dicembre-2023-adozione-comunicato-relativo-avvio-processo-digitalizzazione\" target=\"_blank\">delibera 582 del 13 dicembre 2023 </a> ";//MEV 44999 3.04.11
	public final static String SIMOG_LOTTO_022 = "SIMOG_LOTTO_022 - L'importo indicato per il presente lotto risulta maggiore dell'importo complessivo della gara";

	// UN last adds 02/02/09
	public final static String SIMOG_LOTTO_023 = "SIMOG_LOTTO_023 - Aggiungere una $1 per la cancellazione";

	// PP B302.2.0
	public static final String SIMOG_LOTTO_024 = "SIMOG_LOTTO_024 - I campi 'Appalto prevede ripetizioni o altre opzioni' e 'Ripetizione di precedente appalto' non possono essere valorizzati entrambi a 'SI'";
	public static final String SIMOG_LOTTO_024a = "SIMOG_LOTTO_024a - Se l'appalto prevede ripetizioni, il motivo collegamento CIG deve essere 'No, nessuna ipotesi di collegamento'";

	// UN 12/11/2013 - is3030_RFWEBGL00Active
	public final static String SIMOG_LOTTO_025 = "SIMOG_LOTTO_025 - $1 non valida";
	public final static String SIMOG_LOTTO_026 = "SIMOG_LOTTO_026 - Verificare che la $1 sia successiva alla $2";
	public final static String SIMOG_LOTTO_027 = "SIMOG_LOTTO_027 - L'importo del lotto e' superiore a quello normativamente utilizzabile per 'Somma urgenza beni culturali'";// TICKET
																																												// ALM
																																												// #3832

	// TICKET ALM - 3.04.2 2005
	public final static String SIMOG_LOTTO_028 = "SIMOG_LOTTO_028 - L'importo eccede i limiti previsti dalla norma per la tipologia di procedura selezionata";
	public static final String SIMOG_LOTTO_029 = "SIMOG_LOTTO_029 - I campi '$1' e '$2' non possono essere valorizzati entrambi a 'SI'";
	public static final String SIMOG_LOTTO_030 = "SIMOG_LOTTO_030 - I campi '$1' e '$2' non possono essere entrambi valorizzati";

	// TICKET ALM #4219 - 3.04.4
	public final static String SIMOG_LOTTO_031 = "SIMOG_LOTTO_031 - Il valore del CPV $1 non e' corretto";
	public final static String SIMOG_LOTTO_032 = "SIMOG_LOTTO_032 - La categoria merceologica non e' compresa tra quelle indicate nella scheda gara";
	public final static String SIMOG_LOTTO_033 = "SIMOG_LOTTO_033 - Il CPV $1 e' stato indicato come CPV Prevalente";

	public final static String SIMOG_LOTTO_034 = "SIMOG_LOTTO_034 - la data di adesione all'accordo quadro/convenzione deve essere uguale o antecedente alla data di aggiudicazione dell'accordo quadro";
	public final static String SIMOG_LOTTO_035 = "SIMOG_LOTTO_035 - somma degli importi dei CIG maggiore dell'importo del CIG accordo quadro";

	// TICKET ALM #13793 - 3.04.5
	public final static String SIMOG_LOTTO_036 = "SIMOG_LOTTO_036 - la CPV Prevalente indicata non rientra tra le CPV indicate nel CIG dell'accordo quadro o convenzione";
	public final static String SIMOG_LOTTO_037 = "SIMOG_LOTTO_037 - la CPV Secondaria $1 non rientra tra le CPV indicate nel CIG dell'accordo quadro o convenzione";

	// TICKET ALM #20049
	public final static String SIMOG_LOTTO_038 = "SIMOG_LOTTO_038 - E' stato indicato un importo per le ripetizioni ma il campo 'L'appalto prevede ripetizioni o altre opzioni?' e' stato valorizzato a NO";
	public final static String SIMOG_LOTTO_039 = "SIMOG_LOTTO_039 - Non è possibile procedere con la pubblicazione: la scheda aggiudicazione dell'accordo quadro non è presente";

	//3.04.9
	public final static String SIMOG_LOTTO_040 = "SIMOG_LOTTO_040 - La stazione appaltante non risulta qualificata per l'affidamento del contratto. E' possibile acquisire il CIG esclusivamente se l'affidamento rientra in una delle casistiche sotto elencate. La dichiarazione ha valore di autocertificazione ai fini delle successive verifiche, con correlativa applicazione delle sanzioni previste in caso di dichiarazioni mendaci"; //3.04.9 MEV 40610
	//public final static String SIMOG_LOTTO_041 = "SIMOG_LOTTO_041 - Impossibile controllare la qualificazione della stazione appaltante. Verificare se si rientra in una delle seguenti fattispecie";
	public final static String SIMOG_LOTTO_041 = "SIMOG_LOTTO_041 - Impossibile verificare se la stazione appaltante è qualificata. In assenza di apposita autodichiarazione il CIG non può essere rilasciato.";
	public final static String SIMOG_LOTTO_042 = "SIMOG_LOTTO_042 - Impossibile inserire tale deroga per la qualificazione della SA";
	
	//3.04.15
	public final static String SIMOG_VARIANTI_041 = "SIMOG_VARIANTI_041 - Impossibile verificare se il CIG è valido. In assenza di tale verifica la scheda non può essere salvata/confermata.";
		
		
	//3.04.10 43227
	public final static String SIMOG_MODIFICA_PERFEZIONAMENTO_001 = "SIMOG_MODIFICA_PERFEZIONAMENTO_001 - Data scadenza richiesta invito, campo non previsto";
	public final static String SIMOG_MODIFICA_PERFEZIONAMENTO_002 = "SIMOG_MODIFICA_PERFEZIONAMENTO_002 - Data scadenza pagamenti, campo non previsto";
	public final static String SIMOG_MODIFICA_PERFEZIONAMENTO_003 = "SIMOG_MODIFICA_PERFEZIONAMENTO_003 - Ora scadenza pagamenti, campo non previsto";
	
	
	public final static String SIMOG_RIC_001 = "SIMOG_RIC_001 - Nessun risultato per la ricerca corrente";
	public final static String SIMOG_RIC_002 = "SIMOG_RIC_002 - La ricerca ha restituito un numero elevato di transazioni, sono visualizzate le prime $1";
	public final static String SIMOG_RIC_003 = "SIMOG_RIC_003 - Indicare almeno un criterio di ricerca";
	// is3030_RFWEBGL03Active
	public final static String SIMOG_RIC_004 = "SIMOG_RIC_004 - Classificazione di competenza territoriale della stazione appaltante non corrispondente alla sezione regionale richiedente";

	// TICKET ALM - 3.04.3
	public final static String SIMOG_RIC_005 = "SIMOG_RIC_005 - Non sono presenti gare delegate ad altre SA";
	public final static String SIMOG_RIC_006 = "SIMOG_RIC_006 - La gara nr. $1 non e' delegata o e' gia' stata presa in carico dalla stazione appaltante delegante";
	public final static String SIMOG_RIC_007 = "SIMOG_RIC_007 - Non e' possibile procedere con la presa in carico della gara nr. $1: per ogni CIG deve essere presente una scheda aggiudicazione confermata o una scheda dati comuni in stato diverso da aggiudicata";
	public final static String SIMOG_RIC_008 = "SIMOG_RIC_008 - Non e' possibile procedere con la presa in carico della gara nr. $1: per ogni CIG deve essere presente una scheda dati comuni confermata";
	public final static String SIMOG_RIC_009 = "SIMOG_RIC_009 - Non e' possibile procedere con la presa in carico della gara nr. $1: la funzione di delega selezionata non permette di prendere in carico la gara";

	public static final String SIMOG_UPLOAD_000 = "SIMOG_UPLOAD_000 - Errore durante l'archiviazione dell'aggiornamento";
	public static final String SIMOG_UPLOAD_001 = "SIMOG_UPLOAD_001 - Cartella temporanea per di archiviazione tabelle di servizio non disponibile";
	public static final String SIMOG_UPLOAD_004 = "SIMOG_UPLOAD_004 - Completato caricamento degli aggiornamenti richiesti";
	public static final String SIMOG_UPLOAD_005 = "SIMOG_UPLOAD_005 - Completato aggiornamento tabelle di servizio";
	public static final String SIMOG_UPLOAD_006 = "SIMOG_UPLOAD_006 - Completato Inserimento documentazione";
	public static final String SIMOG_UPLOAD_007 = "SIMOG_UPLOAD_007 - Impossibile aggiungere il file specificato";
	public static final String SIMOG_UPLOAD_010 = "SIMOG_UPLOAD_010 - Completata cancellazione documentazione";
	public static final String SIMOG_UPLOAD_011 = "SIMOG_UPLOAD_011 - Impossibile eliminare il documento";

	public static final String SIMOG_TRS_001 = "SIMOG_TRS_001 - Si e' verificato un errore durante la ricerca delle transazioni";
	public static final String SIMOG_UPD_001 = "SIMOG_UPD_001 - Si sono verificati degli errori durante l'aggiornamento delle Tabelle amministrative, verificare l'esito dell'aggiornamento";
	public static final String SIMOG_TRS_003 = "SIMOG_TRS_003 - Si sono verificati errori durante la visualizzazione dei lotti pubblicati";

	public static final String IMPORTO_NON_INSERITO = "Importo non inserito";
	public static final String SIMOG_LOG_001 = "SIMOG_LOG_001 - Si sono verificati errori durante la visualizzazione dei log applicativi";

	public static final String SIMOG_AGGIUDICAZIONI_002 = "SIMOG_AGGIUDICAZIONI_002 - Errore durante il recupero dei dati per l'inserimento";
	public static final String SIMOG_AGGIUDICAZIONI_006 = "SIMOG_AGGIUDICAZIONI_006 - Errore durante il recupero dei dati per il Riepilogo";
	public static final String SIMOG_AGGIUDICAZIONI_007 = "SIMOG_AGGIUDICAZIONI_007 - Nessuna scheda presente per il lotto in esame";
	public static final String SIMOG_AGGIUDICAZIONI_009 = "SIMOG_AGGIUDICAZIONI_009 - Dati Comuni aggiornati con successo";
	public static final String SIMOG_AGGIUDICAZIONI_012 = "SIMOG_AGGIUDICAZIONI_012 - Dati Comuni confermati con successo";
	public static final String SIMOG_AGGIUDICAZIONI_023 = "SIMOG_AGGIUDICAZIONI_023 - Inserimento dei dati eseguito correttamente";
	public static final String SIMOG_AGGIUDICAZIONI_024 = "SIMOG_AGGIUDICAZIONI_024 - Aggiornamento dei dati eseguito correttamente";
	public static final String SIMOG_AGGIUDICAZIONI_025 = "SIMOG_AGGIUDICAZIONI_025 - Conferma dei dati avvenuta correttamente";
	public static final String SIMOG_AGGIUDICAZIONI_050 = "SIMOG_AGGIUDICAZIONI_050 - Aggiornamento dei dati eseguito correttamente";
	public static final String SIMOG_AGGIUDICAZIONI_080 = "SIMOG_AGGIUDICAZIONI_080 - Richiesta di Modifica inoltrata con successo";
	public static final String SIMOG_AGGIUDICAZIONI_081 = "SIMOG_AGGIUDICAZIONI_081 - $1: Operazione effettuata correttamente";
	public static final String SIMOG_AGGIUDICAZIONI_082 = "SIMOG_AGGIUDICAZIONI_082 - Richiesta Cancellazione inoltrata con successo";
	public static final String SIMOG_AGGIUDICAZIONI_083 = "SIMOG_AGGIUDICAZIONI_083 - Variazioni Anagrafiche in C.O: Operazione effettuata correttamente";
	public static final String SIMOG_AGGIUDICAZIONI_084 = "SIMOG_AGGIUDICAZIONI_084 - Impossibile procedere: la scheda di aggiudicazione dell'accordo quadro per il CIG $1 non e' stata confermata";

	// is3030_RFWEBSC00Active
	public static final String SIMOG_AGGIUDICAZIONI_085 = "SIMOG_AGGIUDICAZIONI_085 - Campi prevalorizzati: Percentuale Ribasso Aggiudicazione, Percentuale Offerta Aumento, Importo di adesione, Data di aggiudicazione";

	public static final String SIMOG_VARIAZIONE_CO_001 = "SIMOG_VARIAZIONE_CO_001 - Errore nell'inoltro della Richiesta di Variazione";
	public static final String SIMOG_VARIAZIONE_SA_001 = "SIMOG_VARIAZIONE_SA_001: Variazione effettuata con successo";

	public static final String SIMOG_RICHIESTA_ANNULLAMENTO_001 = "SIMOG_RICHIESTA_ANNULLAMENTO_001 - Errore nell'inoltro della Richiesta di Modifica";
	public static final String SIMOG_RICHIESTA_ANNULLAMENTO_002 = "SIMOG_RICHIESTA_ANNULLAMENTO_002 - Aggiornamento Richiesta di modifica correttamente eseguito";
	public static final String SIMOG_RICHIESTA_ANNULLAMENTO_003 = "SIMOG_RICHIESTA_ANNULLAMENTO_003 - E' obbligatorio effettuare una scelta";

	public static final String SIMOG_RICHIESTA_CANCELLAZIONE_001 = "SIMOG_RICHIESTA_CANCELLAZIONE_001 - Errore nell'inoltro della Richiesta Cancellazione";
	public static final String SIMOG_RICHIESTA_CANCELLAZIONE_002 = "SIMOG_RICHIESTA_CANCELLAZIONE_002 - Aggiornamento Richiesta Cancellazione correttamente eseguito";
	public static final String SIMOG_RICHIESTA_CANCELLAZIONE_003 = "SIMOG_RICHIESTA_CANCELLAZIONE_003 - E' obbligatorio effettuare una scelta";

	public static final String SIMOG_RUBRICA_001 = "SIMOG_RUBRICA_001 - Aggiornamento aggiudicatario correttamente eseguito";
	public static final String SIMOG_RUBRICA_002 = "SIMOG_RUBRICA_002 - Inserimento aggiudicatario correttamente eseguito";
	public static final String SIMOG_RUBRICA_003 = "SIMOG_RUBRICA_003 - Un utente con il codice fiscale digitato e' gia presente in rubrica";
	public static final String SIMOG_RUBRICA_004 = "SIMOG_RUBRICA_004 - Inserimento responsabile correttamente eseguito";
	public static final String SIMOG_RUBRICA_005 = "SIMOG_RUBRICA_005 - Aggiornamento responsabile correttamente eseguito";
	public static final String SIMOG_RUBRICA_006 = "SIMOG_RUBRICA_006 - Attenzione. Si e' verificato un errore durante l'inserimento. Controllare che il codice fiscale digitato non sia gia' presente in rubrica e che i tipi di dati inseriti siano corretti";
	public static final String SIMOG_RUBRICA_007 = "SIMOG_RUBRICA_007 - Nessun aggiornamento necessario";

	public static final String SIMOG_RUBRICA_008 = "SIMOG_RUBRICA_008 - Cancellazione $1 correttamente eseguita";
	public static final String SIMOG_RUBRICA_009 = "SIMOG_RUBRICA_009 - Cancellazione $1 non permessa";

	public static final String SIMOG_VALIDAZIONE_000 = "SIMOG_VALIDAZIONE_000 - Richiesta operazione non valida";
	public static final String SIMOG_VALIDAZIONE_007 = "SIMOG_VALIDAZIONE_007 - CIG non valido: $1";
	public static final String SIMOG_VALIDAZIONE_007a = "SIMOG_VALIDAZIONE_007a - $1: CIG non valido o non prevede ripetizioni";
	public static final String SIMOG_VALIDAZIONE_007b = "SIMOG_VALIDAZIONE_007b - $1: CIG fa parte di una gara che non presenta come modalita' di realizzazione '$2'";
	public static final String SIMOG_VALIDAZIONE_008 = "SIMOG_VALIDAZIONE_008 - CIG inesistente o non di competenza";
	public static final String SIMOG_VALIDAZIONE_035 = "SIMOG_VALIDAZIONE_035 - Non e' stato indicato il valore del ribasso di aggiudicazione";
	public static final String SIMOG_VALIDAZIONE_060 = "SIMOG_VALIDAZIONE_060 - Campo QUOTIDIANI NAZIONALI : La pubblicita' sui quotidiani non e' sufficiente.";
	public static final String SIMOG_VALIDAZIONE_061 = "SIMOG_VALIDAZIONE_061 - Campo QUOTIDIANI LOCALI : La pubblicita' sui quotidiani non e' sufficiente.";
	public static final String SIMOG_VALIDAZIONE_068 = "SIMOG_VALIDAZIONE_068 - Il codice fiscale dell'Amministrazione non e' valido";
	public static final String SIMOG_VALIDAZIONE_104 = "SIMOG_VALIDAZIONE_104 - $1: selezionare un valore tra quelli previsti";
	public static final String SIMOG_VALIDAZIONE_104b = "SIMOG_VALIDAZIONE_104b - $1: selezionare almeno un valore tra quelli previsti";
	public static final String SIMOG_VALIDAZIONE_104c = "SIMOG_VALIDAZIONE_104c - $1: Selezionare almeno un valore del campo 'motivo deroga' alla previsione dell'obbligo di indicare una quota pari almeno al 30 per cento con riferimento all'occupazione femminile/giovanile";
	public static final String SIMOG_VALIDAZIONE_105 = "SIMOG_VALIDAZIONE_105 - $1: campo non previsto";
	public static final String SIMOG_VALIDAZIONE_106 = "SIMOG_VALIDAZIONE_106 - $1: data formalmente non corretta";
	public static final String SIMOG_VALIDAZIONE_107 = "SIMOG_VALIDAZIONE_107 - Non e' stata indicata la data di $1";
	public static final String SIMOG_VALIDAZIONE_108 = "SIMOG_VALIDAZIONE_108 - Il $1 non e' valido";
	public static final String SIMOG_VALIDAZIONE_109 = "SIMOG_VALIDAZIONE_109 - $1 obbligatorio se $2 non e' valorizzato";
	public static final String SIMOG_VALIDAZIONE_110 = "SIMOG_VALIDAZIONE_110 - Non e' stata selezionata la modalita di acquisizione";
	public static final String SIMOG_VALIDAZIONE_111 = "SIMOG_VALIDAZIONE_111 - Non e' stato indicato $1";
	public static final String SIMOG_VALIDAZIONE_112 = "SIMOG_VALIDAZIONE_112 - $1: Campo non valorizzato";
	public static final String SIMOG_VALIDAZIONE_113 = "SIMOG_VALIDAZIONE_113 - $1: dato formalmente non corretto";
	public static final String SIMOG_VALIDAZIONE_114 = "SIMOG_VALIDAZIONE_114 - Il valore del campo '$1' non e' coerente";
	public static final String SIMOG_VALIDAZIONE_115 = "SIMOG_VALIDAZIONE_115 - L'importo complessivo dell'appalto e' inferiore a $1 euro";
	public static final String SIMOG_VALIDAZIONE_116 = "SIMOG_VALIDAZIONE_116 - Assenza CIG dell'affidamento di incarico esterno di progettazione";
	public static final String SIMOG_VALIDAZIONE_117 = "SIMOG_VALIDAZIONE_117 - $1: formalmente non corretto";
	public static final String SIMOG_VALIDAZIONE_118 = "SIMOG_VALIDAZIONE_118 - $1: il valore digitato contiene caratteri non ammessi";
	public static final String SIMOG_VALIDAZIONE_119A = "SIMOG_VALIDAZIONE_119A - Il cap inserito non è conforme a quello nazionale italiano";
	public static final String SIMOG_VALIDAZIONE_120 = "SIMOG_VALIDAZIONE_120 - La procedura e' stata precedentemente annullata prima dell'"
			+ "apertura delle buste";

	// ---- adds 25 marzo ----
	/* scheda inzio */
	public static final String SIMOG_VALIDAZIONE_119 = "SIMOG_VALIDAZIONE_119 - $1: Data precedente alla data di Aggiudicazione";
	public static final String SIMOG_VALIDAZIONE_121 = "SIMOG_VALIDAZIONE_121 - $1: Il valore digitato contiene caratteri non ammessi";
	public static final String SIMOG_VALIDAZIONE_123 = "SIMOG_VALIDAZIONE_123 - $1: Data antecedente la data di inizio progettazione esecutiva";
	public static final String SIMOG_VALIDAZIONE_125 = "SIMOG_VALIDAZIONE_125 - $1: Il campo e' obbligatorio";
	public static final String SIMOG_VALIDAZIONE_126 = "SIMOG_VALIDAZIONE_126 - $1: Verificare con la data di stipula del contratto";
	public static final String SIMOG_VALIDAZIONE_127 = "SIMOG_VALIDAZIONE_127 - $1: Si e' sicuri del valore della data digitata?";
	public static final String SIMOG_VALIDAZIONE_128 = "SIMOG_VALIDAZIONE_128 - $1: Data non congrua con la data stipula contratto";
	public static final String SIMOG_VALIDAZIONE_129 = "SIMOG_VALIDAZIONE_129 - $1: Selezionare almeno un valore per la posizione contributiva";
	public static final String SIMOG_VALIDAZIONE_130 = "SIMOG_VALIDAZIONE_130 - $1: Attenzione, non e' stata indicata la data del contratto";
	public static final String SIMOG_VALIDAZIONE_131 = "SIMOG_VALIDAZIONE_131 - $1: Data non coerente, verificare la data di stipula del contratto";
	/* scheda avanzamenti */
	public static final String SIMOG_VALIDAZIONE_132 = "SIMOG_VALIDAZIONE_132 - $1: Valore elevato, verificare";
	public static final String SIMOG_VALIDAZIONE_133 = "SIMOG_VALIDAZIONE_133 - $1: Verificare il numero di giorni";
	public static final String SIMOG_VALIDAZIONE_134 = "SIMOG_VALIDAZIONE_134 - $1: L'importo deve essere uguale a l'importo del campo Importo sal";
	public static final String SIMOG_VALIDAZIONE_135 = "SIMOG_VALIDAZIONE_135 - $1: Campo non richiesto";
	/* scheda conclusione */
	public static final String SIMOG_VALIDAZIONE_136 = "SIMOG_VALIDAZIONE_136 - $1: Obbligatorio se $2 e' valorizzato";
	public static final String SIMOG_VALIDAZIONE_137 = "SIMOG_VALIDAZIONE_137 - $1: Risoluzione/rescissione senza oneri! Verificare";
	public static final String SIMOG_VALIDAZIONE_138 = "SIMOG_VALIDAZIONE_138 - $1: Inserire importo oneri economici";
	public static final String SIMOG_VALIDAZIONE_139 = "SIMOG_VALIDAZIONE_139 - $1: Inserire solo numeri";
	public static final String SIMOG_VALIDAZIONE_140 = "SIMOG_VALIDAZIONE_140 - $1: Verificare numero di infortuni";
	/* scheda collaudo */
	public static final String SIMOG_VALIDAZIONE_141 = "SIMOG_VALIDAZIONE_141 - Sono stati indicati sia $1 che $2";
	public static final String SIMOG_VALIDAZIONE_141a = "SIMOG_VALIDAZIONE_141a - Richiesto almeno un valore tra $1 e $2";
	public static final String SIMOG_VALIDAZIONE_142 = "SIMOG_VALIDAZIONE_142 - $1: Campo obbligatorio se $2 e $3 sono uguali a 0";
	public static final String SIMOG_VALIDAZIONE_144 = "SIMOG_VALIDAZIONE_144 - $1: Data antecedente la data di conclusione ( $2 )";
	public static final String SIMOG_VALIDAZIONE_145 = "SIMOG_VALIDAZIONE_145 - $1: Redigere certificato di collaudo in luogo del certificato di regolare esecuzione per somme superiori a "
			+ PageHelper.formattaImporto(new BigDecimal(Costanti.IMPORTO_LOTTO_1000000)) + " euro";
	public static final String SIMOG_VALIDAZIONE_146 = "SIMOG_VALIDAZIONE_146 - $1: Deve essere precedente al campo \" Data del certificato di regolare esecuzione\"";
	public static final String SIMOG_VALIDAZIONE_147 = "SIMOG_VALIDAZIONE_147 - $1: Deve essere precedente o uguale al campo \" Data del certificato di regolare esecuzione\"";
	public static final String SIMOG_VALIDAZIONE_148 = "SIMOG_VALIDAZIONE_148 - $1: Deve essere successivo o uguale al campo \" Data nomina collaudatore/commissione\"";
	public static final String SIMOG_VALIDAZIONE_149 = "SIMOG_VALIDAZIONE_149 - $1: Deve essere successivo o uguale al campo \" Data inizio operazioni di collaudo\"";
	public static final String SIMOG_VALIDAZIONE_150 = "SIMOG_VALIDAZIONE_150 - $1: Redigere certificato regolare di esecuzione in luogo del certificato di collaudo per somme inferiori a "
			+ PageHelper.formattaImporto(new BigDecimal(Costanti.IMPORTO_LOTTO_500000)) + " euro";
	public static final String SIMOG_VALIDAZIONE_151 = "SIMOG_VALIDAZIONE_151 - $1: Deve essere maggiore di Zero";
	public static final String SIMOG_VALIDAZIONE_152 = "SIMOG_VALIDAZIONE_152 - $1: Deve essere un numero decimale";
	public static final String SIMOG_VALIDAZIONE_153 = "SIMOG_VALIDAZIONE_153 - $1: Verificare l'importo digitato";
	public static final String SIMOG_VALIDAZIONE_154 = "SIMOG_VALIDAZIONE_154 - $1: Deve essere minore o uguale dell'importo totale somme a disposizione della scheda aggiudicazione";
	/* scheda sospensioni */
	public static final String SIMOG_VALIDAZIONE_155 = "SIMOG_VALIDAZIONE_155 - $1: Data antecedente la data di $2"; // PP
																														// mod
																														// del
																														// 10.11.08
	public static final String SIMOG_VALIDAZIONE_156 = "SIMOG_VALIDAZIONE_156 - $1: Data antecedente la data di verbale sospensione";
	/* scheda variante */
	public static final String SIMOG_VALIDAZIONE_157 = "SIMOG_VALIDAZIONE_157 - $1: Assenza di valori riferiti al quadro economico";
	public static final String SIMOG_VALIDAZIONE_158 = "SIMOG_VALIDAZIONE_158 - $1: Non e' stato valorizzato l'importo della $2";
	public static final String SIMOG_VALIDAZIONE_159 = "SIMOG_VALIDAZIONE_159 - $1: Indicare almeno una motivazione che ha determinato l'insorgere di una variante";
	/* scheda accordo */
	public static final String SIMOG_VALIDAZIONE_160 = "SIMOG_VALIDAZIONE_160 - $1: Non e' stato valorizzato il nuovo importo della sicurezza";
	public static final String SIMOG_VALIDAZIONE_161 = "SIMOG_VALIDAZIONE_161 - $1: La data deve essere compresa tra "
			+ Costanti.START_DATE_FE + " e oggi";
	// SCHEDA A VALIDATOR
	public static final String SIMOG_VALIDAZIONE_165 = "SIMOG_VALIDAZIONE_165 - $1: Definire una categoria prevalente";
	public static final String SIMOG_VALIDAZIONE_166 = "SIMOG_VALIDAZIONE_166 - Non e' stato selezionato lo strumento di programmazione ";
	public static final String SIMOG_VALIDAZIONE_167 = "SIMOG_VALIDAZIONE_167 - Non e' stato indicato il progettista";
	public static final String SIMOG_VALIDAZIONE_168 = "SIMOG_VALIDAZIONE_168 - Non sono stati inseriti incaricati";
	public static final String SIMOG_VALIDAZIONE_169 = "SIMOG_VALIDAZIONE_169 - $1: E' stata indicata piu' di una categoria $2";
	public static final String SIMOG_VALIDAZIONE_170 = "SIMOG_VALIDAZIONE_170 - $1: Dato NON richiesto in relazione a criterio di aggiudicazione adottato";
	public static final String SIMOG_VALIDAZIONE_171 = "SIMOG_VALIDAZIONE_171 - $1: Non sono stati selezionati i requisiti";
	public static final String SIMOG_VALIDAZIONE_172 = "SIMOG_VALIDAZIONE_172 - $1: Dato non compatibile nei Settori ordinari";
	public static final String SIMOG_VALIDAZIONE_173 = "SIMOG_VALIDAZIONE_173 - Non e' stato indicato il valore dell'offerta di $1";
	public static final String SIMOG_VALIDAZIONE_174 = "SIMOG_VALIDAZIONE_174 - Verificare il valore dell'importo di aggiudicazione";
	public static final String SIMOG_VALIDAZIONE_175 = "SIMOG_VALIDAZIONE_175 - Non e' stato indicato il valore dell'importo $1";
	public static final String SIMOG_VALIDAZIONE_176 = "SIMOG_VALIDAZIONE_176 - $1: Anticipazione non ammessa in uno stato di avanzamento successivo al primo";
	public static final String SIMOG_VALIDAZIONE_177 = "SIMOG_VALIDAZIONE_177 - $1: Non e' stata selezionata la Categoria Scorporabile / Sub-Appaltabile";
	public static final String SIMOG_VALIDAZIONE_178 = "SIMOG_VALIDAZIONE_178 - $1: Codice $2 assente";
	public static final String SIMOG_VALIDAZIONE_179 = "SIMOG_VALIDAZIONE_179 - Soggetto Partecipante della riga $1 non e' contenuto nella lista degli aggiudicatari";
	public static final String SIMOG_VALIDAZIONE_180 = "SIMOG_VALIDAZIONE_180 - Specificare data del certificato di regolare esecuzione o le modalita' del collaudo tecnico amministrativo";
	public static final String SIMOG_VALIDAZIONE_181 = "SIMOG_VALIDAZIONE_181 - Data del certificato di regolare esecuzione non prevista in caso di collaudo tecnico amministrativo";
	public static final String SIMOG_VALIDAZIONE_182 = "SIMOG_VALIDAZIONE_182 - Collaudo tecnico amministrativo non previsto in presenza di certificato di regolare esecuzione";
	public static final String SIMOG_VALIDAZIONE_183 = "SIMOG_VALIDAZIONE_183 - $1: Deve essere successivo o uguale al campo \" Data di redazione certificato di collaudo\"";
	// UN last adds 30/01/09
	public static final String SIMOG_VALIDAZIONE_184 = "SIMOG_VALIDAZIONE_184 - $1: Capacita' massima di $2 caratteri";
	public static final String SIMOG_VALIDAZIONE_185 = "SIMOG_VALIDAZIONE_185 - $1: Non valido se sono presenti aggiudicazioni";
	// F-IXME: DA AGGIUNGERE ALLA DOC aggiunti, tranne 188 che e' un warning
	public static final String SIMOG_VALIDAZIONE_186 = "SIMOG_VALIDAZIONE_186 - CIG $1 formalmente non valido";
	// PP aggiunto per evidenziazione righe duplicate
	public static final String SIMOG_VALIDAZIONE_187 = "SIMOG_VALIDAZIONE_187 - $1 - Elemento duplicato";

	public static final String SIMOG_VALIDAZIONE_188 = "SIMOG_VALIDAZIONE_188 - I dati inseriti non definiscono una tipologia di flusso acquisizione valida";
	public static final String SIMOG_VALIDAZIONE_189 = "SIMOG_VALIDAZIONE_189 - $1: Valore non richiesto per il settore del contratto selezionato";
	public static final String SIMOG_VALIDAZIONE_190 = "SIMOG_VALIDAZIONE_190 - Il contratto non e' stato indicato come escluso in tutto o in parte dall'applicazione del codice";
	// PP 3.02
	public static final String SIMOG_VALIDAZIONE_191 = "SIMOG_VALIDAZIONE_191 - Per la scheda corrente esiste una precedente richiesta di $1 ancora non evasa";

	public static final String SIMOG_VALIDAZIONE_200 = "SIMOG_VALIDAZIONE_200 - Non e' stata effettuata la pubblicazione su $1";
	public static final String SIMOG_VALIDAZIONE_201 = "SIMOG_VALIDAZIONE_201 - $1 non e' interamente coperto da $2";
	public static final String SIMOG_VALIDAZIONE_202 = "SIMOG_VALIDAZIONE_202 - Valorizzare il ribasso aggiudicazione nel caso di offerta a prezzi unitari, ovvero in caso di formulazione di ribasso sull'importo delle prestazioni poste a base di gara";
	public static final String SIMOG_VALIDAZIONE_203 = "SIMOG_VALIDAZIONE_203 - L'importo complessivo dell'appalto e' difforme da quello dichiarato in sede di richiesta CIG";
	public static final String SIMOG_VALIDAZIONE_204 = "SIMOG_VALIDAZIONE_204 - Il campo $1 non deve essere valorizzato";
	public static final String SIMOG_VALIDAZIONE_205 = "SIMOG_VALIDAZIONE_205 - Verificare la correttezza di $1";
	public static final String SIMOG_VALIDAZIONE_206 = "SIMOG_VALIDAZIONE_206 - Valorizzare almeno un campo riferito a $1";
	public static final String SIMOG_VALIDAZIONE_207 = "SIMOG_VALIDAZIONE_207 - $1: attenzione, trattasi di consegna frazionata";
	public static final String SIMOG_VALIDAZIONE_208 = "SIMOG_VALIDAZIONE_208 - $1 incoerente rispetto a $2";
	public static final String SIMOG_VALIDAZIONE_209 = "SIMOG_VALIDAZIONE_209 - $1: previsto $2";
	public static final String SIMOG_VALIDAZIONE_210 = "SIMOG_VALIDAZIONE_210 - $1: il valore del campo deve essere obbligatoriamente $2";
	public static final String SIMOG_VALIDAZIONE_211 = "SIMOG_VALIDAZIONE_211 - Non e' stato indicato $1";
	public static final String SIMOG_VALIDAZIONE_212 = "SIMOG_VALIDAZIONE_212 - Inserire almeno un $1 per il raggruppamento di impresa";
	public static final String SIMOG_VALIDAZIONE_213 = "SIMOG_VALIDAZIONE_213 - Inserire uno ed un solo $1 per il raggruppamento di impresa";
	public static final String SIMOG_VALIDAZIONE_214 = "SIMOG_VALIDAZIONE_214 - $1: Soggetto Partecipante non e' presente in rubrica";
	// gm aggiunto per appalti multilotto
	public static final String SIMOG_VALIDAZIONE_215 = "SIMOG_VALIDAZIONE_215 - Appalti multilotto: $1";
	// pubblicazione bandi
	public static final String SIMOG_VALIDAZIONE_216 = "SIMOG_VALIDAZIONE_216 - La procedura di scelta del contraente selezionata $1";
	// UN 06/12/2012 - Requisito con documenti OE
	public static final String SIMOG_VALIDAZIONE_217 = "SIMOG_VALIDAZIONE_217 - Se specificato SI per Comprova in offerta e' necessario che tutti i documenti siano forniti dall'Operatore Economico";
	// check requisiti ordine generale
	public static final String SIMOG_VALIDAZIONE_218 = "SIMOG_VALIDAZIONE_218 - I Requisiti di ordine generale presenti nella richiesta sono stati eliminati automaticamente";

	// UN Validazione Requisiti OE
	public static final String SIMOG_VALIDAZIONE_219 = "SIMOG_VALIDAZIONE_219 - Non sono stati definiti i requisiti necessari alla partecipazione";
	public static final String SIMOG_VALIDAZIONE_220 = "SIMOG_VALIDAZIONE_220 - Impossibile procedere alla pubblicazione, mancano i requisiti necessari alla partecipazione";

	// is3030_RFWEBGL00Active
	public static final String SIMOG_VALIDAZIONE_221 = "SIMOG_VALIDAZIONE_221 - La combinazione di valori inseriti per le date non consente di determinare la tipologia di pubblicazione";
	public static final String SIMOG_VALIDAZIONE_222 = "SIMOG_VALIDAZIONE_222 - Impossibile procedere alla pubblicazione, procedure di scelta contraente dei lotti incompatibili";
	public final static String SIMOG_VALIDAZIONE_223 = "SIMOG_VALIDAZIONE_223 - Verificare che la $1 sia uguale o successiva alla $2";

	// is3031_ESCL_AVCPASS 3.04.7.1
	public final static String SIMOG_VALIDAZIONE_224 = "SIMOG_VALIDAZIONE_224 - Attenzione, nella scheda GARA non e' stata indicata l'esclusione o meno dal sistema FVOE, impossibile procedere con la pubblicazione";

	// is3031_RFWEBGL02Active
	public final static String SIMOG_VALIDAZIONE_225 = "SIMOG_VALIDAZIONE_225 - Codice CUP, il valore $1 non e' presente negli archivi DIPE, verificare il dato inserito";
	public final static String SIMOG_VALIDAZIONE_225a = "SIMOG_VALIDAZIONE_225a - Il codice CUP indicato $1 risulta nello stato $2. Si prega di contattare il DIPE";
	public final static String SIMOG_VALIDAZIONE_226 = "SIMOG_VALIDAZIONE_226 - I codici CUP presenti devono essere confermati esplicitamente";
	public static final String SIMOG_VALIDAZIONE_227 = "SIMOG_VALIDAZIONE_227 - Impossibile procedere alla $1, esistono codici CUP non validi";

	// Ticket ALM #972
	public static final String SIMOG_VALIDAZIONE_228 = "SIMOG_VALIDAZIONE_228 - $1: selezionare uno o pi&ugrave; valori tra quelli previsti";

	// Rinaldo ticket 654 //////// Controllo Aggiudicatorio singolo/multiplo /////
	public static final String SIMOG_VALIDAZIONE_229 = "SIMOG_VALIDAZIONE_229 - Attenzione la Tipologia Affidatario selezionata $1 non coincide col numero degli aggiudicatari";
	//////////////////////////////////////////////////////////////////////////////

	// Ticket ALM #710
	public static final String SIMOG_VALIDAZIONE_230 = "SIMOG_VALIDAZIONE_230 - $1: Errore di accesso agli archivi DIPE";

	// Ticket ALM #2592
	public static final String SIMOG_VALIDAZIONE_231 = "SIMOG_VALIDAZIONE_231 - $1: ruolo non richiesto per tipo aggiudicatario impresa singola";

	// TICKET ALM #4194 #3582
	public static final String SIMOG_VALIDAZIONE_232 = "SIMOG_VALIDAZIONE_232 - $1: E' stata selezionata una motivazione incompatibile con la modalita' di realizzazione indicata";

	// TICKET ALM #2847 - Varianti
	public static final String SIMOG_VALIDAZIONE_233 = "SIMOG_VALIDAZIONE_233 - $1: Selezionare un solo valore tra quelli previsti";

	// TICKET ALM #3834
	public static final String SIMOG_VALIDAZIONE_234 = "SIMOG_VALIDAZIONE_234 - $1: Selezionare una delle voci relative all'allegato IX";
	public static final String SIMOG_VALIDAZIONE_235 = "SIMOG_VALIDAZIONE_235 - Non e' stato valorizzato '$1'";

	// TICKET ALM - 3.04.3
	public static final String SIMOG_VALIDAZIONE_236 = "SIMOG_VALIDAZIONE_236 - $1: non e' stata trovata una amministrazione valida per il codice fiscale indicato";
	public static final String SIMOG_VALIDAZIONE_237 = "SIMOG_VALIDAZIONE_237 - $1: In caso di affidamento diretto per variante oltre il 20%, la motivazione deve essere 'Nuovo contratto originato da variante oltre il 20%'";
	public static final String SIMOG_VALIDAZIONE_238 = "SIMOG_VALIDAZIONE_238 - $1: In caso di affidamento diretto per lavori, servizi o forniture supplementari, la motivazione deve essere 'Lavori, servizi o forniture supplementari'";
	public static final String SIMOG_VALIDAZIONE_239 = "SIMOG_VALIDAZIONE_239 - $1: In caso di consegne complementari, la motivazione deve essere 'Consegne complementari'";
	public static final String SIMOG_VALIDAZIONE_240 = "SIMOG_VALIDAZIONE_240 - $1: In caso di II fase Concorso di progettazione e idee, la motivazione deve essere 'II fase Concorso di progettazione e idee'";
	public static final String SIMOG_VALIDAZIONE_241 = "SIMOG_VALIDAZIONE_241 - $1: In caso di ripetizione lavori o servizi analoghi, la motivazione deve essere 'Ripetizione di lavori o servizi analoghi'";
	public static final String SIMOG_VALIDAZIONE_242 = "SIMOG_VALIDAZIONE_242 - $1: In caso di procedura a seguito di precedente gara annullata o deserta o senza esito, la motivazione deve essere 'Lavori, servizi o forniture supplementari'";
	public static final String SIMOG_VALIDAZIONE_242a = "SIMOG_VALIDAZIONE_242a - $1: Sono state selezionate una o piu' condizioni che prevedono il concatenamento di CIG";

	// TICKET ALM #3437 MAC
	public static final String SIMOG_VALIDAZIONE_243 = "SIMOG_VALIDAZIONE_243 - $1: in caso di interruzione non anticipata, non e' possibile procedere con il salvataggio della scheda senza aver compilato la scheda 'Fase Iniziale'";

	// TICKET ALM - 3.04.4
	public static final String SIMOG_VALIDAZIONE_244 = "SIMOG_VALIDAZIONE_244 - la CPV indicata non e' coerente con la CPV Prevalente o le CPV Secondarie definite nel lotto";
	public static final String SIMOG_VALIDAZIONE_245 = "SIMOG_VALIDAZIONE_245 - indicare almeno una categoria DPCM";
	public static final String SIMOG_VALIDAZIONE_246 = "SIMOG_VALIDAZIONE_246 - indicare almeno un territorio di riferimento per l'iniziativa";
	public static final String SIMOG_VALIDAZIONE_246a = "SIMOG_VALIDAZIONE_246a - indicare almeno una tipologia di ambito";
	public static final String SIMOG_VALIDAZIONE_247 = "SIMOG_VALIDAZIONE_247 - indicare se l'iniziativa prevede il confronto competitivo";
	public static final String SIMOG_VALIDAZIONE_248 = "SIMOG_VALIDAZIONE_248 - numero gara non presente";
	public static final String SIMOG_VALIDAZIONE_249 = "SIMOG_VALIDAZIONE_249 - la gara dell'iniziativa non e' un accordo quadro/convenzione";
	public static final String SIMOG_VALIDAZIONE_250 = "SIMOG_VALIDAZIONE_250 - il CIG indicato non risulta appartenente alla gara";
	public static final String SIMOG_VALIDAZIONE_251 = "SIMOG_VALIDAZIONE_251 - il codice regionale $1 non e' valido";

	// TICKET ALM - 3.04.3 PT
	public static final String SIMOG_VALIDAZIONE_252 = "SIMOG_VALIDAZIONE_252 - SIMOG_VALIDAZIONE_252 - in caso di proroga tecnica Indicare il CIG della nuova procedura avviata ovvero indicare le motivazioni della proroga nel campo 'Cause della modifica contrattuale'";
	public static final String SIMOG_VALIDAZIONE_252a = "SIMOG_VALIDAZIONE_252a - in caso di 'Altre cause impreviste ed imprevedibili' indicare una motivazione nel campo 'Cause della modifica contrattuale'";

	//MEV 34191 3.04.8
	public static final String SIMOG_VALIDAZIONE_288 = "SIMOG_VALIDAZIONE_288 - e' stata selezionata la voce 'sopravvenute esigenze normative e regolamentari' oppure la voce 'altre cause impreviste ed imprevedibili' di cui all'art. 106, comma 1, lett. c D. Lgs. 50/2016. \r\n" + 
				"e' necessario valorizzare il 'link ai documenti relativi alle varianti in corso d'opera' con la URL della sezione 'Bandi e Contratti' dove sono pubblicati i documenti previsti dal regolamento di vigilanza ANAC";
	
	// TICKET ALM #9272 - 3.04.4
	public static final String SIMOG_VALIDAZIONE_253 = "SIMOG_VALIDAZIONE_253 - la somma degli importi degli aggiudicatari non corrisponde all'importo complessivo di aggiudicazione/affidamento";
	public static final String SIMOG_VALIDAZIONE_254 = "SIMOG_VALIDAZIONE_254 - non e' stato inserito l'importo per l'aggiudicatario";
	public static final String SIMOG_VALIDAZIONE_255 = "SIMOG_VALIDAZIONE_255 - in caso di singolo aggiudicatario, $1 non e' previsto";
	public static final String SIMOG_VALIDAZIONE_255b = "SIMOG_VALIDAZIONE_255b - in caso di ditta mandante, l'importo non e' previsto";
	public static final String SIMOG_VALIDAZIONE_256 = "SIMOG_VALIDAZIONE_256 - e' stata inserita la ditta mandataria tra le ditte mandanti";
	public static final String SIMOG_VALIDAZIONE_257 = "SIMOG_VALIDAZIONE_257 - la categoria merceologica '$1' risulta selezionata per uno o piu' lotti";
	public static final String SIMOG_VALIDAZIONE_258 = "SIMOG_VALIDAZIONE_258 - nuova iniziativa inserita con successo";
	public static final String SIMOG_VALIDAZIONE_259 = "SIMOG_VALIDAZIONE_259 -  iniziativa aggiornata con successo";
	public static final String SIMOG_VALIDAZIONE_260 = "SIMOG_VALIDAZIONE_260 - il campo '$1' non e' piu' in uso. Valorizzare a 'N'";
	public static final String SIMOG_VALIDAZIONE_261 = "SIMOG_VALIDAZIONE_261 - $1: in caso di accordo quadro o convenzione, il valore di '$1' deve essere 'Accordo quadro'";
	public static final String SIMOG_VALIDAZIONE_262 = "SIMOG_VALIDAZIONE_262 - $1: e' possibile selezionare 'Accordo quadro' solo in caso di modalita' di realizzazione equivalente in concessioni, scelta del socio privato nella societa' mista, accordo quadro o convenzione ";
	public static final String SIMOG_VALIDAZIONE_263 = "SIMOG_VALIDAZIONE_263 - $1: in caso di gara delegata in modalita' 'Proposta di Aggiudicazione' non e' possibile selezionare 'Aggiudicata' come esito";
	public static final String SIMOG_VALIDAZIONE_264 = "SIMOG_VALIDAZIONE_264 - $1: non e' possibile selezionare 'Proposta di aggiudicazione' in caso di gara non delegata o delegata non in modalita' 'Proposta di aggiudicazione'";
	public static final String SIMOG_VALIDAZIONE_265 = "SIMOG_VALIDAZIONE_265 - $1: non e' possibile selezionare 'Aggiudicata' o 'Proposta di aggiudicazione' se il perfezionamento della gara non viene completato";
	public static final String SIMOG_VALIDAZIONE_266 = "SIMOG_VALIDAZIONE_266 - $1: e' stata raggiunta la soglia massima annuale di cui all'art. 1 del dPCM 24/12/2015. Aderire a contratto presso soggetti aggregatori o autodichiarare che al SA non e' soggetta agli obblighi di cui al dPCM 24/12/2015";
	public static final String SIMOG_VALIDAZIONE_267a = "SIMOG_VALIDAZIONE_267a - $1: e' richiesta obbligatoriamente l'adesione a una iniziativa presso i soggetti aggregatori in quanto gli importi di uno piu' lotti superano la soglia massima annuale consentita di cui all'art. 1 del dPCM 24/12/2015. Se non si desidera aderire a una iniziativa, modificare gli importi affinche' rientrino nei parametri consentiti. I CIG impattati sono i seguenti: $2";
	public static final String SIMOG_VALIDAZIONE_267b = "SIMOG_VALIDAZIONE_267b - $1: il CIG indicato non e' una iniziativa presso i soggetti aggregatori in quanto uno o piu' gli importi di uno piu' lotti superano la soglia massima annuale di cui all'art. 1 del dPCM 24/12/2015. Se non si desidera aderire a una iniziativa, modificare gli importi. I CIG impattati sono i seguenti: $2";
	public static final String SIMOG_VALIDAZIONE_268 = "SIMOG_VALIDAZIONE_268 - il valore del campo 'modalita' di indizione (settori speciali)', non e' coerente con il valore dei campi 'scelta del contraente' e 'Condizioni che giustificano il ricorso alla procedura negoziata ...' del/dei CIG ";
	public static final String SIMOG_VALIDAZIONE_269 = "SIMOG_VALIDAZIONE_269 - Per il contratto non e' prevista la trasmissione di schede $1";
	public static final String SIMOG_VALIDAZIONE_270 = "SIMOG_VALIDAZIONE_270 - $1: l'importo deve essere uguale o inferiore all'importo del lotto";
	public static final String SIMOG_VALIDAZIONE_271 = "SIMOG_VALIDAZIONE_271 - l'amministrazione con codice fiscale $1 non e' registrata all'interno dell'Anagrafe Unica delle Stazioni Appaltanti (AUSA)";
	public static final String SIMOG_VALIDAZIONE_272 = "SIMOG_VALIDAZIONE_272 - $1: l'importo di riaggiudicazione non puo' essere maggiore dell'importo del CIG";
	public static final String SIMOG_VALIDAZIONE_273 = "SIMOG_VALIDAZIONE_273 - Non e' possibile indicare una modalita' di realizzazione accordo quadro/convenzione in quanto nei dati di gara non e' stato indicato il CIG Accordo Quadro";
	public static final String SIMOG_VALIDAZIONE_274 = "SIMOG_VALIDAZIONE_274 - Non e' possibile indicare una modalita' di realizzazione diversa da accordo quadro/convenzione in quanto nei dati di gara e' stato indicato il CIG Accordo Quadro";
	public static final String SIMOG_VALIDAZIONE_275 = "SIMOG_VALIDAZIONE_275 - Incaricato: indicato un membro mandante non associato a nessun raggruppamento";
	public static final String SIMOG_VALIDAZIONE_276 = "SIMOG_VALIDAZIONE_276 - Incaricato: per il gruppo $1 e' stato indicanto piu' di un mandatario";
	public static final String SIMOG_VALIDAZIONE_277 = "SIMOG_VALIDAZIONE_277 - Incaricato: per il gruppo $1 non e' stato indicanto il mandatario";
	public static final String SIMOG_VALIDAZIONE_278 = "SIMOG_VALIDAZIONE_278 - $1: non e' possibile procedere con l'inserimento della scheda in quanto l'affidatario in sede di offerta non ha richiesto la possibilita' di subappaltare";
	public static final String SIMOG_VALIDAZIONE_279 = "SIMOG_VALIDAZIONE_279 - $1: Il codice fiscale della stazione appaltante delegante non puo' essere uguale al codice fiscale della stazione appaltante delegata";
	public static final String SIMOG_VALIDAZIONE_280 = "SIMOG_VALIDAZIONE_280 - Richiesto almeno un valore tra $1 e $2";
	public static final String SIMOG_VALIDAZIONE_281 = "SIMOG_VALIDAZIONE_281 - L'appalto risulta finanziato con fondi PNRR/PNC";
	public static final String SIMOG_VALIDAZIONE_282 = "SIMOG_VALIDAZIONE_282 - E' obbligatorio valorizzare il campo a SI 'L'appalto è finalizzato alla realizzazione di progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi dell'art. 11 L. 3/2003 e ss.mm.? ' se l'appalto o concessione e' afferente agli investimenti pubblici finanzia con le risorse previste dal PNRR e/o PNC";
	public static final String SIMOG_VALIDAZIONE_283 = "SIMOG_VALIDAZIONE_283 - $1: almeno uno dei campi deve essere maggiore di 0%";
	public static final String SIMOG_VALIDAZIONE_284 = "SIMOG_VALIDAZIONE_284 - $1: Il campo prevede l'inserimento di una quota inferiore al 30%";
	public static final String SIMOG_VALIDAZIONE_286 = "SIMOG_VALIDAZIONE_286 - $1: Non è stato indicato il valore della 'Previsione di una quota inferiore con riferimento all' $1'";
	//MAC 35692 3.04.8
	public static final String SIMOG_VALIDAZIONE_289 = "SIMOG_VALIDAZIONE_289 - $1: Almeno una quota, tra occupazione femminile e occupazione giovanile, deve essere inferiore al 30%";
		
	//MARRA MEV 34470 3.04.8 scelta contraente diverso da Affidamento diretto
		public static final String SIMOG_VALIDAZIONE_291 = "SIMOG_VALIDAZIONE_291 - Verificare la coerenza tra la procedura di scelta del contraente e il motivo della somma urgenza";
	//FINE MEV
	
	
	// Servizi RGS
//	public final static String SIMOG_VALIDAZIONE_285 = "SIMOG_VALIDAZIONE_285 - Codice CUP, il valore $1 non e' presente negli archivi RGS, verificare il dato inserito";
	public static final String SIMOG_VALIDAZIONE_287 = "SIMOG_VALIDAZIONE_287 - $1: Errore di accesso agli archivi RGS";
	
	//MEV 34469 3.04.8
	public static final String SIMOG_VALIDAZIONE_290 = "SIMOG_VALIDAZIONE_290 - $1: Indicare almeno una motivazione che ha determinato la revisione del prezzo";
	// FINE MEV
	
	// 34183 3.04.8.1
	public static final String SIMOG_VALIDAZIONE_292 = "SIMOG_VALIDAZIONE_292 - $1: Data non coerente con quella indicata in fase di Pubblicazione/Perfezionamento";
	// FINE MEV
	//MAC 34161 3.04.8.1
	public static final String SIMOG_VALIDAZIONE_293 = "SIMOG_VALIDAZIONE_293 - Non è possibile creare una scheda 'Dati comuni' prima della 'Data di scadenza dei pagamenti'";
	//FINE MAC
	
	//MEV 37328 3.04.8.1
	public static final String SIMOG_VALIDAZIONE_294 = "SIMOG_VALIDAZIONE_294 - La stazione appaltante è assegnata ad una sezione regionale con sistema informativo proprio.\r\n" + 
			"Per la compilazione e trasmissione delle schede successive all'acquisizione del CIG si invita a procedere tramite il sistema e le indicazioni dell'Osservatorio regionale di competenza di cui all'elenco al link seguente : "
			+ "<a href=\"https://www.anticorruzione.it/-/invio-dati-sezioni-regionali-dell-osservatorio#p3\" target=\"_blank\">Pagina Sezioni Regionali</a> ";
	//FINE MEV
	
	//MEV 39162 3.04.8.1
	public static final String SIMOG_VALIDAZIONE_295 = "SIMOG_VALIDAZIONE_295 - CUP non previsto per accordi quadro e convenzioni";
	// FINE MEV
	
	//MEV 37010 3.04.8.1
	public static final String SIMOG_VALIDAZIONE_296 = "SIMOG_VALIDAZIONE_296 - I dati art.47 (Sezione pari opportunita) sono ereditati dal CIG AQ/Conv indicato nella creazione della gara";
	// FINE MEV
	
	
	// INT85
	// Katia ha detto di no! public static final String SIMOG_VALIDAZIONE_228 =
	// "SIMOG_VALIDAZIONE_228 - E' necessario dichiarare la motivazione per cui si
	// e' abilitati ad acquisire il CIG";

	public static final String SIMOG_CONTRIBUTO_001 = "SIMOG_CONTRIBUTO_001 - Calcolo contributo: Errore inatteso [$1]";
	public static final String SIMOG_CONTRIBUTO_002 = "SIMOG_CONTRIBUTO_002 - Non e' stato possibile calcolare il contributo dovuto dalla Stazione Appaltante. Il contributo sara' correttamente visualizzabile sul sistema di riscossione";
	public static final String SIMOG_CONTRIBUTO_003 = "SIMOG_CONTRIBUTO_003 - Non e' stato possibile calcolare il contributo dovuto dal partecipante. Il contributo sara' correttamente visualizzabile sul sistema di riscossione";

	/* MASSLOADER */
	public static final String SIMOG_MASSLOADER_167 = "SIMOG_MASSLOADER_167 - Esiste una scheda $1 non confermata, confermare la scheda prima di effettuare l'inserimento";

//	public static final String SIMOG_MASSLOADER_168 = "SIMOG_MASSLOADER_168 - Scheda $1 non inseribile, perche' non rispetta la gerarchia di inserimento, salto inserimento Scheda COMPLETA con progressivo \" $2 \" e continuo";
//	public static final String SIMOG_MASSLOADER_168 = "SIMOG_MASSLOADER_168 - Scheda $1 non inseribile, perche' non rispetta la gerarchia di inserimento";

//	public static final String SIMOG_MASSLOADER_169 = "SIMOG_MASSLOADER_169 - Errore durante il caricamento $1";
//	public static final String SIMOG_MASSLOADER_170 = "SIMOG_MASSLOADER_170 - La Scheda Aggiudicazione risulta nulla, impossibile recuperare i dati necessari per il caricamento delle schede";
//	public static final String SIMOG_MASSLOADER_171 = "SIMOG_MASSLOADER_171 - Impossibile recuperare i dati necessari per il caricamento delle schede";

	public static final String SIMOG_MASSLOADER_172 = "SIMOG_MASSLOADER_172 - Anagrafica non valida, o non presente, per il $1  con codice fiscale $2 ";

//	public static final String SIMOG_MASSLOADER_173 = "SIMOG_MASSLOADER_173 - Manca la Scheda $1 nel DB oppure nel file XML, senza la quale non risulta possibile {VALIDARE/INSERIRE} la Scheda $3";
	public static final String SIMOG_MASSLOADER_173 = "SIMOG_MASSLOADER_173 - La Scheda $1 NON risulta, presente e confermata nel DB, oppure presente nel file XML. Non risulta possibile $2 la Scheda $3";

	public static final String SIMOG_MASSLOADER_174 = "SIMOG_MASSLOADER_174 - La ricerca per \"CUI\": $1 non ha dato risultati";
	public static final String SIMOG_MASSLOADER_175 = "SIMOG_MASSLOADER_175 - Solo il tipo contratto \"Lavori\" prevede la scheda \"Istanza di Recesso\"";
	public static final String SIMOG_MASSLOADER_176 = "SIMOG_MASSLOADER_176 - Le aggiudicazioni per i Settori Speciali prevedono solo la Scheda Aggiudicazione";
	public static final String SIMOG_MASSLOADER_177 = "SIMOG_MASSLOADER_177 - CIG[$1] e CUI[$2] non coerenti, non fanno riferimento allo stesso LOTTO";
//	public static final String SIMOG_MASSLOADER_178 = "SIMOG_MASSLOADER_178 - Scheda \" $1 \" non inseribile, e' gia presente una Scheda \" $2 \"";
	public static final String SIMOG_MASSLOADER_178 = "SIMOG_MASSLOADER_178 - Scheda \" $1 \" non $3, e' gia presente una Scheda \" $2 \"";
//	public static final String SIMOG_MASSLOADER_179 = "SIMOG_MASSLOADER_179 - Si sta tentando di inserire i soli dati comuni quando questi risultano gia' presenti nella base di dati";
//	public static final String SIMOG_MASSLOADER_180 = "SIMOG_MASSLOADER_180 - Scheda non inserita a causa di errori riscontrati in un'altra scheda appartenente allo stesso blocco";
	// corretto nei casi di mancanza di anagrafica 172
	public static final String SIMOG_MASSLOADER_181 = "SIMOG_MASSLOADER_181 - Il codice fiscale del responsabile unico del procedimento, presente nei dati comuni non risulta valido";
	public static final String SIMOG_MASSLOADER_182 = "SIMOG_MASSLOADER_182 - Il CIG risulta annullato o non valido, impossibile aggiungere schede";
	public static final String SIMOG_MASSLOADER_183 = "SIMOG_MASSLOADER_183 - Gara non aggiudicata, non e' possibile inserire aggiudicazioni";

	public static final String SIMOG_MASSLOADER_186 = "SIMOG_MASSLOADER_186 - Il CUI indicato fa' riferimento ad una Aggiudicazione inesistente";
	public static final String SIMOG_MASSLOADER_187 = "SIMOG_MASSLOADER_187 - La ricerca per CIG non ha dato risultati, controllare l'esattezza del CIG";
	public static final String SIMOG_MASSLOADER_188 = "SIMOG_MASSLOADER_188 - Trovato duplicato, nella sezione \"$1\" sara' ignorato";
//	public static final String SIMOG_MASSLOADER_189 = "SIMOG_MASSLOADER_189 - Errore durante la ricerca di duplicati per la sezione $1";

	// added li' 3/11/2009 quelli commentati riportano i valori pensati
	// originariamente.
	public static final String SIMOG_MASSLOADER_190 = "SIMOG_MASSLOADER_190 - Se e' valorizzato uno dei campi id e' obbligatorio indicare il nome della scheda";
	public static final String SIMOG_MASSLOADER_191 = "SIMOG_MASSLOADER_191 - Il CUI e gli id forniti per la scheda $1 NON afferiscono alla stessa Aggiudicazione";
//	public static final String SIMOG_MASSLOADER_192 = "SIMOG_MASSLOADER_192 - Si e' incorsi in eccezione durante l'operazione di %operazione{inserimento|modifica|cancellazione|controllo}% di una scheda";
//	public static final String SIMOG_MASSLOADER_192 = "SIMOG_MASSLOADER_192 - Si e' incorsi in eccezione durante l'operazione di $1 di una scheda";
//	public static final String SIMOG_MASSLOADER_193 = "SIMOG_MASSLOADER_193 - E' Fallita l'operazione di %operazione{inserimento|cancellazione|modifica}% per la scheda %nomescheda%";
	public static final String SIMOG_MASSLOADER_193 = "SIMOG_MASSLOADER_193 - E' Fallita l'operazione di $1 per la scheda $2";
//	public static final String SIMOG_MASSLOADER_194 = "SIMOG_MASSLOADER_194 - La scheda Corrente risulta in %statoScheda{Richiesta Modifica|Cancellazione|Definizione}%  ";
	public static final String SIMOG_MASSLOADER_194 = "SIMOG_MASSLOADER_194 - La scheda Corrente risulta $1 ";
//	public static final String SIMOG_MASSLOADER_195 = "SIMOG_MASSLOADER_195 - Campo %nomecampo{idsimog|idLocale}% non valido";
	public static final String SIMOG_MASSLOADER_195 = "SIMOG_MASSLOADER_195 - Campo $1 non valido";
	public static final String SIMOG_MASSLOADER_196 = "SIMOG_MASSLOADER_196 - La scheda corrente risulta gia presente sul Db";

	public static final String SIMOG_MASSLOADER_197 = "SIMOG_MASSLOADER_197 - Caricamento Dati tramite $1 al fine della cancellazione Fallito";

	public static final String SIMOG_MASSLOADER_198 = "SIMOG_MASSLOADER_198 - Per la scheda $1 non e' consentita la comunicazione di variazioni anagrafiche";
	public static final String SIMOG_MASSLOADER_199 = "SIMOG_MASSLOADER_199 - Sono state rilevate delle anagrafiche NON valide, NON risulta per tanto possibile proseguire con il processamento del file";
	public static final String SIMOG_MASSLOADER_200 = "SIMOG_MASSLOADER_200 - Sono state indicate schede senza identificativo Locale, tutte le schede per il CIG verranno scartate";
	public static final String SIMOG_MASSLOADER_201 = "SIMOG_MASSLOADER_201 - In assenza del CUI, che indica la presenza di una aggiudicazione nella base dati, non e' possibile indicare in schede diverse dai dati comuni l'identificativo simog";
	public static final String SIMOG_MASSLOADER_202 = "SIMOG_MASSLOADER_202 - Sono presenti id locali e id simog, questa configurazione non e' permessa.";
	public static final String SIMOG_MASSLOADER_203 = "SIMOG_MASSLOADER_203 - Se e' presente il CUI, l'aggiudicazione deve gia' essere stata inserita, valorizzare l'id simog nella scheda aggiudicazione.";
	// syntax from pp
	public static final String SIMOG_MASSLOADER_204 = "SIMOG_MASSLOADER_204 - Id Locale era nullo, aggiornato con il valore indicato";
	public static final String SIMOG_MASSLOADER_205 = "SIMOG_MASSLOADER_205 - Errore applicativo imprevisto durante elaborazione, notificare al servizio tecnico";
	public static final String SIMOG_MASSLOADER_206 = "SIMOG_MASSLOADER_206 - La scheda e stata riaggiudicata e' non puo essere piu modificata";
	public static final String SIMOG_MASSLOADER_207 = "SIMOG_MASSLOADER_207 - Scheda non prevista per questo tipo di contratto ( $1 )";
	public static final String SIMOG_MASSLOADER_208 = "SIMOG_MASSLOADER_208 - La scheda puo' essere trasmessa solo per l'aggiudicazione principale";
	public static final String SIMOG_MASSLOADER_209 = "SIMOG_MASSLOADER_209 - Non e possibile eliminare perche esiste una scheda riaggiudicata";
	public static final String SIMOG_MASSLOADER_210 = "SIMOG_MASSLOADER_210 - La scheda riaggiudicata non esiste";
	public static final String SIMOG_MASSLOADER_211 = "SIMOG_MASSLOADER_211 - Impossibile effettuare la $1, non e' stata trovata alcuna Scheda da $2";
	public static final String SIMOG_MASSLOADER_212 = "SIMOG_MASSLOADER_212 - Variazione anagrafica: funzione non attiva. La richiesta viene ignorata";
	public static final String SIMOG_MASSLOADER_213 = "SIMOG_MASSLOADER_213 - Non e' stata richiesta alcuna operazione";
	public static final String SIMOG_MASSLOADER_214 = "SIMOG_MASSLOADER_214 - La Scheda $1 NON risulta confermata nel DB, impossibile effettuare la variazione anagrafica";
	public static final String SIMOG_MASSLOADER_215 = "SIMOG_MASSLOADER_215 - La sezione VariazioniSA deve essere l'unica presente nel file trasmesso, elaborazione interrotta";

	public static final String SIMOG_DELEGA_001 = "SIMOG_DELEGA_001 - Attenzione: dal $1 il CIG può' essere acquisito attraverso il sistema della sezione regionale $2 dell'Osservatorio, disponibile all'indirizzo: <a href='$3'>$3</a>";
	public static final String SIMOG_DELEGA_002 = "SIMOG_DELEGA_002 - Attenzione: dal $1 le comunicazioni previste dall'art. 7 comma 8 del Dlgs 163/2006 devono essere inviate esclusivamente attraverso il sistema della sezione regionale $2 dell'Osservatorio, disponibile all'indirizzo: <a href='$3'>$3</a>";
	public static final String SIMOG_DELEGA_003 = "SIMOG_DELEGA_003 - Attenzione: l'acquisizione del CIG e successive comunicazioni dovrebbero essere effettuate attraverso il sistema della sezione regionale $2 dell'Osservatorio, disponibile all'indirizzo: <a href='$3'>$3</a>";

	//MAC 34163 3.04.8
	public static final String SIMOG_DELEGA_004 = "SIMOG_DELEGA_004 - In virtu' delle funzioni delega la stazione appaltante non e' autorizzata all'invio della scheda";
		
	// PP 3.02.2
	public static final String SIMOG_ALLEGATI_001 = "SIMOG_ALLEGATI_001 - l'allegato non e' piu' disponibile per la visualizzazione";
	public static final String SIMOG_ALLEGATI_002 = "SIMOG_ALLEGATI_002 - errore imprevisto durante il reperimento dell'allegato";

	// messaggi richiesti da Dell'Amico per LoaderAppalto
	public static final String LOADER_APPALTO_001 = "LOADER_APPALTO_001 - E' presente piu' di una scheda";
	public static final String LOADER_APPALTO_002 = "LOADER_APPALTO_002 - Utente non autorizzato ad agire sull'appalto di cui sono stati trasmessi i dati";
	public static final String LOADER_APPALTO_003 = "LOADER_APPALTO_003 - Parametri di input non corretti";
	public static final String LOADER_APPALTO_004 = "LOADER_APPALTO_004 - Non e' presente alcuna scheda";
	public static final String LOADER_APPALTO_005 = "LOADER_APPALTO_005 - In base alla funzione di delega selezionata, non e' possibile procedere con la trasmissione dei dati";

//	3.04.7.1
	public static final String SIMOG_AVCPASS_001 = "SIMOG_FVOE_001 - ATTENZIONE: la gara e' attualmente gestita dal sistema FVOE, non sono consentite operazioni di modifica dei dati.";

	// MARRA MEV 34470 3.04.8
	public final static String SIMOG_BANDI_CONTRATTI_001 = "SIMOG_BANDI_CONTRATTI_001 - E' stata selezionata la voce 'Somma urgenza e protezione civile' di cui all'art. 140 D. Lgs. 36 del 31/03/2023. E' necessario valorizzare il 'link ai documenti relativi all'affidamento diretto in somma urgenza e protezione civile' con la URL della sezione 'Bandi e Contratti' dove sono pubblicati i documenti previsti dal regolamento di vigilanza ANAC";
	public final static String SIMOG_BANDI_CONTRATTI_001b = "SIMOG_BANDI_CONTRATTI_001b - link ai documenti relativi all’affidamento diretto in somma urgenza e protezione civile non è valido, sono consentiti i protocolli http e https";
	//FINE MEV
		
	// 3.04.8 Fase Due
	public static final String SIMOG_SOGG_AGGREG_VALIDAZIONE_001 = "SIMOG_SOGG_AGGREG_VALIDAZIONE_001 - Soggetto SA non presente nell'anagrafe soggetti aggregatori";
	// FINE 
	
	// public static final String SIMOG_MASSLOADER_1

	// ----------------- da reciclare ? -----------------------//
//	public final static String SIMOG_SQL_002 		= 	"SIMOG_SQL_002 - Errori non bloccanti durante il rilascio delle risorse DB";
//	public final static String SIMOG_SQL_007 		= 	"SIMOG_SQL_007 - Rollback Completata";

//	public final static String SIMOG_GARA_003		=	"SIMOG_GARA_003 - Errore nel recupero dell'ID sulla tabella GARE";
//	public final static String SIMOG_GARA_007 		= 	"SIMOG_GARA_007 - Errore di visualizzazione lista gare";
//	public final static String SIMOG_LOTTO_001 		= 	"SIMOG_LOTTO_001 - Errore nell'eliminazione del lotto";
//	public final static String SIMOG_LOTTO_004 		= 	"SIMOG_LOTTO_004 - Errore nel recupero dell'id nella tabella TABELLA_LOTTI";
//	public final static String SIMOG_LOTTO_009 		= 	"SIMOG_LOTTO_009 - Errore recupero tipi lotto";
//	public final static String SIMOG_LOTTO_015		=	"SIMOG_LOTTO_015 - Il campo Importo Lotto deve essere numerico";
//	public static final String SIMOG_UPLOAD_002 	= 	"SIMPG_UPLOAD_002 - Impossibile creare la cartella temporanea di archiviazione tabelle di servizio";
//	public static final String SIMOG_UPLOAD_003 	= 	"SIMPG_UPLOAD_003 - Impossibile procedere al caricamento degli aggiornamenti";
//	public static final String SIMOG_UPLOAD_008 	= 	"SIMPG_UPLOAD_008 - Il nome del file e' obbligatorio";
//	public static final String SIMOG_UPLOAD_009 	= 	"SIMPG_UPLOAD_009 - Dimensione massima del file superata";
//	public static final String SIMOG_TRS_002		=	"SIMOG_TRS_002 - Si e' verificato un errore durante il download delle CPV Correnti";
//	public static final String SIMOG_AGGIUDICAZIONI_001 = "SIMOG_AGGIUDICAZIONI_001 - Errore durante l'inserimento Dati Comuni Aggiudicazioni";
//	public static final String SIMOG_VALIDAZIONE_143 = "SIMOG_VALIDAZIONE_143 - $1: Importo non valido controllare";

//	public static final String SIMOG_VALIDAZIONE_002 = "SIMOG_VALIDAZIONE_002 - Il codice fiscale della Stazione Appaltante non e' valido";
//	public static final String SIMOG_VALIDAZIONE_003 = "SIMOG_VALIDAZIONE_003 - Non e' stato indicato il codice categoria della stazione appaltante";
//	public static final String SIMOG_VALIDAZIONE_004 = "SIMOG_VALIDAZIONE_004 - Il codice categoria indicato non e' valido";
//	public static final String SIMOG_VALIDAZIONE_005 = "SIMOG_VALIDAZIONE_005 - Il codice fiscale dell'Amm.ne per conto della quale agisce la SA non e' valido";
//	public static final String SIMOG_VALIDAZIONE_006 = "SIMOG_VALIDAZIONE_006 - Non e' stata indicata la denominazione dell'Amministrazione";
//	public static final String SIMOG_VALIDAZIONE_007 = "SIMOG_VALIDAZIONE_007 - CIG non valido";
//	public static final String SIMOG_VALIDAZIONE_010 = "SIMOG_VALIDAZIONE_010 - Il codice CPV non e' stato inserito";
//	public static final String SIMOG_VALIDAZIONE_011 = "SIMOG_VALIDAZIONE_011 - Il codice CPV inserito non corrisponde a un valore noto oppure non e' sufficientemente specifico";
//	public static final String SIMOG_VALIDAZIONE_012 = "SIMOG_VALIDAZIONE_012 - Il codice CUP non e' valido";
//	public static final String SIMOG_VALIDAZIONE_014 = "SIMOG_VALIDAZIONE_014 - Non e' stato indicato quali prestazioni siano comprese nell'appalto";
//	public static final String SIMOG_VALIDAZIONE_015 = "SIMOG_VALIDAZIONE_015 - Il codice ISTAT/NUTS inserito non e' valido";
//	public static final String SIMOG_VALIDAZIONE_016 = "SIMOG_VALIDAZIONE_016 - Non e' stato indicato il codice Istat del luogo di esecuzione del contratti";
//	public static final String SIMOG_VALIDAZIONE_017 = "SIMOG_VALIDAZIONE_017 - Non e' stato indicato l'importo complessivo";
//	public static final String SIMOG_VALIDAZIONE_018 = "SIMOG_VALIDAZIONE_018 - L'importo complessivo e' inferiore ai 150.000 euro";
//	public static final String SIMOG_VALIDAZIONE_019 = "SIMOG_VALIDAZIONE_019 - Non e' stata indicata la procedura di scelta del contraente";
//	public static final String SIMOG_VALIDAZIONE_021 = "SIMOG_VALIDAZIONE_021 - Non e' stata indicato il criterio di aggiudicazione";
//	public static final String SIMOG_VALIDAZIONE_028 = "SIMOG_VALIDAZIONE_028 - Non sono state indicate le motivazioni del contenzioso in fase di gara";
//	public static final String SIMOG_VALIDAZIONE_029 = "SIMOG_VALIDAZIONE_029 - Non e' stato indicato o non risulta valido il codice fiscale del ricorrente.";
//	public static final String SIMOG_VALIDAZIONE_033 = "SIMOG_VALIDAZIONE_033 - Non e' stato indicato il codice fiscale del legale rappresentante dell'impresa aggiudicataria";
//	public static final String SIMOG_VALIDAZIONE_034 = "SIMOG_VALIDAZIONE_034 - Non e' stato indicato il nominativo del legale rappresentante dell'impresa aggiudicataria";
//	public static final String SIMOG_VALIDAZIONE_036 = "SIMOG_VALIDAZIONE_036 - Non e' stata indicata la data del verbale di aggiudicazione o definizione della procedura negoziata";
//	public static final String SIMOG_VALIDAZIONE_037 = "SIMOG_VALIDAZIONE_037 - La data di aggiudicazione non e' cronologicamente coerente";
//	public static final String SIMOG_VALIDAZIONE_038 = "SIMOG_VALIDAZIONE_038 - Non e' stato indicato il codice fiscale del soggetto";
//	public static final String SIMOG_VALIDAZIONE_039 = "SIMOG_VALIDAZIONE_039 - Non e' stato indicato il nominativo del soggetto";
//	public static final String SIMOG_VALIDAZIONE_040 = "SIMOG_VALIDAZIONE_040 - Il numero di telefono deve essere interamente numerico";
//	public static final String SIMOG_VALIDAZIONE_041 = "SIMOG_VALIDAZIONE_041 - Il numero di fax deve essere interamente numerico";
//	public static final String SIMOG_VALIDAZIONE_042 = "SIMOG_VALIDAZIONE_042 - L'indirizzo e-mail indicato non e' valido";
//	public static final String SIMOG_VALIDAZIONE_043 = "SIMOG_VALIDAZIONE_043 - Non e' stato indicato il ruolo svolto dal soggetto nell'appalto";
//	public static final String SIMOG_VALIDAZIONE_044 = "SIMOG_VALIDAZIONE_044 - Il flag Accordo Quadro non e' valorizzato";
//	public static final String SIMOG_VALIDAZIONE_045 = "SIMOG_VALIDAZIONE_045 - Il valore deve essere  in percentuale fino alla terza cifra decimale";
//	public static final String SIMOG_VALIDAZIONE_046 = "SIMOG_VALIDAZIONE_046 - Non e' stato indicato il codice fiscale della ditta ausiliaria";
//	public static final String SIMOG_VALIDAZIONE_047 = "SIMOG_VALIDAZIONE_047 - Il codice fiscale inserito non e' corretto.";
//	public static final String SIMOG_VALIDAZIONE_048 = "SIMOG_VALIDAZIONE_048 - La data di scadenza presentazione manifestazioni di interesse non e' stata valorizzata oppure non e' cronologicamente valida.";       
//	public static final String SIMOG_VALIDAZIONE_049 = "SIMOG_VALIDAZIONE_049 - La data di scadenza per la presentazione della richiesta di invito non e' stata valorizzata oppure non e' cronologicamente valida.";  
//	public static final String SIMOG_VALIDAZIONE_050 = "SIMOG_VALIDAZIONE_050 - La data di invito non e' stata valorizzata oppure non e' cronologicamente valida.";                                                   
//	public static final String SIMOG_VALIDAZIONE_051 = "SIMOG_VALIDAZIONE_051 - La data scadenza presentazione offerte non e' stata valorizzata oppure non e' coerente.";                                                                            
//	public static final String SIMOG_VALIDAZIONE_052 = "SIMOG_VALIDAZIONE_052 - Il Num. soggetti che hanno presentato manifestazione di interesse non e' stato valorizzato oppure non e' coerente.";                                  
//	public static final String SIMOG_VALIDAZIONE_053 = "SIMOG_VALIDAZIONE_053 - Il Num. soggetti che hanno presentato richiesta di invito non e' stato valorizzato oppure non e' incoerente.";                                                             
//	public static final String SIMOG_VALIDAZIONE_054 = "SIMOG_VALIDAZIONE_054 - Il Num. di soggetti invitati a presentare offerta non e' stato valorizzato oppure non e' incoerente.";                                                          
//	public static final String SIMOG_VALIDAZIONE_055 = "SIMOG_VALIDAZIONE_055 - Il Num. di soggetti che hanno presentato offerta non e' stato valorizzato oppure non e' incoerente.";                                                             
//	public static final String SIMOG_VALIDAZIONE_056 = "SIMOG_VALIDAZIONE_056 - Il Num. di offerte ammesse non e' stato valorizzato oppure non e' incoerente.";                                                             
//	public static final String SIMOG_VALIDAZIONE_057 = "SIMOG_VALIDAZIONE_057 - Non e' stata indicata la data di pubblicazione su GUCE oppure la data non e' cronologicamente coerente.";                        
//	public static final String SIMOG_VALIDAZIONE_058 = "SIMOG_VALIDAZIONE_058 - Non e' stata indicata la data di pubblicazione su GURI oppure la data non e' cronologicamente coerente.";                        
//	public static final String SIMOG_VALIDAZIONE_059 = "SIMOG_VALIDAZIONE_059 - Non e' stata indicata la data di pubblicazione su ALBO oppure la data non e' cronologicamente coerente.";                        
//	public static final String SIMOG_VALIDAZIONE_062 = "SIMOG_VALIDAZIONE_062 - La denominazione dell'Amm.ne per conto della quale agisce la SA non e' stato valorizzato";
//	public static final String SIMOG_VALIDAZIONE_063 = "SIMOG_VALIDAZIONE_063 - La denominazione non risulta valida";
//	public static final String SIMOG_VALIDAZIONE_065 = "SIMOG_VALIDAZIONE_065 - Il valore del campo 'N. OFFERTE > SOGLIA ANOMALIA' non e' coerente";
//	public static final String SIMOG_VALIDAZIONE_066 = "SIMOG_VALIDAZIONE_066 - Il valore del campo 'N. IMPRESE ESCLUSE AUTOMATICAMENTE' non e' coerente";
//	public static final String SIMOG_VALIDAZIONE_067 = "SIMOG_VALIDAZIONE_067 - Il valore del campo 'N.IMPRESE ESCLUSE PER INS. GIUSTIFICAZIONI' non e' coerente";
//	public static final String SIMOG_VALIDAZIONE_069 = "SIMOG_VALIDAZIONE_069 - Non e' stata indicata la data stipula contratto";
//	public static final String SIMOG_VALIDAZIONE_070 = "SIMOG_VALIDAZIONE_070 - Non e' stato indicato l'importo cauzione definitiva";
//	public static final String SIMOG_VALIDAZIONE_071 = "SIMOG_VALIDAZIONE_071 - Non e' stata indicata la data prevista consegna lavori";
//	public static final String SIMOG_VALIDAZIONE_072 = "SIMOG_VALIDAZIONE_072 - Non e' stata indicata la data verbale consegna lavori";
//	public static final String SIMOG_VALIDAZIONE_073 = "SIMOG_VALIDAZIONE_073 - Non e' stato valorizzato il flag 'Consegna sotto riserva di legge'";
//	public static final String SIMOG_VALIDAZIONE_076 = "SIMOG_VALIDAZIONE_076 - Non e' stata indicato nessun soggetto responsabile";
//	public static final String SIMOG_VALIDAZIONE_077 = "SIMOG_VALIDAZIONE_077 - Non e' stata indicato il codice INAIL";
//	public static final String SIMOG_VALIDAZIONE_078 = "SIMOG_VALIDAZIONE_078 - Non e' stata indicato il codice INPS";
//	public static final String SIMOG_VALIDAZIONE_083 = "SIMOG_VALIDAZIONE_083 - Non e' stata indicata la data in cui e' stato raggiunto l'avanzamento";
//	public static final String SIMOG_VALIDAZIONE_084 = "SIMOG_VALIDAZIONE_084 - Il numero dello stato avanzamento non e' valido ";
//	public static final String SIMOG_VALIDAZIONE_085 = "SIMOG_VALIDAZIONE_085 - Non e' stata indicata la data di emissione del certificato di pagamento";
//	public static final String SIMOG_VALIDAZIONE_086 = "SIMOG_VALIDAZIONE_086 - Il valore del campo 'Importo del certificato di pagamento' non e' valido";
//	public static final String SIMOG_VALIDAZIONE_087 = "SIMOG_VALIDAZIONE_087 - Non e' stata indicata la data sottoscrizione eventuale atto aggiuntivo";
//	public static final String SIMOG_VALIDAZIONE_088 = "SIMOG_VALIDAZIONE_088 - Non e' stato indicato l'oggetto dell'Atto Aggiuntivo";
//	public static final String SIMOG_VALIDAZIONE_090 = "SIMOG_VALIDAZIONE_090 - Il valore del campo 'Importo dell'Atto aggiuntivo' non e' valido";
//	public static final String SIMOG_VALIDAZIONE_091 = "SIMOG_VALIDAZIONE_091 - Non e' stata indicata la data verbale ultimazione lavori";
//	public static final String SIMOG_VALIDAZIONE_092 = "SIMOG_VALIDAZIONE_092 - Non e' stato indicato il codice fiscale ditta subappaltatrice";
//	public static final String SIMOG_VALIDAZIONE_093 = "SIMOG_VALIDAZIONE_093 - Il 'codice fiscale ditta subappaltatrice' inserito non e' valido";
//	public static final String SIMOG_VALIDAZIONE_094 = "SIMOG_VALIDAZIONE_094 - Non e' stato indicato 'Lavoro / Servizio / Fornitura, subappaltato'";
//	public static final String SIMOG_VALIDAZIONE_095 = "SIMOG_VALIDAZIONE_095 - Il valore del campo 'Importo lavoro / Servizio / Fornitura,  subappaltato' non e' valido";	
//	public static final String SIMOG_VALIDAZIONE_096 = "SIMOG_VALIDAZIONE_096 - Errore: Numero offerte escluse automaticamente non valido";
//	public static final String SIMOG_VALIDAZIONE_097 = "SIMOG_VALIDAZIONE_097 - Errore: Numero Imprese escluse per insufficienti giustificazioni";
//	public static final String SIMOG_VALIDAZIONE_098 = "SIMOG_VALIDAZIONE_098 - Errore: Offerta Massimo Ribasso non valido";
//	public static final String SIMOG_VALIDAZIONE_099 = "SIMOG_VALIDAZIONE_099 - Errore: Offerta Minimo Ribasso non valido";
//	public static final String SIMOG_VALIDAZIONE_100 = "SIMOG_VALIDAZIONE_100 - Errore: Valore soglia anomalia non valido";
//	public static final String SIMOG_VALIDAZIONE_101 = "SIMOG_VALIDAZIONE_101 - Errore: Numero offerta maggiore soglia non valido";
//	public static final String SIMOG_VALIDAZIONE_102 = "SIMOG_VALIDAZIONE_102 - Errore: Numero percentuale non puo' essere superiore a 100";	
//	public static final String SIMOG_VALIDAZIONE_103 = "SIMOG_VALIDAZIONE_103 - Indicare tipi appalto coerenti con il tipo scheda";
//	public static final String SIMOG_VALIDAZIONE_162 = "SIMOG_VALIDAZIONE_162 - $1: Presenza del certificato di $2";
//	public static final String SIMOG_VALIDAZIONE_163 = "SIMOG_VALIDAZIONE_163 - $1: Inserire almeno una fra Data certificato di collaudo e Data del certificato di regolare esecuzione";
//	public static final String SIMOG_VALIDAZIONE_164 = "SIMOG_VALIDAZIONE_164 - Inserire almeno un importo fra lavori, servizi e forniture ";
//  vedi gestione accordo quadro controllo dipendenze cig
//  public static final String SIMOG_VALIDAZIONE_191 = "SIMOG_VALIDAZIONE_191 - Impossibile cancellare la Gara perch� il suo Lotto ha il CIG che � padre di gare / lotti";
//  public static final String SIMOG_VALIDAZIONE_192 = "SIMOG_VALIDAZIONE_192 - Impossibile modificare il valore di \"Modalita' di realizzazione\" della Gara perch� il suo Lotto ha il CIG che � padre di gare / lotti";

//	public static final String SIMOG_CPV_000 = "";
	/*
	 * 
	 * ERORRI CODIFICATI IN BASE ALLE CLASSI DI ERRORE
	 * 
	 * ERRORI DI DATABASE
	 * 
	 * SIMOG_SQL_XXX
	 * 
	 * es. SIMOG_SQL_001 - Impossibile connettersi al database di funzionamento
	 * 
	 * 
	 * 
	 * ERRORI DI COMUNICAZIONE
	 * 
	 * SIMOG_IO_XXX
	 * 
	 * es. SIMOG_IO_001 - Impossibile Contattare il sistema di autenticazione AVLP
	 * 
	 */
}
