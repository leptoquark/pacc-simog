package it.avlp.simog.validatore;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.validator.DateValidator;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.PercentValidator;
import org.apache.commons.validator.routines.TimeValidator;
import org.apache.log4j.Logger;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avcp.simog.managers.luogo.IstatManager;
import it.avcp.simog.managers.luogo.NutsManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAgg;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

public abstract class SimogValidator {

	protected Connection connection = null;
	protected Logger logger = null;

	static final int QUOTIDIANI_NAZ_MIN = 2;
	static final int QUOTIDIANI_LOC_MIN = 2;

	// PP data di riferimento per rilassare i controlli bloccanti della 3.02 in modo
	// da consentire l'acquisizione con i file in formato 2.9
	public static final String DATA_BLOCCANTI = "20110101";

	// is3028_RFWEBSC02Active
	static final int MAX_GIORNI = 18250;

	// is3028_RFWEBGL08Active
	public static final long GIORNO_MILLISEC = 86400000;

	protected AllValidationBeans mEccezioni;

	protected boolean isSQLConnectionEnabled = true;

	// is3028_RFWEBGL08Active
	private long giorni;

	public long getGiorni() {
		return giorni;
	}

	public void setGiorni(long giorni) {
		this.giorni = giorni;
	}

	public SimogValidator(Connection connection, Logger logger) {
		this.connection = connection;
		this.logger = logger;
		this.mEccezioni = new AllValidationBeans();
		this.giorni = Long.MAX_VALUE;
	}

	protected boolean validatePercentual(BigDecimal val) {
		BigDecimalValidator decVal = PercentValidator.getInstance();
		return (val != null) && decVal.isInRange(val, 0, 100);
	}

	/*************************************************************************************************
	 * Verifica che il valore in ingresso sia compreso nel range [min, max]
	 * 
	 * @param val
	 * @param min
	 * @param max
	 * @return boolean - true se il valore � compreso nel range , false altrimenti
	 */
	protected boolean isInRange(BigDecimal val, BigDecimal min, BigDecimal max) {
		BigDecimalValidator decVal = PercentValidator.getInstance();
		return (val != null) && decVal.isInRange(val, min, max);
	}

	/*************************************************************************************************
	 * Determina se la Stringa in ingresso abbia la lunghezza indicata
	 * 
	 * @param value          String contenente la stringa da valutare
	 * @param requiredLength int indica la lunghezza
	 * @return boolean - true se la stringa ha una lunghezza uguale a quella
	 *         indicata in requiredlength, false altrimenti
	 */

	protected boolean validateLength(String value, int requiredLength) {
		return (value.length() == requiredLength);
	}

	/*************************************************************************************************
	 * Verifica se la stringa in ingresso contenga un numero, usare solo per numeri
	 * interi!
	 * 
	 * @param str String
	 * @return boolean - true se la stringa rappresenta un numero decimale, False
	 *         altrimenti
	 */

	protected boolean isNumber(String str) {
		if (str == null)
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9')
				return false;
		}
		return true;
	}

	/*************************************************************************************************
	 * Verifica se la stringa in ingresso contenga un numero
	 * 
	 * @param str String
	 * @return boolean - true se la stringa rappresenta un numero decimale, False
	 *         altrimenti
	 */

	public static boolean isNumero(String str) {
		if (str == null)
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9')
				return false;
		}
		return true;
	}

	/*************************************************************************************************
	 * verifica se la sottostringa della stringa in ingresso che va dall'indice 0
	 * all'indice indicato dall'intero len contenga o meno un numero decimale.
	 * 
	 * @param str String in ingresso
	 * @param len int indice fino al quale si considera la sottostringa
	 * @return boolean - true se la sottostringa contiene un numero decimale, false
	 *         altrimneti
	 */
	protected boolean isNumber(String str, int len) {
		try {
			return isNumber(str.substring(0, len));
		} catch (Exception e) {
			return false;
		}
	}

	/*************************************************************************************************
	 * verifica se ls stringa in ingresso contiene caratteri vietati
	 * 
	 * @param str String in ingresso
	 * @return boolean - true se la sottostringa non contiene caratteri vietati
	 */
	protected boolean isValidChars(String str) {

		String forbiddenChars = "\""; // per ora solo l'apice doppio

		if (isEmpty(str))
			return true;

		try {
			return (str.indexOf(forbiddenChars) == -1);
		} catch (Exception e) {
			return false;
		}
	}

	/*************************************************************************************************
	 * Validatore per il codice fiscale, si occupa di verificare che il formato del
	 * codice fiscale inserito sia corretto.
	 * 
	 * @param codiceFiscale String contenente il codice fiscale da valutare
	 * @return boolean - true se il formato e'; corretto, false se il formato non e'
	 *         corretto
	 */
	protected boolean validaCodiceFiscale(String codiceFiscale) {
		int i, s, c;
		String cf2;
		int setdisp[] = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24,
				23 };

		if (codiceFiscale.length() == 0)
			return true;
		if (!validateLength(codiceFiscale, 16))
			return false;
		cf2 = codiceFiscale.toUpperCase();
		for (i = 0; i < 16; i++) {
			c = cf2.charAt(i);
			if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z'))
				return false;
		}
		s = 0;
		for (i = 1; i <= 13; i += 2) {
			c = cf2.charAt(i);
			if (c >= '0' && c <= '9')
				s = s + c - '0';
			else
				s = s + c - 'A';
		}
		for (i = 0; i <= 14; i += 2) {
			c = cf2.charAt(i);
			if (c >= '0' && c <= '9')
				c = c - '0' + 'A';
			s = s + setdisp[c - 'A'];
		}
		if (s % 26 + 'A' != cf2.charAt(15))
			return false;
		return true;
	}

	/*******************************************************************************************
	 * verifica validita della partita iva, controlla che il formato della partita
	 * iva inserito sia corretto.
	 * 
	 * @param pi String partita iva da validare
	 * @return boolean - true se valida, altrimenti false
	 */

	protected boolean validaPartitaIva(String pi) {
		if (pi == null)
			return false;

		int i, c, s;
		if (pi.length() == 0)
			return true;
		if (!validateLength(pi, 11))
			return false;

		if (!isNumber(pi))
			return false;

		s = 0;
		for (i = 0; i <= 9; i += 2)
			s += pi.charAt(i) - '0';
		for (i = 1; i <= 9; i += 2) {
			c = 2 * (pi.charAt(i) - '0');
			if (c > 9)
				c = c - 9;
			s += c;
		}
		if ((10 - s % 10) % 10 != pi.charAt(10) - '0')
			return false;
		return true;
	}

	/*********************************************************************************************
	 * Returns true if the parameter is an Empty/null/zero Object
	 * 
	 * @param o Object to check
	 * @return result boolean
	 */
	protected boolean isEmptyOrZero(Object o) {

		boolean retVal = isEmpty(o, true);
		if (retVal)
			return retVal;

		// test se empty numerico
		if (o instanceof BigDecimal)
			retVal = ((BigDecimal) o).compareTo(BigDecimal.ZERO) == 0;
		else if (o instanceof Double)
			retVal = ((Double) o).compareTo(new Double(0)) == 0;
		else if (o instanceof Long)
			retVal = ((Long) o).compareTo(new Long(0)) == 0;
		else if (o instanceof Float)
			retVal = ((Float) o).compareTo(new Float(0)) == 0;
		else if (o instanceof Integer)
			retVal = ((Integer) o).compareTo(new Integer(0)) == 0;
		else if (o instanceof String)
			retVal = ((String) o).trim().compareTo("0") == 0;

		return retVal;
	}

	/*********************************************************************************************
	 * Returns true if the parameter is an Empty/null/Less than zero Object
	 * 
	 * @param o Object to check
	 * @return result boolean
	 */
	protected boolean isEmptyOrLessZero(Object o) {

		boolean retVal = isEmpty(o, true);
		if (retVal)
			return retVal;

		// test se empty numerico or less than zero
		if (o instanceof BigDecimal)
			retVal = ((BigDecimal) o).compareTo(BigDecimal.ZERO) < 0;
		else if (o instanceof Double)
			retVal = ((Double) o).compareTo(new Double(0)) < 0;
		else if (o instanceof Long)
			retVal = ((Long) o).compareTo(new Long(0)) < 0;
		else if (o instanceof Float)
			retVal = ((Float) o).compareTo(new Float(0)) < 0;
		else if (o instanceof Integer)
			retVal = ((Integer) o).compareTo(new Integer(0)) < 0;
		else if (o instanceof String)
			retVal = ((String) o).trim().compareTo("0") < 0;

		return retVal;
	}

	/**********************************************************************************************
	 * Accerta se una stringa è valorizzata con stringa vuota, ma non null.
	 * 
	 * usata solo per il massloader
	 * 
	 * @param o Object
	 * @return boolean
	 */
	protected boolean isStringEmptyValue(String o) {
		return o == null ? false : "".equals(o.trim());
	}

	/**********************************************************************************************
	 * Accerta se un oggetto sia vuoto o meno.
	 * 
	 * @param o Object
	 * @return boolean
	 */
	protected boolean isEmpty(Object o) {
		return isEmpty(o, false);
	}

	/***********************************************************************************************
	 * Accerta se un Object sia vuoto o meno.
	 * 
	 * @param o      Object
	 * @param orZero boolean
	 * @return boolean
	 */
	private boolean isEmpty(Object o, boolean orZero) {

		if (o == null)
			return true;

		if (o instanceof Collection<?>)
			return ((Collection<?>) o).size() == 0;
		else
			return (o == null || (o.toString().trim().length() == 0));
	}

	/************************************************************************************************
	 * Accerta che la Stringa in ingresso sia una data corretta nel formato yyyymmdd
	 * 
	 * @param data String
	 * @return boolean
	 */
	protected boolean isDate(String data) {
		// cambiata la data nel formato yyyy-mm-dd
		// data = PageHelper.formatDateOrNull(data);
		// return DateValidator.getInstance().isValid(data, "yyyymmdd", false);
		// patch richiesta da AVCP il 15.07.2010

		boolean retVal = false;
		// cambiata la data nel formato yyyymmdd
		data = PageHelper.formatDateOrNull(data);

		// 50 anni dalla data di sistema
		String dataComp = String.valueOf(50 + Integer.valueOf(PageHelper.getYear(PageHelper.getCurrentDate())))
				+ "0101";
		if (DateValidator.getInstance().isValid(data, "yyyyMMdd", true) && data != null && data.length() == 8
				&& "19700101".compareTo(data.substring(0, 8)) <= 0 && dataComp.compareTo(data.substring(0, 8)) >= 0)
			retVal = true;

		return retVal;

	}

	/************************************************************************************************
	 * Accerta che la Stringa in ingresso sia un'ora corretta nel formato HH24:MM
	 * 
	 * @param data String
	 * @return boolean
	 */
	protected boolean isValidTime(String ora) {

		boolean retVal = false;

		if (ora != null && ora.length() == 5 && TimeValidator.getInstance().isValid(ora, "HH:mm"))
			retVal = true;

		return retVal;

	}

	/************************************************************************************************
	 * verifica se il valore in ingresso, sia esso un integer, un Long, un double o
	 * un BigDecimal, sia positivo o meno. Se l'Object o in ingresso non ha questi
	 * formati il metodo retituisce comunque false.
	 * 
	 * @param o Object.
	 * @return true se il valore &egrave; un positivo o zero, false se il valore
	 *         &egrave; negativo o se il formato non &egrave; Integer, Long, Double
	 *         o BigDecimal
	 */
	protected boolean isPositive(Object o) {

		if (o instanceof Integer) {
			return ((Integer) o).intValue() >= 0;
		} else if (o instanceof Long) {
			return ((Long) o).longValue() >= 0;
		} else if (o instanceof Float) {
			return ((Float) o).floatValue() >= 0.00;
		} else if (o instanceof Double) {
			return ((Double) o).doubleValue() >= 0.00;
		} else if (o instanceof BigDecimal) {
			return ((BigDecimal) o).doubleValue() >= 0.00;
		} else
			return false;

	}

	/*************************************************************************************************
	 * Verifica se il formato della mail in ingresso sia corretto
	 * 
	 * @param mail : String
	 * @return boolean : true se il formato risulta essere quello di una indirizzo
	 *         mail, false altrimenti
	 */
	protected boolean isMail(String mail) throws NoClassDefFoundError {// Ticket ALM #3159
		return EmailValidator.getInstance().isValid(mail);
	}

	/**************************************************************************************************
	 * Validatore per la parte decimale, controlla se la parte decimale abbia
	 * lunghezza indicata in decimalQua o minore
	 * 
	 * @param num        BigInteger
	 * @param decimalQua int
	 * @return boolean true se la lunghezza della parte decimale e' inferiore a
	 *         quella indicata in decimalQua, false altrimenti
	 */
	protected boolean validateDecimalPart(BigDecimal num, int decimalQua) {
		try {
			String str = num.toString();

			if (str.contains("."))
				return (str.substring(str.indexOf(".") + 1).length() <= decimalQua)
						&& str.indexOf(".") == str.lastIndexOf(".");
			else
				return true;

		} catch (Exception e) {
			return false;
		}

	}

	/**********************************************************************************************
	 * Controlla il valore del flag in base ai valori standard
	 * 
	 * @param val String
	 * @return boolean - true se uguale a valore SI o NO altrimenti false
	 */
	protected boolean isFlag(String val) {

		return val != null && (Costanti.FLAG_VALORE_SI.equals(val) || Costanti.FLAG_VALORE_NO.equals(val));
	}

	/****************************************************************
	 * il metodo determina se il flag contenga o meno il valore SI.
	 * 
	 * @param val String per i Flag da valutare
	 * @return boolean - true se contiene il valore Si, false altromenti
	 */
	protected boolean isYFlag(String val) {

		return isFlag(val) && Costanti.FLAG_VALORE_SI.equalsIgnoreCase(val);
	}

	protected boolean isNFlag(String val) {

		return isFlag(val) && Costanti.FLAG_VALORE_NO.equalsIgnoreCase(val);
	}

	protected boolean isQFlag(String val) {

		return Costanti.FLAG_VALORE_Q.equalsIgnoreCase(val);
	}

	public abstract boolean valida(Object bean, String section);

	/*********************************************************************
	 * il metodo si occupa di restituire il parametro mEccezioni definito nella
	 * classe contenente i messaggi relativi alle accezioni rilevate
	 * 
	 * @return AllValidatorBeans
	 */
	public AllValidationBeans getEccezioni() {
		return mEccezioni;
	}

	/**********************************************************************************************
	 * Validatore per la Pubblicazione, effettua il controllo sui campi
	 * <ul>
	 * <li>Data GUCE
	 * <li>Data GURI
	 * <li>Data pubblicazione Albo Pretorio
	 * <li>Num Quotidiani nazionali
	 * <li>Num Quotidiani regionali
	 * <li>Flag Profilo Committente
	 * <li>Flag sito Ministero Infrastrutture
	 * <li>Flag sito Informatico Osservatorio
	 * </ul>
	 * 
	 * @param pubblicazione PubblicazioneBean
	 */
	public void validaPubblicazioneBase(PubblicazioneBean pubblicazione, float importo_lotto) {
		// ---------------------------- DATA GUCE ------------------------------------

		// 2.10 importo_lotto_temporaneo
		// float importo_lotto = 10000;

		if (!isEmptyOrZero(pubblicazione.getDataGuce())) {
			try {
				if (!isDate(pubblicazione.getDataGuce()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_DataGUCE");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "GUCE"));
			}
		}
		// 2.10 aggiunto controllo 7.1.1.19
		if (isEmpty(pubblicazione.getDataGuce())) {
			try {
				if (importo_lotto > Costanti.IMPORTO_LOTTO_4845000 || importo_lotto == Costanti.IMPORTO_FUORI_SCALA)
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "pubblicazione GUCE"));
			}
		}
		// 2.10 fine controllo 7.1.1.19

		if (!isEmptyOrZero(pubblicazione.getDataBore())) {
			try {
				if (!isDate(pubblicazione.getDataBore()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_BollettinoRegionale");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Bollettino Regionale"));
			}
		}
		/*
		 * else
		 * mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
		 * "pubblicazione Bollettino Regionale"));
		 */
		// ---------------------------- DATA GURI
		// ----------------------------------------
		if (!isEmptyOrZero(pubblicazione.getDataGuri())) {
			try {
				if (!isDate(pubblicazione.getDataGuri()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_DataGURI");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "GURI"));
			}
		}
		// aggiunto controllo 7.1.1.21
		if (isEmpty(pubblicazione.getDataGuri())) {
			try {
				if (importo_lotto >= Costanti.IMPORTO_LOTTO_500000 || importo_lotto == Costanti.IMPORTO_FUORI_SCALA)
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "pubblicazione GURI"));
			}
		}
		// 2.10 fine controllo 7.1.1.21
		/*
		 * else
		 * mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
		 * "pubblicazione GURI"));
		 */
		// ------------------------------ DATA ALBO Pretorio
		// ---------------------------------
		if (!isEmptyOrZero(pubblicazione.getDataAlbo())) {
			try {
				if (!isDate(pubblicazione.getDataAlbo()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_DataAlbo");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Albo pretorio del comune ove si eseguono i lavori"));
			}
		}
		/*
		 * else
		 * mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
		 * "Albo pretorio del comune ove si eseguono i lavori"));
		 */
		// ---------------------------- Quotidiani Nazionali
		// ---------------------------------------

		// 2.10 aggiunto controllo 7.1.1.23
		if (!isEmpty(pubblicazione.getQuotidianiNaz())) {
			try {
				if (!isNumber(pubblicazione.getQuotidianiNaz().toString()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_QuotidianiNazionali");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Quotidiani Nazionali"));
			}
			try {
				if ((QUOTIDIANI_NAZ_MIN > pubblicazione.getQuotidianiNaz())
						&& (importo_lotto > Costanti.IMPORTO_LOTTO_4845000
								|| importo_lotto == Costanti.IMPORTO_FUORI_SCALA))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_060);
			}
		}
		// 2.10 fine controllo 7.1.1.23

		// ------------------------------ Quotidiani Regionali
		// ---------------------------------------

		// 2.10 aggiunto controllo 7.1.1.24
		if (!isEmpty(pubblicazione.getQuotidianiReg())) {
			try {
				if (!isNumber(pubblicazione.getQuotidianiReg().toString()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_QuotidianiRegionali");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Quotidiani Regionali"));
			}
			try {
				if ((QUOTIDIANI_LOC_MIN > pubblicazione.getQuotidianiReg())
						&& (importo_lotto > Costanti.IMPORTO_LOTTO_4845000
								|| importo_lotto == Costanti.IMPORTO_FUORI_SCALA))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_061);
			}
		}
		// 2.10 fine controllo 7.1.1.24

// ------------------------------ Periodici ---------------------------------------

		// 2.10 aggiunto controllo 7.1.1.28
		if (!isEmpty(pubblicazione.getPeriodici())) {
			try {
				if (!isNumber(pubblicazione.getPeriodici().toString()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_Periodici");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Periodici"));
			}
		}
		// 2.10 fine controllo 7.1.1.28

		// -------------------------- Flag per il profilo committente
		// --------------------------------------
		try {
			if (!isFlag(pubblicazione.getProfiloCommitente()))
				throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationField("label_ProfiloCommitente");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Link sito committente"));
		}
		// 2.10 aggiunto controllo 7.1.1.26
		if (isNFlag(pubblicazione.getProfiloCommitente())) {
			try {
				if (importo_lotto > Costanti.IMPORTO_LOTTO_4845000 || importo_lotto == Costanti.IMPORTO_FUORI_SCALA)
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_200.replace("$1", "Link sito committente"));
			}
		}
		// 2.10 fine controllo 7.1.1.26

		// ------------------- Flag per ministeo infrastrutture e trasporti
		// ---------------------------------
		try {
			if (!isEmpty(pubblicazione.getSitoMinisteroInfTrasp()) && !isFlag(pubblicazione.getSitoMinisteroInfTrasp()))
				throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationField("label_MinisteroInfrastrutture");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Sito Ministero Infrastrutture"));
		}
		// 2.10 aggiunto controllo 7.1.1.27
		if (isNFlag(pubblicazione.getSitoMinisteroInfTrasp())) {
			try {
				if (importo_lotto > Costanti.IMPORTO_LOTTO_4845000 || importo_lotto == Costanti.IMPORTO_FUORI_SCALA)
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_200.replace("$1", "Sito del Ministero Infrastrutture"));
			}
		}
		// 2.10 fine controllo 7.1.1.27

		// Flag sito informatico Osservatorio
		try {
			if (!isFlag(pubblicazione.getSitoOsservatorioCP()))
				throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationField("label_InformaticoOsservatorio");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Sito Informatico Osservatorio"));
		}
	}

	/*********************************************************************************************************************
	 * Determina se la scheda in ingresso e' di tipo lavori o meno.
	 * 
	 * @param tipoScheda String
	 * @return boolean - True se la scheda in ingresso &egrave; di tipo lavori,
	 *         False se la schea non &egrave; di tipo lavori
	 */
	protected boolean isLavori(String tipoScheda) {
		return Costanti.TIPO_SCHEDA_LAVORI.equals(tipoScheda);
	}

	/*********************************************************************************************************************
	 * Determina se la scheda in ingresso � di tipo servizi o meno.
	 * 
	 * @param tipoScheda String
	 * @return boolean - True se la scheda in ingresso &egrave; di tipo lavori,
	 *         False se la schea non &egrave; di tipo lavori
	 */
	protected boolean isServizi(String tipoScheda) {
		return Costanti.TIPO_SCHEDA_SERVIZI.equals(tipoScheda);
	}

	/*********************************************************************************************************************
	 * Determina se la scheda in ingresso � di tipo forniture o meno.
	 * 
	 * @param tipoScheda String
	 * @return boolean - True se la scheda in ingresso &egrave; di tipo lavori,
	 *         False se la schea non &egrave; di tipo lavori
	 */
	protected boolean isForniture(String tipoScheda) {
		return Costanti.TIPO_SCHEDA_FORNITURE.equals(tipoScheda);
	}

	/*********************************************************************************************************************
	 * Determina se l'ente specificato sia Speciale o meno.
	 * 
	 * @param tipoEnte
	 * @return boolean - True se l'ente &egrave; speciale, False altrimenti
	 */
	protected boolean isSpeciale(String tipoEnte) {
		return Costanti.TIPO_ENTE_SPECIALE.equals(tipoEnte);
	}

	/*********************************************************************************************************************
	 * Determina se l'ente specificato sia Ordinario o meno.
	 * 
	 * @param tipoEnte
	 * @return boolean - True se l'ente &egrave; speciale, False altrimenti
	 */
	protected boolean isOrdinario(String tipoEnte) {
		return !isSpeciale(tipoEnte);
	}
	// ------- adds 25 marzo --------------//

	/**********************************************************************************************************************
	 * determina se la data 'o' sia maggiore o uguale alla data 'o1', se gli Object
	 * in ingresso sono dei Timestamp queti vengono opportunamente formattati dopo
	 * di che si effettua il confronto.
	 *
	 * @param o  : Object
	 * @param o1 : Object
	 * @return boolean - True se la prima data inserita 'o' risulta maggiore o
	 *         uguala alla seconda data inserita ossia 'o1'
	 */
	protected boolean isDateBiggerEq(Object o, Object o1) {
		if (!isEmpty(o) && !isEmpty(o1)) {
			if (o instanceof Timestamp) {
				o = PageHelper.formatTimeStamp((Timestamp) o);
			}
			if (o1 instanceof Timestamp) {
				o1 = PageHelper.formatTimeStamp((Timestamp) o1);
			}
			return PageHelper.formatDateOrNull(((String) o)).compareTo(PageHelper.formatDateOrNull((String) o1)) >= 0;
		} else {
			return false;
		}
	}

	/*************************************************************************************************
	 * Il metodo confronta le date e determina se la prima data passata come
	 * parametro risulta minore o uguale alla seconda data passata come parametro.
	 * Se gli Object in ingresso sono dei Timestamp queti vengono opportunamente
	 * formattati dopo di che si effettua il confronto.
	 * 
	 * @param o  : Object
	 * @param o1 : Object
	 * @return boolean - True se la prima data passata 'o' risulta minore o uguale
	 *         alla secoda data passata 'o1'
	 */
	protected boolean isDateLowerEq(Object o, Object o1) {
		if (!isEmpty(o) && !isEmpty(o1)) {
			if (o instanceof Timestamp) {
				o = PageHelper.formatTimeStamp((Timestamp) o);
			}
			if (o1 instanceof Timestamp) {
				o1 = PageHelper.formatTimeStamp((Timestamp) o1);
			}
			return PageHelper.formatDateOrNull(((String) o)).compareTo(PageHelper.formatDateOrNull((String) o1)) <= 0;
		} else {
			return false;
		}
	}

	/************************************************************************************************
	 * Il metodo determina se la prima data inserita come parametro risulta maggiore
	 * dalla seconda data inserita come parametro. Se gli Object in ingresso sono
	 * dei Timestamp queti vengono opportunamente formattati dopo di che si effettua
	 * il confronto.
	 * 
	 * @param o  : Object
	 * @param o1 : Object
	 * @return boolean
	 */
	protected boolean isDateBigger(Object o, Object o1) {
		if (!isEmpty(o) && !isEmpty(o1)) {
			if (o instanceof Timestamp) {
				o = PageHelper.formatTimeStamp((Timestamp) o);
			}
			if (o1 instanceof Timestamp) {
				o1 = PageHelper.formatTimeStamp((Timestamp) o1);
			}
			// logger.debug(""+PageHelper.formatDateOrNull(((String)o))+",
			// "+PageHelper.formatDateOrNull((String)o1)+", bigger or lower?
			// "+PageHelper.formatDateOrNull(((String)o)).compareTo(PageHelper.formatDateOrNull((String)o1)));
			return PageHelper.formatDateOrNull(((String) o)).compareTo(PageHelper.formatDateOrNull((String) o1)) > 0;
		} else {
			return false;
		}
	}

	/************************************************************************************************
	 * Il metodo determina se la prima data inserita 'o' sia minore della seconda
	 * data inserita 'o1' Se gli Object in ingresso sono dei Timestamp queti vengono
	 * opportunamente formattati dopo di che si effettua il confronto.
	 * 
	 * @param o  : Object, prima data
	 * @param o1 : Object, seconda data
	 * @return boolean
	 */
	protected boolean isDateLower(Object o, Object o1) {
		if (!isEmpty(o) && !isEmpty(o1)) {
			if (o instanceof Timestamp) {
				o = PageHelper.formatTimeStamp((Timestamp) o);
			}
			if (o1 instanceof Timestamp) {
				o1 = PageHelper.formatTimeStamp((Timestamp) o1);
			}
			return PageHelper.formatDateOrNull(((String) o)).compareTo(PageHelper.formatDateOrNull((String) o1)) < 0;
		} else {
			return false;
		}
	}

	/************************************************************************************************
	 * Il metodo determina se l'anno della data inserita e superiore all'anno in
	 * corso
	 * 
	 * @param data : String data
	 * @return boolean
	 */
	protected boolean isDateYearBigger(String data) {
		if (data != null) {
			// logger.debug(""+PageHelper.getYear(PageHelper.getFormattedDBDate(data))+",
			// "+PageHelper.getYear(PageHelper.formatDate(new java.util.Date()))+", bigger ?
			// "+(PageHelper.getYear(PageHelper.getFormattedDBDate(data)).compareTo(PageHelper.getYear(PageHelper.formatDate(new
			// java.util.Date())))>0));
			return PageHelper.getYear(PageHelper.getFormattedDBDate(data))
					.compareTo(PageHelper.getYear(PageHelper.formatDate(new java.util.Date()))) > 0;
		} else {
			return false;
		}
	}

	/************************************************************************************************
	 * Il metodo restituisce l'anno in corso della data in formato yyyymmdd
	 * 
	 * @param
	 * @return String
	 */
	protected String getYearData(String data) {
		if (data != null)
			return data.substring(0, 4);
		else
			return null;
	}

	/*************************************************************************************************
	 * Validatore per il ruolo, restituisce true se l'id ruolo e' contenuto tra i
	 * ruoli della sezione, false altrimenti
	 *
	 * @param idRuolo long per la descrizione dell'id del ruolo
	 * @param sezione String identificante la sezione.
	 * @param o       deve essere Timestamp o String [yyyymmdd] per l'estensione
	 *                della validit� di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	protected boolean isRuoloValido(long idRuolo, String sezione, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!this.isSQLConnectionEnabled) {
			return true;
		}

		ResponsabileManager rman = new ResponsabileManager(connection, logger);
		return rman.loadRuoliSezione(sezione, o).containsKey(String.valueOf(idRuolo));
	}

	/*************************************************************************************************
	 * Il metodo calcola la somma tra i seguenti importi:
	 * <ul>
	 * <li>importo lavori
	 * <li>importo servizi
	 * <li>importo forniture
	 * <li>importo attuazione sicurezza
	 * <li>importo somme non assoggettate al ribasso d'asta
	 * <li>importo progettazione
	 * </ul>
	 * contenuti nell'AggiudicazioneBean ed importoComplessivo.
	 * 
	 * @param aggBea             AggiudicazioneBean da cui prelevare i valori degli
	 *                           importi
	 * @param importoComplessivo BigDecimal al quale andranno addizionati gli
	 *                           importi
	 * @return BigDecimal
	 */
	// 2.10 aggiunto nuovo metodo per il controllo 9.1.1.35

	/***
	 * PP 20.01.2016 protected double getImportoComplessivoAppalto(BigDecimal l,
	 * BigDecimal s , BigDecimal f, BigDecimal sicurezza, BigDecimal nonassog,
	 * BigDecimal progettazione){ double temp = 0.00; if(l != null) temp +=
	 * l.doubleValue(); if(s != null) temp += s.doubleValue(); if(f != null) temp +=
	 * f.doubleValue(); if(sicurezza != null) temp += sicurezza.doubleValue();
	 * if(nonassog != null) temp += nonassog.doubleValue(); if(progettazione !=
	 * null) temp += progettazione.doubleValue(); return temp; }
	 ***/
	// 2.10 fine nuovo metodo per il controllo 9.1.1.35
	protected double getImportoComplessivoAppalto(BigDecimal l, BigDecimal s, BigDecimal f, BigDecimal sicurezza,
			BigDecimal nonassog, BigDecimal progettazione) {
		BigDecimal temp = new BigDecimal(0.000);
		// double temp2 = 0.000;
		if (l != null)
			temp = temp.add(l);
		if (s != null)
			temp = temp.add(s);
		if (f != null)
			temp = temp.add(f);
		if (sicurezza != null)
			temp = temp.add(sicurezza);
		if (nonassog != null)
			temp = temp.add(nonassog);
		if (progettazione != null)
			temp = temp.add(progettazione);

		return temp.doubleValue();
	}

	/*************************************************************************************************
	 * Il metodo calcola la somma tra i seguenti importi:
	 * <ul>
	 * <li>importo lavori
	 * <li>importo servizi
	 * <li>importo forniture
	 * <li>importo attuazione sicurezza
	 * <li>importo progettazione
	 * <li>importo a disposizione
	 * </ul>
	 * contenuti nell'AggiudicazioneBean ed importoComplessivo.
	 * 
	 * @param aggBea             AggiudicazioneBean da cui prelevare i valori degli
	 *                           importi
	 * @param importoComplessivo BigDecimal al quale andranno addizionati gli
	 *                           importi
	 * @return BigDecimal
	 */
	protected BigDecimal calcola(AggiudicazioneBean aggBea, BigDecimal importoComplessivo) {
		BigDecimal[] importo = new BigDecimal[] { aggBea.getImportoLavori(), aggBea.getImportoServizi(),
				aggBea.getImportoForniture(), aggBea.getImportoAttuazioneSicurezza(), aggBea.getImportoProgettazione(),
				aggBea.getImportoDisposizione() };
		for (int i = 0; i < importo.length; i++) {
			if (importo[i] != null) {
				importoComplessivo = importoComplessivo.add(importo[i]);
			}
		}
		return importoComplessivo;
	}

	/**************************************************************************************************
	 * Effettua la somma dei BigDecimal contenuti nel vettore
	 * 
	 * @param big  BigDecimal[], vettore degli elementi BigDecimal che andranno
	 *             sommati
	 * @param big1 BigDecimal cui verranno addizionati gli elementi contenuti in big
	 * @return BigDecimal
	 */
	protected BigDecimal calcola(BigDecimal[] big, BigDecimal big1) {
		for (int i = 0; i < big.length; i++) {
			if (big[i] != null) {
				big1 = big1.add(big[i]);
			}
		}
		return big1;
	}

	/***************************************************************************************************
	 * Il metodo permette la validazioen di una lista di responsabili.
	 * 
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 */
	protected void valida(List<ResponsabileBean> responsabili) {
		int i = 1;
		for (ResponsabileBean responsabile : responsabili) {
			valida(responsabile, i);
			i++;
		}
	}

	/***************************************************************************************************
	 * Esegue la validazione del responsabile, verificando sel l'id del responsabile
	 * sia nullo o vuoto e se l'id del ruolo sia valido per la sezione in
	 * considerazione. I messaggi di errore della validazione vengono inseriti in
	 * mEccezioni.
	 * 
	 * @param responsabile ResponsabileBean associato al responsabile da validare
	 */

	protected void valida(ResponsabileBean responsabile, int indice) {
		try {
			if (isEmptyOrZero(responsabile.getIdRuolo())) {
				throw new Exception();
				// }else if(!isRuoloValido(responsabileInizio.getIdRuolo(), PSBD.SEZIONE_IN)){
			} else if (!isRuoloValido(responsabile.getIdRuolo(), responsabile.getSezione(),
					responsabile.getDataInizioScheda())) {
				throw new Exception();
			}

		} catch (Exception e) {
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Ruolo incaricato"),
					indice);
		}
		// dati angrafici fatti a parte
	}

	/*****************************************************************************************************
	 * Ad ogni elemento della lista di ResponsabileBean viene aggiunta la sezione
	 * indicat dalla stringa sezione
	 *
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 * @param sezione      String identificante la sezione
	 */
	protected void aggiungiSezione(List<ResponsabileBean> responsabili, String sezione) {
		for (ResponsabileBean responsabile : responsabili) {
			responsabile.setSezione(sezione);
		}
	}

	/******************************************************************************************************
	 * Il metodo riconosce se la stringa passata come parametro e' relativa ad un
	 * numero decimale
	 * 
	 * @param str String contenente il valore da valutare
	 * @return boolean - true se la stringa e' associabile ad un numero con
	 *         decimali, false altrimenti
	 */
	protected boolean isNumberDecimal(String str) {
		if (str == null)
			return false;
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				if (str.charAt(i) == '.') {
					counter++;
				} else {
					return false;
				}
			}
		}
		if (counter > 1) {
			return false;
		}
		return true;
	}

	/**
	 * metodo per la validazione delle tipologiche, usa il metodo getTipologica di
	 * accessidb
	 * 
	 * @param nomeTabella      String
	 * @param campoId          String nome campo id
	 * @param campoDescrizione String nome campo descrizione
	 * @param campoValidita    String nome campo validita [es. data fine scheda]
	 * @param data             Object deve essere timestamp o String[yyyymmdd] data
	 *                         del confronto
	 * @param id               Object String? o Long? l'id di cui verificare
	 *                         l'esitenza
	 * @return boolean
	 */
	protected boolean validaTipologica(String nomeTabella, String campoId, String campoDescrizione,
			String campoValidita, Object data, Object id) {
		/** se non ho una base dati valida ritorno true **/
		if (!this.isSQLConnectionEnabled) {
			return true;
		}

		AccessiDB adb = new AccessiDB(connection, logger);
		Map<String, String> map = new TreeMap<String, String>();
		try {
			// se data nulla la scheda ancora non � stata inserita quind getNow()
			if (data == null) {
				data = adb.getNow();
			}
			map = adb.getTipologica(nomeTabella, campoId, campoDescrizione, campoValidita, data);

			if (id instanceof Long) {
				return map.containsKey(((Long) id).toString());
			}
			return map.containsKey(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// TICKET ALM #2847
	/**
	 * metodo per la validazione delle tipologiche, usa il metodo getTipologica di
	 * accessidb
	 * 
	 * @param nomeTabella      String
	 * @param campoId          String nome campo id
	 * @param campoDescrizione String nome campo descrizione
	 * @param campoValidita    String nome campo validita [es. data fine scheda]
	 * @param data             Object deve essere timestamp o String[yyyymmdd] data
	 *                         del confronto
	 * @param id               Object String? o Long? l'id di cui verificare
	 *                         l'esitenza
	 * @return boolean
	 */
	protected boolean validaTipologicaWithData(String nomeTabella, String campoId, String campoDescrizione,
			String campoInizioValidita, String campoFineValidita, Object data, Object id) {
		/** se non ho una base dati valida ritorno true **/
		if (!this.isSQLConnectionEnabled) {
			return true;
		}

		AccessiDB adb = new AccessiDB(connection, logger);
		Map<String, String> map = new TreeMap<String, String>();
		try {
			// se data nulla la scheda ancora non e' stata inserita quind getNow()
			if (data == null) {
				data = adb.getNow();
			}
			if (SimogFlags.is3042Active())
				map = adb.getTipologicaWithData(nomeTabella, campoId, campoDescrizione, campoInizioValidita,
						campoFineValidita, data);
			else
				map = adb.getTipologica(nomeTabella, campoId, campoDescrizione, campoFineValidita, data);

			if (id instanceof Long) {
				return map.containsKey(((Long) id).toString());
			}
			return map.containsKey(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}// FINE TICKET ALM #2847

	/**
	 * metodo per la validazione delle tipologiche, usa il metodo getTipologica di
	 * accessidb
	 * 
	 * @param nomeTabella      String
	 * @param campoId          String nome campo id
	 * @param campoDescrizione String nome campo descrizione
	 * @param campoValidita    String nome campo validita [es. data fine scheda]
	 * @param data             Object deve essere timestamp o String[yyyymmdd] data
	 *                         del confronto
	 * @param id               Object String? o Long? l'id di cui verificare
	 *                         l'esitenza
	 * @return boolean
	 */
	protected boolean validaTipologica(String nomeTabella, String campoId, String campoDescrizione,
			String campoValidita, String campoFineVal, Timestamp data, Object id) {
		/** se non ho una base dati valida ritorno true **/
		if (!this.isSQLConnectionEnabled) {
			return true;
		}

		AccessiDB adb = new AccessiDB(connection, logger);
		Map<String, String> map = new TreeMap<String, String>();
		try {
			// se data nulla la scheda ancora non � stata inserita quind getNow()
			if (data == null) {
				data = adb.getNow();
			}
			map = adb.getTipologica(nomeTabella, campoId, campoDescrizione, campoValidita, campoFineVal, data);

			if (id instanceof Long) {
				return map.containsKey(((Long) id).toString());
			}
			return map.containsKey(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * @param isSQLConnectionEnabled the isSQLConnectionEnabled to set
	 */
	public void setSQLConnectionEnabled(boolean isSQLConnectionEnabled) {
		this.isSQLConnectionEnabled = isSQLConnectionEnabled;
	}

	protected boolean istatValido(String istat, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!this.isSQLConnectionEnabled) {
			return true;
		}

		IstatManager im = new IstatManager(connection, logger);
		return im.isValid(istat, o);
	}

	protected boolean nutsValido(String nuts, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!this.isSQLConnectionEnabled) {
			return true;
		}

		NutsManager nm = new NutsManager(connection, logger);
		return nm.isValid(nuts, o);
	}

	/**
	 * verifica se i dati inseriti portano ad un flusso di acquisizione valido usato
	 * da web
	 * 
	 * @param bean InfoComuniBean
	 * @return boolean
	 * @throws SQLException
	 */
	protected boolean checkTipoFlusso(InfoComuniBean bean) throws SQLException {

		boolean retVal = false;

		LottoManager lm = new LottoManager(connection, logger);

		InfoComuniManager icm = new InfoComuniManager(connection, logger);
		InfoGaraBean igb = icm.loadInfoGara(bean.getIdLotto());

		retVal = getTipoFlusso(igb) != null;

//		TableBean lotto = null;
//		lotto = lm.getLottoByIdLottoAmm(bean.getIdLotto());
//		float importoLotto = new BigDecimal(lotto.getNulledField(LOTTO.IMPORTO_LOTTO, 0)).floatValue();
//		String dataCreazione = lotto.getNulledField(LOTTO.DATA_PUBBLICAZIONE, 0);
//		
//		String tipoContratto = bean.getTipoContratto();
//		int modoReal = bean.getID_MODO_REAL();
//		String flagEscluso = bean.getFLAG_ESCLUSO();
//		
//		if(modoReal == Costanti.MODOREAL_ACCORDO)
//		{
//			retVal = true;	// accordo quadro
//		}
//		else if(Costanti.MODOREAL_ADESIONE == modoReal || Costanti.MODOREAL_ADESIONE_NOCOMPET == modoReal)
//		{
//			retVal = true;	// adesione
//		}
//		else if(Costanti.FLAG_VALORE_SI.equals(flagEscluso)
//			&& (importoLotto >= SimogFlusso.getImportoRif(tipoContratto, dataCreazione)
//					|| importoLotto == Costanti.IMPORTO_FUORI_SCALA))
//		{
//			retVal = true;	// esclusi
//		}
//		else if(importoLotto > 0 
//			&& importoLotto <= Costanti.IMPORTO_LOTTO_150000
//			&& importoLotto >= SimogFlusso.getImportoRif(tipoContratto, dataCreazione))
//		{
//			retVal = true;	// sottosoglia
//		}
//		else if(importoLotto > Costanti.IMPORTO_LOTTO_150000 || importoLotto == Costanti.IMPORTO_FUORI_SCALA)
//		{
//			retVal = true;	// soprasoglia
//		}

		return retVal;
	}

	/**
	 * Ritorna il tipo di flusso in base ai dati di gara e lotto, usato da
	 * massloader
	 * 
	 * @param igb
	 * @return
	 */
	public static TipoFlusso getTipoFlusso(InfoGaraBean igb) {
		SimogFlusso flusso = new SimogFlusso(igb.getTIPO_SCHEDA_GARA(), igb.getTipoContratto(), igb.getFLAG_ESCLUSO(),
				igb.getID_MODO_REAL(), igb.getDataPubblicazione(), igb.getImportoLotto().floatValue(), 0,
				igb.getDataCreazioneGara(), 0); // TICKET ALM - 3.04.3 #7303

		return flusso.getTipoFlusso();
	}

	/**
	 * verifica se ci sono i presupposti per inserire schede aggiuntive , usato da
	 * web
	 * 
	 * @param importo
	 * @param tipoContratto
	 * @return
	 */
	public static boolean checkOkSchede3023(float importo, String tipoContratto, String dataCreazione, String tipoEnte,
			String flagEscluso, int modoReal) {
		SimogFlusso flusso = new SimogFlusso(tipoEnte, tipoContratto, flagEscluso, modoReal, dataCreazione, importo);

		return (flusso.getTipoFlusso() != null);
	}

	/**
	 * ritorna l'importo di riferimento per il limite inferio dei sottosoglia
	 * 
	 * @param tipoContratto
	 * @param dataCreazione
	 * @return importo
	 */
// duplicato in SimogFlusso    
//	public static float getImportoRif(String tipoContratto, String dataCreazione) {
//		boolean nuovaSotto = (Costanti.DATA_NUOVI_SOTTO.compareTo(dataCreazione)<= 0);
//		
//		return nuovaSotto 
//				? Costanti.IMPORTO_LOTTO_40000 
//				: (Costanti.TIPO_SCHEDA_LAVORI.equals(tipoContratto) 
//								? Costanti.IMPORTO_LOTTO_40000 
//								: Costanti.IMPORTO_LOTTO_20000);
//	}

	public static String getImportoRifStr(String tipoContratto, String dataCreazione) {
		return String.valueOf(SimogFlusso.getImportoRif(tipoContratto, dataCreazione));
	}

	/**
	 * O
	 * 
	 * @param idMotivo
	 * @param o        deve essere Timestamp o String [yyyymmdd] per l'estensione
	 *                 della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isMotivoVarCOValido(String idMotivo, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!isSQLConnectionEnabled) {
			return true;
		}

		AggiudicazioniManager rman = new AggiudicazioniManager(connection, logger);
		return rman.loadMotiviVCO(o).containsKey(String.valueOf(idMotivo));
	}

	/**
	 * @param sceltaContraente
	 * @param o                deve essere Timestamp o String [yyyymmdd] per
	 *                         l'estensione della validitÃ  di una tipologia a
	 *                         posteriori
	 * @param isOrgano
	 * @return Boolean
	 * @throws SQLException
	 */
	public Boolean sceltaContraenteValida(long sceltaContraente, Object o, boolean isOrgano, String idOss)
			throws SQLException {
		AccessiDB adb = new AccessiDB(connection, logger);
// is3028_RFWEBGL00Active
//       if(SimogFlags.isOrganiCostActive())
		return adb.loadSceltaContraente(o, isOrgano, idOss).containsKey(String.valueOf(sceltaContraente));
//       else
//           return adb.getTipologica(SCELTA_CONTRAENTE.TABLE_NAME, SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE, SCELTA_CONTRAENTE.DESCRIZIONE, SCELTA_CONTRAENTE.DATA_FINE_VALIDITA,o).containsKey(String.valueOf(sceltaContraente));      
	}

	/**
	 * 2846
	 * 
	 * @param motivoCollegamento
	 * @param o                  deve essere Timestamp o String [yyyymmdd] per
	 *                           l'estensione della validitÃ  di una tipologia a
	 *                           posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
	public Boolean motivoCollegamentoValida(long motivoCollegamento, Object o) throws SQLException {
		AccessiDB adb = new AccessiDB(connection, logger);
		return adb.loadMotivoCollegamento(o).containsKey(String.valueOf(motivoCollegamento));
	}

	/***
	 * Ritorna la data di riferimento corretta per le validazioni, se non esiste
	 * restituisce la data inizio dell'aggiudicazione
	 * 
	 * @param dataAgg
	 * @param dataRif
	 * @return
	 */

	public Timestamp getDataRiferimento(Timestamp dataAgg, Timestamp dataRif) {
		Timestamp dataRet = null;

		if (dataRif == null)
			dataRet = dataAgg;
		else
			dataRet = dataRif;

		return dataRet;
	}

	/***
	 * Ritorna la data di riferimento corretta per le validazioni, se non esiste
	 * restituisce la data inizio dell'aggiudicazione
	 * 
	 * @param dataAgg
	 * @param dataRif
	 * @return
	 */

	public static Timestamp getDataRiferimentoStatic(Timestamp dataAgg, Timestamp dataRif) {
		Timestamp dataRet = null;

		if (dataRif == null)
			dataRet = dataAgg;
		else
			dataRet = dataRif;

		return dataRet;
	}

	/**
	 * Validazione di una data (se valorizzato e se e' una data)
	 * 
	 * @param dataCorrente
	 * @param nomeCampo
	 * @param obbligatorio
	 */
	public boolean validaData(String dataRif, String nomeCampo, boolean obbligatorio) {
		boolean esito = true;
		if (dataRif == null || "".equals(dataRif)) {
			if (obbligatorio) {
				esito = false;
				mEccezioni.addValidationField("label_Data");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", nomeCampo));
			}
		} else {
			String data = PageHelper.formatDateOrNull(dataRif);
			if (!isDate(data)) {
				esito = false;
				mEccezioni.addValidationField("label_Data");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", nomeCampo));
			}
		}
		return esito;
	}

	/**
	 * Validazione di un orario del tipo hh:mm (se valorizzato e se e' un orario
	 * valido)
	 * 
	 * @param dataCorrente
	 * @param nomeCampo
	 * @param obbligatorio
	 */
	public boolean validaOra(String oraRif, String nomeCampo, boolean obbligatorio) {
		boolean res = true;
		if (oraRif == null || "".equals(oraRif)) {
			if (obbligatorio) {
				res = false;
				mEccezioni.addValidationField("label_Ora");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", nomeCampo));
			} else
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", nomeCampo));
		} else {
			if (!isValidTime(oraRif)) {
				res = false;
				mEccezioni.addValidationField("label_Ora");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", nomeCampo));
			}
		}
		return res;
	}

	/**
	 * Valida ordine temporale di coppie di date
	 * 
	 * @param dataA
	 * @param campoA
	 * @param dataB
	 * @param campoB
	 * @param strettamente true, dataA > dataB; false, dataA >= dataB
	 * @param eqWarn
	 */
	public boolean validaOrdineDate(String dataA, String campoA, String dataB, String campoB, boolean strettamente,
			boolean eqWarn) {
		boolean res = true;

		// Restituisce un errore specifico nel caso in cui la data Avsia precedente alla
		// data B
		if (dataA != null && dataB != null && !"".equals(dataA) && !"".equals(dataB)) {
			int dateValide = dataA.compareTo(dataB);
			if (!strettamente && dateValide > 0) {
				res = false;
				mEccezioni.addValidationField("label_Data");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_223.replace("$1", campoB).replace("$2", campoA));
			}
			if (strettamente && dateValide >= 0) {
				if (dateValide == 0 && eqWarn)
					mEccezioni.addValidationWarn(
							Messaggi.SIMOG_VALIDAZIONE_223.replace("$1", campoB).replace("$2", campoA));
				else {
					res = false;
					mEccezioni.addValidationField("label_Data");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_223.replace("$1", campoB).replace("$2", campoA));
				}
			}
		}
		return res;
	}

	// trova duplicati in una lista di cup
	protected boolean duplicati(List<CupLottoAggExt> list) {
		final Set<String> set1 = new HashSet();

		boolean retVal = false;

		for (CupLottoAggExt yourInt : list) {
			if (!set1.add(yourInt.getCup())) {
				retVal = true;
				break;
			}
		}
		return retVal;
	}

	protected boolean validaCodiciCupLen(CupLottoAgg cupLottoAgg) {
		return cupLottoAgg.getCup().length() != Costanti.CUP_LENGTH;
	}

	// is3031_RFWEBGL02Active
	// ticket ALM #666
	// TB: aggiungo GARA.ID_MODO_REAL come dato da verificare (se uguale a 9, il CUP
	// non e' obbligatorio)
	protected boolean validaFlageCodiciCUP(Lotto lotto, int idModReal, int idSvolgimento) {

		/*
		 * Validazione Flag CUP
		 */
		// campo flag deve essere valorizzato
		if (!isFlag(lotto.getFLAG_CUP())) {
			mEccezioni.addValidationField("label_CodiceCUP");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", " L'appalto e' finalizzato alla realizzazione di "
					+ "progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP ai sensi "
					+ "dell'art. 11 L 3/2003 e ss.mm.? (E' necessario acquisire e comunicare il CUP per interventi finanziati, anche in parte, "
					+ "con risorse Comunitarie)"));
			return false;
		}

		if (isYFlag(lotto.getFLAG_CUP()) && isEmpty(lotto.getElencoCup())) {
			mEccezioni.addValidationField("label_CodiceCUP");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Codici CUP"));
			return false;
		}

		if (isNFlag(lotto.getFLAG_CUP()) && !isEmpty(lotto.getElencoCup())) {
			mEccezioni.addValidationField("label_CodiceCUP");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Codici CUP"));
			return false;
		}

		// Ticket ALM #709
		// Verifica tipologia lavori
		boolean found = false;
		if (lotto.getElencoTipoAppaltoLottoL() != null) { // Ticket ALM #2432
			for (TipoAppaltoAggBean elem : lotto.getElencoTipoAppaltoLottoL()) {
				// TICKET ALM #3714: verifica che sia stata selezionata SOLO la manutenzione
				// ordinaria
				if (elem.getIdAppalto() == Costanti.TIPOAPP_MAN_ORDINARIA
						&& lotto.getElencoTipoAppaltoLottoL().size() == 1) {
					found = true;
				}
			}
		}

		// Ticket ALM #666
		// Se la gara e' in modalità accordo quadro, il cup non e' mai obbligatorio
		// TICKET ALM #2847
		// if(idModReal != Costanti.MODOREAL_ACCORDO )
		if (!SimogFlags.isAccordoQuadroOrConvenzione(idModReal)
				&& !SimogFlags.isSvolgimentoAccordoQuadro(idSvolgimento)) {
			boolean empty = isYFlag(lotto.getFLAG_CUP()) && isEmpty(lotto.getElencoCup());

			String currentDate = PageHelper.getCurrentDate();

			if (isNFlag(lotto.getFLAG_CUP())
					&& currentDate.compareTo(SimogProperties.getInstance().getDataAttivazionePPP()) >= 0
					&& isIdModRealPPP(idModReal)) {
				mEccezioni.addValidationField("label_CodiceCUP");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Codici CUP"));
				return false;
			}

			// se il tipo contratto e' lavori e la manutenzione ordinaria non risulta essere
			// l'unica selezione, il cup e' obbligatorio
			if ((isLavori(lotto.getTIPO_CONTRATTO_LOTTO()) && !found && (isNFlag(lotto.getFLAG_CUP()) || empty))
					|| (!isLavori(lotto.getTIPO_CONTRATTO_LOTTO()) && empty)) {
				mEccezioni.addValidationField("label_CodiceCUP");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Codici CUP"));
				return false;
			}

		}

		// se mi ha detto SI verifico se è lavori e non è manutenzione ordinaria, che
		// non è ammessa
		if (isYFlag(lotto.getFLAG_CUP()) // && isLavori(lotto.getTIPO_CONTRATTO_LOTTO())
				&& lotto.getElencoTipoAppaltoLottoL() != null) {
			if (found) {
				// PP 31.05.2016 infante solo warning
				// mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1","Tipologia
				// Lavoro"));
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Tipologia Lavoro"));
				return true;
			}
		} else {
			// ticket ALM #2321
			if (isNFlag(lotto.getFLAG_CUP()) && !isEmpty(lotto.getElencoCup()) && // Se il flag e' N e la lista e' vuota
					(!(isLavori(lotto.getTIPO_CONTRATTO_LOTTO()))
							|| (!found && (isLavori(lotto.getTIPO_CONTRATTO_LOTTO())))) // Se il settore non e' lavori
																						// oppure e' lavori ma senza la
																						// manutenzione ordinaria
																						// selezionata
			) {
				mEccezioni.addValidationField("label_CodiceCUP");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Codici CUP"));
				return false;
			}
		}

		// Fine Ticket ALM #709

		/*
		 * Validazione codici CUP
		 */
		int idx = 0;
		if (lotto.getElencoCup() != null) {
			for (CupLottoAgg cupLottoAgg : lotto.getElencoCup()) {

				// Ticket ALM #2159
				if (cupLottoAgg.getCup() == null || cupLottoAgg.getCup().isEmpty())
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Codici CUP"),
							++idx); // Fine ticket ALM #2159
				else if (validaCodiciCupLen(cupLottoAgg))
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codici CUP"),
							++idx);
			}

			// ricerca duplicati nei codici inseriti
			if (duplicati(lotto.getElencoCup()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_187.replace("$1", "Codici CUP"));
		}
		return true;
	}

	// Ticket ALM #2432
	// Gestione validazione CUP per schede sottosoglia
	protected void validaFlageCodiciCUP(Lotto lotto) {

		/*
		 * Validazione Flag CUP
		 */
		// campo flag deve essere valorizzato
		if (!isFlag(lotto.getFLAG_CUP())) {
			mEccezioni.addValidationField("label_CodiceCUP");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
					"L'appalto e' finalizzato alla realizzazione di progetti d'investimento pubblico per i quali e' prevista l'acquisizione del codice CUP... "));
			return;
		}

		// se mi ha detto SI ci deve mettere i cup
		if (isYFlag(lotto.getFLAG_CUP()) && isEmpty(lotto.getElencoCup())) {
			mEccezioni.addValidationField("label_CodiceCUP");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Codici CUP"));
			return;
		}

		// se mi ha detto SI verifico se è lavori e non è manutenzione ordinaria, che
		// non è ammessa
		if (isYFlag(lotto.getFLAG_CUP())
				// && isLavori(lotto.getTIPO_CONTRATTO_LOTTO())
				&& lotto.getElencoTipoAppaltoLottoL() != null) {
			boolean found = false;
			for (TipoAppaltoAggBean elem : lotto.getElencoTipoAppaltoLottoL()) {
				if (elem.getIdAppalto() == Costanti.TIPOAPP_MAN_ORDINARIA) {
					found = true;
				}
				if (found) {
					// PP 31.05.2016 infante solo warning
					// mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1","Tipologia
					// Lavoro"));
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Tipologia Lavoro"));
					return;
				}
			}
		} else {
			// se mi ha detto NO non ci deve mettere i cup
			if (isNFlag(lotto.getFLAG_CUP()) && !isEmpty(lotto.getElencoCup())) {
				mEccezioni.addValidationField("label_CodiceCUP");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Codici CUP"));
				return;
			}
		}

		/*
		 * Validazione codici CUP
		 */
		int idx = 0;
		if (lotto.getElencoCup() != null) {
			for (CupLottoAgg cupLottoAgg : lotto.getElencoCup()) {
				if (validaCodiciCupLen(cupLottoAgg))
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codici CUP"),
							++idx);
			}

			// ricerca duplicati nei codici inseriti
			if (duplicati(lotto.getElencoCup()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_187.replace("$1", "Codici CUP"));
		}
	}

	public boolean validaTipologicaNoData(String nomeTabella, String nomeCampoId, String campoDescrizione, Long id) {
		/** se non ho una base dati valida ritorno true **/
		if (!this.isSQLConnectionEnabled) {
			return true;
		}

		AccessiDB adb = new AccessiDB(connection, logger);
		Map<String, String> map = new TreeMap<String, String>();
		try {

			map = adb.getTipologicaNoData(nomeTabella, nomeCampoId, campoDescrizione, nomeCampoId, String.valueOf(id));

			if (id instanceof Long) {
				return map.containsKey(((Long) id).toString());
			}
			return map.containsKey(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	private boolean isIdModRealPPP(int idModReal) {
		String idModRealPPP = SimogProperties.getInstance().getIdModRealPPP();
		String[] arrIdModRealPPP = idModRealPPP.split(";");
		boolean res = false;

		for (String elIdPPP : arrIdModRealPPP) {
			int intIDPPP = Integer.parseInt(elIdPPP);
			if (intIDPPP == idModReal) {
				res = true;
				break;
			}
		}

		return res;

//		   return idModReal==Costanti.MODOREAL_CONCESSIONE_LAVORI || 
//				   idModReal==Costanti.MODOREAL_CONCESSIONE_SF || 
//				   idModReal==Costanti.MODOREAL_FINANZA_DI_PROGETTO ||
//				   idModReal==Costanti.MODOREAL_LOCFIN_OPEREPUBBLICHE ||
//				   idModReal==Costanti.MODOREAL_DISPONIBILITA;
	}

}
