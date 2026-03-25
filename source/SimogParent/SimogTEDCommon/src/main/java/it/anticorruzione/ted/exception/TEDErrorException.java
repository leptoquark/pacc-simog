package it.anticorruzione.ted.exception;

public class TEDErrorException extends Exception {
	private static final long serialVersionUID = 7718828512143293558L;
	private TEDErrors tedError;
	
	
	public TEDErrorException(int statusCode) {
		super();
		this.tedError= TEDErrors.valueOf(statusCode);
	}

	public TEDErrors getTEDError() {
		return tedError;
	}
	
	public String getStatusMessage() {
		String message=null;
		
		switch(this.tedError) {
			case TE_400:
			case TE_406:
			case TE_413:
				message = "TED_ERROR_400 - Errore in fase di invio del formulario al TED. Si prega di riprovare piu' tardi o di notificare al servizio tecnico";
				break;
			case TE_401:
				message = "TED_ERROR_401 - Si riscontrano problemi di comunicazione con il TED. Si prega di riprovare piu' tardi";
				break;
			case TE_403:
				message = "TED_ERROR_403 - La richiesta non puo' essere cancellata";
				break;
			case TE_404:
				message = "TED_ERROR_404 - Formulario non trovato su TED";
				break;
			case TE_422:
				message = "TED_ERROR_422 - Il TED notifica che è stato raggiunto il limite massimo giornaliero di formulari inviati";
				break;
			default:
				message = "TED_ERROR_430 - Errore imprevisto in fase di accesso al TED. Si prega di riprovare piu' tardi o di notificare al servizio tecnico";
				
		}
		
		return message;
	}


	public enum TEDErrors {     
		                TE_400(400), //Errore chiamata del servizio
		                TE_406(406), //Errore inviato parametri nell'header
		                TE_413(413), //Errore sulla codifica del formulario
		 				TE_401(401), //Accesso negato dal TED (utente o password di accesso errati)
		 				TE_403(403), //Errore nel caso si richiede di cancellare un formulario non cancellabile (perchè pubblicato o già cancellato)
		 				TE_404(404), //Formulario non trovato
		 				TE_422(422), //Raggiunto il limite giornaliero di invii
		 				TE_430(0); //Errore imprevisto
		 
		 private int statusCode;
		 
		 TEDErrors(int statusCode) {
			 this.statusCode=statusCode;
		 }
		 
		 public int getStatusCode() {
			 return this.statusCode;
		 }
		 
		 public static TEDErrors valueOf(int statusCode) {
			 for(TEDErrors error : TEDErrors.values()) {
				 if(error.statusCode==statusCode)
					 return error;
			 }	 
			 return null;
		 }
	 
	 }
	
	
}
