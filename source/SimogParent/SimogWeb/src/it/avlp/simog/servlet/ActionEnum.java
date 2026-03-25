package it.avlp.simog.servlet;

public enum ActionEnum {
	NONE,
	LOAD,
	SAVE,
	SELECT,
	RICHIESTA_ANNULLAMENTO,
	RICHIESTA_CANCELLAZIONE,
	VARIAZIONE_ANAGRAFICHE,
	RIAGGIUDICAZIONE;
	
	
	public static ActionEnum fromString(String value){
		ActionEnum ret = NONE;
		
		if(value == null)
			ret = NONE;
		else if(LOAD.name().equalsIgnoreCase(value))
			ret =  LOAD;
		else if(SAVE.name().equalsIgnoreCase(value))
			ret =  SAVE;
		else if(SELECT.name().equalsIgnoreCase(value))
			ret =  SELECT;
		else if(RICHIESTA_ANNULLAMENTO.name().equalsIgnoreCase(value))
			ret =  RICHIESTA_ANNULLAMENTO;
		else if(RICHIESTA_CANCELLAZIONE.name().equalsIgnoreCase(value))
			ret =  RICHIESTA_CANCELLAZIONE;
		else if(VARIAZIONE_ANAGRAFICHE.name().equalsIgnoreCase(value))
			ret =  VARIAZIONE_ANAGRAFICHE;
		else if(RIAGGIUDICAZIONE.name().equalsIgnoreCase(value))
			ret =  RIAGGIUDICAZIONE;
		
		
		return ret;
	}
	
}
