package it.avlp.simog.ws.util;

//import it.avlp.simog.ws.commons.LoggerManager;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * classe per l'encoding (eventualmente anche deconding)
 * di stringhe
 * 
 * */
public class Encoder {
	public static int counter = 0;
	
	/*******************************************************************
	 * Effettua l'encoding della stringa xml
	 * @param xml : String
	 * @return String 
	 * @throws RuntimeException
	 */
	public static String encode(String xml) throws RuntimeException{
		if(counter == 1000){
			counter = 0;
		}
		//Logger logger = LoggerManager.getInstance().getLogger();
		Date dt = new Date();
		long millis = dt.getTime()+counter;
		Random random = new Random(millis);
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm.S" );
		String date = df.format(dt);
		String all =  date+"x"+xml+random.nextFloat();
		counter++;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } 
        catch (NoSuchAlgorithmException e) {
        	//logger.error("sha1 non � supportato dalla piattaforma");
            throw new RuntimeException("sha1 not supported by plattform");
        }
        try {
            byte[] result = md.digest(all.getBytes("ISO-8859-1"));
            StringBuffer resultBuffer = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                resultBuffer.append(byte2Hex(result[i]));
            }
            return resultBuffer.toString();
        } 
        catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("utf-8 not supported by plattform");
        }
	}
	/************************************************
	 * Conversione da byte a esadecimale 
	 * param b : byte
	 * return String
	 */
	private static String byte2Hex(byte b) {
        int i = unsignedByteToInt(b);
        String hexChars = "0123456789abcdef";
        byte low = (byte) (i % 16);
        byte high = (byte) (i / 16);
        return "" + hexChars.charAt(high) + hexChars.charAt(low);
    }

    /***********************************************
     * trasforma da unsigned byte a int
     * param b : byte
     * return int
     */
    private static int unsignedByteToInt(byte b) {
        return (int) b & 0xFF;
    }


}