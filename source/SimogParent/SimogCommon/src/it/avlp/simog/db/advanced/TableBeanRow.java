package it.avlp.simog.db.advanced;


public class TableBeanRow {
	
	private int rowNumber = -1;

	private TableBean currentTable = null;
	
	public TableBeanRow(TableBean tableBean) {
		this.currentTable = tableBean;
		this.rowNumber = tableBean.getFullSize() + 1;
	}
	
	public TableBeanRow(TableBean tableBean, int rowNumber) {
		
		//System.out.println("Adding ROW TABLEBEAN n. [" + rowNumber + "]");
		
		this.currentTable = tableBean;
		this.rowNumber = rowNumber;
	}

	/***************************************************************************************
	 * Restituisce il valore del campo indicato dalla stringa in ingresso. 
	 * Se NULL restituisce il campo vuoto.
	 * 
	 * @param field stringa contenente il nome del campo. 
	 * @return String con il valore contenuto nel campo, vuoto se nullo. 
	 */
	public String getNulledField(String field) {
		//System.out.println ( "ROW [" + rowNumber + "] Getting field [" + field + "]" );
		return currentTable.getNulledField ( field, rowNumber );
	}
	
	/***************************************************************************************
	 * Se il campo indicato dalla stringa esiste (non se è valorizzato, ma se è una colonna
	 * esistente) allora il risultato è true, altrimenti false. 
	 * 
	 * @param field stringa contenente il nome del campo. 
	 * @return boolean. 
	 */
	public boolean existField(String field) {
		//System.out.println ( "ROW [" + rowNumber + "] Getting field [" + field + "]" );
		return currentTable.getColumn(field).size()>0;
	}

	/*
	public String getField( String fieldName ) {
		return currentTable.getField(fieldName, rowNumber);
	}
	*/

	/****************************************************************************************
	 * Inserisce all'interno del campo il valore indicato. 
	 * 
	 * @param columnLabel stringa contenente il nome del campo
	 * @param currentValue stringa contenente il valore da inserire
	 */
	public void addFieldValue(String columnLabel, String currentValue) {
		// PP prova risoluzione ordinamento tablebean   currentTable.getColumn(columnLabel).add (0, currentValue.trim() );
//		currentTable.getColumn(columnLabel).add (currentValue.trim() );
		currentTable.getColumn(columnLabel).add (currentValue==null ? currentValue : currentValue.trim() );
	}

	/**
	 * @return int - the rowNumber
	 */
	public int getRowNumber() {
		return rowNumber;
	}

	/**
	 * @param rowNumber int the rowNumber to set
	 */
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * @return TableBean - the currentTable
	 */
	public TableBean getCurrentTable() {
		return currentTable;
	}

	/**
	 * @param currentTable TableBean the currentTable to set
	 */
	public void setCurrentTable(TableBean currentTable) {
		this.currentTable = currentTable;
	}
}
