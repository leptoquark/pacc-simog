package it.avlp.simog.util;
import java.math.BigInteger;
    
public class CodiceControllo {
	private static final int N[] = {32,48,64,96,112,128,144,160,192,256};
	private static final String publicKey = "65537";
	private static final String modulus[] = {"2645112959","203279822500049",
			"8329355063936851921","39441330075525468182107262717",
			"1974337838470719337734969171740459","240009518785564796693868726276038113547",
			"14809614163281829792758328763193230643048811","852975604640408862016024205293272395404295985323",
			"5007881904411039256854436987575386594205237492299211538541",
			"58824809103317033805871346600368415152919595076982951438971139914674397375651"};
   
    
    /*********************************************************************************************************
     * recupera il codice di controllo dal messaggio in ingresso
     * 
     * @param message String
     * @return String
     */
    public static String getCodiceControllo(String message){
    	
    	int use = 0;
    	
    	if(message == null || message.length() == 0)
    		return "";
    	else{
    		BigInteger msg = new BigInteger(message.getBytes());
    		int dim = msg.bitLength();
    		while(use < N.length && dim > N[use]){
    			use ++;
    		}
    		if(use == N.length)
    			return "";
    		else{
    			BigInteger pk = new BigInteger(publicKey);
    			BigInteger mod = new BigInteger(modulus[use]);
    		
    			return  use + encrypt(msg,pk,mod).toString();
    		}
    			
    		
    		
    	}
    }
    
/*********************************************************************************************
 * Cripta il messaggio 
 * 
 * param message String
 * param pub BigInteger
 * param mod BigInteger
 * return BigInteger
 */
private static BigInteger encrypt(BigInteger message, BigInteger pub, BigInteger mod) {
        return message.modPow(pub, mod);
     }
   
    
}
