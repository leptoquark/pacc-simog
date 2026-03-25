import os
import re

def audit_codebase(base_dir):
    print(f"Auditing codebase in: {base_dir}")
    
    issues = {
        'whitespace_keys': [],
        'escaped_quotes': [],
        'missing_pacc_logo': [],
        'hardcoded_text_candidates': []
    }
    
    # Regex patterns
    whitespace_key_pattern = re.compile(r'key="\s+[^"]+"\s*|key="[^"]+\s+"')
    escaped_quote_pattern = re.compile(r'key=\\"')
    logo_pattern = re.compile(r'logo\.jpg')
    pacc_pattern = re.compile(r'pacc\.png')
    
    for root, dirs, files in os.walk(base_dir):
        for file in files:
            if file.endswith('.jsp') or file.endswith('.inc'):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
                        content = f.read()
                        
                    # Check 1: Whitespace in keys
                    if whitespace_key_pattern.search(content):
                        issues['whitespace_keys'].append(file)
                        
                    # Check 2: Escaped quotes
                    if escaped_quote_pattern.search(content):
                        issues['escaped_quotes'].append(file)
                        
                    # Check 3: Missing PACC logo
                    if logo_pattern.search(content) and not pacc_pattern.search(content):
                        issues['missing_pacc_logo'].append(file)

                    # Check 4: Hardcoded text (simple heuristic)
                    lines = content.split('\n')
                    for i, line in enumerate(lines):
                        line = line.strip()
                        if not line: continue
                        if line.startswith('<%') or line.startswith('<!--') or line.startswith('//'): continue
                        if line.startswith('<') and line.endswith('>'): continue
                        
                        if re.search(r'[a-zA-Z]{3,}', line) and 'utils:message' not in line and 'include' not in line:
                             if '<script' not in line and '</script>' not in line:
                                 issues['hardcoded_text_candidates'].append(f"{file}:{i+1}: {line[:50]}...")
                                 break 
                        
                except Exception as e:
                    print(f"Error reading {file}: {e}")

    print("\n=== Audit Results ===")
    
    print(f"\n[Whitespace in Keys] Found in {len(issues['whitespace_keys'])} files:")
    for f in issues['whitespace_keys']: print(f"  - {f}")
    
    print(f"\n[Escaped Quotes] Found in {len(issues['escaped_quotes'])} files:")
    for f in issues['escaped_quotes']: print(f"  - {f}")
    
    print(f"\n[Missing PACC Logo] Found in {len(issues['missing_pacc_logo'])} files:")
    for f in issues['missing_pacc_logo']: print(f"  - {f}")

    print(f"\n[Hardcoded Text Candidates] Found in {len(issues['hardcoded_text_candidates'])} files:")
    for f in issues['hardcoded_text_candidates']: print(f"  - {f}")

if __name__ == "__main__":
    audit_codebase(r'c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb\WebContent')
