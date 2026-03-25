package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;

public class AggiudicatarioComparator extends MultiComparator {

	
	/* (non-Javadoc)
	 * @see it.avlp.simog.massload.util.comparators.MultiComparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		SoggAggiudicatarioType s1 = (SoggAggiudicatarioType)o1;
		SoggAggiudicatarioType s2 = (SoggAggiudicatarioType)o2;
		if(super.equals(s1.getCODICEFISCALEAGGIUDICATARIO(), s2.getCODICEFISCALEAGGIUDICATARIO())){
			return super.equals(s1.getCODICESTATO(), s2.getCODICESTATO());
		}
		return false;
	}

}
