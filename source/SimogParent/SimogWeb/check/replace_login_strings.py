import os

def replace_strings():
    file_path = r'c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb\WebContent\login.jsp'
    
    try:
        # Read with utf-8 first, if fails try latin-1 (common for legacy JSP)
        # But we know we saved it as UTF-8 in previous steps via PowerShell?
        # Let's try utf-8 first.
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()
        except UnicodeDecodeError:
             with open(file_path, 'r', encoding='latin-1') as f:
                content = f.read()
        
        # Replacements
        # 1. Risposte ai quesiti frequenti
        # It's inside an anchor tag.
        # <a ...>Risposte ai quesiti frequenti</a>
        content = content.replace('>Risposte ai quesiti frequenti</a>', '><utils:message key="login.faq" /></a>')
        
        # 2. Per l'accesso al servizio...
        # It might have encoding issues in the file if it was ISO-8859-1.
        # "Per l'accesso al servizio  necessario identificarsi"
        # We should try to match a substring or regex to be safe.
        # "Per l'accesso al servizio" is safe enough?
        # Let's try to find the line.
        
        if "Per l'accesso al servizio" in content:
             # Find the full paragraph to replace safely
             # <p>Per l'accesso al servizio ... identificarsi</p>
             # Regex might be better
             import re
             content = re.sub(r'<p>Per l\'accesso al servizio.*?identificarsi</p>', '<p><utils:message key="login.identificarsi" /></p>', content, flags=re.DOTALL)
        else:
            print("Warning: Could not find 'Per l'accesso al servizio' string.")

        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
            
        print("Successfully replaced strings in login.jsp")
        
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    replace_strings()
