package it.avlp.simog.beans.comparators;

import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;

public class SoggettiPartecipantiComparator extends MyComparator{

	//mancava il cotrollo sulla denominazione
	public boolean equals(SoggettoPartecipanteBean srb,SoggettoPartecipanteBean srb1){
		if(srb != null && srb1 != null){
			//prima le chiavi del xml 
			if(this.equals(srb.getPartitaIva(),srb1.getPartitaIva())){				//	
				if(this.equals(srb.getId_stato(),srb1.getId_stato())){					//
					if(this.equals(srb.getCameraCommercio(),srb1.getCameraCommercio())){ 	//
						if(this.equals(srb.getCognome(),srb1.getCognome())){					//
							if(this.equals(srb.getCfRappresentante(),srb1.getCfRappresentante())){ 	//
								if(this.equals(srb.getCitta(),srb1.getCitta())){ 						//
									if(this.equals(srb.getCivico(),srb1.getCivico())){ 						//
										if(this.equals(srb.getIndirizzo(),srb1.getIndirizzo())){				//
											if(this.equals(srb.getNome(),srb1.getNome())){							//
												if(this.equals(srb.getCodiceFiscale(),srb1.getCodiceFiscale())){ 		//
													if(this.equals(srb.getCap(),srb1.getCap())){ 							//
														if(this.equals(srb.getProvincia(),srb1.getProvincia())){				//
															if(this.equals(srb.getDenominazione(), srb1.getDenominazione())){ 		//
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
						}
					}
				}
			}						
		}else if(srb == null && srb1 == null){
			return true;
		}return false;
	}
}
