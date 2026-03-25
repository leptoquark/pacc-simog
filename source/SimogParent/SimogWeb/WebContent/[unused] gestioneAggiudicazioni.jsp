<%@ page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>

<%@ page import="it.avlp.simog.beans.*" %>
<%@ page import="it.avlp.simog.common.servlet.*" %>
<%@ page import="it.avlp.simog.util.*" %>
<%@ page import="it.avlp.simog.db.advanced.*" %>
<%@ page import="it.avlp.simog.db.generated.*" %>
<%@ include file="script/domUtils.js" %>

<%@ taglib uri="/WEB-INF/tlds/tagutils.tld" prefix="utils" %>
<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />

<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>
<script type="text/javascript" src="script/pageutils.js"></script>
<!-- language for the calendar -->
<script type="text/javascript" src="calendar/calendar-it.js"></script>

<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>

<%@page import="java.util.ArrayList"%>
<html>
<head>
<% String currentDate =  PageHelper.getCurrentDate(); %>
<% TableBean listaAggiudicazioni = (request.getAttribute(ParametriServlet.LISTA_AGGIUDICAZIONI_TABLEBEAN)!=null)? (TableBean)request.getAttribute(ParametriServlet.LISTA_AGGIUDICAZIONI_TABLEBEAN) : null; %>
<% TableBean datiComuni = (request.getAttribute(PSBD.BLOCCO_DATI_INFO_COMUNI)!=null)?(TableBean)request.getAttribute(PSBD.BLOCCO_DATI_INFO_COMUNI): null; %>
<% TableBean datiPreInsert = (request.getAttribute(ParametriServlet.DATI_PREINSERT_TABLEBEAN)!=null)? (TableBean)request.getAttribute(ParametriServlet.DATI_PREINSERT_TABLEBEAN) : null; %>
<% TableBean datiGara = (request.getAttribute(ParametriServlet.DATI_GARA_TABLEBEAN)!=null)?(TableBean)request.getAttribute(ParametriServlet.DATI_GARA_TABLEBEAN): null; %>


<% TableBeanRow row = (datiComuni!=null)? datiComuni.getRow(0): null; %>
<% TableBeanRow rowGara = (datiGara!=null)? datiGara.getRow(0): null; %>
<% String modificato = (request.getAttribute("Modificato") != null)? (String)request.getAttribute("Modificato") : "0"; %>
<% String modificato0 = (request.getAttribute("Modificato0") != null)? (String)request.getAttribute("Modificato0") : "0"; %>
<%	int indiceTab = 0; %>

<title>Gestione Aggiudicazioni - <%= user.getProfilo() %></title>
</head>
<%@ include file="include/i18n-init.inc" %>
<%@ include file="script/script.js" %>
<body>
	<div id="gabbia">
		<%@ include file="include/header.inc" %>
	
		
	
	<%String showDatiComuni = (String)request.getAttribute(ParametriServlet.SHOW_DATI_COMUNI); %>
	
	<% TableBeanRow rowpre = (datiPreInsert!=null)? datiPreInsert.getRow(0): null; %>
	<% String idLotto = (datiPreInsert!=null)? rowpre.getNulledField(LOTTO.ID_LOTTO) : row.getNulledField(LOTTO.ID_LOTTO);  %>
	<% String cig = (datiPreInsert!=null)? rowpre.getNulledField(LOTTO.CIG): ""; %>
	<% String cig_cicle = (datiPreInsert!=null)? rowpre.getNulledField(LOTTO.CIG_CICLE): ""; %>
	<% String cig_kkk = (datiPreInsert!=null)? rowpre.getNulledField(LOTTO.CIG_KKK): ""; %>
	<% String id_scelta = (datiPreInsert!=null)? rowpre.getNulledField(SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE): ""; %>
	<!-- 2846 -->
	<% String id_motivo = (datiPreInsert!=null)? rowpre.getNulledField(MOTIVO_COLLEGAMENTO.ID_MOTIVO): ""; %>	
	<!-- 2846 -->
	<% String importoLotto = (datiPreInsert!=null)? rowpre.getNulledField(LOTTO.IMPORTO_LOTTO) : row.getNulledField(LOTTO.IMPORTO_LOTTO);  %>
	<% String idInfo = (datiComuni!=null)? row.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO) : "";  %>
	<% String idPubblicazione = (datiComuni!=null)? row.getNulledField(PUBBLICAZIONI.ID_PUBBLICAZIONE) : "";  %>
	<% String dataInizioInfo = (datiComuni!=null)? row.getNulledField(INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO) : "";  %>
	<% String dataInizioPub = (datiComuni!=null)? row.getNulledField(PUBBLICAZIONI.DATA_INIZIO_PUBB) : "";  %>
	<% String tipoente =  (datiComuni!=null)? row.getNulledField(INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE) : "";  %>
	<% String tipocontratto =  (datiComuni!=null)? row.getNulledField(INFO_AGGIUDICAZIONI.TIPO_CONTRATTO) : ""; 
		boolean isOrdinario = tipoente.equals("O");
		String data_cancellazione_lotto = (datiComuni!=null)? row.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO) : ""; 
		String data_inib_pagamenti = (datiComuni!=null)? row.getNulledField(LOTTO.DATA_INIB_PAGAMENTO) : ""; 
		boolean lottoCancellato = ! "".equals( data_cancellazione_lotto ) || ! "".equals( data_inib_pagamenti ); 
	String existsColor = "green";
	String notExistColor = "red";%>
 <div class="bodypage-e">
			<h1>Gestione Schede - Aggiudicazioni</h1>
			
			<%@ include file="include/gestisciErrore.inc" %>
			<div class="hmenu">	
					<ul>
						<% String parametriAggiungi = "pagina=scheda1/aggiudicazioni.jsp&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+idInfo+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+dataInizioInfo+"&"+ParametriServlet.FIELD_NAME_ID_LOTTO+"="+idLotto; %>
						<%if(datiPreInsert==null && !lottoCancellato && !user.isOssReg()){ %>
							<li><a title="Aggiungi Aggiudicazione" href="javascript:changePage('<%= ParametriServlet.SRV_SCHEDA_A %>','Modificato')">Aggiungi Aggiudicazione</a></li>
						<% } %>	
						<% if((listaAggiudicazioni!=null)&&(listaAggiudicazioni.getFullSize()>0)&&(showDatiComuni.equals("false"))) { %>
							<%// String parametriInfoComuni = ParametriServlet.FIELD_NAME_ID_LOTTO+"="+row.getNulledField(LOTTO.ID_LOTTO)+"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+idInfo+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+dataInizioInfo+"&"+ParametriServlet.SHOW_DATI_COMUNI+"="+"true"; %>
							<li><a title="Mostra Dati Comuni" href="javascript:changeTab('<%= PSBD.TAB_INFO_COMUNI%>','Modificato')">Mostra Dati Comuni</a></li>
						<% } %>
						<% if("true".equalsIgnoreCase(showDatiComuni)) {%>
							<%// String parametriTorna = (ParametriServlet.FIELD_NAME_ID_LOTTO+"="+id+"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+idInfo+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+dataInizioInfo); %>
							<li><a title="Lista Aggiudicazioni" href="javascript:changePage('<%=ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA%>','Modificato')" >Lista Aggiudicazioni</a></li>
						<% } %>
					</ul>
			</div>  
			<% String proc_acc = (datiComuni!=null)? row.getNulledField(INFO_AGGIUDICAZIONI.PROCEDURA_ACC): (request.getParameter(ParametriServlet.FIELD_NAME_PROCEDURA_ACC)!=null ? request.getParameter(ParametriServlet.FIELD_NAME_PROCEDURA_ACC):""); %>
			<% String preinf = (datiComuni!=null)? row.getNulledField(INFO_AGGIUDICAZIONI.PREINFORMAZIONE): ""; %>
			<% String term_rid = (datiComuni!=null)? row.getNulledField(INFO_AGGIUDICAZIONI.TERMINE_RIDOTTO): ""; %>
			
			<% String prof_comm = (datiComuni!=null)? row.getNulledField(PUBBLICAZIONI.PROFILO_COMMITTENTE): ""; %>
			<% String sito_min_inf_trasp = (datiComuni!=null)? row.getNulledField(PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP): ""; %>
			<% String sito_osserv = (datiComuni!=null)? row.getNulledField(PUBBLICAZIONI.SITO_OSSERVATORIO_CP): ""; %>
			
			<% boolean confermato = (datiComuni!=null)?(row.getNulledField(INFO_AGGIUDICAZIONI.ID_STATO)).equals(StatiScheda.CONFERMATO_STRING):false; %>
			<% String disabilitato = (lottoCancellato || confermato) ? "disabled" : ""; %>
			<form action="<%=ParametriServlet.SRV_SCHEDA_A %>" method="post" onkeypress="setFormModified('Modificato0')">
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_LOTTO %>" value="<%= idLotto %>">
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_INFO %>" value="<%= idInfo %>">
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE %>" value="<%= idPubblicazione %>">
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO %>" value="<%= dataInizioInfo %>">
				<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB %>" value="<%= dataInizioPub %>">
				<% if(datiPreInsert!=null){ %>
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_CIG %>" value="<%= cig %>">	
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_CIG_CYCLE %>" value="<%= cig_cicle %>">
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_CIG_KKK %>" value="<%= cig_kkk %>">
					<input type="hidden" name="<%= ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE %>" value="<%= id_scelta %>">
				<% } %>				
				<input type="hidden" name="pagina" value="scheda1/aggiudicazioni.jsp">
				<% if(showDatiComuni.equals("true")) {%>
					<input type="hidden" name="<%= ParametriServlet.SHOW_DATI_COMUNI %>" value="false">
				<% } else { %>
					<input type="hidden" name="<%= ParametriServlet.SHOW_DATI_COMUNI %>" value="true">
				<% } %>				
					<input type="hidden" name="<%= PSBD.TAB %>" id="tab" value="">
					<input type="hidden" name="<%= PSBD.ACTION_TYPE %>" id="tipoAzione" value="">
				<input type="hidden" id="Modificato" name="Modificato" value="<%= modificato %>">
				<input type="hidden" id="Modificato0" name="Modificato0" value="<%= modificato0 %>">
				<input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute("ParametriServlet.checkIfOK).toString()) + 1%>" />
				
				
				<% if((listaAggiudicazioni==null)||((listaAggiudicazioni!=null)&&(listaAggiudicazioni.getFullSize()==0))||(showDatiComuni.equals("true"))) { %>
					<%@ include file="include/elencoDatiComuni.inc" %>
				<% } %>
				
				
				
				<% if((listaAggiudicazioni!=null)&&(listaAggiudicazioni.getFullSize()>0)&&(showDatiComuni.equals("false"))) { %>
					<% int maxRigheVisualizzabili = Integer.parseInt( (String)request.getAttribute( ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI ) ); %>
				
					<% Integer startRowInt = (Integer)request.getAttribute( ParametriServlet.START_ROW ); %>
				
					<% int startRow = startRowInt.intValue(); %>
					<% int righeVisualizzate = startRow + listaAggiudicazioni.getTableSize();%>
					<% if ( righeVisualizzate > listaAggiudicazioni.getFullSize() ) { %>
						<% righeVisualizzate = listaAggiudicazioni.getFullSize(); %>
				
					<% } %>
					<%// String parametrinext = ParametriServlet.FIELD_NAME_ID_LOTTO+"="+row.getNulledField(LOTTO.ID_LOTTO)+"&"+ParametriServlet.ACTION_GET_LIST+"="+ParametriServlet.PROGRESS+"&"+ParametriServlet.START_ROW+"="+startRow; %>
					<%// String parametriprevious = ParametriServlet.FIELD_NAME_ID_LOTTO+"="+row.getNulledField(LOTTO.ID_LOTTO)+"&"+ParametriServlet.ACTION_GET_LIST+"="+ParametriServlet.REGRESS+"&"+ParametriServlet.START_ROW+"="+startRow; %>
					
					<input type="hidden" name="<%= ParametriServlet.START_ROW %>" id="<%= ParametriServlet.START_ROW %>" value="<%= startRow %>">
					<input type="hidden" name="<%= ParametriServlet.ACTION_GET_LIST %>" id="<%= ParametriServlet.ACTION_GET_LIST %>" value="">
				</form>
					<div class="hmenu">
						<ul>
							<% if ( righeVisualizzate >  maxRigheVisualizzabili ) { %>
								<li><a href="javascript:loadPages('<%= ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA %>','<%= ParametriServlet.REGRESS %>')" title="Visualizza Precedenti">Precedenti</a></li>
							<% } %>
						
							<% if ( listaAggiudicazioni.getFullSize() - righeVisualizzate > 0 ) { %>
								<li><a href="javascript:loadPages('<%= ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA %>','<%= ParametriServlet.PROGRESS %>')" title="Visualizza Successive">Successive</a></li>
							<% } %>
						</ul>
					</div>
					
					<h3>Visualizzate <%= righeVisualizzate %>/<%= listaAggiudicazioni.getFullSize() %> Aggiudicazioni</h3>
					<!--  SCROLL -->
					<div class="scroll">
					<!-- SCROLL INSIDE -->
						<div class="scrollInside">
							<% TableBeanRow currentRow = null; %>
							<div class="elenco">
								<div class="gara">
								<% for ( int rowIndex = 0; rowIndex < listaAggiudicazioni.getTableSize(); rowIndex++ ) { %>
								<% currentRow = listaAggiudicazioni.getRow(rowIndex); %>
									<h4>Informazioni Aggiudicazione</h4>
									<table>   
										<tr >
											<th class="garaTh" width="20%">CUI</th>
											<th class="garaTh" width="30%">DATA INSERIMENTO AGGIUDICAZIONE</th>
											<th class="garaTh" width="25%">STATO AGGIUDICAZIONE</th>
											<th class="garaTh" width="10%">DETTAGLI</th>							
											
										</tr>
										<tr>
											<td class="garaTd"><%= currentRow.getNulledField( AGGIUDICAZIONI.CUI ) %>-<%=currentRow.getNulledField( AGGIUDICAZIONI.PROG_CUI ) %> </td>
											<td class="garaTd"><%= PageHelper.getFormattedDateFromDateTime(currentRow.getNulledField( AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE )) %></td>
											<td class="garaTd"><%= currentRow.getNulledField( STATI_SCHEDA.DESCRIZIONE ) %></td>
											
											<% String paramAggiud = PSBD.FIELD_NAME_ID_AGGIUDICAZIONE+"="+ currentRow.getNulledField(AGGIUDICAZIONI.ID_AGGIUDICAZIONE)+"&"+PSBD.DATA_INIZIO_AGGIUDICAZIONE+"="+currentRow.getNulledField(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE)
																	+"&"+PSBD.ACTION_TYPE+"="+PSBD.ACTION_LOAD+"&"+PSBD.TAB+"="+PSBD.TAB_AGGIUDICAZIONE;  
												ArrayList listaCrea,listaRiepilogo,listaB2Riepilogo,listaB2Crea;
												listaCrea = new ArrayList();
												listaRiepilogo = new ArrayList();
												String[] itemA = new String[3];
												itemA[1] = ParametriServlet.SRV_SCHEDA_A+"?"+parametriAggiungi +"&"+paramAggiud;
												itemA[0] = "Scheda A";	
												itemA[2] = existsColor;
												listaRiepilogo.add(itemA);%>
																	
											<% //COMMENTARE IN CASO DI RILASCIO SCHEDA A FINO A CONTRASSEGNO
												String idInizioLavori = currentRow.getNulledField(INIZIO_LAVORI.ID_INIZIO);
												String dataInizioLavori = currentRow.getNulledField(INIZIO_LAVORI.DATA_INIZIO_INIZIO);
												boolean hasSchedaB1 = (!idInizioLavori.equals(""));
												String paramAggiud2 = PSBD.FIELD_NAME_ID_AGGIUDICAZIONE+"="+ currentRow.getNulledField(AGGIUDICAZIONI.ID_AGGIUDICAZIONE)+"&"+PSBD.DATA_INIZIO_AGGIUDICAZIONE+"="+currentRow.getNulledField(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE);  
												if((!lottoCancellato || hasSchedaB1)&&isOrdinario){
													
													String titlehrefB1 = "Scheda B1"; 
													String paramAggiudB1 = ParametriServletInizioLavori.SRV_SCHEDA_B1+"?"+parametriAggiungi+"&"+paramAggiud2;
													paramAggiudB1 += hasSchedaB1? "&"+PSBD.ACTION_TYPE+"="+PSBD.ACTION_LOAD+"&"+
																					 ParametriServletInizioLavori.ID_INIZIOLAVORI +"="+idInizioLavori+"&"+
																					 ParametriServletInizioLavori.DATA_INIZIO_INIZIOLAVORI+"="+dataInizioLavori:"";
													String[] itemB1 = new String[3];
													itemB1[0] = titlehrefB1;
													itemB1[1] = paramAggiudB1;
													itemB1[2] = hasSchedaB1? existsColor : notExistColor;							
													listaRiepilogo.add(itemB1);
												}
												// se l'importo � maggiore di 500.000 allora deve poter creare la scheda B2
												
												if(Double.parseDouble(importoLotto) >= SimogProperties.getImportoMinLottoB2().doubleValue()){
													// stato avanzamento 25%
													String id25 = currentRow.getNulledField("ID_25");
													String dataInizio25 = currentRow.getNulledField("DATA_INIZIO_25");
													boolean hasScheda25 = (!id25.equals(""));
													if((!lottoCancellato || hasScheda25)&&isOrdinario){
														String titlehref25 =  "Scheda B2 - 25%"; 
														String paramAggiud25 = ParametriServletSchedaB2.SRV_SCHEDA_B2+"?"+parametriAggiungi+"&"+paramAggiud2;
														paramAggiud25 += hasScheda25? "&"+PSBD.ACTION_TYPE+"="+PSBD.ACTION_LOAD+"&"+
																						 STATI_AVANZ.ID_AVANZAMENTO+"="+id25+"&"+
																						 STATI_AVANZ.DATA_INIZIO_AVANZAMENTO+"="+dataInizio25:"&"+STATI_AVANZ.FASE+"=1";
														String[] item25 = new String[3];
														item25[0] = titlehref25;
														item25[1] = paramAggiud25;
														item25[2] = hasScheda25? existsColor : notExistColor;											
														listaRiepilogo.add(item25);
													}
													
													// stato avanzamento 50%
													String id50 = currentRow.getNulledField("ID_50");
													String dataInizio50 = currentRow.getNulledField("DATA_INIZIO_50");
													boolean hasScheda50 = (!id50.equals(""));
													if((!lottoCancellato || hasScheda50)&&isOrdinario){
														String titlehref50 =  "Scheda B2 - 50%"; 
														String paramAggiud50 = ParametriServletSchedaB2.SRV_SCHEDA_B2+"?"+parametriAggiungi+"&"+paramAggiud2;
														paramAggiud50 += hasScheda50? "&"+PSBD.ACTION_TYPE+"="+PSBD.ACTION_LOAD+"&"+
																						 STATI_AVANZ.ID_AVANZAMENTO+"="+id50+"&"+
																						 STATI_AVANZ.DATA_INIZIO_AVANZAMENTO+"="+dataInizio50:"&"+STATI_AVANZ.FASE+"=2";
														String[] item50 = new String[3];
														item50[0] = titlehref50;
														item50[1] = paramAggiud50;
														item50[2] = hasScheda50? existsColor : notExistColor;				
														listaRiepilogo.add(item50);
													}												  
													// stato avanzamento 75%
													String id75 = currentRow.getNulledField("ID_75");
													String dataInizio75 = currentRow.getNulledField("DATA_INIZIO_75");
													boolean hasScheda75 = (!id75.equals(""));
													if((!lottoCancellato || hasScheda75)&&isOrdinario){
														String titlehref75 =  "Scheda B2 - 75%"; 
														String paramAggiud75 = ParametriServletSchedaB2.SRV_SCHEDA_B2+"?"+parametriAggiungi+"&"+paramAggiud2;
														paramAggiud75 += hasScheda75? "&"+PSBD.ACTION_TYPE+"="+PSBD.ACTION_LOAD+"&"+
																						 STATI_AVANZ.ID_AVANZAMENTO+"="+id75+"&"+
																						 STATI_AVANZ.DATA_INIZIO_AVANZAMENTO+"="+dataInizio75:"&"+STATI_AVANZ.FASE+"=3";
														String[] item75 = new String[3];
														item75[0] = titlehref75;
														item75[1] = paramAggiud75;
														item75[2] = hasScheda75? existsColor : notExistColor;										
														listaRiepilogo.add(item75);
													}
													// stato avanzamento 100%
													String id100 = currentRow.getNulledField("ID_100");
													String dataInizio100 = currentRow.getNulledField("DATA_INIZIO_100"); 
													boolean hasScheda100 = (!id100.equals(""));
													if((!lottoCancellato || hasScheda100)&&isOrdinario){
														String titlehref100 =  "Scheda B2 - 100%"; 
														String paramAggiud100 = ParametriServletSchedaB2.SRV_SCHEDA_B2+"?"+parametriAggiungi+"&"+paramAggiud2;
														paramAggiud100 += hasScheda100? "&"+PSBD.ACTION_TYPE+"="+PSBD.ACTION_LOAD+"&"+
																						 STATI_AVANZ.ID_AVANZAMENTO+"="+id100+"&"+
																						 STATI_AVANZ.DATA_INIZIO_AVANZAMENTO+"="+dataInizio100:"&"+STATI_AVANZ.FASE+"=4";
														String[] item100 = new String[3];
														item100[0] = titlehref100;
														item100[1] = paramAggiud100;
														item100[2] = hasScheda100? existsColor : notExistColor;													
														listaRiepilogo.add(item100);
													}
												}
												
												String idFineLavori = currentRow.getNulledField(FINE_LAVORI.ID_ULTIM);
												String dataInizioFineLavori = currentRow.getNulledField(FINE_LAVORI.DATA_INIZIO_ULTIM);
												boolean hasSchedaB4 = (!idFineLavori.equals(""));
												if((!lottoCancellato || hasSchedaB4)&&isOrdinario){
													String titlehrefB4 =  "Scheda B4"; 
													String paramAggiudB4 = ParametriServletSchedaB4.SRV_SCHEDA_B4+"?"+parametriAggiungi+"&"+paramAggiud2;
													paramAggiudB4 += hasSchedaB4? "&"+PSBD.ACTION_TYPE+"="+PSBD.ACTION_LOAD+"&"+
																					FINE_LAVORI.ID_ULTIM+"="+idFineLavori+"&"+
																					FINE_LAVORI.DATA_INIZIO_ULTIM+"="+dataInizioFineLavori:"";
													String[] itemB4 = new String[3];
													itemB4[0] = titlehrefB4;
													itemB4[1] = paramAggiudB4;
													itemB4[2] = hasSchedaB4? existsColor : notExistColor;										
													listaRiepilogo.add(itemB4);
												}
												if((!lottoCancellato || hasSchedaB4)&&isOrdinario){
													String titlehrefB4 =  "SchedaR129"; 
													String paramAggiudB4 = ParametriServletR129.SRV_SCHEDA_R129+"?"+parametriAggiungi+"&"+paramAggiud2;
													paramAggiudB4 +=  "&action=loadAll";
													String[] itemB4 = new String[3];
													itemB4[0] = titlehrefB4;
													itemB4[1] = paramAggiudB4;
													itemB4[2] = hasSchedaB4? existsColor : notExistColor;										
													listaRiepilogo.add(itemB4);
												}
												
												
											
												//FINE COMMENTARE
												%>
											<td class="garaTd" nowrap="nowrap" >
													<select onclick="urlCallFromCombo(this);" id="riepilogo<%= rowIndex %>" >
														<option value="">...scegliere una scheda</option>
													    		<%String[] stringElement = null;%>
																<%for(int i=0;i<listaRiepilogo.size();i++){ %>
																	<%	stringElement = (String[])listaRiepilogo.get(i);%>
																	<option style="background-color: <%= stringElement[2] %>; color:white;" 
																	 		  value="<%= stringElement[1] %>"><%= stringElement[0] %></option>																
																<% } %>
													
													</select>				      									
											</td>														
										</tr>
									</table>												
								<% } %>
								</div>
							</div>
						</div>
					</div>
				<% } %>				
			</div>
	
		<%@ include file="include/newfooter.inc" %>
	</div>
</body>

</html>
