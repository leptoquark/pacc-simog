package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.RecMotivoVarType;

public class EventiMotiviVariatiComparator extends MultiComparator {

	@Override
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		RecMotivoVarType m1 = (RecMotivoVarType)o1;
		RecMotivoVarType m2 = (RecMotivoVarType)o2;
		return super.equals(m1.getIDMOTIVOVAR(),m2.getIDMOTIVOVAR());
	}

}
