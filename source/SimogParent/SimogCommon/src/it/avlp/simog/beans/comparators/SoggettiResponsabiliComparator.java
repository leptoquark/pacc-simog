package it.avlp.simog.beans.comparators;

import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;


public class SoggettiResponsabiliComparator extends MyComparator{
	
	/**
	 * Metodo che si occupa del confronto tra due anagrafiche:
	 * - null, null = true
	 * - not null , not null = esito equals
	 * - altri casi false
	 * @param srb
	 * @param srb1
	 * @return
	 */
	public boolean equals(SoggettoResponsabileBean srb,SoggettoResponsabileBean srb1){
		if(srb != null && srb1 != null){
			if(this.equals(srb.getCodiceFiscaleResponsabile(),srb1.getCodiceFiscaleResponsabile())){
				if(this.equals(srb.getCognome(),srb1.getCognome())){
					if(this.equals(srb.getComuneIstat(),srb1.getComuneIstat())){
						if(this.equals(srb.getEmail(),srb1.getEmail())){
							if(this.equals(srb.getFax(),srb1.getFax())){
								if(this.equals(srb.getIndirizzo(),srb1.getIndirizzo())){
									if(this.equals(srb.getNome(),srb1.getNome())){
										if(this.equals(srb.getTelefono(),srb1.getTelefono())){
											if(this.equals(srb.getCap(),srb1.getCap())){
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}else if(srb == null && srb1 == null){
			return true;
		}return false;
		
	}


}
