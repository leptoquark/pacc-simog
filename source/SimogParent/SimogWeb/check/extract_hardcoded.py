#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script to extract hardcoded text from JSP files and generate i18n keys.
For use in batch translation of high-priority pages.
"""
import re
import os

# Files to process (relative to WebContent)
HIGH_PRIORITY_FILES = [
    'homeRUP.jsp',
    'homeRSSA.jsp',
    'homeAVCP.jsp',
    'homeADMIN.jsp',
    'homeOSR.jsp'
]

WEB_CONTENT_DIR = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), 'WebContent')

def extract_hardcoded_text(file_path):
    """Extract hardcoded Italian text from JSP file."""
    with open(file_path, 'r', encoding='utf-8', errors='ignore') as f:
        content = f.read()
    
    # Find text between HTML tags that doesn't contain JSP/JSTL tags
    # This is a rough heuristic - will need manual review
    pattern = r'>\s*([A-Za-zÀ-ÿ\s]{10,})\s*<'
    matches = re.findall(pattern, content)
    
    return [m.strip() for m in matches if m.strip() and not any(x in m for x in ['<%', '%>', 'utils:message'])]

if __name__ == "__main__":
    for filename in HIGH_PRIORITY_FILES:
        file_path = os.path.join(WEB_CONTENT_DIR, filename)
        if os.path.exists(file_path):
            print(f"\n=== {filename} ===")
            texts = extract_hardcoded_text(file_path)
            for i, text in enumerate(texts[:5]):  # Limit to first 5
                print(f"{i+1}. {text[:80]}...")
        else:
            print(f"File not found: {filename}")
