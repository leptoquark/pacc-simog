package it.avlp.simog.validatore;

import java.sql.Connection;

import org.apache.log4j.Logger;

public class DummyValidator extends SimogValidator {
	
	public DummyValidator(Connection connection, Logger logger) {
		super(connection, logger);
	}
	public void clearExceptions(){
		this.mEccezioni.clear();
	}
	@Override
	public boolean valida(Object bean, String section) {
		return true;
	}
}