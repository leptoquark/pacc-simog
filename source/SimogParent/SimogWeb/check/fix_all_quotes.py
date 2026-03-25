import os

def fix_all_quotes(directory):
    count = 0
    for root, dirs, files in os.walk(directory):
        for file in files:
            if file.endswith('.jsp') or file.endswith('.inc'):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                    
                    # Replace key=\" with key="
                    new_content = content.replace('key=\\"', 'key="')
                    
                    if new_content != content:
                        print(f"Fixing quotes in: {file}")
                        with open(file_path, 'w', encoding='utf-8') as f:
                            f.write(new_content)
                        count += 1
                except Exception as e:
                    print(f"Error processing {file}: {e}")
    
    print(f"Finished. Fixed quotes in {count} files.")

if __name__ == "__main__":
    base_dir = r'c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb\WebContent'
    fix_all_quotes(base_dir)
