package it.avlp.simog.db.advanced;

import it.avlp.simog.db.generated.GARA;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class GaraTableBean extends TableBean {

	private boolean spezzata = false;
	private Long nextIdGara,prevIdGara;

	/**
	 * Metodo che usa i puntatori per l'iterazione di un gran numero di records
	 * 
	 * @param rs
	 * @param startRow
	 * @param maxRowsAllowed
	 * @param closeRS
	 * @param logger
	 * @param puntatori
	 * @throws SQLException
	 */
	public GaraTableBean(ResultSet rs, int startRow,int maxRowsAllowed, boolean closeRS,Logger logger, boolean usaSpezzata) throws SQLException {
		super();
		try{
			boolean exitFor = false;
//			logger.debug("start");
//			logger.debug("conditions[startRow:"+startRow+",maxRowsAllowed:"+maxRowsAllowed+"]");
			if(startRow > 0){ rs.absolute(startRow); }
			int begin = rs.getRow();
			rs.setFetchSize(maxRowsAllowed);
//			logger.debug("conditions[initial row:"+begin+"]");
			Long nextIdGara,prevIdGara = 0L;
			while ( !exitFor && rs.next()) {
				exitFor = ! (rs.getRow()- begin < ( maxRowsAllowed ) );
				if(exitFor){prevIdGara = rs.getLong(GARA.ID_GARA);}
//				logger.debug("exitFor:" + exitFor + ", couter:" +(rs.getRow()- begin));
				TableBeanRow newRow = new TableBeanRow ( (TableBean)this, ++super.rowsAdded );	
				this.setRow(newRow, rs);
	
			}
			//controllo se la gara ha lotti sulla prox pagina.	
			if(rs.next()){
				nextIdGara = rs.getLong(GARA.ID_GARA);
				if(prevIdGara.compareTo(nextIdGara) == 0){ this.spezzata = true; }
			}
			if(this.spezzata && usaSpezzata){
				//logger.debug("adding Empty row");
				this.addEmptyRow(rs.getMetaData(), "-1");
			}
			rs.last();
			setFullSize( rs.getRow() ); 
//			logger.debug("full size is: " + this.getFullSize());
			if(closeRS){
				try { rs.close(); } catch ( Exception e ) {}
				rs = null;		
			}
		}catch(SQLException sqle){sqle.printStackTrace(); throw(sqle);}
	}
	/**
	 * Setta l'oggetto row
	 * 
	 * @param newRow
	 * @param rs
	 * @throws SQLException
	 */
	private void setRow(TableBeanRow newRow,ResultSet rs) throws SQLException {
		java.sql.ResultSetMetaData rsmdt = rs.getMetaData();
		for ( int colCount = 1; colCount <= rsmdt.getColumnCount(); colCount++ ) {
			Object objectFromDB = rs.getObject( colCount );
			String currentValue =  objectFromDB != null ? objectFromDB.toString() : "null";
			newRow.addFieldValue ( rsmdt.getColumnLabel(colCount), currentValue );
		}
	}
	/**
	 * Metodo usato quando la query restituisce solo i record voluti (un numero limitato),
	 * ad esempio 10 (i record per una pagina)
	 * non chiude il resultSet.
	 * 
	 * @param rs
	 * @throws SQLException
	 * @deprecated
	 */
	public GaraTableBean(ResultSet rs,int maxRow,Logger logger) throws SQLException {
		int counter = 0;
		this.nextIdGara = 0L;
		this.prevIdGara = 0L;
		while ( rs.next()) {
			TableBeanRow newRow;
			if(counter < maxRow){
				newRow = new TableBeanRow ( (TableBean)this, ++super.rowsAdded );
				//logger.debug("count:" +counter);
				this.setRow(newRow, rs);
			}
			this.prevIdGara = this.nextIdGara;
			//logger.debug("prevIdGara:" +prevIdGara);
			this.nextIdGara = rs.getLong(GARA.ID_GARA);
			//logger.debug("nextIdGara :" +nextIdGara );
			counter++;
		}
		if(this.isSpezzata(counter,maxRow) ){
			//logger.debug("adding Empty row");
			this.addEmptyRow(rs.getMetaData(), "-1");
		}
		
	}

	
	/**
	 * Metodo usato quando la query restituisce solo i record voluti (un numero limitato),
	 * ad esempio 10 (i record per una pagina)
	 * non chiude il resultSet.
	 * 
	 * @param rs
	 * @throws SQLException
	 * @deprecated
	 */
	public GaraTableBean(ResultSet rs,ResultSet rs1,int maxRow,Logger logger) throws SQLException {
		int counter = 0;
		this.nextIdGara = 0L;
		this.prevIdGara = 0L;
		while ( rs.next()) {
			TableBeanRow newRow;
			if(counter < maxRow){
				newRow = new TableBeanRow ( (TableBean)this, ++super.rowsAdded );
				//logger.debug("count:" +counter);
				this.setRow(newRow, rs);
			}
			this.prevIdGara = rs.getLong(GARA.ID_GARA);
			//logger.debug("prevIdGara:" +prevIdGara);
			counter++;
		}
		// se il secondo result set non e' nullo controlla se la gara e' spezzata tra due pagine.
		if(rs1 != null){
			if(rs1.next()){
				this.nextIdGara = rs1.getLong(GARA.ID_GARA);
				//logger.debug("nextIdGara :" +nextIdGara );
			}
			if(this.isSpezzata() ){
				//logger.debug("adding Empty row");
				this.addEmptyRow(rs.getMetaData(), "-1");
			}
		}
		
	}
	

	/**
	 * Controlla se la gara e' spezzata su piu' pagine
	 * 
	 * @param counter
	 * @param maxRow
	 * @return
	 * @deprecated
	 */
	private boolean isSpezzata() {
		//controllo se sono uguali e se il valore dll'id non sia quello iniziale.
		this.spezzata = this.prevIdGara.compareTo(this.nextIdGara) == 0;
		return this.spezzata;
	}
	
	/**
	 * Controlla se la gara e' spezzata su piu' pagine
	 * 
	 * @param counter
	 * @param maxRow
	 * @return
	 * @deprecated
	 */
	private boolean isSpezzata(int counter, int maxRow) {
		//controllo se sono uguali e se il valore dll'id non sia quello iniziale.
		this.spezzata = this.prevIdGara.compareTo(this.nextIdGara) == 0 && !this.nextIdGara.equals((Long)0L) && counter > maxRow;
		return this.spezzata;
	}
	//public boolean isSpezzata(){ return this.spezzata; }
	

}
