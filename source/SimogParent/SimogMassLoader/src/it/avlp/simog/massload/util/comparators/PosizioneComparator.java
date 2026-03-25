package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.PosizioneType;

public class PosizioneComparator extends MultiComparator {

	
	/* (non-Javadoc)
	 * @see it.avlp.simog.massload.util.comparators.MultiComparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		PosizioneType p1 = (PosizioneType)o1;
		PosizioneType p2 = (PosizioneType)o2;
		if(super.equals(p1.getCODICEFISCALEAGGIUDICATARIO(),p2.getCODICEFISCALEAGGIUDICATARIO())){
			return super.equals(p1.getCODICESTATO(),p2.getCODICESTATO());
		}
		return false;
	}

}
