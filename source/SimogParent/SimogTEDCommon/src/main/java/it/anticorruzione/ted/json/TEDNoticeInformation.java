package it.anticorruzione.ted.json;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe che immagazzina i dati di risposta del servizio rest GET dei notice
 * 
 */
public class TEDNoticeInformation extends JSONObject {

	private String submission_id;
	private String received_at;
	private String status;
	private String reason_code;
	private String status_updated_at;
	private String no_doc_ext;
	private String form;
	private JSONArray languages;
	private JSONObject publication_info;
	private JSONObject technical_validation_report;
	private JSONObject validation_rules_report;
	private JSONObject quality_control_report;
	private String ref_submission_id;
	
	
	public TEDNoticeInformation(String jsonString) {
		super(jsonString);
		this.submission_id=this.loadStringProp("submission_id");
		this.received_at=this.loadStringProp("received_at");
		this.status=this.loadStringProp("status");
		this.reason_code=this.loadStringProp("reason_code");
		this.status_updated_at=this.loadStringProp("status_updated_at");
		this.no_doc_ext=this.loadStringProp("no_doc_ext");
		this.form=this.loadStringProp("form");
		this.languages=this.getJSONArray("languages");
		this.publication_info=this.loadJSONObjProp("publication_info");
		this.technical_validation_report=this.loadJSONObjProp("technical_validation_report");
		this.validation_rules_report=this.loadJSONObjProp("validation_rules_report");
		this.quality_control_report=this.loadJSONObjProp("quality_control_report");
		this.ref_submission_id=this.loadStringProp("ref_submission_id");
	}
	
	
	private String loadStringProp(String prop) {
		if (this.has(prop) && !this.isNull(prop)) {
	        return this.getString(prop);
	      }
		return null;
	}
	
	private JSONObject loadJSONObjProp(String prop) {
		if (this.has(prop) && !this.isNull(prop)) {
	        return this.getJSONObject(prop);
	      }
		return null;
	}
	
	public String getSubmission_id() {
		return submission_id;
	}

	public String getReceived_at() {
		return received_at;
	}

	public String getStatus() {
		return status;
	}

	public String getReason_code() {
		return reason_code;
	}

	public String getStatus_updated_at() {
		return status_updated_at;
	}

	public String getNo_doc_ext() {
		return no_doc_ext;
	}

	public String getForm() {
		return form;
	}

	public JSONArray getLanguages() {
		return languages;
	}

	public JSONObject getPublication_info() {
		return publication_info;
	}

	public JSONObject getTechnical_validation_report() {
		return technical_validation_report;
	}

	public JSONObject getValidation_rules_report() {
		return validation_rules_report;
	}

	public JSONObject getQuality_control_report() {
		return quality_control_report;
	}

	public String getRef_submission_id() {
		return ref_submission_id;
	}

	public String getNoDocOjs() {
		if(this.publication_info!=null) {
			return this.publication_info.getString("no_doc_ojs");
		}
		
		return null;
	}
	
	public String getPublicationDate() {
		if(this.publication_info!=null) {
			return this.publication_info.getString("publication_date");
		}
		
		return null;
	}
	
	public String getTedLink() {
		if(this.publication_info!=null) {
			 JSONObject tedLink = this.publication_info.getJSONObject("ted_links");
			 return tedLink.getString("IT");
		}
		
		return null;
	}
	
	public String getErrors() {
		String ris ="";
		JSONArray errArray = null;
		if(this.quality_control_report!=null) {
			 errArray = this.quality_control_report.getJSONArray("items");
		} else if(this.validation_rules_report!=null) {
			 errArray = this.validation_rules_report.getJSONArray("items");
		} else if(this.technical_validation_report!=null) {
			 errArray = this.technical_validation_report.getJSONArray("items");
		}
		if(errArray!=null) {
			for(int i=0;i<errArray.length();i++) {
				JSONObject err = errArray.getJSONObject(i);
				if("ERROR".equals(err.getString("severity"))) {
					ris+=err.getString("message");
					ris+="\n";
				} else if("CRITICAL".equals(err.getString("severity"))) {
					ris+=err.getString("details");
					ris+="\n";
				}
			}
		}
		return ris;
	}
}
