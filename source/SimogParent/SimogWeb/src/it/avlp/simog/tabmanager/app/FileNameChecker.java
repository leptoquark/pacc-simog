package it.avlp.simog.tabmanager.app;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;

public class FileNameChecker implements FilenameFilter {

	private Logger logger = null;
	
	public FileNameChecker(Logger logger) {
		this.logger = logger;
	}

	public boolean accept(File arg0, String arg1) {
		boolean esitoAnalisi = false;
		logger.debug ( "Analisi nome file [" + arg1 + "]" );
		File checkingFile = new File ( arg0, arg1 );
		if ( checkingFile.isFile() ) {
			esitoAnalisi = arg1.toUpperCase().endsWith(".XML");
			logger.debug ( "Esito analisi [" + arg1 + "] esito [" + esitoAnalisi + "]" );
			if ( ! esitoAnalisi ) {
				File f = new File ( arg0, arg1 );
				logger.warn( "Cancellato FILE [" + f.getPath() + "]");
				f.delete();
			}
		}
		return esitoAnalisi;
	}
}
