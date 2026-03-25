package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.DittaAusiliariaType;

public class DittaAusiliariaComparator extends MultiComparator {

	
	/* (non-Javadoc)
	 * @see it.avlp.simog.massload.util.comparators.MultiComparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		DittaAusiliariaType d1 = (DittaAusiliariaType)o1;
		DittaAusiliariaType d2 = (DittaAusiliariaType)o2;
		if(super.equals(d1.getCODICEFISCALEAUSILIARIA(), d2.getCODICEFISCALEAUSILIARIA())){
			return super.equals(d1.getCODICESTATOAUSILIARIA(), d2.getCODICESTATOAUSILIARIA());
		}
		return false;
	}

}
