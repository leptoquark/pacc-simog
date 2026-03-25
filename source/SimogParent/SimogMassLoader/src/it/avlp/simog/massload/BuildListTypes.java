package it.avlp.simog.massload;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.massload.manager.DbManager;
import it.avlp.simog.util.ListTypesBuilder;
import it.avlp.simog.util.PageHelper;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.generationjava.io.WritingException;

/**
 * @author PP
 * 
 *         Utility per la generazione del file ListTypes.xsd a partire dai dati
 *         presenti nel DB
 */
public class BuildListTypes {

	private static String nomeFile = "";
	private static String nomeFileConf = "";
	private static String nomePathConf = "";

	// protected static Connection currentActiveConnection = null;
	protected static MassLoaderProperties configuration = null;
	protected static Logger logger = null;

	

	/**
	 * @param args
	 * @throws WritingException
	 * @throws IOException
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			nomeFile = "c:/_lavoro/step/_SIMOG/src/massloaderenv/conf/"
					+ PageHelper.getCurrentDate() + "_ListTypes.xsd";
			nomePathConf = "c:/_lavoro/step/_SIMOG/src/massloaderenv/conf";
			nomeFileConf = "massloader.properties";
		} else {
			nomeFile = args[0];
			nomePathConf = args[1];
			nomeFileConf = args[2];
		}
//
//		/*
//		 * Inizializzazione configurazione
//		 */
//		init(nomePathConf, nomeFileConf);
//
//		// init connessione db
//		DbManager dbm = new DbManager(logger, configuration);
//
//		/*
//		 * istanza nuovo file
//		 */
//		FileWriter w = new FileWriter(nomeFile);
//		ListTypesBuilder.write(w, new it.avlp.simog.util.SimpleDbManager(dbm.getCurrentActiveConnection(),logger), false);
//		dbm.closeConnection();

	}

	public static void init(String nomePathConf, String nomeFileConf)
			throws Exception {

		try {
			PropertyConfigurator.configure(nomePathConf
					+ "/massloader.log4j.properties");
			logger = Logger.getLogger("MASSLOADER_LOGGER");
			logger.debug("LOGGER applicativo inizializzato correttamente");

			configuration = new MassLoaderProperties(nomePathConf + "/"
					+ nomeFileConf, logger);
		} catch (SimogException e) {
			throw new Exception(e.getMessage());
		}
	}

}
