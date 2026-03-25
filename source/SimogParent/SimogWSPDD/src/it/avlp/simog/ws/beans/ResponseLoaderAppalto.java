package it.avlp.simog.ws.beans;

import it.avlp.simog.ws.massload.xmlbeans.FeedBack;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ResponseLoaderAppalto")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseLoaderAppalto {

	@XmlElement
	private FeedBack feedBack;

	public FeedBack getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(FeedBack feedBack) {
		this.feedBack = feedBack;
	}

}
