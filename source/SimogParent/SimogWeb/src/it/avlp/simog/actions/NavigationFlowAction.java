package it.avlp.simog.actions;

import java.sql.Connection;

import org.apache.log4j.Logger;

public abstract class NavigationFlowAction extends BaseAction {

	protected NavigationFlowAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}
	
	public abstract boolean canSave() ;

}
