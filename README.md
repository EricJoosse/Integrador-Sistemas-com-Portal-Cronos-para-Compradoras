# Integrador de Sistemas com o Portal Cronos para setores de Compras

O Portal Cronos é um sistema web para automatizar cotações, ofertas, e ordens de compras.

O Portal oferece integração automatizada de cotações, ofertas, ordens de compras, etc, com qualquer outro sistema.

__A integração tem as seguintes duas partes :__

1. A parte pública (OpenSource)  : o lado do sistema que integra com o Portal  Cronos. 
   Esta parte se encontra aqui no GitHub.
2. A parte privada da integração : o lado do Portal Cronos.
   Esta parte se encontra no BitBucket.
   
Os seguintes sistemas já foram integrados com o Portal Cronos : 
* SAP	     
* APS	     
* WinThor   
* IFS       
* 3LM       
* MVarandas
* Datasul (em andamento)

Este projeto é independente do IDE (Eclipse, Netbeans, etc.), porém usa Maven. 
Se preferir não usar Maven, veja as versões de cada dependência externa no arquivo pom.xml.

Além de integração completa este projeto contem também uma ponte Java que é um complemento para sistemas
que usam linguagens de programação incompletas que não conseguem fazer upload de arquivos, 
como por exemplo o Datasul que usa a linguagem POSTGRE.  
