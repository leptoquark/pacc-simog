package test;

import it.avlp.simog.massload.Main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String pre = "c:/TEST/";
		String in = "/WRK";
		String prima = Test1.now();
//		String[] folders = new String[]{pre +"PIEMONTE",
//										pre +"ENEL",
//										pre +"LOMBARDIA",
//										pre +"FRIULI",
//										pre +"AOSTA"};
		String[] folders = new String[]{pre +"LOMBARDIA"};
		for(int i = 0;i<folders.length;i++){
			//System.out.println("[folder ("+i+")]" + Test1.now());
			File folder = new File(folders[i] + in);
			List<String> files = Arrays.asList(folder.list());
			int i1 = 0;
			for(String s : files){
				i1++;
				String nomePathConf = "C:/TEST/MassLoaderEnv/conf";
				String nomeFileConf = "massloader.properties";
				try{
					//System.out.println("[file ("+i1+")]" + Test1.now());
					//if(new File(folders[i]).isFile()){
						//Thread.sleep(1000);
						Main.main(new String[]{folders[i],s,nomePathConf,nomeFileConf});
					//}
					//System.out.println("[file ("+i1+")]" + Test1.now() +"END");
				}catch(Exception e){
					System.err.println("Exception");
					//System.exit(0);
				}System.out.println("file:"+s+", Nr. : "+i1);
			}
			//System.out.println("[folder ("+i+")]" + Test1.now() + " END");
		}
		System.out.println("[BEGIN ("+prima+")] END ["+Test1.now()+"]");

	}
	 public static String now() {
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss,SSS");
		    return sdf.format(cal.getTime());

	 }

}
