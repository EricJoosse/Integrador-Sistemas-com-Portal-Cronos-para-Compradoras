
REM Limpar CLASSPATH :
set CLASSPATH=

set path=%path%;C:\Program Files\Java\jre1.8.0_92\bin

set CLASSPATH=%CLASSPATH%;C:\Program Files\Java\jre1.8.0_92\lib\rt.jar

set CLASSPATH=%CLASSPATH%;jersey-bundle-1.12.jar
set CLASSPATH=%CLASSPATH%;jersey-core-1.12.jar
set CLASSPATH=%CLASSPATH%;jersey-multipart-1.12.jar
REM set CLASSPATH=%CLASSPATH%;jdbc-oracle.jar
set CLASSPATH=%CLASSPATH%;.

REM @echo %classpath%

chcp 1252>nul
cd\
cd "Arquivos de Programas PC"
cd "Integração Comprador - Portal Cronos"

REM java HelloWorld >> Job15a15minOfertamentoJava.log
java Integracao_Fornecedor_PortalCronos >> Job15a15minOfertamentoJava.log

quit


