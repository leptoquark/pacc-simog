package it.avlp.simog.beans.variante;


public enum MotivoRevPrezziBean {
	UNO("1"),
	DUE("2"),
	TRE("3"),
	QUATTRO("4"),
	CINQUE("5");
	
	private final String code;
	
	public String code() {
	      return code;
	}

    /**
     * @param text
     */
	MotivoRevPrezziBean(final String code) {
        this.code = code;
    }
	
	
	
	public static Enum getMotivo(String code){
		Enum result = null;
	      for( MotivoRevPrezziBean item : values() ){
	         if( code.equalsIgnoreCase("1") ){
	            result = UNO;
	            break;
	         }else if (code.equalsIgnoreCase("2")) {
	        	 result = DUE;
		            break;
			}else if (code.equalsIgnoreCase("3")) {
				result = TRE;
	            break;
			}else if (code.equalsIgnoreCase("4")) {
				result = QUATTRO;
	            break;
			}else if (code.equalsIgnoreCase("5")) {
				result = CINQUE;
	            break;
			}
	      }
	      return result;
	 }

    
}
