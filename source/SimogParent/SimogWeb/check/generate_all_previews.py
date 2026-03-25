import re
import os

def load_properties(filepath):
    props = {}
    try:
        with open(filepath, 'r', encoding='utf-8', errors='ignore') as f:
            for line in f:
                line = line.strip()
                if not line or line.startswith('#'):
                    continue
                if '=' in line:
                    key, value = line.split('=', 1)
                    try:
                        value = value.encode('utf-8').decode('unicode_escape')
                    except Exception:
                        pass
                    props[key.strip()] = value.strip()
    except Exception as e:
        print(f"Error loading properties: {e}")
    return props

def process_text(content, props):
    missing_keys = []
    
    def replace_message(match):
        key = match.group(1).strip()
        if key in props:
            return props[key]
        else:
            missing_keys.append(key)
            return f"???{key}???"
    
    content = re.sub(r'<utils:message\s+key=["\']([^"\']+)["\']\s*/>', replace_message, content, flags=re.DOTALL)
    
    # Remove JSP directives
    content = re.sub(r'<%@.*?%>', '', content, flags=re.DOTALL)
    content = re.sub(r'<%!?.*?%>', '', content, flags=re.DOTALL)
    content = re.sub(r'<%=.*?%>', '[dynamic]', content, flags=re.DOTALL)
    
    # Remove JSTL tags
    content = re.sub(r'<c:[\w]+.*?/>', '[condition]', content, flags=re.DOTALL)
    content = re.sub(r'<c:[\w]+.*?>.*?</c:[\w]+>', '[block]', content, flags=re.DOTALL)
    content = re.sub(r'<fmt:[\w]+.*?/>', '', content, flags=re.DOTALL)
    
    # Clean EL expressions
    content = re.sub(r'\$\{[^}]+\}', '[variable]', content, flags=re.DOTALL)
    
    return content, missing_keys

def generate_preview(jsp_path, output_path, base_dir, props):
    try:
        with open(jsp_path, 'r', encoding='utf-8', errors='ignore') as f:
            content = f.read()
        
        # Process includes
        def resolve_include(match):
            include_path = match.group(1)
            if include_path.startswith('/'):
                include_path = include_path[1:]
            full_path = os.path.join(base_dir, 'WebContent', include_path)
            if os.path.exists(full_path):
                with open(full_path, 'r', encoding='utf-8', errors='ignore') as inc_f:
                    inc_content = inc_f.read()
                processed, _ = process_text(inc_content, props)
                return processed
            return f"<!-- Include not found: {include_path} -->"
        
        content = re.sub(r'<%@\s*include\s+file="([^"]+)"\s*%>', resolve_include, content)
        content = re.sub(r'<jsp:include\s+.*page="([^"]+)".*?/>', resolve_include, content)
        
        final_html, missing = process_text(content, props)
        final_html = re.sub(r'\n\s*\n', '\n', final_html)
        
        with open(output_path, 'w', encoding='utf-8') as f:
            f.write(final_html)
        
        return missing
    except Exception as e:
        print(f"Error: {jsp_path}: {e}")
        return []

def find_all_jsp_files(base_dir):
    jsp_files = []
    web_content = os.path.join(base_dir, 'WebContent')
    for root, dirs, files in os.walk(web_content):
        # Skip WEB-INF
        if 'WEB-INF' in root:
            continue
        for f in files:
            if f.endswith('.jsp'):
                rel_path = os.path.relpath(os.path.join(root, f), base_dir)
                jsp_files.append(rel_path)
    return jsp_files

def main():
    base_dir = r'c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb'
    props_file = os.path.join(base_dir, 'src', 'messages_ar.properties')
    html_dir = os.path.join(base_dir, 'html')
    
    if not os.path.exists(html_dir):
        os.makedirs(html_dir)
    
    print("Loading properties...")
    props = load_properties(props_file)
    print(f"Loaded {len(props)} keys")
    
    print("\nFinding all JSP files...")
    jsp_files = find_all_jsp_files(base_dir)
    print(f"Found {len(jsp_files)} JSP files")
    
    all_missing = {}
    
    for jsp_rel_path in jsp_files:
        jsp_path = os.path.join(base_dir, jsp_rel_path)
        html_name = 'preview_' + os.path.basename(jsp_rel_path).replace('.jsp', '.html')
        output_path = os.path.join(html_dir, html_name)
        
        missing = generate_preview(jsp_path, output_path, base_dir, props)
        if missing:
            all_missing[jsp_rel_path] = missing
        print(f"Generated: {html_name} ({len(missing)} missing keys)")
    
    # Summary report
    print("\n" + "="*60)
    print("TRANSLATION AUDIT REPORT")
    print("="*60)
    
    if all_missing:
        print(f"\nFiles with missing translations: {len(all_missing)}")
        for file, keys in sorted(all_missing.items()):
            print(f"\n{file}:")
            for k in keys[:5]:  # Show first 5
                print(f"  - {k}")
            if len(keys) > 5:
                print(f"  ... and {len(keys)-5} more")
    else:
        print("\n✓ All translations found!")
    
    # Save report
    report_path = os.path.join(html_dir, 'translation_report.txt')
    with open(report_path, 'w', encoding='utf-8') as f:
        f.write("TRANSLATION AUDIT REPORT\n")
        f.write("="*60 + "\n\n")
        total_missing = sum(len(v) for v in all_missing.values())
        f.write(f"Total JSP files: {len(jsp_files)}\n")
        f.write(f"Files with missing keys: {len(all_missing)}\n")
        f.write(f"Total missing keys: {total_missing}\n\n")
        
        for file, keys in sorted(all_missing.items()):
            f.write(f"{file}:\n")
            for k in keys:
                f.write(f"  - {k}\n")
            f.write("\n")
    
    print(f"\nReport saved to: {report_path}")
    print("Done.")

if __name__ == "__main__":
    main()
