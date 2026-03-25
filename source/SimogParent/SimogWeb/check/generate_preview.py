import re
import os

def load_properties(filepath):
    props = {}
    try:
        with open(filepath, 'r', encoding='ascii') as f:
            for line in f:
                line = line.strip()
                if not line or line.startswith('#'):
                    continue
                if '=' in line:
                    key, value = line.split('=', 1)
                    try:
                        value = value.encode('ascii').decode('unicode_escape')
                    except Exception as e:
                        print(f"Error decoding value for key {key}: {e}")
                    props[key.strip()] = value.strip()
    except Exception as e:
        print(f"Error loading properties: {e}")
    return props

def process_text(content, props):
    # Replace utils:message tags
    def replace_message(match):
        key = match.group(1).strip()
        return props.get(key, f"???{key}???")
    
    content = re.sub(r'<utils:message\s+key="([^"]+)"\s*/>', replace_message, content)
    
    # Replace specific JSP expressions
    # Replace specific JSP expressions using regex to handle whitespace
    content = re.sub(r'<%=\s*request\.getContextPath\(\)\s*%>', '.', content)
    content = re.sub(r'<%=\s*lang\s*%>', 'ar', content)
    content = re.sub(r'<%=\s*dir\s*%>', 'rtl', content)
    content = re.sub(r'<%=\s*SimogProperties\.getInstance\(\)\.getAmbiente\(\)\s*%>', 'COLLAUDO', content)
    content = re.sub(r'<%=\s*ParametriServlet\.FIELD_NAME_LOGIN\s*%>', 'j_username', content)
    content = re.sub(r'<%=\s*ParametriServlet\.FIELD_NAME_PASS\s*%>', 'j_password', content)
    
    # Replace JS variables
    if 'dettaglio.erroreCodiceFiscale' in props:
        content = content.replace('<%= msgErroreCF %>', props['dettaglio.erroreCodiceFiscale'])
    if 'avviso.digitarePassword' in props:
        content = content.replace('<%= msgPassword %>', props['avviso.digitarePassword'])
    if 'avviso.popupBloccato' in props:
        content = content.replace('<%= msgPopup %>', props['avviso.popupBloccato'])
        
    # Remove JSP directives (except includes which we handle separately)
    content = re.sub(r'<%@\s*page\s+.*?%>', '', content)
    content = re.sub(r'<%@\s*taglib\s+.*?%>', '', content)
    content = re.sub(r'<%@\s*import\s+.*?%>', '', content)
    
    # Remove Java code blocks
    content = re.sub(r'<%[^=].*?%>', '', content, flags=re.DOTALL)
    
    return content

def main():
    base_dir = r'c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb'
    props_file = os.path.join(base_dir, 'src', 'messages_ar.properties')
    header_file = os.path.join(base_dir, 'WebContent', 'include', 'newbasicHeader.inc')
    login_file = os.path.join(base_dir, 'WebContent', 'login.jsp')
    footer_file = os.path.join(base_dir, 'WebContent', 'include', 'newfooter.inc')
    output_file = os.path.join(base_dir, 'WebContent', 'preview_login.html')
    
    print("Loading properties...")
    props = load_properties(props_file)
    
    print("Reading files...")
    with open(header_file, 'r', encoding='utf-8') as f:
        header_content = f.read()
    with open(login_file, 'r', encoding='utf-8') as f:
        login_content = f.read()
    with open(footer_file, 'r', encoding='utf-8') as f:
        footer_content = f.read()
        
    print("Processing...")
    
    # Process individual files first
    header_processed = process_text(header_content, props)
    footer_processed = process_text(footer_content, props)
    
    # Process login content, injecting header and footer
    # We replace the include directives with the actual processed content
    
    # Handle header include
    # Regex for <%@ include file="include/newbasicHeader.inc" %>
    # Note: the path in include might vary slightly (e.g. leading slash), so we match loosely
    login_content_step1 = re.sub(r'<%@\s*include\s+file=".*?newbasicHeader.inc"\s*%>', lambda m: header_processed, login_content)
    
    # Handle footer include
    login_content_step2 = re.sub(r'<%@\s*include\s+file=".*?newfooter.inc"\s*%>', lambda m: footer_processed, login_content_step1)
    
    # Now process the rest of login content (replacing remaining JSP tags)
    final_html = process_text(login_content_step2, props)
    
    # Final cleanup: Remove any double blank lines
    final_html = re.sub(r'\n\s*\n', '\n', final_html)
    
    print(f"Writing to {output_file}...")
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(final_html)
    
    print("Done.")

if __name__ == "__main__":
    main()
