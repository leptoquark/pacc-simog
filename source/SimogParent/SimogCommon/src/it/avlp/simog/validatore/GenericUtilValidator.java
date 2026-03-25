package it.avlp.simog.validatore;

import java.math.BigDecimal;
import java.sql.Connection;

import org.apache.log4j.Logger;

public class GenericUtilValidator extends SimogValidator {
	
	public GenericUtilValidator(Connection connection, Logger logger) {
		super(connection, logger);
	}
	public void clearExceptions(){
		this.mEccezioni.clear();
	}
	@Override
	public boolean valida(Object bean, String section) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public String getYearData(String data) {
		// TODO Auto-generated method stub
		return super.getYearData(data);
	}
	@Override
	public boolean isDate(String data) {
		// TODO Auto-generated method stub
		return super.isDate(data);
	}
	@Override
	public boolean isDateBigger(Object o, Object o1) {
		// TODO Auto-generated method stub
		return super.isDateBigger(o, o1);
	}
	@Override
	public boolean isDateBiggerEq(Object o, Object o1) {
		// TODO Auto-generated method stub
		return super.isDateBiggerEq(o, o1);
	}
	@Override
	public boolean isDateLower(Object o, Object o1) {
		// TODO Auto-generated method stub
		return super.isDateLower(o, o1);
	}
	@Override
	public boolean isDateLowerEq(Object o, Object o1) {
		// TODO Auto-generated method stub
		return super.isDateLowerEq(o, o1);
	}
	@Override
	public boolean isDateYearBigger(String data) {
		// TODO Auto-generated method stub
		return super.isDateYearBigger(data);
	}
	@Override
	public boolean isEmpty(Object o) {
		// TODO Auto-generated method stub
		return super.isEmpty(o);
	}
	@Override
	public boolean isEmptyOrLessZero(Object o) {
		// TODO Auto-generated method stub
		return super.isEmptyOrLessZero(o);
	}
	@Override
	public boolean isEmptyOrZero(Object o) {
		// TODO Auto-generated method stub
		return super.isEmptyOrZero(o);
	}
	@Override
	public boolean isFlag(String val) {
		// TODO Auto-generated method stub
		return super.isFlag(val);
	}
	@Override
	public boolean isForniture(String tipoScheda) {
		// TODO Auto-generated method stub
		return super.isForniture(tipoScheda);
	}
	@Override
	public boolean isInRange(BigDecimal val, BigDecimal min, BigDecimal max) {
		// TODO Auto-generated method stub
		return super.isInRange(val, min, max);
	}
	@Override
	public boolean isLavori(String tipoScheda) {
		// TODO Auto-generated method stub
		return super.isLavori(tipoScheda);
	}
	@Override
	public boolean isMail(String mail) {
		// TODO Auto-generated method stub
		return super.isMail(mail);
	}
	@Override
	public boolean isNFlag(String val) {
		// TODO Auto-generated method stub
		return super.isNFlag(val);
	}
	@Override
	public boolean isNumber(String str, int len) {
		// TODO Auto-generated method stub
		return super.isNumber(str, len);
	}
	@Override
	public boolean isNumber(String str) {
		// TODO Auto-generated method stub
		return super.isNumber(str);
	}
	@Override
	public boolean isNumberDecimal(String str) {
		// TODO Auto-generated method stub
		return super.isNumberDecimal(str);
	}
	@Override
	public boolean isOrdinario(String tipoEnte) {
		// TODO Auto-generated method stub
		return super.isOrdinario(tipoEnte);
	}
	@Override
	public boolean isPositive(Object o) {
		// TODO Auto-generated method stub
		return super.isPositive(o);
	}
	@Override
	public boolean isServizi(String tipoScheda) {
		// TODO Auto-generated method stub
		return super.isServizi(tipoScheda);
	}
	@Override
	public boolean isSpeciale(String tipoEnte) {
		// TODO Auto-generated method stub
		return super.isSpeciale(tipoEnte);
	}
	@Override
	public boolean isStringEmptyValue(String o) {
		// TODO Auto-generated method stub
		return super.isStringEmptyValue(o);
	}
	@Override
	public boolean isYFlag(String val) {
		// TODO Auto-generated method stub
		return super.isYFlag(val);
	}
	@Override
	public boolean validaCodiceFiscale(String codiceFiscale) {
		// TODO Auto-generated method stub
		return super.validaCodiceFiscale(codiceFiscale);
	}
	@Override
	public boolean validaPartitaIva(String pi) {
		// TODO Auto-generated method stub
		return super.validaPartitaIva(pi);
	}
	@Override
	public boolean validateLength(String value, int requiredLength) {
		// TODO Auto-generated method stub
		return super.validateLength(value, requiredLength);
	}
	@Override
	public boolean validatePercentual(BigDecimal val) {
		// TODO Auto-generated method stub
		return super.validatePercentual(val);
	}
	
	
}