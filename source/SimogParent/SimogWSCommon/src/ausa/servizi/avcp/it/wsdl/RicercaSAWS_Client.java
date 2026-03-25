package ausa.servizi.avcp.it.wsdl;

import java.rmi.RemoteException;

import it.avlp.simog.util.SimogProperties;

public class RicercaSAWS_Client {

	public static void main(String[] args) {
		RicercaSAWSProxy proxy = new RicercaSAWSProxy("http://10.119.26.28:8080/ServiziAUSA-ear-ServiziAUSA-ejb/RicercaSABean");
		try {
			ListaAusaDTO lista = proxy.ricercaSAdaCFWS("01307110484", null);
			System.out.println(lista.getEsito().getCodiceEsito());
			System.out.println(lista.getLista(0).getCodiceAusa());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getCodAusaFromCF(String cfAmm) {
		String res = "";
		RicercaSAWSProxy proxy = new RicercaSAWSProxy(SimogProperties.getInstance().getUrlWsAusa());
		
		try {
			ListaAusaDTO lista = proxy.ricercaSAdaCFWS(cfAmm, null);
			if("000".equals(lista.getEsito().getCodiceEsito()) && lista.getLista().length>0)
			   res = lista.getLista(0).getCodiceAusa();
		} catch (RemoteException e) {
			e.printStackTrace();
			return "Codice AUSA non disponibile";
		}
		
		return res;
	}
	
}
