package pcronos.integracao.comprador;


public class RetornoWebServiceDTO {
	
	public String StatusCodeHTTP;
	public String MensagensXML;
	
	public RetornoWebServiceDTO(String statusCodeHTTP, String mensagensXML)
	{
		this.StatusCodeHTTP = statusCodeHTTP;
		this.MensagensXML = mensagensXML;
	}

}
