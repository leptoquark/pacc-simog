<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="errore.jsp" %>

<%@page import="it.avlp.simog.common.servlet.ParametriServletR129"%>
<%@page import="it.avlp.simog.common.servlet.ParametriServlet"%>
<%@page import="it.avlp.simog.common.servlet.PSBD"%>

<%@ include file="include/newbasicHeader.inc" %>
<%@ include file="include/controlloSessione.inc" %>


<%@ taglib prefix="u" uri="http://simog.avlp.it/tags-util" %>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% int indiceTab = 0;%>

<%--Carico la lista delle schede gia compilate e i dati della gara --%>
 <c:set var="listaSchede" value="${sessionScope['lista_r129']}"></c:set>
 <c:set var="datiGara" value="${sessionScope['dati_gara']}"></c:set>
 <jsp:useBean id="schedaR129" type="it.avlp.simog.beans.r129.SchedaR129" class="it.avlp.simog.beans.r129.SchedaR129" scope="request"></jsp:useBean>

<c:set var="aggiudicazione" value="${schedaR129.aggiudicazione}" scope="page"></c:set>

 <% R129Bean r129 = schedaR129.getRitardoFE();
 	pageContext.setAttribute("r129",r129);
 %>
<c:set var="rupOk" value="${(UTENTE.login eq datiGara.cfRup or datiGara.cfRup eq null ) and aggiudicazione.flagAggiudPrincipale ne 'N'}" />
 <c:set var="hide" value="${(datiGara.deleted || r129.confirmed) || rupOk eq false || UTENTE.ossReg || UTENTE.RASA || (r129.idRecord < 1 && !schedaR129.aggiungibile) || schedaR129.readOnly || schedaR129.delegaScheda || schedaR129.riaggiudicata}" />
 <c:set var="annullabile" value="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && datiGara.deleted ne true && r129.confirmed eq true && r129.richAnn ne true && r129.richDelete ne true && schedaR129.delegaScheda eq false  and schedaR129.riaggiudicata eq false}"></c:set>

 
 <c:set var="disabled" value="${hide ? 'disabled' :'' }"></c:set>

 
 <c:set var="disabled" value="${hide ? 'disabled' : '' }"></c:set>
 <c:set var="noConf" value="${(hide || (r129.idRecord le 0)) || r129.richAnn eq true ? 'disabled':''}"></c:set>


<link rel="stylesheet" href="theme/tabmenu.css"/>
<!-- calendar stylesheet -->
<link rel="stylesheet" type="text/css" media="all" href="calendar/calendar-blue.css" title="win2k-cold-1" />
<!-- main calendar program -->
<script type="text/javascript" src="calendar/calendar.js"></script>
<!-- language for the calendar -->
<%@ include file="include/calendar-dynamic.inc" %>
<!-- the following script defines the Calendar.setup helper function, which makes
     adding a calendar a matter of 1 or 2 lines of code. -->
<script type="text/javascript" src="calendar/calendar-setup.js"></script>
<script type="text/javascript"  src="script/pageutils.js"></script>

<title><utils:message key="scheda.gestioneSchede" /> - <utils:message key="scheda.r129" /> - <%= user.getProfilo() %></title>

<script type="text/javascript" >
<!--
	function HDate(dt, format) {
    
        this.mdy        = 0;
        this.mode       = '';
        this.p2f        = true;
        this.sysDate    = false;
        this.mdyDate    = null;
        this.mdyFormat  = 'it';  
        this.mdyStart   = new Date();
        this.mdyIntv    = new Date();        
        
        this.mdyRe      = [];
        this.mdyRe['it']     = /^([0-2]\d|3[01])[\/\-\.]?(0\d|1[0-2])[\/\-\.]?(19\d{2}|2\d{3})$/;
        this.mdyRe['en']     = /^(0\d|1[0-2])[\/\-\.]?([0-2]\d|3[01])[\/\-\.]?(19\d{2}|2\d{3})$/;
        this.mdyRe['iso8601'] = /^(19\d{2}|2\d{3})[\/\-\.]?(0\d|1[0-2])[\/\-\.]?([0-2]\d|3[01])$/;
        
        this.startingFromFlag  = false;
        
        this.splitDate = function(dt) {
            var mdy = dt.replace(/\D/g,"-").split("-");
            for (i in mdy) mdy[i] = parseInt(mdy[i], 10);
            
            switch (this.mdyFormat) {
                case 'en' : 
                    mdy = [mdy[1],mdy[0], mdy[2]];
                    break;
                case 'iso8601': 
                    mdy = [mdy[2],mdy[1], mdy[0]];
                    break;
            }
            
            return mdy;
        };


        this.isLeapYear = function(year) {
            return (!(year % 100))? (!(year % 400)) : (!(year % 4));
        };
        
        this.isValid = function() {
            
            if (this.sysDate) return true;
            
            /* correttezza formale (da 01/01/1900 a 31/12/2049) */
            if (!(this.mdyRe[this.mdyFormat]).test(this.mdy)) return false;
            
            var mdy = this.splitDate(this.mdy);
           
            /* Leap year and February */
            if (mdy[1] == 2) {
                if (mdy[0] >= 30) return false; 
                if (mdy[0] == 29 && !this.isLeapYear(mdy[2])) return false;
            };
            /* 31 days   */ 
            if ((mdy[0] == 31) && (/^(4|6|9|11)$/).test(mdy[1])) return false; 
            
            return true;
        };
        
        
        /**
         * methods 
         */
        
        this.startingFrom = function(dt) {
        
            this.startingFromFlag = true;
            
            if (dt) {
                var mdy = this.splitDate(dt);
                this.mdyStart.setDate(mdy[0]);
                this.mdyStart.setMonth(mdy[1]-1);
                this.mdyStart.setYear(mdy[2]);
            }
            
            this.mdyIntv.setDate(this.mdyStart.getDate());
            this.mdyIntv.setMonth(this.mdyStart.getMonth());
            this.mdyIntv.setYear(this.mdyStart.getFullYear());
            
            if (this.mdyStart.getTime() < this.mdyDate.getTime()) this.p2f = false;

            return this;
        };
        
       
       
        /* mode methods */       
        
        this.hasAtConstructor = function() {
            if (!this.startingFromFlag) {
                this.resetAttributes();
                var err = 'Method HDate: Interval modifiers \'hasAt...\' require startingFrom() method before.';
                throw new Error(err);
            }
        }
        
        this.hasAtLeast = function() {
            this.hasAtConstructor();
            this.mode = 'atleast';
            return this;
        };
        
        this.hasAtMost = function() {
            this.hasAtConstructor();
            this.mode = 'atmost';
            return this;
        };
        
        this.add = function() {
            this.mdyIntv = new Date(this.mdyDate);
            this.mode = 'add';
            return this;
        }

        this.subtract = function() {
            this.mdyIntv = new Date(this.mdyDate);
            this.mode = 'subtract';
            return this;
        }
        
        
        
        /* Interval modifiers */
        
        this.intModConstructor = function(value) {
        
            if (!this.mode) { 
                this.resetAttributes();
                var err = 'Method HDate: Interval modifiers call without a mode methods.';
                throw new Error(err);
            }
            
            if ((this.mode == 'add')) return -value;
            if (this.startingFromFlag && !this.p2f) return -value;
            return value;

        }

        this.years = function(iYears) {
            this.mdyIntv.setYear(this.mdyIntv.getFullYear() - this.intModConstructor(iYears));
            return this;
        };

        this.months = function(iMonths) {
            this.mdyIntv.setMonth(this.mdyIntv.getMonth() - this.intModConstructor(iMonths));
            return this;
        };

        this.days = function(iDays) {        
            this.mdyIntv.setDate(this.mdyIntv.getDate() - this.intModConstructor(iDays));
            return this;
        };
        
        
        
        /* Result Methods */ 
    
        this.getInterval = function(dt) {
            return Math.floor((this.mdyDate.getTime() - dt.mdyDate.getTime()) / 86400000); 
        }


        this.ofInterval = function() {
        
            var mdyStart = this.mdyStart;
            var mdyIntv = this.mdyIntv;
            var mdyDate = this.mdyDate;
            
            
            //alert([mdyStart.toString(), mdyIntv.toString(), mdyDate.toString()].join('\n'));
            
            
            var mode = this.mode;
            
            this.mdyStart   = new Date();
            this.mdyIntv    = new Date(); 
            
            if ((/^(atleast|atmost)$/).test(mode)) {
                if (this.p2f) {
                    return (mode == 'atleast')?
                            (mdyIntv.getTime() >= this.mdyDate.getTime()):
                            (mdyIntv.getTime() <= this.mdyDate.getTime());
                }
                else {
                    return (mode == 'atleast')?
                            (mdyIntv.getTime() <= this.mdyDate.getTime()):
                            (mdyIntv.getTime() >= this.mdyDate.getTime());                
                }  
            }
            if ((/^(add|subtract)$/).test(mode)) {
                return (mdyIntv);
            }
            
        };
        
        
        
        this.set = function(dt, format) {
            
            this.sysDate    = true;
            this.mdyDate = new Date();
            format = (format && dt)? format : 'it';
            this.mdyFormat = format.toLowerCase();

            if (dt) {
            
                this.mdy = dt;
                var mdy = this.splitDate(dt);

                this.mdyDate = new Date(mdy[2], mdy[1]-1, mdy[0]);

                //this.mdyDate.setDate(mdy[0]);
                //this.mdyDate.setMonth(mdy[1]-1);
                //this.mdyDate.setYear(mdy[2]);
                
                this.sysDate = false;
            }
            
            return this;
        }
        
        
        /* Reset Flags if an error is thrown or if you use the same objects for multiple operations */

        this.resetAttributes = function() {
            this.p2f            = true;
            this.mode           = ''; 
            this.sysDate        = false;
            this.mdyFormat      = 'it';  
            this.startingFromFlag  = false;
        };
        

        
        /**
         * constructor
         */ 
         
        this.set(dt, format);


    }
    
	function differenzaDate(dataStringa1,dataStringa2,id) {
		if (dataStringa1 == null || dataStringa2 == null || dataStringa1 == '' || dataStringa2 == '') {
			document.getElementById(id).value= ''; 
		}
		else {
		    data1 = new HDate(dataStringa1,'it');
		    data2 = new HDate(dataStringa2,'it');
			if (data1.isValid() && data2.isValid()) {
				intv = data2.getInterval(data1); 
				if(intv < 0) {
					alert('Le date inserite non sono coerenti, reinserire i dati');
					document.getElementById(id).value = '' ;
					return true;
				}
				else document.getElementById(id).value = intv ;
			}
			else document.getElementById(id).value= '';
		}
		return true;
	}
//-->
</script>

</head>

<body >
	<div id="gabbia" align="left">
		<%@ include file="/include/header.inc" %>			
		<div class="bodypage-e" align="left">
		<%--Header Scheda e Lista Schede gia compilate --%>
			<h1>Gestione Schede - Istanza di Recesso</h1>
			<div  class="hmenu" align="left">	
			  <ul>
			     <%String riScheda =ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=";%>
			     
			      
					<li><a title="Torna alla lista Aggiudicazioni" href="javascript:changePage('<%=riScheda%>${datiGara.idLotto}','Modificato')">Lista Aggiudicazioni</a></li>			    
			 	  
			    <c:if test="${!UTENTE.ossReg && !UTENTE.RASA && r129.idRecord > 0 && datiGara.deleted eq false && rupOk eq true && schedaR129.aggiungibile && schedaR129.delegaScheda eq false and schedaR129.riaggiudicata eq false}">
			      <li>
			        <c:url  value="srvSchedaR129" var="newMod">
					    <c:param name="toDo" value="load"></c:param>
					    	<c:param name="toEdit" value="-1"></c:param>
				      </c:url>
				      <a href="<c:out value='${newMod}'/>" >Aggiungi nuova scheda</a>
				 </li>
				</c:if>
				</ul>
			</div> 
			<%--Errori.... --%>
			<%@ include file="/include/gestisciErrore.inc" %>
			  
			  <%--Carico la scheda corrente e la lista delle schede --%>
			 <h2>Lista Schede Istanza di Recesso</h2>
		<div style="overflow: auto;height: 13em; width: 100%;"  >
			
			<div class="gara">	 	
			 
		     <table align="center" width="300px">   
			<tr> 
		     	<th class="garaTh">Data Inserimento</th> 
		     	<th class="garaTh">Tipologia Comunicazione</th> 
		     	<th class="garaTh">Motivazione della sospensione/ritardo</th>
		     	<th class="garaTh">Stato scheda</th>
		     	<th class="garaTh">Azioni</th>
		     </tr>
		        <c:set var="counter" value='0' scope="page"/>
			<c:forEach items="${listaSchede}" var="scheda">
				<tr>
					<td class="garaTd"><c:out value="${scheda.dataComunicazione}"></c:out></td>
				 	<td  class="garaTd"><c:out value="${scheda.ritardo ? 'Ritardo nella consegna':'Sospensione della consegna'}"></c:out></td>
					<td  class="garaTd"><c:out value="${scheda.motivoSospensione}"></c:out></td>
					<td  class="garaTd"><c:out value="${scheda.descrizioneStato}"></c:out></td>
					
					<td class="hmenu">
					    <c:url  value="srvSchedaR129" var="modURL">
					    <c:param name="toDo" value="load"></c:param>
					    	<c:param name="toEdit" value="${counter}"></c:param>
					    </c:url>
					    <c:choose >
					    	<c:when test="${!UTENTE.ossReg && !UTENTE.RASA && datiGara.deleted eq false && rupOk eq true && scheda.confirmed eq false && 
					    	schedaR129.delegaScheda eq false and schedaR129.riaggiudicata eq false}">
					    		<a href="<c:out value='${modURL}'/>">Modifica</a>
					    	</c:when>
					    	<c:otherwise>
					    		<a href="<c:out value='${modURL}'/>">Visualizza</a>
					    	</c:otherwise>
					    </c:choose>
					    
					</td>
					
				</tr>
				<c:set var="counter" value="${counter + 1}" scope="page"/>
			</c:forEach>
			 </table>
		</div></div>
		
		
			<%-- PANNELLO DELLE RICHIESTE DI ANNULLAMENTO DELLA SCHEDA [DISATTIVATO] --%>
			<%@ include file="../include/RichAnnPanel.jsp" %>
			<%-- --%>
		
				<table >	
					<tr>
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',4,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',4,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>
						
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>		
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && r129.okCancellazione eq true && schedaR129.delegaScheda eq false and schedaR129.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>	
				<c:set var="statoid" value="${r129.idStato}"/>
				<c:set var="statoann" value="${r129.richAnn || r129.richDelete}"/>
				<c:set var="statodesc" value="${r129.descrizioneStato}"/>
				<%@ include file="../include/statoscheda.inc" %>
					</tr>
				</table>
			 <fieldset>
			 <h2>Scheda Istanza di Recesso - <c:out value="${r129.idRecord < 1 ?  'Inserimento' : (hide == true ? 'Visualizzazione' : 'Modifica')}" /></h2>
			<form action="<%=ParametriServletR129.SRV_SCHEDA_R129%>" method="post" onkeypress="setFormModified('Modificato')" >
					    <input type="hidden" name="checkIfOK" id="checkIfOK" value="<%=new Integer(session.getAttribute(ParametriServlet.checkIfOK).toString()) + 1%>" />
			
			<%--Campi hidden e altro, copiati dalle altre schede, non so se servono. DA VERIFICARE --%>						
				<input type="hidden" name="<%=PSBD.TAB%>" id="<%=PSBD.TAB%>" value="" />	
				<input type="hidden" name="<%=PSBD.ACTION_TYPE%>" id="<%=PSBD.ACTION_TYPE%>" value="" />
				
				<input type="hidden" name="<%=ParametriServletR129.FIELD_NAME_ID_RECORD %>" value='<c:out value="${r129.idRecord}"/>'/>
				<input type="hidden" name="<%=ParametriServletR129.FIELD_NAME_DATA_INIZIO_RECORD %>" value='<c:out value="${r129.dataInizioRecord}"/>'/>
				<input type="hidden" id="Modificato"  value="0" />
				
				<fieldset class="gara">
					
				  <table width="100%">
				  
				  	<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>RIFERIMENTO AI DATI DELLA FASE DI AGGIUDICAZIONE O DI DEFINIZIONE DI PROCEDURA NEGOZIATA</strong></p></td>
					</tr>
	   				<%@include file="/include/intestazione.jsp" %>
	   				<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>RITARDO O SOSPENSIONE NELLA CONSEGNA</strong></p></td>
					</tr>
					<!--  *************************************************************************************************
									TERMINE PREVISTO PER LA CONSEGNA LAVORI
					******************************************************************************************************** -->
					
	
					<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_DATA_TERMINE %>" >Termine previsto per la consegna</label></td>
	   					<td>
							<input  tabindex="<%=++indiceTab%>" style="text-align:center;" 
									onchange="setFormModified('Modificato');differenzaDate(document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_TERMINE %>').value,document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_CONSEGNA_LAVORI %>').value,'ritardoConsegna')"  <c:out value="${disabled}"/>
									type="text" 
									id="<%= ParametriServletR129.FIELD_NAME_DATA_TERMINE %>"
									name="<%= ParametriServletR129.FIELD_NAME_DATA_TERMINE %>" 
									onblur="Calendar.validaData(this);differenzaDate(document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_TERMINE %>').value,document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_CONSEGNA_LAVORI %>').value,'ritardoConsegna')" 
									value="<c:out value='${r129.dataTermine}'/>" />
							
							<c:if test="${hide != true}">
								<img src="calendar/img.gif" id="calendarTermine" style="cursor: pointer; border: 1px solid red;" title="Date selector"
											onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletR129.FIELD_NAME_DATA_TERMINE %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarTermine",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});	
							    						    	
								</script>
							</c:if> 
						</td>
	   				</tr>
	   				
	   				<!-- ************************************************************************************************ 
	   								     DATA DELLA CONSEGNA LAVORI
	   				***************************************************************************************************** -->
	   				<tr>
	   					<td><label  >Data della consegna lavori</label></td>
	   					<td>
							<!--  input tabindex="<%=++indiceTab%>" disabled style="text-align:center;" 
							type="text" value="<c:out value='${r129.dataConsegna}'/>" /-->
							
							<input  tabindex="<%=++indiceTab%>" style="text-align:center;"
									onchange="setFormModified('Modificato');differenzaDate(document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_TERMINE %>').value,document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_CONSEGNA_LAVORI %>').value,'ritardoConsegna')" <c:out value="${disabled}"/>
									type="text" id="<%= ParametriServletR129.FIELD_NAME_DATA_CONSEGNA_LAVORI %>"  
									name="<%= ParametriServletR129.FIELD_NAME_DATA_CONSEGNA_LAVORI %>"
									onblur="Calendar.validaData(this);differenzaDate(document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_TERMINE %>').value,document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_CONSEGNA_LAVORI %>').value,'ritardoConsegna')"
									value="<c:out value='${r129.dataConsegna}'/>" />
							<c:if test="${hide != true}">
								<img src="calendar/img.gif" id="calendarConsegna" style="cursor: pointer; border: 1px solid red;" title="Date selector"
											onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletR129.FIELD_NAME_DATA_CONSEGNA_LAVORI %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarConsegna",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>

							
						</td>
	   				</tr>	   				
					<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_TIPO_COMUNICAZIONE %>" >Tipologia comunicazione</label></td>
	   					<td width="40%" > 
	   						<input tabindex="<%=++indiceTab%>"  
	   							   id="check1Y" 
	   							   type="radio" 
	   							   name="<%= ParametriServletR129.FIELD_NAME_TIPO_COMUNICAZIONE %>" 
	   							   value="<%= Costanti.TIPCOM_RITARDO %>" 
	   							   <c:out value="${r129.ritardo ? 'checked' : ''}" /> 
	   							   <c:out value="${disabled}"/>/>Ritardo nella consegna <br/>
	   					   	<input tabindex="<%=++indiceTab%>" id="check1N" type="radio" 
	   					   			name="<%= ParametriServletR129.FIELD_NAME_TIPO_COMUNICAZIONE %>" value="<%= Costanti.TIPCOM_SOSPENSIONE %>" 
	   					   			<c:out value="${r129.sospensione ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>Sospensione nella consegna</td>
	   				</tr>
	   				
	   				
	   				<!-- *******************************************************************************************
	   								RITARDO NELLA CONSEGNA IN GIORNI
	   				************************************************************************************************ -->
	   				
	   				<tr>
	   					<td><label>Ritardo nella consegna in giorni</label></td>
	   					<td>
							<input tabindex="<%=++indiceTab%>" style="text-align:center;font-weight: bold;" 
							type="text" readonly id="ritardoConsegna" name="ritardoConsegna" value=""/>
							
						</td>
	   				</tr>
	   				 
	   				<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_DURATA_SOSPENSIONE%>" >Durata della sospensione in giorni</label></td>
	   					<td>
							<input  name="<%= ParametriServletR129.FIELD_NAME_DURATA_SOSPENSIONE%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" <c:out value="${disabled}"/>
							type="text" value="<c:out value='${r129.durataSospensione}'/>"  onblur="validateNumber(this)" maxlength="9"/>
							
						</td>
	   				</tr>
					
					<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_MOTIVAZIONE_SOSPENSIONE%>" >Motivazione della sospensione/ritardo</label></td>
	   					<td>
							<input name="<%= ParametriServletR129.FIELD_NAME_MOTIVAZIONE_SOSPENSIONE%>"  tabindex="<%=++indiceTab%>"  style="width:100%; text-align:left;" <c:out value="${disabled}"/> 
							type="text" value="<c:out value='${r129.motivoSospensione}'/>" />
							 
						</td>
	   				</tr>
					
					<tr>
						<td align="center" colspan="2"><p class="detailHelp"><strong>ISTANZA DI RECESSO</strong></p></td>
					</tr>
					
					<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_DATA_IST_RECESSO %>" >Data di presentazione dell'istanza di recesso</label></td>
	   					<td>
							<input tabindex="<%=++indiceTab%>"  style="text-align:center;" onchange="setFormModified('Modificato')" <c:out value="${disabled}"/>
							type="text" id="<%= ParametriServletR129.FIELD_NAME_DATA_IST_RECESSO %>" name="<%= ParametriServletR129.FIELD_NAME_DATA_IST_RECESSO %>" 
							onblur="Calendar.validaData(this)" value="<c:out value='${r129.dataIstRecesso}'/>">
							<c:if test="${hide != true}">
								<img src="calendar/img.gif" id="calendarRecesso" style="cursor: pointer; border: 1px solid red;" title="Date selector"
											onmouseover="this.style.background='red';" onmouseout="this.style.background=''" />
								<script type="text/javascript">
									    Calendar.setup({
								        inputField     :    "<%= ParametriServletR129.FIELD_NAME_DATA_IST_RECESSO %>",     // id of the input field
								        ifFormat       :    "%d/%m/%Y",      // format of the input field
								        button         :    "calendarRecesso",  // trigger for the calendar (button ID)
								        align          :    "Tl",           // alignment (defaults to "Bl")
								        singleClick    :    true							       
							    		});					    	
								</script>
							</c:if>
						</td>
	   				</tr>
	   				
	   				<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_FLAG_ISTANZA_RECESSO %>" >L'istanza di recesso e stata accolta?</label></td>
	   					<td width="40%" >
	   					<select tabindex="<%=++indiceTab%>" name="<%= ParametriServletR129.FIELD_NAME_FLAG_ISTANZA_RECESSO %>" <c:out value="${disabled}"/> >
	   						<option/>
							<option value="S" <c:out value="${r129.flagAccolta == 'S' ? 'selected' : ''}"/>>SI</option>
							<option value="N" <c:out value="${r129.flagAccolta == 'N' ? 'selected' : ''}"/>>NO</option>
	   					</select> 
	   				</tr>
	   				<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_FLAG_TARDIVA %>" >Si e proceduto a consegna tardiva?</label></td>
	   					<td width="40%" > <input tabindex="<%=++indiceTab%>" id="check2Y" type="radio" name="<%= ParametriServletR129.FIELD_NAME_FLAG_TARDIVA %>" value="S" <c:out value="${r129.flagTardiva == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>SI 
	   					 <input tabindex="<%=++indiceTab%>" id="check2N" type="radio" name="<%= ParametriServletR129.FIELD_NAME_FLAG_TARDIVA %>" value="N" <c:out value="${r129.flagTardiva == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>NO</td>
	   				</tr>
	   				<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_FLAG_RIPRESA %>" >Si e proceduto alla ripresa dei lavori?</label></td>
	   					<td width="40%" > <input tabindex="<%=++indiceTab%>" id="check3Y" type="radio" name="<%= ParametriServletR129.FIELD_NAME_FLAG_RIPRESA %>" value="S" <c:out value="${r129.flagRipresa == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>SI 
	   					 <input tabindex="<%=++indiceTab%>" id="check3N" type="radio" name="<%= ParametriServletR129.FIELD_NAME_FLAG_RIPRESA %>" value="N" <c:out value="${r129.flagRipresa == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>NO</td>
	   				</tr>
	   				<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_FLAG_RISERVE %>" >L'appaltatore ha formulato riserve?</label></td>
	   					<td width="40%" > <input tabindex="<%=++indiceTab%>" id="check4Y" type="radio" name="<%= ParametriServletR129.FIELD_NAME_FLAG_RISERVE %>" value="S" <c:out value="${r129.flagRiserva == 'S' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>SI 
	   					<input tabindex="<%=++indiceTab%>" id="check4N" type="radio" name="<%= ParametriServletR129.FIELD_NAME_FLAG_RISERVE %>" value="N" <c:out value="${r129.flagRiserva == 'N' ? 'checked' : ''}" /> <c:out value="${disabled}"/>/>NO</td>
	   				</tr>
	   				
	   				<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_RIMBORSO_SPESE%>" >Eventuale rimborso delle spese in &euro;</label></td>
	   					<td>
	   					<% // adds 19052008 
						String importoSpese = "";
						if(((R129Bean)pageContext.getAttribute("r129")).getImportoSpese() != null){
							importoSpese =PageHelper.formattaImporto(((R129Bean)pageContext.getAttribute("r129")).getImportoSpese());
						}
						%>
							<input  name="<%= ParametriServletR129.FIELD_NAME_RIMBORSO_SPESE%>"  tabindex="<%=++indiceTab%>" style="text-align:right;" <c:out value="${disabled}"/>
							type="text" value="<c:out value='<%=importoSpese %>'/>"  onblur="validateAmount(this)"/>
							
						</td>
	   				</tr>
	   				
	   				<tr>
	   					<td><label for="<%= ParametriServletR129.FIELD_NAME_ONERI%>" >Eventuale compenso degli oneri derivanti dal ritardo in &euro;</label></td>
	   					<td>
	   					<% // adds 19052008 
						String importoOneri = "";
						if(((R129Bean)pageContext.getAttribute("r129")).getImportoOneri() != null){
							importoOneri =PageHelper.formattaImporto(((R129Bean)pageContext.getAttribute("r129")).getImportoOneri());
						}
						%>
							<input name="<%= ParametriServletR129.FIELD_NAME_ONERI%>"  tabindex="<%=++indiceTab%>"  style="text-align:right;" <c:out value="${disabled}"/>
							type="text" value="<c:out value='<%=importoOneri %>'/>" onblur="validateAmount(this)" />
							
						</td>
	   				</tr>
				  </table>
				  
				  <input type="hidden"  value="save" name="toDo" id="toDo"/>
				
			</fieldset>
			 
				<table >	
					<tr>
						<td><input <c:out value="${disabled}"/> type="button" value="Salva" onclick="checkAndAction('check',4,'<%=PSBD.ACTION_SALVA %>')"/></td>
						<td><input <c:out value="${noConf}"/>  type="button" value="Conferma" onclick="checkAndAction('check',4,'<%=PSBD.ACTION_CONFERMA %>')"/></td>
						<td><input <c:out value="${disabled}"/> type="button" value="Reimposta" onclick="reimpostaForm('<%=PSBD.ACTION_REIMPOSTA %>')"/></td>
						
						<c:if test="${annullabile eq true}">
							<td><input type="button" value="<%= ParametriServlet.ACTION_RICHIEDI_ANNULLA %>" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_ANNULLAMENTO %>')"/></td>	
						</c:if>
						<c:if test="${!UTENTE.ossReg && !UTENTE.RASA && rupOk eq true && r129.okCancellazione eq true && schedaR129.delegaScheda eq false and schedaR129.riaggiudicata eq false}">
							<td><input type="button" value="Richiedi Cancellazione" onclick="doAction('<%=PSBD.ACTION_CARICA_JSP_CANCELLAZIONE %>')"/></td>	
						</c:if>				
				<%@ include file="../include/statoscheda.inc" %>
					</tr>
				</table>
			</form>
			 </fieldset>
		</div>  
		<%@ include file="include/newfooter.inc" %>
	</div>
	
</body>
<script type="text/javascript">
	differenzaDate(document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_TERMINE %>').value,document.getElementById('<%= ParametriServletR129.FIELD_NAME_DATA_CONSEGNA_LAVORI %>').value,'ritardoConsegna');
</script>
<%@page import="it.avlp.simog.beans.r129.R129Bean"%>
<%@page import="it.avlp.simog.db.Costanti"%>
<%@page import="it.avlp.simog.util.PageHelper"%>

<%@page import="it.avlp.simog.db.Costanti;"%>
</html>