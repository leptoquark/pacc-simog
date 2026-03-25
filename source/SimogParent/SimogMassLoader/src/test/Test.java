package test;

import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.massload.MassLoader;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument;
public class Test {

	public static void main(String[] args){
		try{
			/** Ricordarsi di settare isDerby a true in Main.main**/
			//////Main.main(new String[]{"DEBUG"});
			/**cancellazione intero db Derby**/
			//Main.main(new String[]{"C:/TEST/AZIENDA","DELETE"});
			/**creazione dei files CSV**/
			//Main.main(new String[]{"C:/TEST/AZIENDA","C:/TEST/MassLoaderEnv/conf","massloader.properties","CREATE_REPORT"});
//		   FeedBackDocument outputNew = new FeedBackDocument();
//		   FeedBackDocument output = new FeedBackDocument();
//			String input = "";
//			
//			Main.mainStream("utenteTest", "/TEST/MassLoaderEnv/conf", "massloader.properties", input, output, outputNew);
//			
			MassLoader classe = new MassLoader(OrigineSchedaEnum.ND);
		
			String s = IOReader.extractContents("C:/TEST/AZIENDA/WRK/scheda.xml");
            String buffer = new String(s.getBytes("UTF-8"),"UTF-8");
            FeedBackDocument out = (FeedBackDocument) FeedBackDocument.Factory.newInstance();
            FeedBackDocument outNew = (FeedBackDocument) FeedBackDocument.Factory.newInstance();
            
 ////           String ret = classe.mainStream("pippo", "/test/massloaderenv/conf", "massloader.properties", buffer, out, outNew);
		   
            System.out.println(outNew.xmlText());
            
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}
