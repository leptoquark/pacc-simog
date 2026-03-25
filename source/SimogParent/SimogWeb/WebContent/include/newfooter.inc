</div>
<%@ taglib prefix="utils" uri="http://simog.avlp.it/tags-util"  %>
<footer _ngcontent-exg-c5="" class="it-footer">
	<div _ngcontent-exg-c5="" class="d-flex float-right my-3 mr-2">
		<p _ngcontent-exg-c5="" style="color: white;">V3.04.16 (build 03)</p>
			</div>
	<div _ngcontent-exg-c5="" class="it-footer-main">
		<div _ngcontent-exg-c5="" class="container">
			<section _ngcontent-exg-c5="" class="footer-secondary">
				<div _ngcontent-exg-c5="" class="row">
					<div _ngcontent-exg-c5="" class="col-md-6">
						<h4 _ngcontent-exg-c5="" class="text-uppercase h5">
							<utils:message key="footer.contatti" />
						</h4>
						<a _ngcontent-exg-c5="" href="mailto:protocollo@pec.anticorruzione.it">
							protocollo@pec.anticorruzione.it
						</a>
						<p _ngcontent-exg-c5="">
							<utils:message key="footer.contactCenter" /><br> +39 06 62289571
						</p>
	</div> 
					<div _ngcontent-exg-c5="" class="col-md-6">
						<h4 _ngcontent-exg-c5="" class="text-uppercase h5">
							<utils:message key="footer.quicklinks" />
						</h4>
						<div _ngcontent-exg-c5="" class="link-list-wrapper">
							<ul _ngcontent-exg-c5="" class="footer-list link-list">
								<li _ngcontent-exg-c5="">
									<a _ngcontent-exg-c5="" class="list-item" href="http://www.anticorruzione.it" title="<utils:message key="footer.portaleIstituzionale" plain="true" />"><utils:message key="footer.portaleIstituzionale" /></a>
								</li>
								<li _ngcontent-exg-c5="">
									<a _ngcontent-exg-c5="" class="list-item" href="https://servizi.anticorruzione.it" title="<utils:message key="footer.portaleServizi" plain="true" />"><utils:message key="footer.portaleServizi" /></a>
								</li>
							</ul>
	</div>
					</div>
				</div>
			</section>
		</div>
	</div>
	<div _ngcontent-exg-c5="" class="it-footer-small-prints">
		<div _ngcontent-exg-c5="" class="container">
			<div _ngcontent-exg-c5="" class="row">
				<div _ngcontent-exg-c5="" class="col-12">
					<h2 _ngcontent-exg-c5="" class="sr-only">Sezione Link Utili</h2>
					<div _ngcontent-exg-c5="">
						<ul _ngcontent-exg-c5="" class="it-list">
							<li _ngcontent-exg-c5="" class="list-inline-item">
								<a _ngcontent-exg-c5="" class="list-item" href="https://www.anticorruzione.it/note-legali" title="<utils:message key="footer.noteLegali" plain="true" />" style="text-decoration: none;">
									<span _ngcontent-exg-c5=""><utils:message key="footer.noteLegali" /></span>
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</footer>
<!-- MAD 35041 3.04.8 -->

<!-- Matomo -->
<script type="text/javascript">
try {
	var _paq = window._paq = window._paq || [];
	  _paq.push(['trackPageView']);
	  _paq.push(['enableLinkTracking']);
	  (function() {
		  try {
			  var u="https://ingestion.webanalytics.italia.it/";
			    _paq.push(['setTrackerUrl', u+'matomo.php']);
			    _paq.push(['setSiteId', 'XKqJ1e6pYR']);
			    var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];
			    g.type='text/javascript'; g.async=true; g.src=u+'matomo.js'; s.parentNode.insertBefore(g,s);
				console.log("fine caricamento analytics");
		  } catch (e) {
			console.error("errore script google analytics" + e);
		}
	  })();
	} catch (error) {
	  console.error("errore script google analytics" + error);
	}
</script>
<!-- End Matomo Code -->

<%-- Google Translate Auto-Translation for dynamic content --%>
<%@ include file="google_translate.inc" %>

<%-- SIMOG Auto-Translate: Traduzione automatica IT → AR per testo residuo --%>
<script type="text/javascript" src="<%= request.getContextPath() %>/script/auto-translate.js"></script>