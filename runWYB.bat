@echo off
setlocal
cd /d "%~dp0"
chcp 65001 > nul

where javac > nul 2>&1
if errorlevel 1 (
    echo JDK not found. Install a JDK and add its bin folder to PATH.
    pause
    exit /b 1
)

javac -encoding UTF-8 -cp ".;lib\gson-2.13.2.jar" -d out *.java
if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

java -cp "out;lib\gson-2.13.2.jar" project.WYB
set "WYB_EXIT_CODE=%errorlevel%"
pause
exit /b %WYB_EXIT_CODE%
