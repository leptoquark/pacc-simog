import os
import re
from pathlib import Path


BASE_DIR = Path(__file__).resolve().parents[1]
WEB_DIR = BASE_DIR / "WebContent"
IT_FILE = BASE_DIR / "src" / "messages_it.properties"
AR_FILE = BASE_DIR / "src" / "messages_ar.properties"


def load_properties(path: Path):
    data = {}
    with path.open("r", encoding="utf-8", errors="ignore") as fh:
        for raw in fh:
            line = raw.strip()
            if not line or line.startswith("#") or "=" not in line:
                continue
            key, value = line.split("=", 1)
            data[key.strip()] = value.strip()
    return data


def scan_message_keys(it_keys):
    key_re = re.compile(r'<utils:message\s+key="([^"]+)"')
    missing = []

    for path in WEB_DIR.rglob("*"):
        if path.suffix.lower() not in {".jsp", ".inc"}:
            continue
        text = path.read_text(encoding="utf-8", errors="ignore")
        for key in key_re.findall(text):
            k = key.strip()
            if k and k not in it_keys:
                missing.append((path, k))

    return missing


def scan_hardcoded_strings():
    # Basic heuristic: text content between tags in JSP/INC that looks like human text
    text_re = re.compile(r">([^<]{3,})<")
    skip_re = re.compile(
        r"^\s*(<%|<%@|<!--|</?(c|u|fmt|jsp|utils):|</?(script|style|input|img|meta|link|br)\b)",
        re.IGNORECASE,
    )

    suspects = []
    for path in WEB_DIR.rglob("*"):
        if path.suffix.lower() not in {".jsp", ".inc"}:
            continue
        lines = path.read_text(encoding="utf-8", errors="ignore").splitlines()
        for idx, line in enumerate(lines, start=1):
            s = line.strip()
            if not s or skip_re.search(s):
                continue
            if "<utils:message" in s:
                continue

            for m in text_re.finditer(line):
                txt = " ".join(m.group(1).split())
                if len(txt) < 3:
                    continue
                if not re.search(r"[A-Za-zÀ-ÿ]", txt):
                    continue
                if re.search(r"https?://|&nbsp;|\$\{|<%=|^\W+$", txt):
                    continue
                suspects.append((path, idx, txt[:180]))
    return suspects


def main():
    it_props = load_properties(IT_FILE)
    ar_props = load_properties(AR_FILE)

    missing_in_ar = sorted([k for k in it_props if k not in ar_props])
    missing_in_it = sorted([k for k in ar_props if k not in it_props])
    missing_msg_keys = scan_message_keys(set(it_props.keys()))
    hardcoded = scan_hardcoded_strings()

    def out(line):
        # Keep output robust also on cp1252 terminals.
        print(line.encode("ascii", "backslashreplace").decode("ascii"))

    out(f"IT keys: {len(it_props)}")
    out(f"AR keys: {len(ar_props)}")
    out(f"Missing in AR: {len(missing_in_ar)}")
    out(f"Extra in AR (not in IT): {len(missing_in_it)}")
    out(f"Missing utils:message keys in IT bundle: {len(missing_msg_keys)}")
    out(f"Hardcoded text suspects: {len(hardcoded)}")

    if missing_in_ar:
        out("\n--- Missing in AR ---")
        for k in missing_in_ar:
            out(k)

    if missing_msg_keys:
        out("\n--- Missing utils:message keys ---")
        for p, k in missing_msg_keys:
            out(f"{p.relative_to(BASE_DIR)} :: {k}")

    if hardcoded:
        out("\n--- Hardcoded suspects (first 300) ---")
        for p, line, txt in hardcoded[:300]:
            out(f"{p.relative_to(BASE_DIR)}:{line} :: {txt}")


if __name__ == "__main__":
    main()
