@echo off
setlocal enabledelayedexpansion

echo ============================================================
echo   MetroCare Online Healthcare Management System
echo   Starting Java Web Server with JDBC and Embedded H2 DB
echo ============================================================
echo.

:: 1. Detect Java JDK
if defined JAVA_HOME (
    echo [*] Using JAVA_HOME: %JAVA_HOME%
    set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
) else (
    where java >nul 2>nul
    if !errorlevel! equ 0 (
        echo [*] Using system java from PATH
        set "JAVA_EXE=java"
    ) else if exist "C:\Program Files\Android\Android Studio\jbr\bin\java.exe" (
        echo [*] Found Java JDK in Android Studio directory
        set "JAVA_HOME=C:\Program Files\Android\Android Studio\jbr"
        set "JAVA_EXE=C:\Program Files\Android\Android Studio\jbr\bin\java.exe"
        set "PATH=%JAVA_HOME%\bin;%PATH%"
    ) else (
        echo [!] Java JDK 11+ was not found on your system.
        echo Please install JDK 11 or higher from: https://adoptium.net/
        pause
        exit /b 1
    )
)

:: 2. Detect or download Maven
where mvn >nul 2>nul
if !errorlevel! equ 0 (
    echo [*] Found system Maven
    set "MVN_CMD=mvn"
) else (
    set "MAVEN_DIR=%~dp0.tools\apache-maven-3.9.6"
    set "MVN_CMD=!MAVEN_DIR!\bin\mvn.cmd"
    if not exist "!MVN_CMD!" (
        echo [*] Maven not found. Downloading portable Apache Maven...
        powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $zip = '%~dp0.tools\maven.zip'; New-Item -ItemType Directory -Force -Path '%~dp0.tools' | Out-Null; Write-Host 'Downloading Apache Maven 3.9.6...'; Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip' -OutFile $zip; Write-Host 'Extracting Maven...'; Expand-Archive -Path $zip -DestinationPath '%~dp0.tools' -Force; Remove-Item $zip -Force; Write-Host 'Maven setup complete!'"
    )
)

if not exist "%MVN_CMD%" (
    where mvn >nul 2>nul
    if !errorlevel! neq 0 (
        echo [!] Could not locate or download Maven automatically.
        echo Please install Maven from https://maven.apache.org/download.cgi and add it to PATH.
        pause
        exit /b 1
    )
)

echo.
echo [*] Starting Tomcat embedded server on http://localhost:8080/ ...
echo [*] Database mode: Embedded H2 (Tables and sample data auto-load on start)
echo.
echo ------------------------------------------------------------
echo Once you see 'INFO: Starting ProtocolHandler', open:
echo 👉 http://localhost:8080/
echo ------------------------------------------------------------
echo.

call "%MVN_CMD%" clean tomcat7:run
pause
