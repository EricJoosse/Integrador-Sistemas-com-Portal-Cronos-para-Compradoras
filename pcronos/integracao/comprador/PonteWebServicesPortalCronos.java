package pcronos.integracao.comprador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
  private static void upload_File(String url, File f, String formName, String username, String senha) throws FileNotFoundException 
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
        System.out.println("Response: " + clientResp.getClientResponseStatus());
 
        client.destroy();
  }

  public static void main(String[] args) throws IOException
  {    
    String enderecoBaseWebService = "http://52.10.223.6/v2/api/";
    String username = "ws-compesa";
    String senha = "123456";
    String diretorioArquivosXml = "C:/temp/PortalCronos/XML/";
    String filenameRequisicao = diretorioArquivosXml + "requisicao_85263_201604281133.xml";
    upload_File(enderecoBaseWebService + "Requisicao/PostFileRequisicao", new File(filenameRequisicao), "form1", username, senha) ;
  }
}
