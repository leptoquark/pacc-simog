package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.CondizioneType;

public class CondizioneComparator extends MultiComparator {

	
	/* (non-Javadoc)
	 * @see it.avlp.simog.massload.util.comparators.MultiComparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		CondizioneType c1 = (CondizioneType)o1;
		CondizioneType c2 = (CondizioneType)o2;
		return super.equals(c1.getIDCONDIZIONE(), c2.getIDCONDIZIONE());
	}

}
