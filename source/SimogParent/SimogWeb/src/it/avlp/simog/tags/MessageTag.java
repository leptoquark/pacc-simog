package it.avlp.simog.tags;

import it.avlp.simog.util.MessageHelper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag JSP per visualizzare messaggi internazionalizzati
 * 
 * I messaggi vengono automaticamente protetti dalla classe "notranslate"
 * per evitare che Google Translate li traduca nuovamente.
 * 
 * Uso:
 * <utils:message key="login.title" />
 * <utils:message key="msg.operazioneCompletata" param1="valore1" param2="valore2" />
 */
public class MessageTag extends TagSupport {
    
    private static final long serialVersionUID = 1L;
    
    private String key;
    private String[] params;
    private boolean plain = false; // Se true, non wrappa in <span> (utile per attributi HTML)
    
    public void setKey(String key) {
        this.key = key;
    }
    
    /**
     * Imposta se il messaggio deve essere output senza wrapping in <span>
     * Utile quando il messaggio viene usato in attributi HTML come title="..."
     * 
     * @param plain true per output diretto senza <span>, false per wrapping normale
     */
    public void setPlain(boolean plain) {
        this.plain = plain;
    }
    
    /**
     * Versione stringa per JSP (accetta "true"/"false")
     */
    public void setPlain(String plainStr) {
        this.plain = Boolean.parseBoolean(plainStr);
    }
    
    public void setParam1(String param) {
        setParam(0, param);
    }
    
    public void setParam2(String param) {
        setParam(1, param);
    }
    
    public void setParam3(String param) {
        setParam(2, param);
    }
    
    public void setParam4(String param) {
        setParam(3, param);
    }
    
    public void setParam5(String param) {
        setParam(4, param);
    }
    
    private void setParam(int index, String param) {
        if (params == null) {
            params = new String[5];
        }
        if (index < params.length) {
            params[index] = param;
        }
    }
    
    @Override
    public int doStartTag() throws JspException {
        try {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            JspWriter out = pageContext.getOut();
            
            String message;
            if (params != null && params.length > 0) {
                // Rimuove i null
                int count = 0;
                for (String p : params) {
                    if (p != null) count++;
                }
                String[] cleanParams = new String[count];
                int idx = 0;
                for (String p : params) {
                    if (p != null) {
                        cleanParams[idx++] = p;
                    }
                }
                message = MessageHelper.getMessage(request, key, (Object[]) cleanParams);
            } else {
                message = MessageHelper.getMessage(request, key);
            }
            
            // Se plain=true, output diretto senza wrapping (utile per attributi HTML)
            if (plain) {
                // Output diretto senza span - JSP gestisce automaticamente l'escape quando usato in attributi
                String safeMessage = escapeHtmlSafe(message);
                out.print(safeMessage);
            } else if (message != null && message.equals(key)) {
                // Messaggio non tradotto - output diretto senza span per evitare HTML malformato
                // JSP gestisce automaticamente l'escape quando usato in attributi
                out.print(message);
            } else {
                // Messaggio tradotto - proteggi da Google Translate con span
                // Escape HTML per sicurezza
                String safeMessage = escapeHtmlSafe(message);
                out.print("<span class=\"notranslate\">");
                out.print(safeMessage);
                out.print("</span>");
            }
        } catch (IOException e) {
            throw new JspException("Errore durante la scrittura del messaggio", e);
        }
        
        return SKIP_BODY;
    }
    
    /**
     * Escape HTML per sicurezza, preservando entità HTML esistenti
     */
    private String escapeHtmlSafe(String text) {
        if (text == null) {
            return "";
        }
        // Prima escape & (ma preserva entità HTML esistenti)
        String result = text.replace("&", "&amp;");
        // Poi escape altri caratteri pericolosi
        result = result.replace("<", "&lt;")
                       .replace(">", "&gt;");
        // Non escape quote perché JSP li gestisce automaticamente
        return result;
    }
}

