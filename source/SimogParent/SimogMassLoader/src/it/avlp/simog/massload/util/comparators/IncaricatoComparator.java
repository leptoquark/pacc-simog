package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.IncaricatoType;

public class IncaricatoComparator extends MultiComparator {

	
	/* (non-Javadoc)
	 * @see it.avlp.simog.massload.util.comparators.MultiComparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		IncaricatoType i1 = (IncaricatoType)o1;
		IncaricatoType i2 = (IncaricatoType)o2;
		if(super.equals(String.valueOf(i1.getSEZIONE()+""), String.valueOf(i2.getSEZIONE()+""))){
			if(super.equals(i1.getIDRUOLO(), i2.getIDRUOLO())){
				return super.equals(i1.getCODICEFISCALERESPONSABILE(), i2.getCODICEFISCALERESPONSABILE());
			}
		}
		return false;
	}

}
