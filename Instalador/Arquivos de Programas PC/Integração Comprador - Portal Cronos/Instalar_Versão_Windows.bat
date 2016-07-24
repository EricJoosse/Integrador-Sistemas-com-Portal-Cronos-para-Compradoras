SCHTASKS /End /TN "Integração Portal Cronos - Fornecedor"
SCHTASKS /Delete /TN "Integração Portal Cronos - Fornecedor" /F
SCHTASKS /Create /RU SYSTEM /SC MINUTE /TR "C:\Arquivos de Programas PC\Integração Fornecedor - Portal Cronos\Job15a15minOfertamentoJava_Windows.bat" /MO 20 ????? funciona, ou 15 /TN "Integração Portal Cronos - Fornecedor" ......At Startup"
SCHTASKS /Run /TN "Integração Portal Cronos - Fornecedor"
