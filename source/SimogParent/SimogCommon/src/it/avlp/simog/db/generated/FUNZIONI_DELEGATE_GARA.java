package it.avlp.simog.db.generated;
//TICKET ALM #659 - 3.04.4
public class FUNZIONI_DELEGATE_GARA {
	public final static String TABLE_NAME = "FUNZIONI_DELEGATE_GARA";
	
	//ID_F_DELEGATE_GARA
	public final static String ID_FUNZIONI_DELEGATE_GARA = "ID_FUNZIONI_DELEGATE_GARA";
	public final static String T_ID_FUNZIONI_DELEGATE_GARA= TABLE_NAME + "."+ ID_FUNZIONI_DELEGATE_GARA;
	
	public long ID_FUNZIONI_DELEGATE_GARA_field;
	
	//FLAG_SA_AGENTE
	public final static String FLAG_SA_AGENTE = "FLAG_SA_AGENTE";
	public final static String T_FLAG_SA_AGENTE = TABLE_NAME + "." + FLAG_SA_AGENTE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_SA_AGENTE_field;
	
	//ID_F_DELEGATE
	public final static String ID_F_DELEGATE = "ID_F_DELEGATE";
	public final static String T_ID_F_DELEGATE = TABLE_NAME + "." + ID_F_DELEGATE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long Id_f_delegate_field;

    //ID_GARA
	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = TABLE_NAME + "."+ID_GARA;
	
	public long Id_gara_field;
	
	//CF_AMM_AGENTE
	public final static String CF_AMM_AGENTE = "CF_AMM_AGENTE";
	public final static String T_CF_AMM_AGENTE = TABLE_NAME + "." + CF_AMM_AGENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_AMM_AGENTE_field;

	
	//DEN_AMM_AGENTE
	public final static String DEN_AMM_AGENTE = "DEN_AMM_AGENTE";
	public final static String T_DEN_AMM_AGENTE = TABLE_NAME + "." + DEN_AMM_AGENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DEN_AMM_AGENTE_field;
	
       //CF_RUP_DELEGATA
		public final static String CF_RUP_DELEGATA = "CF_RUP_DELEGATA";
		public final static String T_CF_RUP_DELEGATA = TABLE_NAME + "." + CF_RUP_DELEGATA;
			// COLUMN TYPE [varchar]
			// COLUMN SIZE [20]
			// NULLABLE [YES]

		public String CF_RUP_DELEGATA_field;

	
	//CF_AMM_DELEGATA
		public final static String CF_AMM_DELEGATA = "CF_AMM_DELEGATA";
		public final static String T_CF_AMM_DELEGATA = TABLE_NAME + "." + CF_AMM_DELEGATA;
			// COLUMN TYPE [varchar]
			// COLUMN SIZE [20]
			// NULLABLE [YES]

		public String CF_AMM_DELEGATA_field;

		
		//DEN_AMM_DELEGATA
		public final static String DEN_AMM_DELEGATA = "DEN_AMM_DELEGATA";
		public final static String T_DEN_AMM_DELEGATA = TABLE_NAME + "." + DEN_AMM_DELEGATA;
			// COLUMN TYPE [varchar]
			// COLUMN SIZE [250]
			// NULLABLE [YES]

		public String DEN_AMM_DELEGATA_field;
		
		//ID_SA_DELEGATA
		public final static String ID_SA_DELEGATA = "ID_SA_DELEGATA";
		public final static String T_ID_SA_DELEGATA = TABLE_NAME + "." + ID_SA_DELEGATA;
			// COLUMN TYPE [varchar]
			// COLUMN SIZE [40]
			// NULLABLE [YES]

		public String ID_SA_DELEGATA_field;
		
		//CF_AMM_DELEGATA
		public final static String DENOM_SA_DELEGATA = "DENOM_SA_DELEGATA";
		public final static String T_DENOM_SA_DELEGATA = TABLE_NAME + "." + DENOM_SA_DELEGATA;
			// COLUMN TYPE [varchar]
			// COLUMN SIZE [300]
			// NULLABLE [YES]

		public String DENOM_SA_DELEGATA_field;
		
		public final static String DATA_PRESA_IN_CARICO = "DATA_PRESA_IN_CARICO";
		public final static String T_DATA_PRESA_IN_CARICO = TABLE_NAME + "." + DATA_PRESA_IN_CARICO;
			// COLUMN TYPE [datetime]
			// NULLABLE [YES]

		public String DATA_PRESA_IN_CARICO_field;
}
