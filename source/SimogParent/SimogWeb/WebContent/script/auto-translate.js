/**
 * SIMOG - Traduzione Automatica IT → AR
 * 
 * Traduce automaticamente il testo italiano residuo SOLO quando la lingua è arabo (lang="ar"),
 * con gestione sicura dei pulsanti e supporto per contenuto dinamico (JSF/AJAX).
 * 
 * Versione: 4.0
 * Compatibile con: Browser moderni (IE11+, Chrome, Firefox, Edge)
 */

(function () {
    'use strict';

    /* ==============================
       CONFIG
    ============================== */
    var TRANSLATE_API = 'https://translate.googleapis.com/translate_a/single';

    // Mappatura controllata pulsanti (UI)
    var BUTTON_MAP = {
        'Cerca': 'بحث',
        'Salva': 'حفظ',
        'Conferma': 'تأكيد',
        'Annulla': 'إلغاء',
        'Accedi': 'دخول',
        'Entrata': 'دخول',
        'Invia': 'إرسال',
        'Chiudi': 'إغلاق',
        'Procedi': 'متابعة'
    };

    // Cache semplice (compatibile IE11) - usa oggetto invece di Map
    var cache = {};
    var debounceTimer = null;

    /* ==============================
       UTILS
    ============================== */
    
    /**
     * Verifica se la pagina è in lingua araba
     * @returns {boolean}
     */
    function isArabicPage() {
        var html = document.documentElement;
        var lang = html.getAttribute('lang');
        var xmlLang = html.getAttribute('xml:lang');
        
        if (lang && lang.toLowerCase().indexOf('ar') === 0) return true;
        if (xmlLang && xmlLang.toLowerCase().indexOf('ar') === 0) return true;
        
        // fallback: parametro URL (compatibile IE11 - usa indexOf invece di includes)
        if (location.search.indexOf('locale=ar') !== -1) return true;
        
        // fallback: selettore lingua attiva
        var activeLang = document.querySelector('.lang-active');
        if (activeLang && activeLang.textContent.indexOf('العربية') !== -1) return true;
        
        return false;
    }

    /**
     * Verifica se il testo contiene caratteri arabi
     * @param {string} text
     * @returns {boolean}
     */
    function isArabicText(text) {
        return /[\u0600-\u06FF]/.test(text);
    }

    /**
     * Verifica se un nodo deve essere saltato nella traduzione
     * @param {Node} node
     * @returns {boolean}
     */
    function shouldSkipNode(node) {
        if (!node || !node.textContent) return true;
        
        var parent = node.parentElement;
        if (!parent) return true;
        
        // Salta elementi con classe notranslate
        if (parent.classList && parent.classList.contains('notranslate')) return true;
        // Fallback per IE11 (classList potrebbe non essere supportato)
        if (parent.className && parent.className.indexOf('notranslate') !== -1) return true;
        
        // Salta se Google Translate ha già tradotto questo elemento
        if (parent.classList && parent.classList.contains('goog-te-banner-frame')) return true;
        if (parent.className && parent.className.indexOf('goog-te-banner-frame') !== -1) return true;
        
        var tag = parent.tagName;
        // Salta tag che non devono essere tradotti (compatibile IE11 - usa indexOf invece di includes)
        var skipTags = ['SCRIPT', 'STYLE', 'NOSCRIPT', 'INPUT', 'TEXTAREA', 'SELECT', 'OPTION'];
        for (var i = 0; i < skipTags.length; i++) {
            if (skipTags[i] === tag) return true;
        }
        
        // Salta testo che sembra solo numeri/punteggiatura
        if (/^[\d\s.,:/()\-]+$/.test(node.textContent)) return true;
        
        return false;
    }

    /* ==============================
       TEXT TRANSLATION (FORZATA)
    ============================== */
    
    /**
     * Raccoglie tutti i nodi di testo traducibili
     * @returns {Array<Node>}
     */
    function collectTextNodes() {
        var nodes = [];
        var allElements = document.querySelectorAll('*');
        
        // Ottimizzazione: limita la ricerca a elementi visibili e non già tradotti
        for (var i = 0; i < allElements.length; i++) {
            var el = allElements[i];
            // Salta se l'elemento è nascosto o già tradotto da Google Translate
            if (el.style && el.style.display === 'none') continue;
            if (el.classList && el.classList.contains('goog-te-banner-frame')) continue;
            
            var childNodes = el.childNodes;
            for (var j = 0; j < childNodes.length; j++) {
                var n = childNodes[j];
                if (n.nodeType === 3) { // Node.TEXT_NODE = 3 (compatibile IE11)
                    var text = n.textContent ? n.textContent.trim() : '';
                    if (!text) continue;
                    if (isArabicText(text)) continue;
                    if (shouldSkipNode(n)) continue;
                    nodes.push(n);
                }
            }
        }
        
        return nodes;
    }

    /**
     * Traduce un batch di testi usando l'API di Google Translate
     * @param {Array<string>} texts
     * @param {Function} callback
     */
    function translateBatch(texts, callback) {
        if (!texts || texts.length === 0) {
            if (callback) callback();
            return;
        }
        
        // Limita la dimensione del batch per evitare URL troppo lunghi
        var MAX_BATCH_SIZE = 50;
        if (texts.length > MAX_BATCH_SIZE) {
            // Processa in batch più piccoli
            var batch = texts.slice(0, MAX_BATCH_SIZE);
            var remaining = texts.slice(MAX_BATCH_SIZE);
            
            translateBatch(batch, function() {
                translateBatch(remaining, callback);
            });
            return;
        }
        
        // Usa un separatore unico per evitare problemi con split
        var SEPARATOR = '\u0001'; // Carattere di controllo raro
        var joined = texts.join(SEPARATOR);
        var url = TRANSLATE_API + '?client=gtx&sl=it&tl=ar&dt=t&q=' + encodeURIComponent(joined);
        
        // Usa fetch se disponibile, altrimenti XMLHttpRequest
        if (typeof fetch !== 'undefined') {
            fetch(url)
                .then(function(response) {
                    if (!response.ok) {
                        throw new Error('HTTP error! status: ' + response.status);
                    }
                    return response.json();
                })
                .then(function(data) {
                    try {
                        if (!data || !data[0]) {
                            throw new Error('Risposta API non valida');
                        }
                        
                        // Estrae le traduzioni
                        var translated = [];
                        for (var i = 0; i < data[0].length; i++) {
                            if (data[0][i] && data[0][i][0]) {
                                translated.push(data[0][i][0]);
                            }
                        }
                        var translatedText = translated.join('');
                        var translatedArray = translatedText.split(SEPARATOR);
                        
                        // Salva in cache
                        for (var j = 0; j < texts.length; j++) {
                            if (translatedArray[j]) {
                                cache[texts[j]] = translatedArray[j];
                            }
                        }
                        
                        if (callback) callback();
                    } catch (e) {
                        console.error('Errore parsing traduzione SIMOG', e);
                        if (callback) callback();
                    }
                })
                .catch(function(e) {
                    console.error('Errore traduzione SIMOG', e);
                    if (callback) callback();
                });
        } else {
            // Fallback per browser più vecchi (IE11)
            var xhr = new XMLHttpRequest();
            xhr.open('GET', url, true);
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        try {
                            var data = JSON.parse(xhr.responseText);
                            if (!data || !data[0]) {
                                throw new Error('Risposta API non valida');
                            }
                            
                            // Estrae le traduzioni
                            var translated = [];
                            for (var i = 0; i < data[0].length; i++) {
                                if (data[0][i] && data[0][i][0]) {
                                    translated.push(data[0][i][0]);
                                }
                            }
                            var translatedText = translated.join('');
                            var translatedArray = translatedText.split(SEPARATOR);
                            
                            // Salva in cache
                            for (var j = 0; j < texts.length; j++) {
                                if (translatedArray[j]) {
                                    cache[texts[j]] = translatedArray[j];
                                }
                            }
                            
                            if (callback) callback();
                        } catch (e) {
                            console.error('Errore parsing traduzione SIMOG', e);
                            if (callback) callback();
                        }
                    } else {
                        console.error('Errore HTTP traduzione SIMOG: ' + xhr.status);
                        if (callback) callback();
                    }
                }
            };
            xhr.onerror = function() {
                console.error('Errore network traduzione SIMOG');
                if (callback) callback();
            };
            xhr.send();
        }
    }

    /**
     * Processa la traduzione di tutti i nodi di testo
     */
    function processTextTranslation() {
        if (!isArabicPage()) return;
        
        // Verifica se Google Translate è già attivo e sta traducendo
        if (document.querySelector('.goog-te-banner-frame') || 
            document.querySelector('.skiptranslate')) {
            // Google Translate è attivo, non interferire
            return;
        }
        
        var nodes = collectTextNodes();
        var texts = [];
        var textSet = {}; // Oggetto invece di Set (compatibile IE11)
        
        // Raccoglie testi unici non ancora in cache
        for (var i = 0; i < nodes.length; i++) {
            var n = nodes[i];
            var original = n.textContent ? n.textContent.trim() : '';
            if (original && !textSet[original] && !cache[original]) {
                texts.push(original);
                textSet[original] = true;
            }
        }
        
        if (texts.length === 0) {
            // Applica traduzioni già in cache
            applyCachedTranslations(nodes);
            return;
        }
        
        // Traduce nuovi testi
        translateBatch(texts, function() {
            applyCachedTranslations(nodes);
        });
    }

    /**
     * Applica le traduzioni dalla cache ai nodi
     * @param {Array<Node>} nodes
     */
    function applyCachedTranslations(nodes) {
        for (var i = 0; i < nodes.length; i++) {
            var n = nodes[i];
            var original = n.textContent ? n.textContent.trim() : '';
            if (original && cache[original]) {
                n.textContent = cache[original];
                var parent = n.parentElement;
                if (parent) {
                    parent.setAttribute('dir', 'rtl');
                }
            }
        }
    }

    /* ==============================
       BUTTON TRANSLATION (SAFE)
    ============================== */
    
    /**
     * Traduce i pulsanti usando la mappatura controllata
     */
    function translateButtons() {
        if (!isArabicPage()) return;
        
        // Verifica se Google Translate è già attivo
        if (document.querySelector('.goog-te-banner-frame') || 
            document.querySelector('.skiptranslate')) {
            return; // Non interferire con Google Translate
        }
        
        var buttons = document.querySelectorAll('input[type="submit"], input[type="button"], button');
        for (var i = 0; i < buttons.length; i++) {
            var btn = buttons[i];
            // Salta se già tradotto o protetto
            if (btn.classList && btn.classList.contains('notranslate')) continue;
            if (btn.className && btn.className.indexOf('notranslate') !== -1) continue;
            
            var value = btn.value ? btn.value.trim() : (btn.textContent ? btn.textContent.trim() : '');
            if (!value) continue;
            
            // Salta se già in arabo
            if (isArabicText(value)) continue;
            
            if (BUTTON_MAP[value]) {
                if (btn.value !== undefined && btn.value !== null) {
                    btn.value = BUTTON_MAP[value];
                } else {
                    btn.textContent = BUTTON_MAP[value];
                }
                btn.setAttribute('dir', 'rtl');
            }
        }
    }

    /* ==============================
       DEBOUNCE + OBSERVER
    ============================== */
    
    /**
     * Debounce per evitare troppe chiamate durante modifiche DOM
     */
    function debounceProcess() {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(function() {
            processTextTranslation();
            translateButtons();
        }, 800);
    }

    /**
     * Inizializza il sistema di traduzione automatica
     */
    function init() {
        // Verifica se la pagina è in arabo
        if (!isArabicPage()) {
            return; // Non fare nulla se non è una pagina araba
        }
        
        // Prima esecuzione dopo un breve delay per permettere al DOM di caricarsi
        setTimeout(function() {
            debounceProcess();
        }, 500);
        
        // Observer per DOM dinamico (JSF / AJAX)
        if (typeof MutationObserver !== 'undefined') {
            var observer = new MutationObserver(function(mutations) {
                var shouldProcess = false;
                mutations.forEach(function(mutation) {
                    if (mutation.type === 'childList' && mutation.addedNodes.length > 0) {
                        shouldProcess = true;
                    }
                });
                
                if (shouldProcess) {
                    debounceProcess();
                }
            });
            
            // Inizia l'osservazione quando il body è disponibile
            if (document.body) {
                observer.observe(document.body, {
                    childList: true,
                    subtree: true
                });
            } else {
                // Attendi che il body sia disponibile
                if (document.readyState === 'loading') {
                    document.addEventListener('DOMContentLoaded', function() {
                        if (document.body) {
                            observer.observe(document.body, {
                                childList: true,
                                subtree: true
                            });
                        }
                    });
                }
            }
        }
    }

    // Avvia l'inizializzazione quando lo script è caricato
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        // DOM già caricato
        init();
    }
})();

