@echo off
chcp 65001 > nul

javac -encoding UTF-8 -cp ".;lib\gson-2.13.2.jar" -d out *.java

if %errorlevel% neq 0 (
    echo 컴파일 오류가 발생했습니다.
    pause
    exit /b
)

java -cp "out;lib\gson-2.13.2.jar" project.WYB
pause