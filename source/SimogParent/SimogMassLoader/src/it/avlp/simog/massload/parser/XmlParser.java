package it.avlp.simog.massload.parser;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.Main;
import it.avlp.simog.massload.util.FeedBackWriterBase;
import it.avlp.simog.massload.xmlbeans.AnomaliaType;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument;
import it.avlp.simog.massload.xmlbeans.FlussoType;
import it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import test.IOReader;

/**
 * Classe che si occupa di una prima validazione del file xml
 * in ingresso rispetto al suo xsd
 *
 */
public class XmlParser {
	
	private Logger logger = null;
	private ArrayList<XmlError> errors = new ArrayList<XmlError>();
	private TrasferimentoDatiDocument doc = null;
	private FeedBackDocument feedbackxml = null;
	private String userName;
		
	public XmlParser ( Logger logger, String user ) {
		this.logger = logger;
		feedbackxml = FeedBackDocument.Factory.newInstance();
		feedbackxml.addNewFeedBack();
		userName = user;
		
		// da loaderAppalto non ricevo lo username
		if(user != null)
		   FeedBackWriterBase.addMassLoaderVersion(feedbackxml, this.userName);
	}

//	/**
//	 * @param filename
//	 * @param tantoPerFarloDiverso
//	 * @throws XmlException
//	 * @throws java.io.IOException
//	 * @deprecated
//	 */
//	public void parse(String filename, boolean tantoPerFarloDiverso)throws XmlException,java.io.IOException{
//		logger.debug("parsing document: " + filename);		    
//	    String s = IOReader.extractContents(filename);
//	    String s1 = new String(s.getBytes("UTF-8"),"UTF-8");
//	    doc = TrasferimentoDatiDocument.Factory.parse(s1);	
//	    FlussoType flu = getFeedbackXml().getFeedBack().addNewInfoFlusso();
//
//	    FeedBackWriter.writeInfo(flu,Calendar.getInstance(),1,1,0,0);
//	}
    /**
     * validazione del file xml
     * 
     * @param filename
     * @return boolean: esito operazione
     * @throws org.apache.xmlbeans.XmlException
     * @throws java.io.IOException
     */
    public boolean xsdValidate(String filename, boolean isContent)throws XmlException,java.io.IOException{
    	try{
		    if (isContent)
		    	logger.debug("parsing document (content)");		    
		    else
		    	logger.debug("parsing document: " + filename);
		    
		    String buffer = null;
		    
		    if (!isContent){
		    	String s = IOReader.extractContents(filename);
		    	buffer = new String(s.getBytes("UTF-8"),"UTF-8");
		    }
		    else
		    	buffer = new String(filename.getBytes("UTF-8"),"UTF-8");
		    	
		    doc = TrasferimentoDatiDocument.Factory.parse(buffer);		    
		    XmlOptions opts = new XmlOptions();
		    opts.setErrorListener(errors);	
		    
		 // XXX: scrittura dell'intestazione l'aggiornamento puo' essere fatto altrove
	    	FlussoType flu = getFeedbackXml().getFeedBack().addNewInfoFlusso();
		    
		    // -- end
	    	
		    //se il documento e' valido
		    if (doc.validate(opts)){
		    	FeedBackWriterBase.writeInfo(flu,Calendar.getInstance(),0,0,0,0);
		    	logger.debug("document is valid.");	
		    	
		    //altrimenti	
		    }else{
		    	FeedBackWriterBase.writeInfo(flu,Calendar.getInstance(),1,1,0,0);
			    
		        logger.debug("document is invalid!");
		        AnomalieSchede anoms = feedbackxml.getFeedBack().addNewAnomalieSchede();
		        FeedBackWriterBase.writeAnomalie(anoms, "", 0);
		        for(XmlError error : errors){
		        		        	
		        	if(error.getSeverity() == XmlError.SEVERITY_ERROR){
		        		
			        	String buff = "";
	
		        		if (error.getCursorLocation().getName()!=null)
			        		buff += error.getCursorLocation().getName() + " -> ";
	
			        	buff += (String) error.toString();
	
			        	logger.debug(">> " + buff);
			        	
			        	AnomaliaType anom = anoms.addNewAnomalia();
			        	FeedBackWriterBase.writeAnomalia(anom, "XSD_PARSE", 0,ValidationBean.VALBEAN_SEV_ERR, buff);
				        
		        	}
		        }
		    }
		    return (errors.size()==0);
    	}catch(XmlException e){
    		//scrivo il feedback in caso di eccezione
    		this.setFeedBack(e.getMessage());
    		//forward dell'eccezione.
    		throw e;
    	}
    }
    
    /**
     * Scrive l'xml di feedback in caso sia lanciata l'eccezione di dipo xmlException
     * 
     * @param messaggioEccezione
     * @param num
     */
    private void setFeedBack(String messaggioEccezione){
    	
		// non esco ma geneero un feedback anomalo System.exit(RET_VALUES.FATAL_ERROR.ordinal());
		this.getErrors().add(messaggioEccezione);
		
    	FlussoType flu = getFeedbackXml().getFeedBack().addNewInfoFlusso();

    	FeedBackWriterBase.writeInfo(flu,Calendar.getInstance(),1,1,0,0);
	    AnomalieSchede anoms = feedbackxml.getFeedBack().addNewAnomalieSchede();
	    FeedBackWriterBase.writeAnomalie(anoms, "", 0);
    	AnomaliaType anom = anoms.addNewAnomalia();
    	FeedBackWriterBase.writeAnomalia(anom, "XSD_PARSE", 0,ValidationBean.VALBEAN_SEV_ERR, messaggioEccezione);
  	
    	
    }
    
	public TrasferimentoDatiDocument getDoc() {
		return doc;
	}

	public ArrayList getErrors() {
		return errors;
	}

	public void setErrors(ArrayList errors) {
		this.errors = errors;
	}

	public FeedBackDocument getFeedbackXml() {
		return feedbackxml;
	}
}