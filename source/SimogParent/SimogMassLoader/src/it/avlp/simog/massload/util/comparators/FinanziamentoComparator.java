package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.FinanziamentoType;

public class FinanziamentoComparator extends MultiComparator {

	/* (non-Javadoc)
	 * @see it.avlp.simog.massload.util.comparators.MultiComparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		FinanziamentoType f1 = (FinanziamentoType)o1;
		FinanziamentoType f2 = (FinanziamentoType)o2;
		if(super.equals(f1.getIDFINANZIAMENTO(),f2.getIDFINANZIAMENTO())){
			//l'oggetto bugdecimal restituito da xmlbean dovrebbe avere la 
			//stessa scala, quindi equals OK
			return f1.getIMPORTOFINANZIAMENTO().equals(f2.getIMPORTOFINANZIAMENTO());
		}
		return false;
	}

}
