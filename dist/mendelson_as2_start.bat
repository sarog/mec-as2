@echo off
rem ******************************************
rem
rem Copyright (c) 2000-2026, mendelson-e-commerce GmbH  All Rights Reserved.
rem Please remember to install Java 17 or higher to run the mendelson AS2 server
rem
rem ******************************************

set CLASSPATH="as2.jar";"jetty10\start.jar"
call :appendJarsAndZips jlib
call :appendJarsAndZips jlib\bc
call :appendJarsAndZips jlib\caffeine
call :appendJarsAndZips jlib\dark
call :appendJarsAndZips jlib\db
call :appendJarsAndZips jlib\help
call :appendJarsAndZips jlib\httpclient
call :appendJarsAndZips jlib\jackson
call :appendJarsAndZips jlib\jpod
call :appendJarsAndZips jlib\ldap
call :appendJarsAndZips jlib\lucene
call :appendJarsAndZips jlib\mina
call :appendJarsAndZips jlib\oshi
call :appendJarsAndZips jlib\svg
call :appendJarsAndZips jetty10\lib

:RunIt
java -Xmx6144M -Xms92M -classpath %CLASSPATH% de.mendelson.comm.as2.AS2

:appendToCP
set CLASSPATH=%CLASSPATH%;%1
goto :EOF

:appendJarsAndZips
for %%D in (%1\*.jar,%1\*.zip) do call :appendToCP "%%D"
goto :EOF
