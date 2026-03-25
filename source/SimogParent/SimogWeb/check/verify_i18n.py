import os
import re

def load_properties(file_path):
    properties = {}
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                if line and not line.startswith('#') and '=' in line:
                    key, value = line.split('=', 1)
                    properties[key.strip()] = value.strip()
    except Exception as e:
        print(f"Error loading {file_path}: {e}")
    return properties

def scan_jsp_for_hardcoded_text(jsp_dir, properties_keys):
    hardcoded_warnings = []
    # Regex to find text outside of tags, this is a very rough heuristic
    # It looks for text between > and < that contains letters
    text_pattern = re.compile(r'>([^<]+)<')
    
    # Regex for message tags
    msg_tag_pattern = re.compile(r'<utils:message key="([^"]+)"')

    for root, dirs, files in os.walk(jsp_dir):
        for file in files:
            if file.endswith('.jsp') or file.endswith('.inc'):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
                        content = f.read()
                        
                        # Check for used keys that don't exist
                        used_keys = msg_tag_pattern.findall(content)
                        for key in used_keys:
                            if key not in properties_keys:
                                hardcoded_warnings.append(f"MISSING KEY in {file}: {key}")

                        # Check for potential hardcoded text (simple heuristic)
                        # We skip lines that look like JSP tags or scriptlets
                        lines = content.split('\n')
                        for i, line in enumerate(lines):
                            line = line.strip()
                            if not line: continue
                            if line.startswith('<%') or line.startswith('<!--'): continue
                            
                            # If line contains Italian words and no message tag, flag it
                            # This is tricky, let's just look for common Italian words if we want to be specific
                            # or just look for significant text chunks not in tags.
                            # For now, let's stick to the missing keys check as it's more reliable.
                            
                except Exception as e:
                    print(f"Error scanning {file}: {e}")
    return hardcoded_warnings

def main():
    base_path = r"c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb\src"
    it_path = os.path.join(base_path, "messages_it.properties")
    ar_path = os.path.join(base_path, "messages_ar.properties")
    
    it_props = load_properties(it_path)
    ar_props = load_properties(ar_path)
    
    print(f"Italian keys: {len(it_props)}")
    print(f"Arabic keys: {len(ar_props)}")
    
    missing_in_ar = []
    for key in it_props:
        if key not in ar_props:
            missing_in_ar.append(key)
            
    if missing_in_ar:
        print("\nKeys missing in Arabic:")
        for key in missing_in_ar:
            print(f"- {key}")
    else:
        print("\nAll Italian keys are present in Arabic file.")

    # Check for keys in JSPs that are missing in properties
    jsp_path = r"c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb\WebContent"
    print(f"\nScanning JSPs in {jsp_path} for missing keys...")
    warnings = scan_jsp_for_hardcoded_text(jsp_path, it_props) # Use IT props as master
    
    if warnings:
        print("\nWarnings found in JSPs:")
        for w in warnings:
            print(w)
    else:
        print("No missing keys found in JSPs.")

if __name__ == "__main__":
    main()
