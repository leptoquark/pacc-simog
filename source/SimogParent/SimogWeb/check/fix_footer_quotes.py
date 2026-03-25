import os

def fix_footer_quotes():
    file_path = r'c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb\WebContent\include\newfooter.inc'
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # Replace key=\" with key="
        new_content = content.replace('key=\\"', 'key="')
        
        if new_content != content:
            print(f"Fixing quotes in: {file_path}")
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(new_content)
        else:
            print("No changes needed.")
            
    except Exception as e:
        print(f"Error processing file: {e}")

if __name__ == "__main__":
    fix_footer_quotes()
