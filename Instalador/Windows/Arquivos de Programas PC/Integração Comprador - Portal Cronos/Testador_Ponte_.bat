echo %JAVA_HOME%

REM Antigo - com diretórios flexíveis:
REM "%JAVA_HOME%\bin\java -cp ponte-integracao-pcronos-2.1.0.jar pcronos.integracao.comprador.PonteWebServicesPortalCronos 'www.portalcronos.com.br:81\\v2\\api\\Requisicao\\PostFileRequisicao' ws-sjcc 123456 'c:\\temp\\req_10798130000175_243680_20200724_145856.xml' 'c:\\temp\\Ret_10798130000175_243680_20200724_145856.xml' 'c:\\temp\\Status_10798130000175_243680_20200724_145856.txt'

REM Versão atual - com único diretório fixo:
"%JAVA_HOME%\bin\java -cp ponte-integracao-pcronos-2.1.0.jar pcronos.integracao.comprador.PonteWebServicesPortalCronos 'www.portalcronos.com.br:81\\v2\\api\\Requisicao\\PostFileRequisicao' ws-sjcc 123456 'req_10798130000175_243680_20200724_145856.xml' 'Ret_10798130000175_243680_20200724_145856.xml' 'Status_10798130000175_243680_20200724_145856.txt'
