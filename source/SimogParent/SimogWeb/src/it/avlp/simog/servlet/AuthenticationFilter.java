package it.avlp.simog.servlet;
import it.avcp.iam.model.impl.IAMPrincipalImpl;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.login.iaa.IAACostanti;

import java.io.IOException;
import java.security.Principal;
import java.util.Hashtable;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

/**
 * An example authentication filter which is used to intercept all the requests
 * for fetching the user name from it and put the user name to the Log4j Mapped
 * Diagnostic Context (MDC), so that the user name could be used for
 * differentiating log messages.
 *
 * @author veerasundar.com/blog
 *
 */
public class AuthenticationFilter implements Filter {

   public void doFilter(ServletRequest request, ServletResponse response,
         FilterChain chain) throws IOException, ServletException {

      HttpServletRequest aaa = (HttpServletRequest) request;
      HttpServletResponse resp = (HttpServletResponse) response;
      HttpSession sess = aaa.getSession();

      // se esiste il bean utente prendo i dati da aggiungere al log
      Utente bean = (Utente) sess.getAttribute(ParametriServlet.UTENTE);

      try {
         /*
          * This code puts the value "userName" to the Mapped Diagnostic
          * context. Since MDc is a static class, we can directly access it with
          * out creating a new object from it. Here, instead of hard coding the
          * user name, the value can be retrieved from a HTTP Request object.
          */
         String dati = sess.getId();
         if (bean != null) {
            dati += "_" + bean.getLogin();

            Hashtable uff = bean.getUfficiByProfilo(bean.getProfiloEnum());
            if (uff != null && uff.size() > 0) {

               StazioneAppaltante app = (StazioneAppaltante) uff.values()
                     .toArray()[0];

               dati += "_" + app.getCodiceFiscaleAmministrazione();
            }
         }

         MDC.put("Jsession", dati);

         // se IAM non attivo devo proseguire
//         if (!SimogFlags.is3024IAMActive()) {
            chain.doFilter(request, response);
            return;
//         }

      } finally {
         MDC.remove("Jsession"); 
      } 

//      if (SimogFlags.is3024IAMActive()) {
//         // if (sess.getAttribute(IAACostanti.SIMOG_SAML_RESPONSE) != null) {
//         if (sess.getAttribute(IAACostanti.SIMOG_SAML_RESPONSE) != null) {
//            chain.doFilter(request, response); // sono già validato
//            return;
//         }
//         else {
//            try {
//               String samlResponse = getSamlFromIAM();
//               if(samlResponse != null)
//                  sess.setAttribute(IAACostanti.SIMOG_SAML_RESPONSE, samlResponse);
//               
//               // redirect alla servlet che impone l'autenticazione, non posso usare il parametro del simog.ini!
//               resp.sendRedirect(aaa.getContextPath() + IAACostanti.AUTH_SERVLET);
//               return;
//            } catch (Throwable t) {
//               t.printStackTrace();
//            }
//         }
//      }
   }
//
//    private String getSamlFromIAM() throws Exception {
//       String retVal = null;
//       
//       Subject caller = null;
//
//       try {
//           caller = (Subject) PolicyContext.getContext("javax.security.auth.Subject.container");
//       } catch (PolicyContextException e) {
//           System.out.println("*** IAM:" + e.getMessage());
//           throw (e);
//       }
//
//       IAMPrincipalImpl iam = null;
//       
//       if(caller!=null) {
//           Set<Principal> set = caller.getPrincipals();
//
//           for (Principal principal : set) {
//               if (principal != null) {
////                  logger.debug("Prinncipal != null" + principal);
////                  logger.debug("IAMPrincipalImpl.class: " + IAMPrincipalImpl.class.getName());
////                  logger.debug("principal.class: " + principal.getClass().getName());
//
//                   if (principal instanceof IAMPrincipalImpl) {
////                      logger.debug("Principal is IAM " + principal);
//                       iam = (IAMPrincipalImpl)principal;
////                       logger.debug("IAM: " + iam);
////                       logger.debug("IAM.getSAML: " + iam.getSAML());
//                       retVal = iam.getSAML();
//                   }
//               }
//           }
//       }
//       
//       return retVal;
//    }
    
   public void destroy() {
      // TODO Auto-generated method stub
      
   }

   public void init(FilterConfig arg0) throws ServletException {
      // TODO Auto-generated method stub
      
   }

}