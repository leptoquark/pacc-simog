<%-- Google Translate Auto-Translation for Dynamic Content --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Get current locale from session --%>
<c:set var="userLocale" value="${sessionScope.userLocale}" />

<%-- Hidden container for Google Translate widget --%>
<div id="google_translate_element" style="display:none;"></div>

<script type="text/javascript">
// Google Translate initialization
function googleTranslateElementInit() {
    new google.translate.TranslateElement({
        pageLanguage: 'it',
        includedLanguages: 'ar',
        layout: google.translate.TranslateElement.InlineLayout.SIMPLE,
        autoDisplay: false
    }, 'google_translate_element');
}

// Auto-trigger translation to Arabic when locale is Arabic
function autoTranslateToArabic() {
    // Check if we're in Arabic locale
    var isArabicLocale = '<c:out value="${userLocale.language}" default="it"/>' === 'ar';
    
    if (isArabicLocale) {
        // Wait for Google Translate to load, then trigger Arabic translation
        var checkInterval = setInterval(function() {
            var selectElement = document.querySelector('.goog-te-combo');
            if (selectElement) {
                // Find Arabic option and select it
                for (var i = 0; i < selectElement.options.length; i++) {
                    if (selectElement.options[i].value === 'ar') {
                        selectElement.selectedIndex = i;
                        selectElement.dispatchEvent(new Event('change'));
                        clearInterval(checkInterval);
                        break;
                    }
                }
            }
        }, 500);
        
        // Stop checking after 10 seconds to avoid infinite loop
        setTimeout(function() {
            clearInterval(checkInterval);
        }, 10000);
    }
}

// Run auto-translate after page load
if (document.readyState === 'complete') {
    autoTranslateToArabic();
} else {
    window.addEventListener('load', autoTranslateToArabic);
}
</script>

<%-- Load Google Translate script --%>
<script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>

<style>
/* Hide Google Translate bar but keep functionality */
.goog-te-banner-frame.skiptranslate { display: none !important; }
body { top: 0px !important; }

/* Optional: Mark elements that should NOT be translated (already translated) */
.notranslate { /* Content with this class won't be re-translated */ }

/* Style for translated content indicator (optional) */
.goog-te-gadget { display: none; }

/* CRITICAL: Protect all form inputs from Google Translate to prevent data corruption */
/* This prevents Google Translate from modifying form values before submission */
form input[type="text"],
form input[type="number"],
form input[type="email"],
form input[type="tel"],
form input[type="password"],
form input[type="date"],
form input[type="time"],
form textarea,
form select {
    /* Force notranslate on all form inputs to protect data integrity */
    /* Note: This CSS selector alone may not be enough - inputs should also have class="notranslate" */
}

/* Protect form elements explicitly */
form input.notranslate,
form textarea.notranslate,
form select.notranslate {
    /* Explicitly protected form elements */
}
</style>

<script type="text/javascript">
/**
 * CRITICAL: Protect all form inputs from Google Translate
 * This script adds 'notranslate' class to all form inputs to prevent
 * Google Translate from modifying form values before submission.
 * 
 * This is essential to prevent data corruption in database inserts.
 */
(function() {
    function protectFormInputs() {
        // Protect all input fields
        var inputs = document.querySelectorAll('form input[type="text"], form input[type="number"], form input[type="email"], form input[type="tel"], form input[type="date"], form input[type="time"]');
        for (var i = 0; i < inputs.length; i++) {
            if (!inputs[i].classList.contains('notranslate')) {
                inputs[i].classList.add('notranslate');
            }
        }
        
        // Protect all textareas
        var textareas = document.querySelectorAll('form textarea');
        for (var i = 0; i < textareas.length; i++) {
            if (!textareas[i].classList.contains('notranslate')) {
                textareas[i].classList.add('notranslate');
            }
        }
        
        // Protect all select elements
        var selects = document.querySelectorAll('form select');
        for (var i = 0; i < selects.length; i++) {
            if (!selects[i].classList.contains('notranslate')) {
                selects[i].classList.add('notranslate');
            }
        }
        
        // Protect option values (critical for select dropdowns)
        var options = document.querySelectorAll('form select option');
        for (var i = 0; i < options.length; i++) {
            if (!options[i].classList.contains('notranslate')) {
                options[i].classList.add('notranslate');
            }
        }
    }
    
    // Run immediately
    protectFormInputs();
    
    // Run after DOM is fully loaded
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', protectFormInputs);
    }
    
    // Run after page load (in case forms are added dynamically)
    window.addEventListener('load', protectFormInputs);
    
    // Run after Google Translate modifies the page
    // Monitor for Google Translate DOM changes
    var observer = new MutationObserver(function(mutations) {
        var shouldProtect = false;
        mutations.forEach(function(mutation) {
            if (mutation.type === 'childList' || mutation.type === 'attributes') {
                // Check if Google Translate is active
                if (document.querySelector('.goog-te-combo') || 
                    document.querySelector('.skiptranslate')) {
                    shouldProtect = true;
                }
            }
        });
        if (shouldProtect) {
            // Small delay to let Google Translate finish
            setTimeout(protectFormInputs, 100);
        }
    });
    
    // Start observing
    observer.observe(document.body, {
        childList: true,
        subtree: true,
        attributes: true,
        attributeFilter: ['class']
    });
})();
</script>
