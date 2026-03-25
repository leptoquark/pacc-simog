package it.avlp.simog.beans;

public enum OrigineSchedaEnum {
   
   ND(0, "ND"),
   WEB(1, "WEB"),
   MASSLOADER(2, "MASSLOADER"),
   LOADER_APPALTO(3, "LOADERAPPALTO"),
   AVCPASS(4, "AVCPASS")
   ;
   
   private int code;
   private String descrizione;
   
   public int code() {
      return code;
   }
   public String descrizione() {
      return descrizione;
   }

   private OrigineSchedaEnum(int code, String descrizione) {
      this.code = code;
      this.descrizione = descrizione;
   }
   
   public static OrigineSchedaEnum getOrigine(String descrizione){
      OrigineSchedaEnum result = null;
      for( OrigineSchedaEnum item : values() ){
         if( item.descrizione.equalsIgnoreCase(descrizione) ){
            result = item;
            break;
         }
      }
      return result;
   }

}
