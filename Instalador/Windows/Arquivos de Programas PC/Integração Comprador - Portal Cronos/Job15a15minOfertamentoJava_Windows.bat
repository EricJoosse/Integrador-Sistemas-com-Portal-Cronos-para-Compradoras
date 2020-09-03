
REM Limpar CLASSPATH :
set CLASSPATH=

set path=%path%;C:\Program Files\Java\jre1.8.0_92\bin

REM http://stackoverflow.com/questions/23730887/why-is-rt-jar-not-part-of-the-class-path-system-property : 
REM 	"rt.jar doesn't need to be in the classpath, since it is already in the bootclasspath. It is safe to remove it from your classpath."
REM set CLASSPATH=%CLASSPATH%;C:\Program Files\Java\jre1.8.0_92\lib\rt.jar

set CLASSPATH=%CLASSPATH%;jersey-bundle-1.12.jar
set CLASSPATH=%CLASSPATH%;jersey-core-1.12.jar
set CLASSPATH=%CLASSPATH%;jersey-multipart-1.12.jar
REM set CLASSPATH=%CLASSPATH%;jdbc-oracle.jar
set CLASSPATH=%CLASSPATH%;.

REM @echo %classpath%

REM "chcp 1252>nul" é necessário para evitar que DOS não reconhece acentos Portugueses no caminho 
REM "Integração Fornecedor - Portal Cronos" em alguns ou talvez até em todos os servidores:
chcp 1252>nul

REM Navegar para o diretório onde fica a ponte, e de lá executar java.exe:
cd\
cd "Arquivos de Programas PC"
cd "Integração Comprador - Portal Cronos"

REM java HelloWorld >> Job15a15minOfertamentoJava.log
java Integracao_Fornecedor_PortalCronos >> Job15a15minOfertamentoJava.log

quit


