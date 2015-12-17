@echo off

set classpath="%~dp0${artifactId}-${version}.jar;${dependencyClasspath}"

@REM Check for JAVA_HOME
if not "%JAVA_HOME%" == "" goto JavaHomeAvailable
goto Run

:JavaHomeAvailable
if exist "%JAVA_HOME%\bin\java.exe" goto RunAtJavaHome

echo.
echo ERROR
echo The JAVA_HOME environment variable is set to an invalid path.
echo The 'java.exe' could not be found. Please set it to a JRE or
echo JDK installation path and try again.
echo.
goto End

:RunAtJavaHome
echo Launching EventSounds
"%JAVA_HOME%\bin\java.exe" -classpath %classpath% wtf.dizzle.csgostate.eventsounds.EventSounds %*
goto End

:Run
echo Launching EventSounds
java.exe -classpath %classpath% wtf.dizzle.csgostate.eventsounds.EventSounds %*
goto End

:End