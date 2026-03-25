<%@ page import="it.avlp.simog.util.MessageHelper" %>
<%@ page import="java.util.Locale" %>
<%
	// Usa la variabile currentLocale già dichiarata nel file includente
	// (newbasicHeader.inc o basicHeader.inc)
	// NOTA: Questo file deve essere incluso DOPO un file header che dichiara currentLocale
%>
<script type="text/javascript" src="script/i18n.js"></script>
<script type="text/javascript">
// Inizializzazione sistema i18n JavaScript
var i18nMessages = {
	'error.dataChanged': '<%= MessageHelper.getMessage(request, "js.error.dataChanged") %>',
	'error.saveConfirm': '<%= MessageHelper.getMessage(request, "js.error.saveConfirm") %>',
	'error.confirmConfirm': '<%= MessageHelper.getMessage(request, "js.error.confirmConfirm") %>',
	'error.changesLost': '<%= MessageHelper.getMessage(request, "js.error.changesLost") %>',
	'error.deleteRecord': '<%= MessageHelper.getMessage(request, "js.error.deleteRecord") %>',
	'error.saveRequired': '<%= MessageHelper.getMessage(request, "js.error.saveRequired") %>',
	'error.insertFilter': '<%= MessageHelper.getMessage(request, "js.error.insertFilter") %>',
	'error.filterMinChars': '<%= MessageHelper.getMessage(request, "js.error.filterMinChars") %>',
	'error.requestModification': '<%= MessageHelper.getMessage(request, "js.error.requestModification") %>',
	'error.multiLottoWarning': '<%= MessageHelper.getMessage(request, "js.error.multiLottoWarning") %>',
	'error.motivationLength': '<%= MessageHelper.getMessage(request, "js.error.motivationLength") %>',
	'error.selectRequestType': '<%= MessageHelper.getMessage(request, "js.error.selectRequestType") %>',
	'error.selectYesNo': '<%= MessageHelper.getMessage(request, "js.error.selectYesNo") %>',
	'error.decimalPart': '<%= MessageHelper.getMessage(request, "js.error.decimalPart") %>',
	'error.thousandsFormat': '<%= MessageHelper.getMessage(request, "js.error.thousandsFormat") %>',
	'error.invalidValue': '<%= MessageHelper.getMessage(request, "js.error.invalidValue") %>',
	'error.decimalsAllowed': '<%= MessageHelper.getMessage(request, "js.error.decimalsAllowed") %>',
	'error.numberTooLarge': '<%= MessageHelper.getMessage(request, "js.error.numberTooLarge") %>',
	'error.invalidChars': '<%= MessageHelper.getMessage(request, "js.error.invalidChars") %>',
	'error.negativeValue': '<%= MessageHelper.getMessage(request, "js.error.negativeValue") %>',
	'error.confirmOperation': '<%= MessageHelper.getMessage(request, "js.error.confirmOperation") %>',
	'error.deleteDocuments': '<%= MessageHelper.getMessage(request, "js.error.deleteDocuments") %>',
	'error.selectFile': '<%= MessageHelper.getMessage(request, "js.error.selectFile") %>',
	'error.amountModificationBlocked': '<%= MessageHelper.getMessage(request, "js.error.amountModificationBlocked") %>',
	'warning.cigValid': '<%= MessageHelper.getMessage(request, "js.warning.cigValid") %>',
	'button.cancel': '<%= MessageHelper.getMessage(request, "js.button.cancel") %>',
	'message.wait': '<%= MessageHelper.getMessage(request, "js.message.wait") %>',
	'error.publishGara': '<%= MessageHelper.getMessage(request, "js.error.publishGara") %>',
	'error.rectifyConfirm': '<%= MessageHelper.getMessage(request, "js.error.rectifyConfirm") %>',
	'error.riaggiudicaConfirm': '<%= MessageHelper.getMessage(request, "js.error.riaggiudicaConfirm") %>',
	'error.deleteSubject': '<%= MessageHelper.getMessage(request, "js.error.deleteSubject") %>',
	'error.specifyMotivation': '<%= MessageHelper.getMessage(request, "js.error.specifyMotivation") %>',
	'error.confirmDeleteRequest': '<%= MessageHelper.getMessage(request, "js.error.confirmDeleteRequest") %>',
	'error.lottoRequired': '<%= MessageHelper.getMessage(request, "js.error.lottoRequired") %>',
	'error.invitationLetterWarning': '<%= MessageHelper.getMessage(request, "js.error.invitationLetterWarning") %>',
	'error.subsequentAwardWarning': '<%= MessageHelper.getMessage(request, "js.error.subsequentAwardWarning") %>',
	'error.codiceFiscaleInvalid': '<%= MessageHelper.getMessage(request, "js.error.codiceFiscaleInvalid") %>',
	'error.codiceFiscaleChars': '<%= MessageHelper.getMessage(request, "js.error.codiceFiscaleChars") %>',
	'error.enterPassword': '<%= MessageHelper.getMessage(request, "js.error.enterPassword") %>',
	'error.fieldNotValued': '<%= MessageHelper.getMessage(request, "js.error.fieldNotValued") %>',
	'error.dateFormat': '<%= MessageHelper.getMessage(request, "js.error.dateFormat") %>',
	'error.invalidMonth': '<%= MessageHelper.getMessage(request, "js.error.invalidMonth") %>',
	'error.invalidDay': '<%= MessageHelper.getMessage(request, "js.error.invalidDay") %>',
	'error.invalidYear': '<%= MessageHelper.getMessage(request, "js.error.invalidYear") %>',
	'error.invalidDate': '<%= MessageHelper.getMessage(request, "js.error.invalidDate") %>',
	'error.yearRange': '<%= MessageHelper.getMessage(request, "js.error.yearRange") %>',
	'error.insertOneFilter': '<%= MessageHelper.getMessage(request, "js.error.insertOneFilter") %>',
	'error.filterMinTwoChars': '<%= MessageHelper.getMessage(request, "js.error.filterMinTwoChars") %>',
	'error.selectDocumentType': '<%= MessageHelper.getMessage(request, "js.error.selectDocumentType") %>',
	'error.selectDescription': '<%= MessageHelper.getMessage(request, "js.error.selectDescription") %>',
	'error.xwinNotFound': '<%= MessageHelper.getMessage(request, "js.error.xwinNotFound") %>',
	'error.verifyCPV': '<%= MessageHelper.getMessage(request, "js.error.verifyCPV") %>',
	'error.selectCategory': '<%= MessageHelper.getMessage(request, "js.error.selectCategory") %>',
	'error.importoLottoRequired': '<%= MessageHelper.getMessage(request, "js.error.importoLottoRequired") %>',
	'error.selectInitiative': '<%= MessageHelper.getMessage(request, "js.error.selectInitiative") %>',
	'error.dittaRaggruppamentoNotInRubrica': '<%= MessageHelper.getMessage(request, "js.error.dittaRaggruppamentoNotInRubrica") %>',
	'error.errorRow': '<%= MessageHelper.getMessage(request, "js.error.errorRow") %>',
	'error.alreadySelected': '<%= MessageHelper.getMessage(request, "js.error.alreadySelected") %>',
	'error.alreadySelectedRole': '<%= MessageHelper.getMessage(request, "js.error.alreadySelectedRole") %>',
	'error.alreadyInsertedRequisito': '<%= MessageHelper.getMessage(request, "js.error.alreadyInsertedRequisito") %>',
	'button.add': '<%= MessageHelper.getMessage(request, "js.button.add") %>',
	'title.addDitta': '<%= MessageHelper.getMessage(request, "js.title.addDitta") %>',
	'title.addRequisito': '<%= MessageHelper.getMessage(request, "js.title.addRequisito") %>',
	'title.addPosizione': '<%= MessageHelper.getMessage(request, "js.title.addPosizione") %>',
	'error.specifyMotivation': '<%= MessageHelper.getMessage(request, "js.error.specifyMotivation") %>',
	'error.confirmDeleteRequest': '<%= MessageHelper.getMessage(request, "js.error.confirmDeleteRequest") %>',
	'error.motivationLength': '<%= MessageHelper.getMessage(request, "js.error.motivationLength") %>',
	'error.deleteSubject': '<%= MessageHelper.getMessage(request, "js.error.deleteSubject") %>',
	'error.lottoRequired': '<%= MessageHelper.getMessage(request, "js.error.lottoRequired") %>',
	'error.invitationLetterWarning': '<%= MessageHelper.getMessage(request, "js.error.invitationLetterWarning") %>',
	'error.subsequentAwardWarning': '<%= MessageHelper.getMessage(request, "js.error.subsequentAwardWarning") %>',
	'info.totalAmountDescription': '<%= MessageHelper.getMessage(request, "js.info.totalAmountDescription") %>'
};

// Inizializza i18n con i messaggi
i18n.init(i18nMessages);
</script>

