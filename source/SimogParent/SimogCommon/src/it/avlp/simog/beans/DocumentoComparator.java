package it.avlp.simog.beans;

import it.avlp.simog.beans.RequisitoGara.Documento;

import java.util.Comparator;

public class DocumentoComparator implements Comparator<Documento> {

   public int compare(Documento o1, Documento o2) {
      Long codiceTipoDoc1 = new Long(o1.getCodice_tipo_doc());
      Long codiceTipoDoc2 = new Long(o2.getCodice_tipo_doc());
      return codiceTipoDoc1.compareTo(codiceTipoDoc2);
   }
   
}
