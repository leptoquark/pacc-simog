package it.avlp.simog.massload.util.comparators;

import it.avlp.simog.massload.xmlbeans.ResponsabileType;

public class AnagraficaResponsabileComparator extends MultiComparator {

	@Override
	public int compare(Object o1, Object o2) throws Exception {
		return 0;
	}

	@Override
	public boolean equals(Object o1, Object o2) throws Exception {
		ResponsabileType s1 = (ResponsabileType)o1;
		ResponsabileType s2 = (ResponsabileType)o2;
		return super.equals(s1.getCODICEFISCALERESPONSABILE(), s2.getCODICEFISCALERESPONSABILE());
	}

}
