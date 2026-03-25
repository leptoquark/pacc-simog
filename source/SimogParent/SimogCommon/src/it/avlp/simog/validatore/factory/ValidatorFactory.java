package it.avlp.simog.validatore.factory;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.common.servlet.ParametriServletGara;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.common.servlet.ParametriServletSchedaB4;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.common.servlet.ParametriServletVariante;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.validatore.AccordiBonariValidator;
import it.avlp.simog.validatore.AvanzamentiValidator;
import it.avlp.simog.validatore.CollaudoValidator;
import it.avlp.simog.validatore.ConclusioneValidator;
import it.avlp.simog.validatore.DummyValidator;
import it.avlp.simog.validatore.EsclusiValidator;
import it.avlp.simog.validatore.GaraValidator;
import it.avlp.simog.validatore.InizioLavoriValidator;
import it.avlp.simog.validatore.LottoValidator;
import it.avlp.simog.validatore.R129Validator;
import it.avlp.simog.validatore.RubricaValidator;
import it.avlp.simog.validatore.SchedaAValidator;
import it.avlp.simog.validatore.SchedaAdesioneValidator;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.SospensioniValidator;
import it.avlp.simog.validatore.SottosogliaValidator;
import it.avlp.simog.validatore.StipulaValidator;
import it.avlp.simog.validatore.SubappaltiValidator;
import it.avlp.simog.validatore.VariantiValidator;

import java.sql.Connection;

import org.apache.log4j.Logger;


public class ValidatorFactory {
	
	/*********************************************************************************************************************
	 * A seconda del tipo di scheda decritto nella stringa scheda, viene rilasciato il Simog Validator opportuno. 
	 * 
	 * @param scheda String contenente il tipo di scheda
	 * @param connection Connection
	 * @param logger Logger
	 * @return SimogValidator
	 * @throws SimogException
	 */
	public static SimogValidator getValidator(String scheda, Connection connection, Logger logger)throws SimogException{
	   
      //  !*!*!*!*!*! codice per elaborazione BOLZANO
//      if(SimogFlags.isBOLZANODummyValidatorActive()){
//         return new DummyValidator(connection, logger);
//      }
//      else{
   		if(IdentificativoSchede.TAB_AGGIUDICAZIONE.equals(scheda) || IdentificativoSchede.TAB_INFO_COMUNI.equals(scheda) )
   			return new SchedaAValidator(connection,logger);
   		else if(IdentificativoSchede.TAB_ADESIONE.equals(scheda))
   			return new SchedaAdesioneValidator(connection,logger);
   		else if(IdentificativoSchede.TAB_INIZIO_LAVORI.equals(scheda))
   			return new InizioLavoriValidator(connection, logger);
   		else if(IdentificativoSchede.TAB_STIPULA.equals(scheda))
   			return new StipulaValidator(connection, logger);
   		else if(ParametriServletAvanzamento.TAB_AVANZAMENTO.equals(scheda))
   			return new AvanzamentiValidator(connection, logger);
   		else if(ParametriServletSchedaB4.TAB_FINE_LAVORI.equals(scheda))
   			return new ConclusioneValidator(connection, logger);
   		else if(ParametriServletR129.TAB_SCHEDA_R129.equals(scheda))
   			return new R129Validator(connection, logger);
   		else if(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI.equals(scheda))
   			return new SospensioniValidator(connection, logger);
   		else if(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI.equals(scheda))
   			return new SubappaltiValidator(connection, logger);
   		else if(ParametriServletAccordo.TAB_SCHEDA_ACCORDO.equals(scheda))
   			return new AccordiBonariValidator(connection, logger);
   		else if(ParametriServletRubrica.TAB_RUBRICA.equals(scheda))
   			return new RubricaValidator(connection, logger);
   		else if(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI.equals(scheda))
   			return new ConclusioneValidator(connection,logger);
   		else if(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO.equals(scheda))
   			return new CollaudoValidator(connection,logger);
   		else if (ParametriServletVariante.TAB_SCHEDA_VARIANTE.equals(scheda))
   			return new VariantiValidator(connection,logger);
   		else if (ParametriServletLotto.TAB_LOTTO.equals(scheda))
   			return new LottoValidator(connection,logger);
   		else if (ParametriServletGara.TAB_GARA.equals(scheda))
   			return new GaraValidator(connection,logger);
   		else if( IdentificativoSchede.TAB_SOTTOSOGLIA.equals(scheda) )
   			return new SottosogliaValidator(connection, logger);
   		else if( IdentificativoSchede.TAB_ESCLUSI.equals(scheda) )
   			return new EsclusiValidator(connection, logger);			
   		else throw new SimogException(Messaggi.SIMOG_VALIDAZIONE_000);
//   	}
   }
}
