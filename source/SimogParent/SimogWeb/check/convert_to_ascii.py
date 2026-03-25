import sys

def escape_unicode(text):
    result = []
    for char in text:
        if ord(char) > 127:
            result.append('\\u{:04x}'.format(ord(char)))
        else:
            result.append(char)
    return ''.join(result)

def convert_file(input_path):
    try:
        with open(input_path, 'r', encoding='utf-8') as f:
            lines = f.readlines()
        
        with open(input_path, 'w', encoding='ascii') as f:
            for line in lines:
                # Split key and value to avoid escaping the key if strictly ascii (though standard properties allow it)
                # But simple iteration is safer for full file
                f.write(escape_unicode(line))
        
        print(f"Successfully converted {input_path} to ASCII with Unicode escapes.")
    except Exception as e:
        print(f"Error converting file: {e}")
        sys.exit(1)

if __name__ == "__main__":
    convert_file(r"c:\omnia\java-workspace\simog\simog_all\SIMOG_WEB\SimogParent\SimogWeb\src\messages_ar.properties")
