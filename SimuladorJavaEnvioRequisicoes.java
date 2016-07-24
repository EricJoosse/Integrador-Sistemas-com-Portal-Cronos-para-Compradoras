import java.util.Date;
import java.util.Properties;
import java.util.Locale;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.net.URI;
import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.CharacterData;
import org.xml.sax.InputSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;



public final class SimuladorJavaEnvioRequisicoes {

  public static Locale       locale;
  public static NumberFormat nf;
  public static String       siglaSistema;
  public static String       username;
  public static String       senha;
  public static String       usernameBancoDeDados;
  public static String       senhaBancoDeDados;
  public static String       enderecoIpServidorBancoDeDados;
  public static String       portaServidorBancoDeDados;
  public static String       sidOracle;
  public static String       cnpjComprador;
  public static boolean      isWindowsServiceEmProducao;
  public static String       enderecoBaseWebService;
  public static String       diretorioArquivosXml;
  public static String       nomeArquivoXmlRequisicao;
  public static String       dsObservacaoFornecedorPadraoSeNaoTemNaBaseDeDados;
  public static boolean      toDebugar;

  static 
  {
    try {
      locale = new Locale("pt", "BR");
      nf = NumberFormat.getInstance(locale);

      Properties config = new Properties();
      config.load(new FileInputStream("SimuladorJavaEnvioRequisicoes.properties"));

   // siglaSistema = config.getProperty("SiglaSistema");
      username = config.getProperty("UsuarioWebService");
      senha = config.getProperty("SenhaWebService");
   // usernameBancoDeDados = config.getProperty("UsuarioBancoDeDados");
   // senhaBancoDeDados = config.getProperty("SenhaBancoDeDados");
   // enderecoIpServidorBancoDeDados = config.getProperty("EnderecoIpServidorBancoDeDados");
   // portaServidorBancoDeDados = config.getProperty("PortaServidorBancoDeDados");
   // sidOracle = config.getProperty("SID_Oracle");
   // cnpjComprador = config.getProperty("CnpjComprador");
   // isWindowsServiceEmProducao = Boolean.parseBoolean(config.getProperty("WindowsServiceEmProducao"));
      toDebugar = Boolean.parseBoolean(config.getProperty("Debugar"));

   // if (isWindowsServiceEmProducao)
   //   enderecoBaseWebService = config.getProperty("EnderecoBaseWebServiceProducao");
   // else
        enderecoBaseWebService = config.getProperty("EnderecoBaseWebServiceHomologacao");

      diretorioArquivosXml = config.getProperty("DiretorioArquivosXml") + "/" ;
      nomeArquivoXmlRequisicao = config.getProperty("NomeArquivoXmlRequisicao") ;
   // dsObservacaoFornecedorPadraoSeNaoTemNaBaseDeDados = config.getProperty("DsObservacaoFornecedorPadraoSeNaoTemNaBaseDeDados");

      if (toDebugar) 
      {
        System.out.println("username                       = " + username);
        System.out.println("senha                          = " + senha);
     // System.out.println("usernameBancoDeDados           = " + usernameBancoDeDados);
     // System.out.println("senhaBancoDeDados              = " + senhaBancoDeDados);
     // System.out.println("enderecoIpServidorBancoDeDados = " + enderecoIpServidorBancoDeDados);
     // System.out.println("portaServidorBancoDeDados      = " + portaServidorBancoDeDados);
     // System.out.println("sidOracle                      = " + sidOracle);
     // System.out.println("cnpjComprador                  = " + cnpjComprador);
     // System.out.println("isWindowsServiceEmProducao     = " + isWindowsServiceEmProducao);
        System.out.println("toDebugar                      = " + toDebugar);
        System.out.println("enderecoBaseWebService         = " + enderecoBaseWebService);
     // System.out.println("dsObservacaoFornecedorPadraoSeNaoTemNaBaseDeDados = " + dsObservacaoFornecedorPadraoSeNaoTemNaBaseDeDados);
      }
    } 
/*
    catch (FileNotFoundException fe)
    {
    }
    catch (IOException ie) 
    {
    }
*/
    catch (Exception ex) 
    {
       try
       {
        LogarErro(ex);
       }
       catch (Exception ex2)
       {
         throw new ExceptionInInitializerError(ex2);
       }
    }
  }


  public static String getCharacterDataFromElement(Element e) 
  {
    Node child = e.getFirstChild();
    if (child instanceof CharacterData) {
      CharacterData cd = (CharacterData) child;
      return cd.getData();
    }
    return "";
  }

/*


<Cotacoes xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<Cotacao>
 	<Dt_Gravacao>16/04/2015 14:31:35</Dt_Gravacao>
  	<Cd_Cotacao>001-0100</Cd_Cotacao>
  	<Cd_Comprador>01234567000145</Cd_Comprador>  
 	<Cd_Condicao_Pagamento>123</Cd_Condicao_Pagamento>
  	<Tp_Frete>CIF</Tp_Frete>
  	<Dt_Previsao_Entrega>21/04/2015</Dt_Previsao_Entrega>
  	<Dt_Inicio_Cotacao>16/04/2015 14:30:00</Dt_Inicio_Cotacao>
  	<Dt_Fim_Cotacao>20/04/2015 11:00:00</Dt_Fim_Cotacao>
  	<Dt_Cadastro>16/04/2015 14:29:46</Dt_Cadastro>
  	<Produtos>
    		<Produto>
      			<Cd_Produto_Fornecedor>045879</Cd_Produto_Fornecedor>
			<Cd_Produto>620</Cd_Produto>
      			<Cd_Unidade_Compra>UN</Cd_Unidade_Compra>
      			<Qt_Embalagem>12</Qt_ Embalagem>
      			<Qt_Produto>1000</Qt_Produto>
     		</Produto>
  	</Produtos>
</Cotacao>
</Cotacoes>



<?xml version="1.0" encoding="iso-8859-1"?>
<RecebimentoCotacao>
  <Cd_Cotacao>082-0239</Cd_Cotacao>
  <Cd_Comprador>06056930000143</Cd_Comprador>
  <Cd_Fornecedor>02870737000190</Cd_Fornecedor>
  <Dt_Recebimento>24/03/2016 00:05:44</Dt_Recebimento>
</RecebimentoCotacao>


<?xml version="1.0" encoding="iso-8859-1"?>
<OfertasCotacao>
  <Dt_Gravacao>24/03/2016 11:26:04</Dt_Gravacao>
  <Cd_Cotacao>082-0240</Cd_Cotacao>
  <Cd_Comprador>06056930000143</Cd_Comprador>
  <Cd_Fornecedor>02870737000190</Cd_Fornecedor>
  <Cd_Condicao_Pagamento_Fornecedor>4</Cd_Condicao_Pagamento_Fornecedor>
  <Tp_Frete_Fornecedor>CIF</Tp_Frete_Fornecedor>
  <Qt_Prz_Entrega>1</Qt_Prz_Entrega>
  <Vl_Minimo_Pedido>300,00</Vl_Minimo_Pedido>
  <Ds_Observacao_Fornecedor>Texto padrão do fornecedor para todas as cotações de todos os compradores.</Ds_Observacao_Fornecedor>
  <Produtos>
    <Produto>
      <Cd_Produto_Fornecedor>1852</Cd_Produto_Fornecedor>
      <Cd_Produto>428</Cd_Produto>
      <Qt_Embalagem>3</Qt_Embalagem>
      <Vl_Preco>33,4500</Vl_Preco>
      <Ds_Obs_Oferta_Fornecedor></Ds_Obs_Oferta_Fornecedor>
    </Produto>
  </Produtos>
  <Erros />
</OfertasCotacao>

*/

  private static void Debugar(String txt) throws IOException 
  {  
     if (toDebugar) 
     {
       BufferedWriter bWriter = new BufferedWriter(new FileWriter(diretorioArquivosXml + "Debug.log", true));
       bWriter.append(txt);
       bWriter.newLine();
       bWriter.flush();
       bWriter.close();
     }
  }
  

  private static void LogarErro(Exception ex) throws IOException 
  {  
    Date hoje = new Date();
    File file = new File(diretorioArquivosXml + "Erro - " + new SimpleDateFormat("yyyy.MM.dd - HH'h'mm.ss ").format(hoje) + ".log");
    PrintStream ps = new PrintStream(file);
    ex.printStackTrace(ps);
    ps.close();
  }
  

  private static void  PublicarErro(Document docOfertas, Element elmErros, String cdProdutoFornecedor, String mensagemErro) throws IOException 
  {
      Element elmErro = docOfertas.createElement("Erro");
      elmErros.appendChild(elmErro);

      Element elmCdProdutoFornecedorErro = docOfertas.createElement("Cd_Produto_Fornecedor");
      elmCdProdutoFornecedorErro.appendChild(docOfertas.createTextNode(cdProdutoFornecedor));
      elmErro.appendChild(elmCdProdutoFornecedorErro);

      Debugar(mensagemErro);
      Element elmMensagem = docOfertas.createElement("Mensagem");
      elmMensagem.appendChild(docOfertas.createTextNode(mensagemErro));
      elmErro.appendChild(elmMensagem);

  }





  public static void upload_File(String url, File f, String formName, String username, String senha) throws FileNotFoundException {

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
        if (toDebugar) System.out.println("Response: " + clientResp.getClientResponseStatus());
 
        client.destroy();
  }



 
/*
  public static void uploadOfertas_BodyXML(String url, String nomeArquivo, String username, String senha) {

    Client client = Client.create();
    WebResource webResource = client.resource(url);
    client.addFilter(new HTTPBasicAuthFilter(username, senha));


    // http://www.coderanch.com/t/551977/Web-Services/java/Jersey-RESTful-web-service-post

    String response = webResource.type(MediaType.APPLICATION_XML)
                                 .accept(MediaType.TEXT_PLAIN)
                                 .entity(new File(nomeArquivo))
                                 .post(String.class);
    if (toDebugar) System.out.println("response : " + response);

   }
*/

    public static void main(String[] args) throws IOException
    {    
      Date hoje = new Date();
   // String filenameRequisicao = diretorioArquivosXml + String.format("REQ_%s_%s_%s.java.xml", cnpjComprador, "XXXX", new SimpleDateFormat("yyyyMMdd'_'HHmmss").format(hoje));

      String filenameOriginal = nomeArquivoXmlRequisicao;
      String filenameRequisicao = diretorioArquivosXml + filenameOriginal;
      String filenameRenomeadoRequisicao = diretorioArquivosXml + "TesteEnviadoPorSimuladorJava." 
                                                                + new SimpleDateFormat("yyyyMMdd'_'HHmmss").format(hoje)
                                                                + "_"
                                                                + filenameOriginal;

      Path FROM = Paths.get(filenameRequisicao);           // Paths.get("C:\\Temp\\from.txt");
      Path TO   = Paths.get(filenameRenomeadoRequisicao);  // Paths.get("C:\\Temp\\to.txt");
      CopyOption[] options = new CopyOption[]{
         StandardCopyOption.REPLACE_EXISTING,
         StandardCopyOption.COPY_ATTRIBUTES
      }; 
      Files.copy(FROM, TO, options);

      upload_File(enderecoBaseWebService + "Requisicao/PostFileRequisicao", new File(filenameRenomeadoRequisicao), "form1", username, senha) ;
    }

}
