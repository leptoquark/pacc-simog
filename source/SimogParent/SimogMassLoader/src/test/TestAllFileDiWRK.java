package test;

import it.avlp.simog.massload.Main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Classe che serve per testare tutti i files presenti nella cartella indicata
 * @author vletizia
 *
 */
public class TestAllFileDiWRK {

	public static void main(String[] args) {

		String prima = Test1.now();
		File folder = new File("C:/TEST/AZIENDA/WRK");
		List<String> files = Arrays.asList(folder.list());
		String nomePathConf = "C:/TEST/MassLoaderEnv/conf";
		String nomeFileConf = "massloader.properties";
		int i1 = 0;
		for(String s : files){
			i1++;
			
			try{
				System.out.println("[file ("+i1+")]" + Test1.now());
				if(new File("C:/TEST/AZIENDA/WRK/" + s).isFile()){
					System.out.println("Is a File: " +s);
					// TODO: VL PER RIPRISTINARE LA FUNZIONALITA DI QUESTA CLASSE DI TEST VEDI XXX su nomefile in Main
//					Main.nomeFile = s;
					Main.main(new String[]{"C:/TEST/AZIENDA/",s,nomePathConf,nomeFileConf});
				}else{
					System.out.println("Is Not a File: "+s);
				}
				System.out.println("[file ("+i1+")]" + Test1.now() +"END");
			}catch(Exception e){
				System.err.println("Exception");
			}System.out.println("file:"+s+", Nr. : "+i1);
		}

		System.out.println("[BEGIN ("+prima+")] END ["+Test1.now()+"]");

	}
	 public static String now() {
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss,SSS");
		    return sdf.format(cal.getTime());

	 }
}
