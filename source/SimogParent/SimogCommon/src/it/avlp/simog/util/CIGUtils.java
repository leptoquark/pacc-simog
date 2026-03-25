package it.avlp.simog.util;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;

public class CIGUtils {

	public String calcolaVecchioKKK(Long cig) {
//		logger.error("calcolaVecchioKKK ---");
//		logger.info("calcolaVecchioKKK ---");
		System.out.println("calcolaVecchioKKK ---");
		long kkk = (cig * 211) % 4091;
		
		String hexKkk = Long.toHexString(kkk);
		
		String hex = StringUtils.leftPad(hexKkk, 3, "0").toUpperCase();		
		
		return hex;
	}	
	
	public String calcolaCig(String cig_letter, Long cig_index) {
//		logger.error("NUOVO ALGORITMO ---> calcolaCig");
//		logger.info("NUOVO ALGORITMO ---> calcolaCig");
		System.out.println("NUOVO ALGORITMO ---> calcolaCig");

		String newCig = "";

		String CIGNN = Long.toHexString(cig_index).toUpperCase();

		CIGNN = "000000" + CIGNN;
		CIGNN = CIGNN.substring(CIGNN.length() - 6);

		//Long.toHexString(cig);
		//String fill = StringUtils.leftPad(hexCig, 6, "0").toUpperCase();

		
		
		newCig = (cig_letter + CIGNN).toUpperCase();

		return newCig;
	}
	
	
	
	public String calcolaKKK(String cig_letter, Long nnnnnn) {
//		logger.error("NUOVO ALGORITMO ---> calcolaKKK");
//		logger.info("NUOVO ALGORITMO ---> calcolaKKK");
		System.out.println("NUOVO ALGORITMO ---> calcolaKKK");

		String A = cig_letter;

		String ABC = "ABCDEFGHIJKLMNOPQRSTU";
		Integer ORD = ABC.indexOf(A) + 1;
		Long kkk = (nnnnnn + ORD) * 211 % 4091; //

		String kkkS=hexKKK(kkk);
		return kkkS;
	}
	
	public String hexKKK(Long kkk) {

		String CIG_KKK = Long.toHexString(kkk).toUpperCase();

		CIG_KKK = "000" + CIG_KKK;
		CIG_KKK = CIG_KKK.substring(CIG_KKK.length() - 3);

		return CIG_KKK;

	}

	public String calcolaKKKfromHex(String esaCig) {
		// estrazione del primo carattere dall'esadecimale
		String A = esaCig.substring(0, 1);
		// estrazione degli altri caratteri (6 esadecimali)
		String NNNNNN = esaCig.substring(1);
		// conversione in long
		Long NNNNNNl = new BigInteger(NNNNNN, 16).longValue();

		String abc = "ABCDEFGHILMNOPQRSTU";
		
		Integer ORD = abc.indexOf(A) + 1;

		// calcolo del KKK
		// KKK = Hex((Dec(NNNNNN) + Ord (A)) * 211 mod 4091)
		Long kkk = (NNNNNNl + ORD) * 211 % 4091; //

		String hex = Long.toHexString(kkk);
		
		String fill = StringUtils.leftPad(String.valueOf(hex), 3, "0").toUpperCase();
		
		return fill;
	}

}
