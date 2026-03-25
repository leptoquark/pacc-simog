package it.avlp.simog.massload;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.garamanager.lotto.LottoManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;



public class QueryCIG {
	Logger log = Logger.getLogger("QueryCIG");
	
	public void testCig()throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
		con = DriverManager.getConnection("jdbc:microsoft:sqlserver://192.168.30.230:1433;SelectMethod=Cursor;user=sa;password=step;DatabaseName=simogb1");
		pstmt = con.prepareStatement("insert into cig_test(cig,cig_cicle,cig_kkk,who" +
				") values(?,?,?,?)");
		
	    con.setAutoCommit(false);
	    con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	    LottoManager lm = new LottoManager(con,log);
	    CIGBean res = lm.generaCig(new CIGBean("test","test","test","test"),"test");
	    pstmt.setString(1, res.getCig());
	    pstmt.setInt(2,res.getCigCicle());
	    pstmt.setString(3, res.getCigKKK());
	    pstmt.setString(4, "do");
	    pstmt.execute();
	    con.commit();
		}
		finally{
	    pstmt.close();
	    con.close();
		}
	}
	public static void main(String[] args){
		
		try{
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			QueryCIG cc = new QueryCIG();
			for(int i=0;i<1000;i++)
				cc.testCig();
	    
	    
	    
	   }catch (Exception e) {
		e.printStackTrace();
   	 }
	}

}
