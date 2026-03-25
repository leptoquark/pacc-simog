@echo off
rem Create directory for test pages
mkdir "%~dp0test_pages"

rem Copy preview HTML files, stripping the 'preview_' prefix
for %%F in (preview_*.html) do (
    set "filename=%%~nF"
    set "newname=!filename:preview_=!"
    copy "%%F" "%~dp0test_pages\!newname!.html" >nul
)

echo Test pages generated in %~dp0test_pages
pause
