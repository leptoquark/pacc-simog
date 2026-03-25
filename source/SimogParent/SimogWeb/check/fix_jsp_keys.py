import os
import re

def fix_jsp_keys(directory):
    count = 0
    # Regex to find <utils:message key="..." /> with potential whitespace
    # We want to capture the whole tag to replace it, or just the key part.
    # Replacing just the key part is safer.
    # Pattern: key="\s*([^"]+?)\s*"
    pattern = re.compile(r'key="\s*([^"]+?)\s*"')
    
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.jsp') or file.endswith('.inc'):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                    
                    new_content = pattern.sub(r'key="\1"', content)
                    
                    if new_content != content:
                        print(f"Fixing keys in: {file}")
                        with open(file_path, 'w', encoding='utf-8') as f:
                            f.write(new_content)
                        count += 1
                except Exception as e:
                    print(f"Error processing {file}: {e}")
    
    print(f"Finished. Fixed keys in {count} files.")

if __name__ == "__main__":
    base_dir = r'c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb\WebContent'
    fix_jsp_keys(base_dir)
