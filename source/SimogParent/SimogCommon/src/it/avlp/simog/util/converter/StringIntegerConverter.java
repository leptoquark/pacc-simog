package it.avlp.simog.util.converter;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.IntegerConverter;


public class StringIntegerConverter implements Converter{

	private Integer defaultValue = null;
//	private boolean useDefault = true;
	
	public StringIntegerConverter(){
		this.defaultValue = null;
//		this.useDefault = false;
	}
	
	public StringIntegerConverter(Integer defaultValue){
		this.defaultValue = defaultValue;
//		this.useDefault = true;
	}
	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object val) {
		if(val == null || String.valueOf(val).trim().length() == 0)
			return defaultValue ;
		
		else return (new IntegerConverter(defaultValue)).convert(type, val);
	}

		

}
