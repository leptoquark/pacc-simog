package it.anticorruzione.ted.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.validator.UrlValidator;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.anticorruzione.ted.beans.LottoTED;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.ws.massload.xmlbeans.AppaltoTypeAgg;

public class UtilityClass {

	private final static String ROOT_ELEMENT="rec:TED_ESENDERS";
	
	public static Calendar currentCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.clear(Calendar.ZONE_OFFSET);
		return cal;
	}
	
	public static Calendar currentCalendar(int day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day);
		cal.clear(Calendar.ZONE_OFFSET);
		
		return cal;
	}
	
	public static Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.clear(Calendar.ZONE_OFFSET);
        return calendar;

    }
	
	public static String dateToString(Date date) {
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(date);
	}
	
	public static Date stringTEDtoDate(String tedDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		try {
			return df.parse(tedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dateToStringTED(Date tedDate, boolean truncateDate) {
		String formatStr = "yyyy-MM-dd";
		if(!truncateDate)
			formatStr+="'T'HH:mm:ss'Z'";
		
		DateFormat df = new SimpleDateFormat(formatStr);
		return df.format(tedDate);

		
	}
	
	public static Calendar stringToCalendar(String strDate, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date data = new Date();
		try {
			data = sdf.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateToCalendar(data);
	}
	
	public static String getEncodedAuth(String login, String password) {
		return new UtilityClass().encodeString(new String(login+":"+password));
	}
	
	public static String encodeString(String stringa) {
		return new String(Base64.encodeBase64(stringa.getBytes()));
	}
	
	/**
	 * Rimuovi gli spazi dai tag 
	 * @param noticeString la stringa xml da trimmare 
	 * @return la stringa xml trimmata
	 */
public static String trimma(String noticeString) {
		noticeString = noticeString.replace("xml-fragment", ROOT_ELEMENT).trim();
	    return noticeString.replaceAll(">[\\s\r\n]*<", "><");
	 
}

	public static boolean checkProceduraPiuFasi(String id_proc) {
		return id_proc.equals("2") || 
				id_proc.equals("9") || 
				id_proc.equals("13") || 
				id_proc.equals("25") || 
				id_proc.equals("8") ||
				id_proc.equals("30");
	}
	
	public static boolean checkTipoSceltaContraente(String procDelta, String procLotto) {
		if(procDelta.equals("1")) 
			return procLotto.equals(String.valueOf(Costanti.PROC_APE));
		if(procDelta.equals("2"))
			return procLotto.equals(String.valueOf(Costanti.PROC_RIS));
		if(procDelta.equals("3"))
			return procLotto.equals(String.valueOf(Costanti.PROC_COMP_NEG));
		if(procDelta.equals("4"))
			return procLotto.equals(String.valueOf(Costanti.DIA_COMP));
		if(procDelta.equals("5"))
			return procLotto.equals(String.valueOf(Costanti.PAT_INN));
		
		return false;
	}
	
	public static LottoTED getLottoFromCIG(List<LottoTED> listaLotti, String cig) {
		
		LottoTED res = null;
		for(LottoTED l : listaLotti) {
			String currCig = l.getLotto().getFullCIG();
			if(currCig.equals(cig)) {
				res = l;
				break;
			}
		}
		
		return res;
		
	}

	public static String getLotNo(List<LottoTED> listaLotti, String cigrettifica) {

			for(LottoTED lt : listaLotti) {
				if(lt.getLotto().getFullCIG().equals(cigrettifica))
					return String.valueOf(lt.getDeltaLottoTED().getNOLOT());
			}
		
		return null;
	}

	public static BigDecimal[] getLowHighTotal(List<AggiudicatarioBean> aggiudicatari) {
		BigDecimal[] res = null;
		List<BigDecimal> importi = new ArrayList<BigDecimal>();
		for(AggiudicatarioBean agg : aggiudicatari) {
			if(agg.getImpAggiudicatario()!=null && agg.getImpAggiudicatario().doubleValue()>0) {
				importi.add(agg.getImpAggiudicatario());
			}
		}
		
		if(importi.size()>=2) {
		   Collections.sort(importi);
		   res = new BigDecimal[] {importi.get(0),importi.get(importi.size()-1)};
		}
		
		return res;
		
	}

	public static AppaltoTypeAgg getAppaltoTypeAgg(List<AppaltoTypeAgg> listaAppAgg, String cigModifica) {
		
		for(AppaltoTypeAgg appalto : listaAppAgg) {
			if(appalto.getCIGAGG().getValue().equals(cigModifica))
				return appalto;
		}
		
		return null;
	}
	
	public static String getCurrentYear() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		return String.valueOf(year);
	}
	
	public static boolean isUrlValid(String url) {
		UrlValidator urlValidator = new UrlValidator();
		return urlValidator.isValid(url);
	}
	
	public static Date getDateWithoutTime(Date dateToConvert) {
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(dateToConvert);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long time = cal.getTimeInMillis();
		return new Date(time);
	}

	public static String convertNoDocOjs(String noDocOjs) {
		String res = "S ";
		String noOjs = noDocOjs.split(" ")[1].split("-")[0];
		res+=noOjs+"/";
		String year = noDocOjs.split("/")[0];
		res+=year;
		return res;
	}
	
	public static BigDecimal roundDecimal(BigDecimal value) {
		return value.setScale(2);
	}
	
	public static Document getDocumentFromXmlString(String xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}
}
