package it.avlp.simog.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filtro per la gestione della locale e internazionalizzazione
 * Supporta italiano (it) e arabo (ar)
 */
public class LocaleFilter implements Filter {
    
    private static final String DEFAULT_LOCALE = "it";
    private static final String LOCALE_PARAM = "locale";
    private static final String SESSION_LOCALE = "userLocale";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nessuna inizializzazione necessaria
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        
        // Imposta encoding UTF-8 per supportare caratteri arabi
        httpRequest.setCharacterEncoding("UTF-8");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("text/html; charset=UTF-8");
        
        // Determina la locale da usare
        Locale locale = determineLocale(httpRequest, session);
        
        // Salva la locale nella sessione
        session.setAttribute(SESSION_LOCALE, locale);
        
        // Imposta la locale per la richiesta corrente
        httpRequest.setAttribute("locale", locale);
        
        chain.doFilter(request, response);
    }
    
    /**
     * Determina la locale da usare basandosi su:
     * 1. Parametro della richiesta (locale=ar o locale=it)
     * 2. Locale salvata in sessione
     * 3. Accept-Language header del browser
     * 4. Locale di default (italiano)
     */
    private Locale determineLocale(HttpServletRequest request, HttpSession session) {
        // 1. Controlla il parametro della richiesta
        String localeParam = request.getParameter(LOCALE_PARAM);
        if (localeParam != null && !localeParam.isEmpty()) {
            if ("ar".equalsIgnoreCase(localeParam)) {
                return new Locale("ar");
            } else if ("it".equalsIgnoreCase(localeParam)) {
                return new Locale("it");
            }
        }
        
        // 2. Controlla la sessione
        Locale sessionLocale = (Locale) session.getAttribute(SESSION_LOCALE);
        if (sessionLocale != null) {
            return sessionLocale;
        }
        
        // 3. Controlla l'header Accept-Language
        Locale browserLocale = request.getLocale();
        if (browserLocale != null) {
            String language = browserLocale.getLanguage();
            if ("ar".equals(language)) {
                return new Locale("ar");
            } else if ("it".equals(language)) {
                return new Locale("it");
            }
        }
        
        // 4. Default: italiano
        return new Locale(DEFAULT_LOCALE);
    }
    
    @Override
    public void destroy() {
        // Nessuna pulizia necessaria
    }
}

