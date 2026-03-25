package it.avlp.simog.servlet;

import it.avlp.simog.beans.VO;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.converter.StringIntegerConverter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;


public abstract class BeanUtilsServlet extends ServletBase {

	
	protected List getListOfObjects(Class c, HttpServletRequest req) {
		return getListOfObjects(c, req, "");
	}
	
	protected ActionEnum getOperazione(HttpServletRequest request) {
		String operazione =(String) getRequestParameter(String.class, "operazione", request);
		return ActionEnum.fromString(operazione);
		
	}

	/**
	 * Costruisce una lista di oggetti della classe c.Vengono chiamati gli
	 * opportuni setters usando i valori presenti in request.
	 * 
	 * @param c
	 *            classe degli oggetti da creare
	 * @param req
	 *            request
	 * @return una lista di oggetti di tipo c
	 */
	protected List getListOfObjects(Class c, HttpServletRequest req,
			String prefix) {
		List l = new ArrayList();
		try {
			String npfx = prefix.trim().length() == 0 ? "" : prefix + "."; 
			int numOfObjects = 0;
			try {
				numOfObjects = Integer.parseInt(req.getParameter(npfx + "numOfObjects"));
			} catch (Exception e) {
				// TODO: handle exception
			}

			for (int i = 1; i <= numOfObjects; i++) {
				l.add(getObjectFromRequest(c, req, prefix, i));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return l;
	}

	/**
	 * Costruisce un oggetto della classe c inizializzandolo con valori presi
	 * dalla request. Cerca in request attributti con nomi uguali agli attributi
	 * dell'oggetto e chiama i relativi setters
	 * 
	 * @param c
	 *            classe dell'oggetto da costruire
	 * @param req
	 * @return oggetto di tipo c, inizializzato con i valori presi dalla request
	 */
	protected Object getObjectFromRequest(Class c, HttpServletRequest req) {
		return getObjectFromRequest(c, req, "");
	}

	protected Object getObjectFromRequest(Class c, HttpServletRequest req,
			Object suffix) {
		return getObjectFromRequest(c, req, "", suffix);

	}

	/**
	 * Costruisce un oggetto della classe c inizializzandolo con valori presi
	 * dalla request. Cerca in request attributti con nomi uguali agli attributi
	 * dell'oggetto + suffix e chiama i relativi setters
	 * 
	 * @param c
	 *            classe dell'oggetto da costruire
	 * @param req
	 * @param suffix
	 *            stringa da aggiungere ai nomi dei attributi del bean per
	 *            trovarli in request
	 * @return oggetto di tipo c, inizializzato con i valori presi dalla request
	 */
	protected Object getObjectFromRequest(Class c, HttpServletRequest req,
			String prefix, Object suffix) {
		try {

			Object o = c.newInstance();
			Map m = BeanUtils.describe(o);
			m = removeROandSystem(m, c);
			
			SqlDateConverter scdc = new SqlDateConverter(null);
			scdc.setPattern(PageHelper.DEFAULT_VIEW_DATE);
			ConvertUtils.register(scdc, Date.class);
			ConvertUtils.register(new StringIntegerConverter(null), Integer.class);
			Set<String> keys = m.keySet();
			String canPref = prefix.trim().length() == 0 ? "" : prefix + ".";
			for (String id : keys) {
				String compName = canPref + id + suffix;
				Class inner = null;
				try {
					
					inner = c.getDeclaredField(id).getType();
					if (List.class.isAssignableFrom(inner)) {
						ParameterizedType pt = (ParameterizedType) c
								.getDeclaredField(id).getGenericType();
						
						Type ot = pt.getActualTypeArguments()[0];
						BeanUtils.setProperty(o, id, getListOfObjects(
								Class.forName(ot.toString().replace("class", "")
										.trim()), req, canPref + id));
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				Package pkg = inner != null ? inner.getPackage() : null;
				if (pkg != null && pkg.getName().startsWith((VO.class.getPackage().getName()))) {
					BeanUtils.setProperty(o, id, getObjectFromRequest(inner,
							req, canPref + id, suffix));
				}
				else {
					Object val = req.getParameter(compName);
					if (val != null)
						BeanUtils.setProperty(o, id, val);
				}
			}

			return o;

		} catch (Exception e) {
			
			e.printStackTrace();
			try {
				return c.newInstance();
			} catch (InstantiationException e1) {
				
				
				e1.printStackTrace();
				return null;
			} catch (IllegalAccessException e1) {
				
				
				e1.printStackTrace();
				return null;
			}
		}

	}
	@SuppressWarnings("all")
	private Map removeROandSystem(Map m, Class<?> c) {
		m.remove("class");
		Map m2 = new HashMap();
		for(Object s: m.keySet()){
			String id =(String) s;
			try{
				
				for (Method meth: c.getMethods()){
					if(meth.getName().equals("set" + id.substring(0,1).toUpperCase() + id.substring(1) )){
						m2.put(id, m.get(id));
					}
					
				}
		
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return m2;
		
	}

	protected Object getRequestParameter(Class<?> type,String name, HttpServletRequest request ){
		return getRequestParameter(type,name,request,null);
	}
	protected Object getRequestParameter(Class<?> type,String name, HttpServletRequest request, Object defaultValue ){
		String param = request.getParameter(name);
		if(param == null)
			return null;
		else if(param.trim().length() == 0)
			return defaultValue;
		return ConvertUtils.convert(param, type);
	}
	
	protected Object getSessionAttribute(Class<?> type,String name, HttpServletRequest request ){
		return  ConvertUtils.convert(request.getSession() != null ?request.getSession().getAttribute(name) : null , type);
	}
	
	protected Object getRequestAttribute(Class<?> type,String name, HttpServletRequest request ){
		return  ConvertUtils.convert(request.getAttribute(name), type);
	}
	
	
	
	protected Object getValueFromContexts(Class<?> type,String name, HttpServletRequest request ){
		Object o = getRequestParameter(type, name, request);
		if(o != null)
			return o;
		
		o =  getRequestAttribute(type, name, request);
		if(o != null)
			return o;

		o =  getSessionAttribute(type, name, request);
		
		return o;
		
		
	}
	

}
