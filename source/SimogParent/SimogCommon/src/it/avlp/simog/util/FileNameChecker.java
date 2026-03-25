package it.avlp.simog.util;


import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;


public class FileNameChecker implements FilenameFilter {

	private Logger logger = null;
	private boolean neededDeleteOfInvalids = false;
	private String fileTypeToCheck = "XML";
	
	public FileNameChecker(Logger logger) {
		this(logger, true, "XML");
	}

	public FileNameChecker(Logger logger, boolean neededDeleteOfInvalids, String fileTypeToCheck) {
		super();
		this.logger = logger;
		this.neededDeleteOfInvalids = neededDeleteOfInvalids;
		this.fileTypeToCheck = "." + fileTypeToCheck;
		logger.debug ("Richiesto filtro con opzioni Estensione[" + fileTypeToCheck + "] cancellazione non validi[" + neededDeleteOfInvalids + "]" ); 
	}
       

	
	public boolean accept(File arg0, String arg1) {
		logger.debug ( "Analisi nome file [" + arg1 + "]" );
		boolean esitoAnalisi = arg1.toUpperCase().endsWith( fileTypeToCheck );
		logger.debug ( "Esito analisi [" + arg1 + "] esito [" + esitoAnalisi + "]" );
		if ( neededDeleteOfInvalids ) {
			logger.debug("!! Richiesta cancellazione non validi [" + neededDeleteOfInvalids + "]");
			if ( ! esitoAnalisi ) {
				File f = new File ( arg0, arg1 );
				logger.warn( "Cancellato FILE [" + f.getPath() + "]");
				f.delete();
				// se il file considerato non termina con XML viene cancellato
			}
		}
		return ( esitoAnalisi );
	}
}
