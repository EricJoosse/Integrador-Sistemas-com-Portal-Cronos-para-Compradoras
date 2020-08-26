@echo off
echo %JAVA_HOME%

chcp 1252>nul

if not exist C:/PCronos/"Integração Comprador - Portal Cronos"/Instalador/Windows/"Arquivos de Programas PC"/"Integração Comprador - Portal Cronos"/Testador_Ponte_via_MSDOS_no_Eclipse.bat (
    echo.
    echo          Este testador serve apena para testes dentro do Eclipse! O outro arquivo Chamada_Ponte_NomeSistema.bat serve para testes nos servidores dos compradores.
    echo.
    
    echo MSGBOX "Este testador serve apena para testes dentro do Eclipse! O outro arquivo Chamada_Ponte_NomeSistema.bat serve para testes nos servidores dos compradores." > %temp%\TEMPmessage.vbs
    call %temp%\TEMPmessage.vbs
    del %temp%\TEMPmessage.vbs /f /q
    exit
) else (
  cd\
  cd "PCronos"
  cd "Integração Comprador - Portal Cronos"
  cd Deploy
)

REM Antigo - com diretórios flexíveis:
REM "%JAVA_HOME%\bin\java -cp ponte-integracao-pcronos-2.1.1.jar pcronos.integracao.comprador.PonteWebServicesPortalCronos 'www.portalcronos.com.br:81\\v2\\api\\Requisicao\\PostFileRequisicao' ws-sjcc 123456 'c:\\temp\\req_10798130000175_243680_20200724_145856.xml' 'c:\\temp\\Ret_10798130000175_243680_20200724_145856.xml' 'c:\\temp\\Status_10798130000175_243680_20200724_145856.txt' true

REM Versão atual - com único diretório fixo:
set path=C:\JRE-1.7.0_80\bin
C:/JRE-1.7.0_80/bin/java.exe -cp ponte-integracao-pcronos-2.1.1.jar pcronos.integracao.comprador.PonteWebServicesPortalCronos "http://www.portalcronos.com.br:81/v2/api/Requisicao/PostFileRequisicao" ws-sjcc 123456 req_10798130000175_243680_20200724_145856.xml Ret_10798130000175_243680_20200724_145856.xml Status_10798130000175_243680_20200724_145856.txt true

REM Descomentar o seguinte para testar ou debugar os arquivos de retorno:
echo.
type C:\ProgramData\PortalCronos\Ret_10798130000175_243680_20200724_145856.xml
type C:\ProgramData\PortalCronos\Status_10798130000175_243680_20200724_145856.txt

echo.
set path
pause
