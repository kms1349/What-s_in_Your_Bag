@echo off
chcp 65001 > nul
javac -encoding UTF-8 *.java
if %errorlevel% neq 0 (
    echo 컴파일 오류가 발생했습니다.
    pause
    exit /b
)
java WYB.java
pause