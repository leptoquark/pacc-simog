package it.avlp.simog.beans;

public class NavigationBean{
	private String viewName;		// nome della scheda nella combo box di selezione
	private String tab;				// nome interno della scheda
	private long stato;				// stato
	private boolean readonly;		// scheda non selezionabile
	private String path;			// path della servlet che gestisce la scheda
	private Long id;				// id della scheda
	private boolean richAnn;		// richiesta annullamento in corso
	private boolean valido = true;	// la scheda non contiene errori di validazione
	private boolean richDelete;		// richiesta cancellazione in corso
	private boolean delegaScheda = false;
	private boolean riaggiudicata = false;  //e stata conclusa e ce la nuova aggiudicazione
	private boolean riaggiudicabile = false; // e stata conclusa e non ce la nuova aggiudicazione
	
	public boolean isDelegaScheda() {
		return delegaScheda;
	}
	public void setDelegaScheda(boolean delegaScheda) {
		this.delegaScheda = delegaScheda;
	}
	
	public boolean isReadonly() {
		return readonly;
	}
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}
	
	/*
	 * scheda esistente
	 */
	public boolean isPresent() {
		return stato > 0;
	}
	
	/*
	 * scheda confermata
	 */
	public boolean isDone() {
		return stato == StatiScheda.CONFERMATO;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String name) {
		this.viewName = name;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public long getStato() {
		return stato;
	}
	public void setStato(long stato) {
		this.stato = stato;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isRichAnn() {
		return richAnn;
	}
	public void setRichAnn(boolean richAnn) {
		this.richAnn = richAnn;
	}
	public boolean isValido() {
		return valido;
	}
	public void setValido(boolean valido) {
		this.valido = valido;
	}
	public boolean isRichDelete() {
		return richDelete;
	}
	public void setRichDelete(boolean richDelete) {
		this.richDelete = richDelete;
	}
	public boolean isRiaggiudicata() {
		return riaggiudicata;
	}
	public void setRiaggiudicata(boolean riaggiudicata) {
		this.riaggiudicata = riaggiudicata;
	}
	public boolean isRiaggiudicabile() {
		return riaggiudicabile;
	}
	public void setRiaggiudicabile(boolean riaggiudicabile) {
		this.riaggiudicabile = riaggiudicabile;
	}
	
	

}
