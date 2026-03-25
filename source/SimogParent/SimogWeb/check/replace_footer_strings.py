import os

def replace_footer_strings():
    file_path = r'c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb\WebContent\include\newfooter.inc'
    
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
            
        # Replace Privacy-Cookies
        # title="Privacy-Cookies"
        content = content.replace('title="Privacy-Cookies"', 'title="<utils:message key=\\"footer.privacyCookies\\" />"')
        # >Privacy-Cookies</span>
        content = content.replace('>Privacy-Cookies</span>', '><utils:message key="footer.privacyCookies" /></span>')
        
        # Replace Accessibilità (Accessibilit&agrave;)
        # title="Accessibilit&agrave;"
        content = content.replace('title="Accessibilit&agrave;"', 'title="<utils:message key=\\"footer.accessibilita\\" />"')
        # >Accessibilit&agrave;</span>
        content = content.replace('>Accessibilit&agrave;</span>', '><utils:message key="footer.accessibilita" /></span>')
        
        with open(file_path, 'w', encoding='utf-8') as f:
            f.write(content)
            
        print("Successfully replaced strings in newfooter.inc")
        
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    replace_footer_strings()
