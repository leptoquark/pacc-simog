package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.RequisitoType;

public class RequisitiComparator extends MultiComparator {

	
	/* (non-Javadoc)
	 * @see it.avlp.simog.massload.util.comparators.MultiComparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		RequisitoType r1 = (RequisitoType)o1;
		RequisitoType r2 = (RequisitoType)o2;
		return super.equals(r1.getIDCATEGORIA(),r2.getIDCATEGORIA());
	}

}
