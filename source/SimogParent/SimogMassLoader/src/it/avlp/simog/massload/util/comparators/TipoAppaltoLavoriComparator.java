package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;

public class TipoAppaltoLavoriComparator extends MultiComparator {

	

	/* (non-Javadoc)
	 * @see it.avlp.simog.massload.util.comparators.MultiComparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) throws Exception{
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		TipiAppaltoType a1 = (TipiAppaltoType)o1;
		TipiAppaltoType a2 = (TipiAppaltoType)o2;
		return super.equals(a1.getIDAPPALTO(), a2.getIDAPPALTO());
	}

}
