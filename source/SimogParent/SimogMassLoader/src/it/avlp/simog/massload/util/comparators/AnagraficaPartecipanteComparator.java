package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;

public class AnagraficaPartecipanteComparator extends MultiComparator {

	@Override
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		AggiudicatarioType s1 = (AggiudicatarioType)o1;
		AggiudicatarioType s2 = (AggiudicatarioType)o2;
		if(super.equals(s1.getCODICEFISCALEAGGIUDICATARIO(), s2.getCODICEFISCALEAGGIUDICATARIO())){
			return super.equals(s1.getCODICESTATO(), s2.getCODICESTATO());
		}
		return false;
	}

}
