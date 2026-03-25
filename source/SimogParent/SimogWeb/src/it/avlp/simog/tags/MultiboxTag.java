package it.avlp.simog.tags;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import it.avlp.simog.common.servlet.ParametriServletVariante;

public class MultiboxTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5847250739421544688L;
	private String campo;
	private String lista;
	private String listaCampiSelezionati;
	// private String scope = "request";
	private String disabled = "";
	private String disabledTag = "";
	private String disabledTagFlag = "";

	private String onchange = null;
	private String idField;
	private String readonly = "false";

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		Map<String, String> optionList = null;
		List<?> optionCheckedList = null;

		if (lista == null || listaCampiSelezionati == null)
			return SKIP_BODY;

		else { // hello world
			try {
				boolean ro = Boolean.parseBoolean(readonly);
				disabled = ro ? "disabled" : "";

				if (Boolean.parseBoolean(disabledTagFlag)) {
					if(idField.equals("idMisuraPremiale")) {
						disabledTag="disabled='true' class='checkboxfieldsMP'";
					} else if(idField.equals("idMotivoDeroga")){
						disabledTag="disabled='true' class='checkboxfieldsMD'";
					} else {
						disabledTag="disabled='true' class='checkboxfields'";
					}
				} else {
					disabledTag = "";
				}

				optionList = (Map<String, String>) pageContext.getVariableResolver().resolveVariable(lista);
				optionCheckedList = (List<?>) pageContext.getVariableResolver().resolveVariable(listaCampiSelezionati);

				Object[] keys = optionList.keySet().toArray();
				if (lista.equals(ParametriServletVariante.BEAN_MOTIVI_VARIANTE)) {
					String[] stringArray = Arrays.copyOf(keys, keys.length, String[].class);
					int[] intArray = new int[stringArray.length];
					for (int i = 0; i < stringArray.length; i++)
						intArray[i] = Integer.parseInt(stringArray[i]);

					Arrays.sort(intArray);
					keys = new String[intArray.length];
					for (int i = 0; i < intArray.length; i++)
						keys[i] = String.valueOf(intArray[i]);
				} else
					Arrays.sort(keys);

				List<String> stringCheckedList = getListaId(optionCheckedList, idField);

				for (int i = 0; i < keys.length; i++) {

					out.println("<tr>");
					out.print("<th><label>" + optionList.get(keys[i])
							+ "</label></th>");
					out.print("<td><input " + disabledTag
							+ "  type=\"checkbox\" name =\"" + campo +"\""
							+ " id = \""+campo+keys[i]
							+ "\" value=\"" + keys[i] + "\" ");

					if (stringCheckedList.contains(keys[i].toString())) {
						out.println("\" checked=\"checked\" ");

					}

					if (onchange != null)
						out.print("onchange = \"" + onchange + "\" ");

					out.print(" />");
					if (stringCheckedList.contains(keys[i].toString()) && ro) {
						out.println(
								"<input " + "  type=\"hidden\" name =\"" + campo + "\" value=\"" + keys[i] + "\" /> ");

					}

					out.print(" </td></tr>");

				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new JspException(e);
			}

		}
		return SKIP_BODY;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public void setListaCampiSelezionati(String listaCampiSelezionati) {
		this.listaCampiSelezionati = listaCampiSelezionati;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
		this.disabledTagFlag = disabled;

	}

	/*****************************************************************************
	 * Genera una lista dei metodi chiamati dagli elementi nella listaOggetti param
	 * listaOggetti : List param campo : String return Lista di Stringhe throws
	 * JspException
	 */
	private List<String> getListaId(List<?> listaOggetti, String campo) throws JspException {
		List<String> ris = new ArrayList<String>();
		if (listaOggetti == null)
			return ris;
		for (Object oggetto : listaOggetti) {
			try {
				String nomeMetodo = "get" + campo.substring(0, 1).toUpperCase() + campo.substring(1);
				Method metodo = oggetto.getClass().getMethod(nomeMetodo, new Class[] {});
				String nuovaStringa = metodo.invoke(oggetto, new Object[] {}).toString();
				ris.add(nuovaStringa);
			} catch (Exception e) {

				throw new JspException(e);
			}
		}
		return ris;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
}
