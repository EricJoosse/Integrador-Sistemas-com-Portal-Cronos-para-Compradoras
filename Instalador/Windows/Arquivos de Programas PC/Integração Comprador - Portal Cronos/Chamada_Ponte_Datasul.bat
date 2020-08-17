D:
cd\
cd totvs
cd projetos-datasul11
cd ccp0001
cd Includes

REM Antigo - com diretórios flexíveis:
REM "%JAVA_HOME%\bin\java" -cp ponte-integracao-pcronos-2.1.1.jar pcronos.integracao.comprador.PonteWebServicesPortalCronos http://www.portalcronos.com.br:81/v2/api/Requisicao/PostFileRequisicao ws-sjcc 123456 'c:\\temp\\req_10798130000175_243680_20200724_145856.xml' 'c:\\temp\\Ret_10798130000175_243680_20200724_145856.xml' 'c:\\temp\\Status_10798130000175_243680_20200724_145856.txt'
 
REM Versão atual - com único diretório fixo:
"%JAVA_HOME%\bin\java" -cp ponte-integracao-pcronos-2.1.1.jar pcronos.integracao.comprador.PonteWebServicesPortalCronos "http://www.portalcronos.com.br:81/v2/api/Requisicao/PostFileRequisicao" ws-sjcc 123456 req_10798130000175_243680_20200724_145856.xml Ret_10798130000175_243680_20200724_145856.xml Status_10798130000175_243680_20200724_145856.txt

REM Descomentar o seguinte para testar ou debugar os arquivos de retorno:
REM echo.
REM type C:\ProgramData\PortalCronos\Ret_10798130000175_243680_20200724_145856.xml
REM type C:\ProgramData\PortalCronos\Status_10798130000175_243680_20200724_145856.txt
