package it.anticorruzione.ted.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraWS;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoWS;
import it.avlp.simog.ws.massload.xmlbeans.SchedaType;

public class MarshallerTED {

	public static String marshalDeltaGaraTED(DeltaGaraTED deltaGaraTED) {
		DeltaGaraWS deltaGaraWS = new DeltaGaraWS();
		deltaGaraWS.setDeltaGara(deltaGaraTED);
		
		StringWriter sw = new StringWriter();
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(DeltaGaraWS.class);
			Marshaller m = jaxbContext.createMarshaller();
			m.marshal(deltaGaraWS, sw);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sw.toString();
	}
	
	public static String marshalDeltaLottoTED(DeltaLottoTED deltaLottoTED) {
		DeltaLottoWS deltaLottoWS = new DeltaLottoWS();
		deltaLottoWS.setDeltaLotto(deltaLottoTED);
		
		StringWriter sw = new StringWriter();
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(DeltaLottoWS.class);
			Marshaller m = jaxbContext.createMarshaller();
			m.marshal(deltaLottoWS, sw);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sw.toString();
	}
	
	public static DeltaGaraTED unmarshalDeltaGaraTED(String deltaGaraStr) {
		DeltaGaraWS deltaGaraWS = new DeltaGaraWS();

				String packageName = deltaGaraWS.getClass().getPackage().getName();
				
				JAXBContext jc;
				try {
					
					jc = JAXBContext.newInstance(packageName);
					Unmarshaller u = jc.createUnmarshaller();
					ByteArrayInputStream is = new ByteArrayInputStream(deltaGaraStr.getBytes());
					deltaGaraWS = (DeltaGaraWS) u.unmarshal(is);
					
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				
				return deltaGaraWS.getDeltaGara();
	}
	
	
	public static DeltaLottoTED unmarshalDeltaLottoTED(String deltaLottoStr) {
		DeltaLottoWS deltaLottoWS = new DeltaLottoWS();

				String packageName = deltaLottoWS.getClass().getPackage().getName();
				
				JAXBContext jc;
				try {
					
					jc = JAXBContext.newInstance(packageName);
					Unmarshaller u = jc.createUnmarshaller();
					ByteArrayInputStream is = new ByteArrayInputStream(deltaLottoStr.getBytes());
					deltaLottoWS = (DeltaLottoWS) u.unmarshal(is);
					
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				
				return deltaLottoWS.getDeltaLotto();
	}
}
