/**
 * Sistema di Internazionalizzazione JavaScript per SIMOG
 * 
 * Questo sistema permette di tradurre i messaggi JavaScript in base alla locale corrente.
 * I messaggi vengono inizializzati dal server tramite variabili JavaScript nei JSP.
 * 
 * Utilizzo:
 *   i18n.init(messages); // Inizializzazione con messaggi dal server
 *   alert(i18n.get('error.saveConfirm')); // Recupera messaggio tradotto
 *   if (i18n.confirm('error.cancelConfirm')) { ... } // Confirm tradotto
 */

var i18n = {
    messages: {},
    
    /**
     * Inizializza il sistema con i messaggi tradotti dal server
     * @param {Object} messages - Oggetto con chiavi e valori tradotti
     */
    init: function(messages) {
        if (messages && typeof messages === 'object') {
            this.messages = messages;
        }
    },
    
    /**
     * Recupera un messaggio tradotto
     * @param {String} key - Chiave del messaggio
     * @param {Object} params - Parametri opzionali per sostituzione (es: {name: "valore"})
     * @returns {String} Messaggio tradotto o la chiave se non trovato
     */
    get: function(key, params) {
        var message = this.messages[key] || key;
        
        // Sostituzione parametri se forniti
        if (params && typeof params === 'object') {
            for (var param in params) {
                if (params.hasOwnProperty(param)) {
                    message = message.replace(new RegExp('\\{' + param + '\\}', 'g'), params[param]);
                }
            }
        }
        
        return message;
    },
    
    /**
     * Mostra un alert con messaggio tradotto
     * @param {String} key - Chiave del messaggio
     * @param {Object} params - Parametri opzionali
     */
    alert: function(key, params) {
        alert(this.get(key, params));
    },
    
    /**
     * Mostra un confirm con messaggio tradotto
     * @param {String} key - Chiave del messaggio
     * @param {Object} params - Parametri opzionali
     * @returns {Boolean} true se confermato, false altrimenti
     */
    confirm: function(key, params) {
        return confirm(this.get(key, params));
    },
    
    /**
     * Mostra un prompt con messaggio tradotto
     * @param {String} key - Chiave del messaggio
     * @param {String} defaultValue - Valore di default
     * @param {Object} params - Parametri opzionali
     * @returns {String|null} Valore inserito o null se annullato
     */
    prompt: function(key, defaultValue, params) {
        return prompt(this.get(key, params), defaultValue || '');
    }
};

