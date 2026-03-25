package it.avlp.simog.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

//import com.ditd.util.Util;
//import com.gbs.security.IUserInfo;

/**
 * 
 * filtro per controllare la scadenza della sessione, viene rinnovato 
 * il cookie JSESSIONID ad ogni richiesta, per il periodo specificato nel parametro
 * DURATA_SESSIONE (in secondi) in simog.ini
 */
@Deprecated
public class ExtendSession extends ServletBase implements Filter 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2659366569592871448L;
	private static Logger log = Logger.getLogger(
			ExtendSession.class.getName());

	public void doFilter(ServletRequest servletRequest,
						 ServletResponse servletResponse, 
						 FilterChain filterChain)
	throws IOException, ServletException
	{
//		try
//		{		   
//			HttpServletRequest request =
//				(HttpServletRequest)servletRequest;
//			HttpServletResponse response = (HttpServletResponse)
//			servletResponse;
//
//			it.avlp.simog.beans.Utente user = (it.avlp.simog.beans.Utente) request.getSession().getAttribute(it.avlp.simog.common.servlet.ParametriServlet.UTENTE);
////				IUserInfo user = (IUserInfo)request.getUserPrincipal();
//			if(user != null)
//			{
//				Cookie[] myCookies = request.getCookies();
//				Cookie cookie;
//				if(myCookies != null)
//				{
//					for(int i = 0; i < myCookies.length; i++)
//					{
//						cookie = myCookies[i];
//						if(cookie.getName() != null &&
//								cookie.getName().equals("JSESSIONID"))
//						{
//							cookie.setMaxAge(configuration.getDurataSessione().intValue());
//							//logger.debug("cookie life aumentata a: " + configuration.getDurataSessione().toString());
//							cookie.setPath(request.getContextPath());
//							response.addCookie(cookie);
//							break;
//						}
//					}
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			log.fatal("ExtendSession: Unable to pull features from database : " + e.getMessage());
//		
//		}
//		finally
//		{
//			filterChain.doFilter(servletRequest, servletResponse);
//		}
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
//		no op
	}

	public void destroy()
	{
//		no op
	}

	@Override
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
