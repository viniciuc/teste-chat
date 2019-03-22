package ExercicioRabbetMQ;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
        


/**
 *
 * @author Alef Vinicius, Maryelen Moura, Rafael Reis, Samuel Hora
 * 
 */


public class MontaXml{
    
    
public static byte[] montaXml(String remetente, String msg) throws IOException, URISyntaxException, JDOMException {
        
        //Captura data e hora do sistema
        Date date = GregorianCalendar.getInstance().getTime();
		SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
        
        Element root = new Element("message");
        //Estrutura da mensagem 
        Element sender = new Element("sender");
        Element data = new Element("date");
        Element time = new Element("time"); 
        Element content = new Element("content");
        
        //adicionando conteúdo às tags
        sender.setText(remetente);
        content.setText(msg);
        data.setText(formatData.format(date));
        time.setText(formatHora.format(date));
        
        //adicionando as tags à tag message
        root.addContent(sender);
        root.addContent(data);
        root.addContent(time);
        root.addContent(content);
        
       Document doc = new Document(root);
       
       XMLOutputter xout = new XMLOutputter();

       byte[] xmlBytes = null;
       
        try {

//              xout.output(doc, System.out);                
              StringWriter elemStrWriter = new StringWriter();
              xout.output(doc, elemStrWriter);
              xmlBytes = elemStrWriter.toString().getBytes("UTF-8");
              
//              String s = new String(xmlBytes);
//              
//              System.out.println("1111");
//              System.out.println(s);
//              

//              System.out.println("2222");
//              lerXml2(xmlBytes);
              

        } catch (IOException e) {
        }
       
        
        return xmlBytes;
        
//        return doc;
   
    }

public static void exibeMensagem(byte[] xmlByte) throws JDOMException, IOException {
		
        
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new ByteArrayInputStream(xmlByte));
        
        Element root = (Element) doc.getRootElement();        
        Element message = (Element) root;
		
	//lembrem de criar as variáveis remetente, data, hora e conteudo de forma global
        String remetente = message.getChildText("sender");
        String data = message.getChildText("date");
        String hora = message.getChildText("time");
        String conteudo = message.getChildText("content");
        
        System.out.println("("+data+" às "+hora+") "+remetente+" diz: "+conteudo+" ");
        
        
}



 
public static void lerXml2(byte[] xmlBytes) throws JDOMException, IOException{
    
    SAXBuilder builder = new SAXBuilder();
    Document document = builder.build(new ByteArrayInputStream(xmlBytes));
    
    
    XMLOutputter xout = new XMLOutputter();

        try {

//                System.out.println("Document");
                xout.output(document, System.out);                
              
        }catch(Exception e){}

}

}


