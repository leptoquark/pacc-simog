package it.avlp.simog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Helper class per l'accesso ai messaggi internazionalizzati
 * 
 * Soluzione definitiva per il caricamento di file properties UTF-8:
 * - Caricamento da file system esterno (/opt/SIMOG/) con fallback a classpath
 * - Cache dei bundle per performance
 * - Supporto UTF-8 completo
 * - Fallback multipli
 * - Thread-safe
 * - Gestione corretta delle risorse
 */
public class MessageHelper {
    
    private static final String BUNDLE_NAME = "messages";
    private static final String DEFAULT_LOCALE = "it";
    
    // Path esterno per file properties (configurabile)
    private static final String EXTERNAL_PROPERTIES_PATH = "/opt/SIMOG/";
    
    // Cache dei bundle caricati per performance (thread-safe)
    private static final Map<String, ResourceBundle> bundleCache = new HashMap<String, ResourceBundle>();
    private static final Object cacheLock = new Object();
    
    /**
     * Ottiene il messaggio tradotto per la chiave specificata
     * 
     * @param request La richiesta HTTP
     * @param key La chiave del messaggio
     * @return Il messaggio tradotto
     */
    public static String getMessage(HttpServletRequest request, String key) {
        Locale locale = getLocale(request);
        return getMessage(locale, key);
    }
    
    /**
     * Ottiene il messaggio tradotto per la chiave specificata con parametri
     * 
     * @param request La richiesta HTTP
     * @param key La chiave del messaggio
     * @param params Parametri da sostituire nel messaggio (usando {0}, {1}, etc.)
     * @return Il messaggio tradotto con parametri sostituiti
     */
    public static String getMessage(HttpServletRequest request, String key, Object... params) {
        String message = getMessage(request, key);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                message = message.replace("{" + i + "}", String.valueOf(params[i]));
            }
        }
        return message;
    }
    
    /**
     * Ottiene il messaggio tradotto per la locale specificata
     * 
     * @param locale La locale da usare
     * @param key La chiave del messaggio
     * @return Il messaggio tradotto
     */
    public static String getMessage(Locale locale, String key) {
        if (locale == null) {
            locale = new Locale(DEFAULT_LOCALE);
        }
        
        if (key == null || key.trim().isEmpty()) {
            return key;
        }
        
        if (isDebugEnabled()) {
            System.err.println("[MessageHelper] getMessage - Locale: " + locale.getLanguage() + ", Key: " + key);
        }
        
        try {
            // Usa il classloader del thread corrente per trovare i file properties
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = MessageHelper.class.getClassLoader();
            }
            
            // Prova prima con il locale specificato
            String bundleName = BUNDLE_NAME + "_" + locale.getLanguage();
            if (isDebugEnabled()) {
                System.err.println("[MessageHelper] Tentativo caricamento bundle: " + bundleName);
            }
            ResourceBundle bundle = getOrLoadBundle(classLoader, bundleName);
            
            if (bundle != null) {
                try {
                    String message = bundle.getString(key);
                    if (message != null && !message.equals(key)) {
                        if (isDebugEnabled()) {
                            System.err.println("[MessageHelper] Messaggio trovato in bundle " + bundleName + ": " + 
                                             (message.length() > 50 ? message.substring(0, 50) + "..." : message));
                        }
                        return message;
                    } else {
                        if (isDebugEnabled()) {
                            System.err.println("[MessageHelper] Messaggio non trovato o uguale alla chiave in bundle " + bundleName);
                        }
                    }
                } catch (java.util.MissingResourceException e) {
                    // Chiave non trovata nel bundle corrente, continua
                    if (isDebugEnabled()) {
                        System.err.println("[MessageHelper] Chiave " + key + " non trovata in bundle " + bundleName);
                    }
                }
            } else {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Bundle " + bundleName + " non caricato");
                }
            }

            // Se la chiave manca nel bundle primario (es. file esterno non aggiornato),
            // prova il bundle omonimo direttamente dal classpath dell'applicazione.
            ResourceBundle classpathBundle = loadBundleFromClasspathOnly(classLoader, bundleName);
            if (classpathBundle != null) {
                try {
                    String message = classpathBundle.getString(key);
                    if (message != null && !message.equals(key)) {
                        if (isDebugEnabled()) {
                            System.err.println("[MessageHelper] Messaggio trovato in classpath bundle " + bundleName);
                        }
                        return message;
                    }
                } catch (java.util.MissingResourceException e) {
                    if (isDebugEnabled()) {
                        System.err.println("[MessageHelper] Chiave " + key + " non trovata nel classpath bundle " + bundleName);
                    }
                }
            }
            
            // Se non trovato, prova con il bundle di default
            if (!locale.getLanguage().equals(DEFAULT_LOCALE)) {
                bundleName = BUNDLE_NAME + "_" + DEFAULT_LOCALE;
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Fallback a bundle default: " + bundleName);
                }
                bundle = getOrLoadBundle(classLoader, bundleName);
                if (bundle != null) {
                    try {
                        String message = bundle.getString(key);
                        if (isDebugEnabled()) {
                            System.err.println("[MessageHelper] Messaggio trovato in bundle default: " + 
                                             (message.length() > 50 ? message.substring(0, 50) + "..." : message));
                        }
                        return message;
                    } catch (java.util.MissingResourceException e) {
                        if (isDebugEnabled()) {
                            System.err.println("[MessageHelper] Chiave " + key + " non trovata nemmeno in bundle default");
                        }
                    }
                }

                // Ultimo fallback: classpath bundle di default
                classpathBundle = loadBundleFromClasspathOnly(classLoader, bundleName);
                if (classpathBundle != null) {
                    try {
                        String message = classpathBundle.getString(key);
                        if (isDebugEnabled()) {
                            System.err.println("[MessageHelper] Messaggio trovato in classpath bundle default: " +
                                             (message.length() > 50 ? message.substring(0, 50) + "..." : message));
                        }
                        return message;
                    } catch (java.util.MissingResourceException e) {
                        if (isDebugEnabled()) {
                            System.err.println("[MessageHelper] Chiave " + key + " non trovata nemmeno nel classpath bundle default");
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Log dell'errore per debug (solo se abilitato)
            if (isDebugEnabled()) {
                System.err.println("[MessageHelper] Errore nel caricamento del ResourceBundle per locale " + locale + 
                                 " e chiave " + key + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        // Se il messaggio non viene trovato, ritorna la chiave
        if (isDebugEnabled()) {
            System.err.println("[MessageHelper] Ritorno chiave originale: " + key);
        }
        return key;
    }
    
    /**
     * Ottiene un bundle dalla cache o lo carica se non presente
     * Ottimizzato per JBoss 5
     */
    private static ResourceBundle getOrLoadBundle(ClassLoader classLoader, String bundleName) {
        // Controlla la cache
        synchronized (cacheLock) {
            ResourceBundle cached = bundleCache.get(bundleName);
            if (cached != null) {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Bundle dalla cache: " + bundleName);
                }
                return cached;
            }
        }
        
        // Carica il bundle (ottimizzato per JBoss 5)
        ResourceBundle bundle = loadBundleDefinitive(classLoader, bundleName);
        
        // Salva in cache solo se caricato con successo
        if (bundle != null) {
            synchronized (cacheLock) {
                bundleCache.put(bundleName, bundle);
            }
        } else {
            // Se non caricato, salva null in cache per evitare tentativi ripetuti
            // ma solo per un breve periodo (JBoss 5 potrebbe avere problemi temporanei)
            synchronized (cacheLock) {
                // Non salvare null in cache permanente, lascia che riprovi
                // Questo aiuta con problemi temporanei di classloader in JBoss 5
            }
        }
        
        return bundle;
    }
    
    /**
     * Verifica se il debug è abilitato (può essere configurato)
     */
    private static boolean isDebugEnabled() {
        // Per ora sempre true, può essere configurato via system property
        String debug = System.getProperty("simog.i18n.debug", "true");
        return "true".equalsIgnoreCase(debug);
    }
    
    /**
     * SOLUZIONE DEFINITIVA PER JBOSS 5: Carica un ResourceBundle usando il classloader specificato
     * 
     * Ottimizzato per JBoss 5 che ha un sistema di classloader gerarchico particolare.
     * 
     * Strategia multi-fallback per garantire il caricamento corretto:
     * 1. Prova da file system esterno (/opt/SIMOG/) - PRIORITÀ
     * 2. Prova con InputStream + UTF-8 usando classloader del thread (JBoss 5)
     * 3. Prova con classloader della classe (fallback per JBoss 5)
     * 4. Prova con ResourceBundle.getBundle() standard (ultimo fallback)
     */
    private static ResourceBundle loadBundleDefinitive(ClassLoader classLoader, String bundleName) {
        String resourcePath = bundleName.replace('.', '/') + ".properties";
        String fileName = bundleName + ".properties";
        
        // ===== PRIORITÀ 1: Carica da file system esterno (/opt/SIMOG/) =====
        File externalFile = new File(EXTERNAL_PROPERTIES_PATH + fileName);
        if (externalFile.exists() && externalFile.isFile() && externalFile.canRead()) {
            FileInputStream fis = null;
            InputStreamReader reader = null;
            try {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Caricamento da file system esterno: " + 
                                     externalFile.getAbsolutePath());
                }
                
                fis = new FileInputStream(externalFile);
                reader = new InputStreamReader(fis, "UTF-8");
                PropertyResourceBundle bundle = new PropertyResourceBundle(reader);
                
                // Verifica che il bundle non sia vuoto
                java.util.Enumeration<String> keys = bundle.getKeys();
                if (keys.hasMoreElements()) {
                    int keyCount = 0;
                    while (keys.hasMoreElements()) {
                        keys.nextElement();
                        keyCount++;
                    }
                    if (isDebugEnabled()) {
                        System.err.println("[MessageHelper] Bundle caricato da file system: " + bundleName + 
                                         " con " + keyCount + " chiavi (path: " + externalFile.getAbsolutePath() + ")");
                    }
                    return bundle;
                } else {
                    if (isDebugEnabled()) {
                        System.err.println("[MessageHelper] Bundle vuoto da file system: " + bundleName);
                    }
                }
            } catch (Exception e) {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Errore caricamento da file system " + 
                                     externalFile.getAbsolutePath() + ": " + e.getMessage());
                }
            } finally {
                // Chiudi le risorse correttamente
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        // Ignora
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception e) {
                        // Ignora
                    }
                }
            }
        } else {
            if (isDebugEnabled()) {
                System.err.println("[MessageHelper] File esterno non trovato o non accessibile: " + 
                                 externalFile.getAbsolutePath() + " - Fallback a classpath");
            }
        }
        
        // Lista di classloader da provare (ottimizzato per JBoss 5)
        ClassLoader[] classLoaders = new ClassLoader[] {
            classLoader,                                    // Classloader del thread (JBoss 5)
            MessageHelper.class.getClassLoader(),           // Classloader della classe
            Thread.currentThread().getContextClassLoader(), // Context classloader (se diverso)
            ClassLoader.getSystemClassLoader()              // System classloader
        };
        
        // ===== METODO PRINCIPALE: InputStream con UTF-8 =====
        // Prova con tutti i classloader disponibili (ottimizzato per JBoss 5)
        for (ClassLoader cl : classLoaders) {
            if (cl == null) continue;
            
            InputStream stream = null;
            InputStreamReader reader = null;
            try {
                // Verifica che il file esista
                java.net.URL resourceUrl = cl.getResource(resourcePath);
                if (resourceUrl == null) {
                    if (isDebugEnabled()) {
                        System.err.println("[MessageHelper] File non trovato con classloader " + 
                                         cl.getClass().getName() + ": " + resourcePath);
                    }
                    continue; // Prova il prossimo classloader
                }
                
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] File trovato: " + resourceUrl.toString() + 
                                     " (classloader: " + cl.getClass().getName() + ")");
                }
                
                stream = cl.getResourceAsStream(resourcePath);
                if (stream != null) {
                    // Usa UTF-8 per leggere i file properties (compatibile Java 1.6)
                    reader = new InputStreamReader(stream, "UTF-8");
                    PropertyResourceBundle bundle = new PropertyResourceBundle(reader);
                    
                    // Verifica che il bundle non sia vuoto
                    java.util.Enumeration<String> keys = bundle.getKeys();
                    if (keys.hasMoreElements()) {
                        int keyCount = 0;
                        while (keys.hasMoreElements()) {
                            keys.nextElement();
                            keyCount++;
                        }
                        if (isDebugEnabled()) {
                            System.err.println("[MessageHelper] Bundle caricato: " + bundleName + 
                                             " con " + keyCount + " chiavi (classloader: " + 
                                             cl.getClass().getName() + ")");
                        }
                        return bundle;
                    } else {
                        if (isDebugEnabled()) {
                            System.err.println("[MessageHelper] Bundle vuoto: " + bundleName);
                        }
                    }
                }
            } catch (Exception e) {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Errore con classloader " + cl.getClass().getName() + 
                                     " per " + bundleName + ": " + e.getMessage());
                }
            } finally {
                // Chiudi le risorse correttamente
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        // Ignora
                    }
                }
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Exception e) {
                        // Ignora
                    }
                }
            }
        }
        
        // ===== FALLBACK: ResourceBundle.getBundle() standard =====
        // Pulisce la cache prima di tentare (importante per JBoss 5)
        for (ClassLoader cl : classLoaders) {
            if (cl == null) continue;
            try {
                // Pulisce la cache per evitare problemi (JBoss 5 può avere cache particolari)
                ResourceBundle.clearCache(cl);
                ResourceBundle bundle = ResourceBundle.getBundle(bundleName, Locale.getDefault(), cl);
                if (bundle != null) {
                    // Verifica che il bundle non sia vuoto
                    try {
                        java.util.Enumeration<String> keys = bundle.getKeys();
                        if (keys.hasMoreElements()) {
                            if (isDebugEnabled()) {
                                System.err.println("[MessageHelper] Bundle caricato con ResourceBundle.getBundle(): " + 
                                                 bundleName + " (classloader: " + cl.getClass().getName() + ")");
                            }
                            return bundle;
                        }
                    } catch (Exception e) {
                        // Ignora
                    }
                }
            } catch (Exception e) {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Errore con ResourceBundle.getBundle() e classloader " + 
                                     cl.getClass().getName() + ": " + e.getMessage());
                }
            }
        }
        
        if (isDebugEnabled()) {
            System.err.println("[MessageHelper] Impossibile caricare bundle: " + bundleName + 
                             " con nessuno dei classloader disponibili");
        }
        return null;
    }

    /**
     * Carica un bundle solo dal classpath, ignorando il file system esterno.
     * Usato come fallback quando il file esterno esiste ma non contiene nuove chiavi.
     */
    private static ResourceBundle loadBundleFromClasspathOnly(ClassLoader classLoader, String bundleName) {
        String resourcePath = bundleName.replace('.', '/') + ".properties";

        ClassLoader[] classLoaders = new ClassLoader[] {
            classLoader,
            MessageHelper.class.getClassLoader(),
            Thread.currentThread().getContextClassLoader(),
            ClassLoader.getSystemClassLoader()
        };

        for (ClassLoader cl : classLoaders) {
            if (cl == null) continue;

            InputStream stream = null;
            InputStreamReader reader = null;
            try {
                stream = cl.getResourceAsStream(resourcePath);
                if (stream == null) {
                    continue;
                }

                reader = new InputStreamReader(stream, "UTF-8");
                PropertyResourceBundle bundle = new PropertyResourceBundle(reader);

                java.util.Enumeration<String> keys = bundle.getKeys();
                if (keys.hasMoreElements()) {
                    return bundle;
                }
            } catch (Exception e) {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Errore caricamento classpath-only per " + bundleName +
                                     " con classloader " + cl.getClass().getName() + ": " + e.getMessage());
                }
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        // Ignora
                    }
                }
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Exception e) {
                        // Ignora
                    }
                }
            }
        }

        return null;
    }
    
    /**
     * Pulisce la cache dei bundle (utile per ricaricare dopo modifiche)
     */
    public static void clearCache() {
        synchronized (cacheLock) {
            bundleCache.clear();
            // Pulisce anche la cache di ResourceBundle
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                if (classLoader != null) {
                    ResourceBundle.clearCache(classLoader);
                }
                ResourceBundle.clearCache(MessageHelper.class.getClassLoader());
            } catch (Exception e) {
                // Ignora
            }
        }
    }
    
    /**
     * Ottiene la locale dalla richiesta HTTP
     * 
     * Strategia di ricerca (in ordine di priorità):
     * 1. Parametro della richiesta (?locale=ar)
     * 2. Attributo della richiesta (impostato da LocaleFilter)
     * 3. Sessione (impostata da LocaleFilter)
     * 4. Default (italiano)
     * 
     * @param request La richiesta HTTP
     * @return La locale corrente
     */
    public static Locale getLocale(HttpServletRequest request) {
        if (request == null) {
            return new Locale(DEFAULT_LOCALE);
        }
        
        // 1. Controlla il parametro della richiesta (PRIORITÀ MASSIMA)
        String localeParam = request.getParameter("locale");
        if (localeParam != null && !localeParam.isEmpty()) {
            if ("ar".equalsIgnoreCase(localeParam)) {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Locale da parametro richiesta: ar");
                }
                return new Locale("ar");
            } else if ("it".equalsIgnoreCase(localeParam)) {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Locale da parametro richiesta: it");
                }
                return new Locale("it");
            }
        }
        
        // 2. Controlla l'attributo della richiesta (impostato da LocaleFilter)
        Locale requestLocale = (Locale) request.getAttribute("locale");
        if (requestLocale != null) {
            if (isDebugEnabled()) {
                System.err.println("[MessageHelper] Locale da attributo richiesta: " + requestLocale.getLanguage());
            }
            return requestLocale;
        }
        
        // 3. Controlla la sessione (impostata da LocaleFilter)
        HttpSession session = request.getSession(false);
        if (session != null) {
            Locale locale = (Locale) session.getAttribute("userLocale");
            if (locale != null) {
                if (isDebugEnabled()) {
                    System.err.println("[MessageHelper] Locale da sessione: " + locale.getLanguage());
                }
                return locale;
            }
        }
        
        // 4. Default: italiano
        if (isDebugEnabled()) {
            System.err.println("[MessageHelper] Locale default: " + DEFAULT_LOCALE);
        }
        return new Locale(DEFAULT_LOCALE);
    }
}

