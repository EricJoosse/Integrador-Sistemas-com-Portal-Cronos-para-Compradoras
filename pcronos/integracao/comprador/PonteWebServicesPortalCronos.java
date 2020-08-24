package pcronos.integracao.comprador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

//import java.time.Instant;                   // Para Java 1.8 e maior
//import java.time.ZoneId;                    // Para Java 1.8 e maior
//import java.time.format.DateTimeFormatter;  // Para Java 1.8 e maior
//import java.time.LocalDateTime;             // Para Java 1.8 e maior

import java.util.Date;             // Para Java 1.7 e menor
import java.util.Calendar;         // Para Java 1.7 e menor
import java.text.SimpleDateFormat; // Para Java 1.7 e menor


import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;



public final class PonteWebServicesPortalCronos 
{
  private static final String DIR_TEMP = "C:/ProgramData/PortalCronos/PonteJava";
  public static final String DIR_ARQS_PONTE_MSDOS = "C:/ProgramData/PortalCronos/";
  public static final int QTD_DIAS_ARQS_XML_GUARDADOS = 30;

  private static RetornoWebServiceDTO upload_File(String url, File f, String formName, String username, String senha, Boolean toDebugar) throws FileNotFoundException 
  { 
        final ClientConfig config = new DefaultClientConfig();
        final Client client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(username, senha));
 
        final WebResource resource = client.resource(url);
 
        final FormDataMultiPart multiPart = new FormDataMultiPart();
        if (f != null) 
        {
            multiPart.bodyPart(new FileDataBodyPart("file", f,
                    MediaType.APPLICATION_OCTET_STREAM_TYPE));
        }
 
        final ClientResponse clientResp = resource.type(
                MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class,
                multiPart);
        
        String respostaStatusCodeHTTP = Integer.toString(clientResp.getClientResponseStatus().getStatusCode());
        if (toDebugar) System.out.println("upload_File(): respostaStatusCodeHTTP: " + respostaStatusCodeHTTP);
        if (toDebugar) System.out.println("upload_File(): HTTP Status Code = " + respostaStatusCodeHTTP + " (" + clientResp.getClientResponseStatus().getReasonPhrase() + ")");
 
        // Neste caso JAXB seria mais trabalhoso do que SAX e DOM,
    	// então não usar um Entity :
        String respostaXML = clientResp.getEntity(String.class);
        if (toDebugar) System.out.println("upload_File(): respostaXML = " + respostaXML) ;
        
        client.destroy();
         
        return new RetornoWebServiceDTO(respostaStatusCodeHTTP, respostaXML);
  }


  
  public static RetornoWebServiceDTO uploadStringXML(String url, String stringXML, String usuario, String senha) throws IOException, FileNotFoundException
  {
	  return uploadStringXML(url, stringXML, usuario, senha, false);
  }
  
  public static RetornoWebServiceDTO uploadStringXML(String url, String stringXML, String usuario, String senha, Boolean toDebugar) throws IOException, FileNotFoundException
  { 
	  	File diretorioXML = new File(DIR_TEMP + "/");
	  	if (!diretorioXML.exists()) { 
	  		diretorioXML.mkdirs();
	  	}
	  	
	    purgeArquivosTemp();
	    
  	    // Para Java 1.7 e menor:
		SimpleDateFormat SdfFormatter = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
		Date horaEnv = new Date(System.currentTimeMillis());
		
		// Para Java 1.8 e maior:
	 // LocalDateTime horaEnv = LocalDateTime.now();
	 // DateTimeFormatter Envformatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH.mm.ss");

		String filenameRequisicao = DIR_TEMP + "/PostFile.";
		filenameRequisicao += usuario + ".";
		
		// Para Java 1.8 e maior:
	 // filenameRequisicao += horaEnv.format(Envformatter) + ".xml";

  	    // Para Java 1.7 e menor:
		filenameRequisicao += SdfFormatter.format(horaEnv) + ".xml";

	    java.io.FileWriter fw = new java.io.FileWriter(filenameRequisicao);
	    fw.write(stringXML);
	    fw.close();
	    
	    RetornoWebServiceDTO retornoWebServiceDTO = upload_File(url, new File(filenameRequisicao), "form1", usuario, senha, toDebugar) ;
	 // String respostaStatusCodeHTTP = retornoWebServiceDTO.StatusCodeHTTP;
	 // String respostaXML =  retornoWebServiceDTO.MensagensXML;
	    
	    return retornoWebServiceDTO;
	            
  }


  private static void purgeArquivosTemp()
  {
 	  // Para Java 1.8 e maior:
   // LocalDateTime horaInicio = LocalDateTime.now();

      // Para Java 1.7 e menor:
      Calendar horaInicio = Calendar.getInstance(); 

	  try
	  {
	       	   File dirTemp = new File(DIR_TEMP);
	       	   
	       	   if (!dirTemp.exists())
	       		   throw new Exception("O diretório " + DIR_TEMP + " não existe!");
	       	   
	       	   for (final File file : dirTemp.listFiles()) 
			   {
	       	 	   // Para Java 1.8 e maior:
	       		   
//				   LocalDateTime datahoraArquivo = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault()); 
//				   
//				   if (datahoraArquivo.isBefore(horaInicio.minusDays(QTD_DIAS_ARQS_XML_GUARDADOS))) 
//				   {
//				      file.delete();
//				   }



	       		   // Para Java 1.7 e menor:

	       	       Calendar datahoraArquivo = Calendar.getInstance(); 
	       	       datahoraArquivo.setTimeInMillis(file.lastModified());
	       	       horaInicio.add(Calendar.DAY_OF_MONTH, (0 - QTD_DIAS_ARQS_XML_GUARDADOS));
	       	       
				   if (datahoraArquivo.before(horaInicio)) 
				   {
				      file.delete();
				   }
			  }
	  }
	  catch (Exception ex)
	  {
		  System.out.println("purgeArquivosTemp(): Erro: " + ex.getMessage());
	  }
   }


  public static void main(String[] args) throws IOException, Exception 
  {    
//    String enderecoBaseWebService = "http://www.portalcronos.com.br:81/v2/api/";
//    String username = "ws-guaraves_matriz";
//    String senha = "123456";
//    String strXML = "<?xml version=\"1.0\" encoding=\"ISO - 8859 - 1\"?> <Requisicao>                                                                <Tp_Movimento>I</Tp_Movimento>                          <Dt_Gravacao>30/06/2020 21:16:08</Dt_Gravacao>                           <Cd_Comprador>12727145000178</Cd_Comprador>                          <Cd_Requisicao>333333</Cd_Requisicao>                         <Cd_Setor>12</Cd_Setor>                                                  <Dt_Requisicao>30/06/2020</Dt_Requisicao>                      <Cd_Usuario_Solicitante>ferreira      </Cd_Usuario_Solicitante>      <Ds_Observacao>                              </Ds_Observacao>                          <Produtos>        <Produto>                                                                   <Cd_Produto>15010628       </Cd_Produto>                         <Ds_Produto>CABO DE REDE BLUECOM                    </Ds_Produto>                         <Ds_Unidade_Compra>METRO                                   </Ds_Unidade_Compra>           <Cd_Marca></Cd_Marca>                                                        <Qtd_Solicitada>    0,500</Qtd_Solicitada>                                    <Ds_Observacao_Produto>                              </Ds_Observacao_Produto>         </Produto>      </Produtos> </Requisicao>";
//    String diretorioArquivosXml = "C:/temp/PortalCronos/XML/";
//    String filenameRequisicao = diretorioArquivosXml + "requisicao_85263_201604281133.xml";
//    
//    RetornoWebServiceDTO retornoWebServiceDTO = uploadStringXML(enderecoBaseWebService + "Requisicao/PostFileRequisicao", strXML, username, senha);
//    System.out.println(retornoWebServiceDTO.StatusCodeHTTP);
//    System.out.println(retornoWebServiceDTO.MensagensXML);
//    

	  if (args.length != 6 && args.length != 7)
		  throw new Exception("Erro! Não foram passados 6 ou 7 parâmetros!");
	  
	  String url = args[0];
	  String usuario = args[1];
	  String senha = args[2];
	  
	  // Antigo - com diretórios flexíveis:
   // String dirMaisNomeArqUploadXML = args[3];
   // String dirMaisNomeArqRetornoXML = args[4];
   // String dirMaisNomeArqRetornoStatusCodeHTTP = args[5];
    
	  // Versão atual - com um único diretório fixo:
	  String ArqUploadXML = args[3];
	  String ArqRetornoXML = args[4];
	  String ArqRetornoStatusCodeHTTP = args[5];
	  
	  Boolean toDebugar = null;
	  
	  if (args.length == 6) 
		  toDebugar = false;
	  else if (args.length == 7) 
	      toDebugar = Boolean.parseBoolean(args[6]);
	  
	  if (ArqRetornoStatusCodeHTTP.equals(ArqRetornoXML))
		  throw new Exception("O nome do arquivo de retorno do HTTP status code não pode ser igual ao nome do arquivo de retorno XML!");
	  
	  String dirMaisNomeArqUploadXML = DIR_ARQS_PONTE_MSDOS + ArqUploadXML;
	  String dirMaisNomeArqRetornoXML = DIR_ARQS_PONTE_MSDOS + ArqRetornoXML;
	  String dirMaisNomeArqRetornoStatusCodeHTTP = DIR_ARQS_PONTE_MSDOS + ArqRetornoStatusCodeHTTP;

	  File diretorioPonteMSDOS = new File(DIR_ARQS_PONTE_MSDOS);
	  if (!diretorioPonteMSDOS.exists()) { 
	   	 diretorioPonteMSDOS.mkdirs();
	  }
	  // FIM versão atual - com um único diretório fixo  
	  
	  
	  File fDirMaisNomeArqUploadXML = new File(dirMaisNomeArqUploadXML);
	  if (!fDirMaisNomeArqUploadXML.exists())
		  throw new Exception("O arquivo " + ArqUploadXML + " para upload para o Portal Cronos não existe!");
	  
	  
	  File fDirMaisNomeArqRetornoXML = new File(dirMaisNomeArqRetornoXML);
	  if (fDirMaisNomeArqRetornoXML.exists())
		  throw new Exception("O arquivo de retorno XML " + ArqRetornoXML + " já existe! Nunca pode ser usado um arquivo já existente! (para evitar interferência indevida de requisições diferentes)");

	  
	  File fDirMaisNomeArqRetornoStatusCodeHTTP = new File(dirMaisNomeArqRetornoStatusCodeHTTP);
	  if (fDirMaisNomeArqRetornoStatusCodeHTTP.exists())
		  throw new Exception("O arquivo de retorno de HTTP status code " + ArqRetornoStatusCodeHTTP + " já existe! Nunca pode ser usado um arquivo já existente! (para evitar interferência indevida de requisições diferentes)");
	  
	  
	  String strXML = new String(Files.readAllBytes(Paths.get(dirMaisNomeArqUploadXML)), Charset.forName("ISO-8859-1"));
	  
   	  RetornoWebServiceDTO retornoWebServiceDTO = uploadStringXML(url, strXML, usuario, senha, toDebugar); 

      // O seguinte cria o arquivo fisicamente se não existir um arquivo com este nome.
   	  // Parâmetro "append = "true" é uma segurança extra para detectar eventuais interferências indevidas de requisições diferentes: 
      FileWriter fwXML = new FileWriter(dirMaisNomeArqRetornoXML, true); 
      fwXML.write(retornoWebServiceDTO.MensagensXML);
      fwXML.close();
    
      // O seguinte cria o arquivo fisicamente se não existir um arquivo com este nome.
   	  // Parâmetro "append = "true" é uma segurança extra para detectar eventuais interferências indevidas de requisições diferentes: 
      FileWriter fwHTTP = new FileWriter(dirMaisNomeArqRetornoStatusCodeHTTP, true);
      fwHTTP.write(retornoWebServiceDTO.StatusCodeHTTP);
      fwHTTP.close();
    
	  
	  // upload_File(enderecoBaseWebService + "Requisicao/PostFileRequisicao", new File(filenameRequisicao), "form1", username, senha) ;
  }
}
