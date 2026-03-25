package it.avcp.spc.appalti.ejb.servizi;

import it.avcp.spc.appalti.ejbImpl.servizi.RicercaContributoTo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//@Remote
public interface GestioneContributoWrapper {
	
	public RicercaContributoTo determinazioneContributoOE(String codiceFiscale, BigDecimal importo, Date dataPubbl,String motivoEscusione, String tipoProcedura, String accordoQuadro,String Applicazione, long idGara );
	public RicercaContributoTo determinazioneContributoSA(String codiceFiscale, BigDecimal importo, Date dataPubbl,String motivoEscusione, String tipoProcedura, String accordoQuadro,String Applicazione, long idGara );
	public RicercaContributoTo listaDeterminazioneContributoOE(String codiceFiscale, List<BigDecimal> importo, Date dataPubbl,String motivoEscusione, String tipoProcedura, String accordoQuadro, String Applicazione);
	public RicercaContributoTo listaDeterminazioneContributoSA(String codiceFiscale, List<BigDecimal> importo, Date dataPubbl,String motivoEscusione, String tipoProcedura, String accordoQuadro, String Applicazione);	

}
